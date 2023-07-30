package com.stc.demo.services;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stc.demo.entity.File;
import com.stc.demo.entity.Item;
import com.stc.demo.entity.Permissions;
import com.stc.demo.exception.AccessException;
import com.stc.demo.exception.FolderAleardyExistException;
import com.stc.demo.exception.PathNotFoundException;
import com.stc.demo.exception.SpaceAleardyExistException;
import com.stc.demo.model.CreateItemRequest;
import com.stc.demo.repository.FileRepository;
import com.stc.demo.repository.ItemRepository;
import com.stc.demo.repository.PermissionGroupRepository;
import com.stc.demo.repository.PermissionsRepository;
import com.stc.demo.util.ItemTypeEnum;
import com.stc.demo.util.PermissionLevelEnnum;

@Service
public class ItemServices {
	@Autowired
	PermissionGroupRepository pgrepo;

	@Autowired
	PermissionsRepository prepo;

	@Autowired
	ItemRepository irepo;

	@Autowired
	FileRepository frepo;

	public Item createSpace(CreateItemRequest request) throws SpaceAleardyExistException {

		List<Item> items = irepo.getPath(preparePath(request.getName(), null).toString(), ItemTypeEnum.Space.name(),request.getName());
		if (!items.isEmpty())
			throw new SpaceAleardyExistException("Space Already Exist");
		return saveItem(request.getName(), null, ItemTypeEnum.Space.name(), request.getGropId(), null);
	}

	public Item createFolder(CreateItemRequest request)
			throws AccessException, PathNotFoundException, FolderAleardyExistException {
		Pair<Item, String> itemPath = getFolderOrFile(ItemTypeEnum.Folder.name(), request.getPath(), request.getName(),
				request.getUserEmail(), true,PermissionLevelEnnum.EDIT.name());
		Item pItem = itemPath.getLeft();
		return saveItem(itemPath.getRight(), request.getName(), ItemTypeEnum.Folder.name(), request.getGropId(),
				pItem.getId());
	}

	public Item createFile(MultipartFile file, String path, String email)
			throws PathNotFoundException, FolderAleardyExistException, AccessException, IOException {
		Pair<Item, String> itemPath = getFolderOrFile(ItemTypeEnum.File.name(), path, file.getOriginalFilename(), email,
				true,PermissionLevelEnnum.EDIT.name());
		Item pItem = itemPath.getLeft();
		Item item = saveItem(itemPath.getRight(), file.getOriginalFilename(), ItemTypeEnum.File.name(),
				pItem.getGroup().getId(), pItem.getId());
		frepo.save(File.builder().data(file.getBytes()).item(item).build());
		return item;
	}

	public Item getFileMetaData(String path, String email)
			throws AccessException, PathNotFoundException, FolderAleardyExistException {
		Pair<Item, String> itemPath = getFolderOrFile(ItemTypeEnum.File.name(), path, null, email, false,PermissionLevelEnnum.VIEW.name());
		return itemPath.getLeft();
	}

	public Pair<byte[], String> getFile(String path, String email)
			throws AccessException, PathNotFoundException, FolderAleardyExistException {
		Pair<Item, String> itemPath = getFolderOrFile(ItemTypeEnum.File.name(), path, null, email, false,PermissionLevelEnnum.VIEW.name());
		Item item = itemPath.getLeft();
		File fileBinary = frepo.findByItem(item);
		return Pair.of(fileBinary.getData(), item.getName());
	}

	public Item saveItem(String itemPath, String itemName, String itemType, Long groupId, Long pid) {
		StringBuilder path = preparePath(itemPath, itemName);
		long id = new Date().getTime();
		irepo.save(id, path.toString(),
				Optional.ofNullable(itemName).orElse(path.toString().replace("{", "").replace("}", "")), itemType,
				groupId, pid);
		return irepo.findById(id).orElse(null);
	}

	public StringBuilder preparePath(String path, String appendPath) {
		StringBuilder pathBuilder = new StringBuilder();
		pathBuilder.append("{").append(path.replace("-", ""));
		Optional.ofNullable(appendPath).ifPresent(s -> pathBuilder.append(".").append(s));
		pathBuilder.append("}");
		return pathBuilder;
	}

	public List<Item> getItemBypath(String itemPath, String appendPath, String type,String name) {
		if (type == null)
			return irepo.getPathNoType(preparePath(itemPath, appendPath).toString());
		return irepo.getPath(preparePath(itemPath, appendPath).toString(), type,name);
	}

	public Pair<Item, String> getFolderOrFile(String type, String itemPath, String name, String email,
			boolean checkExist, String permissionLevel)
			throws PathNotFoundException, FolderAleardyExistException, AccessException {

		String namePath = itemPath.substring(itemPath.lastIndexOf("/"));
		String parentType = itemPath.split("/").length > 1 ? ItemTypeEnum.Folder.name() : ItemTypeEnum.Space.name();
		String path = itemPath.replace("/", ".");
		List<Item> items = getItemBypath(path, null, name == null ? type : parentType,name==null?namePath:name);
		if (items == null || items.isEmpty())
			throw new PathNotFoundException("the path of  folder Or File is not found");
		if (checkExist && !getItemBypath(path, name, type,name).isEmpty())
			throw new FolderAleardyExistException("the folder OR File already exist in same path");
		List<Permissions> p = prepo.getP(email, items.get(0).getGroup());
		if (p == null || p.isEmpty() || !checkPermission(p,permissionLevel))
			throw new AccessException("You do not have permission to add folder");
		return Pair.of(items.get(0), path);
	}

	public boolean checkPermission(List<Permissions> permissions, String level) {
		return Optional.ofNullable(permissions).orElse(Collections.emptyList()).stream()
				.anyMatch(value -> level.equals(value.getLevel()));
	}
}

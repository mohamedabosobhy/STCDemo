package com.stc.demo.controller;

import com.stc.demo.exception.AccessException;
import com.stc.demo.exception.FolderAleardyExistException;
import com.stc.demo.exception.PathNotFoundException;
import com.stc.demo.services.ItemServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.stc.demo.entity.Item;

@Controller
public class GraphqlController {

	@Autowired
	ItemServices itemServices;

	@QueryMapping
	public Item fileMetaData(@Argument String path, @Argument String email)
			throws AccessException, PathNotFoundException, FolderAleardyExistException {
		return itemServices.getFileMetaData(path, email);
	}

}

package com.stc.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stc.demo.entity.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO item (id,name, path,type,group_id,parent_id) values (:id,:name, CAST(:path AS ltree[]),:type,:gid ,:pid)", nativeQuery = true)
    void save(@Param("id") Long id,@Param("path") String path, @Param("name") String name,@Param("type") String type,@Param("gid") Long groupId,@Param("pid") Long pid);

    @Transactional
    @Query(value="select * from item where path=CAST(:path AS ltree[]) and type=:type and name=:name ",nativeQuery = true)
   List <Item> getPath(@Param("path")String path,@Param("type")String type,@Param("name")String name);

    @Transactional
    @Query(value="select * from item where path=CAST(:path AS ltree[])",nativeQuery = true)
    List <Item> getPathNoType(@Param("path")String path);

    @Query
    List<Item> getItemByName(String name);

    
}
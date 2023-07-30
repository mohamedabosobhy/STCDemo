package com.stc.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.stc.demo.entity.File;
import com.stc.demo.entity.Item;


public interface FileRepository extends JpaRepository<File, Long> {
	 @Transactional
	File findByItem(Item item);
    
}
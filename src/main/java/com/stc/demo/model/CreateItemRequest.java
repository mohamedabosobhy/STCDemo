package com.stc.demo.model;

import lombok.Data;

@Data

public class CreateItemRequest {
 String name;
 long parentId;
 long gropId;
 String userEmail;
 String path;
	
}

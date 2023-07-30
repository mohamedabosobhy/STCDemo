package com.stc.demo.config;

import com.stc.demo.entity.Item;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class PathStringSerializer extends JsonSerializer<Item> {
    @Override
    public void serialize(Item item, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        gen.writeStartObject();
        gen.writeNumberField("id",item.getId());
        gen.writeStringField("name",item.getName() );
        gen.writeStringField("type",item.getType() );
        gen.writeStringField("group",item.getGroup().getGroup_name() );
        StringBuilder bb = preparePath(item,new StringBuilder());
        gen.writeStringField("path", bb.deleteCharAt(bb.lastIndexOf("/")).toString());
        gen.writeEndObject();
    }
    public StringBuilder preparePath(Item item,StringBuilder path)
    {
        if(item.getParentId()==null) {
            return path.append(item.getName()).append("/");
        }
        else return preparePath(item.getParentId(),path).append(item.getName()).append("/");
    }


}

package com.stc.demo.entity;

import com.stc.demo.config.PathStringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;


import javax.persistence.*;


@Entity
@Table(name = "item")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonSerialize(using = PathStringSerializer.class)
public class Item  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    @JsonIgnore
    private PermissionGroup group;
    
    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "parent_id", nullable = true)
    @JsonIgnore
    private Item parentId;
    @Column(name = "path", columnDefinition = "ltree[]")
    private String path;

    
}

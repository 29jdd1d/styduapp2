package com.study.exam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "resource_category")
public class ResourceCategory extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "type")
    private String type; // POLITICS, ENGLISH, MATH, PROFESSIONAL

    @Column(name = "sort_order")
    private Integer sortOrder = 0;
}

package com.app.service.client.model;

import com.app.service.client.model.base.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "Tên danh mục ")
    private String name;

    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "code")
    @NotEmpty(message = "Mã danh mục ")
    private String code;
    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Integer status;
    @Column(name = "parent_id")
    private Long parentId;
}

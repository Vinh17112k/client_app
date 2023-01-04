package com.app.service.client.model;

import com.app.service.client.model.base.BaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="role")
public class Role extends BaseEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_id")
  private Long roleId;
  @NotNull(message = "Mã quyền")
  @Column(name = "code")
  private String code;
  @NotNull(message = "Tên quyền")
  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;
  @Column(name = "status")
  private Short status;
  @Column(name = "group_no")
  private Long groupNo;
}

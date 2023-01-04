package com.app.service.client.model.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public abstract class BaseEntity implements Serializable {

  @CreatedBy
  @Column(name = "created_by", length = 255, updatable = false)
  @JsonIgnore
  protected String createdBy;

  @CreatedDate
  @Column(name = "created_date", updatable = false)
  @JsonIgnore
  protected LocalDateTime createdDate = LocalDateTime.now();

  @LastModifiedBy
  @Column(name = "last_modified_by")
  @JsonIgnore
  protected String lastModifiedBy;
  @NotNull
  @Column(name = "is_deleted")
  private Boolean isDeleted = Boolean.FALSE;
  @LastModifiedDate
  @Column(name = "last_modified_date")
  @JsonIgnore
  protected LocalDateTime lastModifiedDate = LocalDateTime.now();
}

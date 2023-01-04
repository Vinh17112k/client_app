package com.app.service.client.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "ward")
public class Ward {
    @Id
    @Column(name = "ward_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wardId;
    @Column(name = "district_id")
    private Long DistrictID;
    @Column(name = "ward_code")
    private String WardCode;

    @Column(name = "ward_name")
    private String WardName;
    @Column(name = "status")
    private Integer Status = 1;
}

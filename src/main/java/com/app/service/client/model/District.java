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
@Table(name = "district")
public class District {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "district_id")
    private Long DistrictID;
    @Column(name = "province_id")
    private Long ProvinceID;
    @Column(name = "district_name")
    private String DistrictName;

    @Column(name = "country_id")
    private Long CountryID;

    @Column(name = "code")
    private String Code;
    @Column(name = "support_type")
    private String SupportType;
    @Column(name = "type")
    private String Type;
    @Column(name = "status")
    private Integer Status = 1;
}

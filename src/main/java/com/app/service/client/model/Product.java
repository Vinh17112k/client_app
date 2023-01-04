package com.app.service.client.model;

import com.app.service.client.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
@Table(name = "product")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "make_id", nullable = true, referencedColumnName = "make_id")
    private Make make;
    @NotEmpty(message = "Mã sản phẩm")
    @Column(name = "code")
    private String code;

    @NotEmpty(message = "Tên sản phẩm")
    @Column(name = "name")
    private String name;
    //Year
    @Column(name = "year")
    private String year;

    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "weight")
    @NotNull(message = "Khối lượng sản phẩm")
    private Integer weight;
    @Column(name = "expired_date")
    private LocalDateTime expiredDate;
    @Column(name = "price")
    private Float price;
    @Column(name = "our_price")
    private float ourPrice;
    @Column(name = "status")
    private Integer status;
    @Column(name = "stock_qty")
    private Integer stockQty;
    @Column(name = "original_country")
    private String originalCountry;
    @Column(name = "operation_system")
    private String operationSystem;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "product_type_id", referencedColumnName = "product_type_id")
    private ProductType productType;

    private Integer coreQuantity;
    private String processSpeed;
    private String ram;
    private String card;
    private String screenSize;
    private String screenHd;
    private String hardDisk;
    private String pin;
}

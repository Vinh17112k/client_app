package com.app.service.client.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    //------------ Mapped Column -----------//
    @ManyToOne(fetch = FetchType.LAZY)
//
    @JsonIgnore
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @Column(name ="order_date")
    private LocalDateTime orderDate;

    @Column(name ="delivery_date")
    private LocalDateTime deliveryDate;

    @Column(name = "sub_total")
    private Float subTotal;

    @Column(name = "shipping_total")
    private Long shippingTotal;

    //Default 5 %
    @Column(name = "tax_rate")
    private Float taxRate;


    @Column(name = "tax_total")
    private Float taxTotal;

    @Column(name = "grand_total")
    private Float grandTotal;
    @Column(name = "status")
    // 0 dang cho default, 1 da duyet boi client, 2 huy boi khach hang 3 huy boi client
    private Integer status = 0;
    @Column(name = "shipping_method")
    private String shippingMethod;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;

    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @JsonIgnore
    private List<OrderDetail> orderDetailList;
    @Column(name = "payment_id")
    private Long paymentId;
    @Column(name = "shipment_id")
    private Long shipmentId;
    @Column(name = "is_payment")
    private Boolean isPayment = false;
}

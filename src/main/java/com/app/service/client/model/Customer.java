package com.app.service.client.model;

/*
    Created By: noman azeem
    Contact: syed.noman.azeem@gmail.com
*/

import com.app.service.client.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//import org.hibernate.validator.constraints.Length;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class Customer extends BaseEntity implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "username")
    private String username;
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birthday")
    private String birthday;
    @Column(name = "password")
    private String password;
    @Column(name = "password_raw")
    private String passwordRaw;

    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "status")
    private Integer status;
    @Column(name = "gender")
    private Integer gender;
    private Long countryId;
    private String wardCode;
    @Column(name = "verify_code")
    private String verifyCode;
    @Column(name = "code_expiration_time")
    private Long codeExpirationTime;
    @OneToOne(mappedBy = "customer")
    private Cart shoppingCart;
    private String avatarUser;
//    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;
    private String providerId;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "customer_roles",
        joinColumns = @JoinColumn(
            name = "customer_id", referencedColumnName = "customer_id"),
        inverseJoinColumns = @JoinColumn(
            name = "role_id", referencedColumnName = "role_id"))
    private Collection<Role> roles;

    @Override
    public int hashCode() {
        return 42;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return status != null ? status == 1 : false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status != null ? status == 1 : false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status != null ? status == 1 : false;
    }

    @Override
    public boolean isEnabled() {
        return status != null ? status == 1 : false;
    }
}
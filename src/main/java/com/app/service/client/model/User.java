package com.app.service.client.model;

import com.app.service.client.model.base.BaseEntity;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseEntity implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "username")
    private String username;

    @Column(name = "dept_id")
    private Long deptId;
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birthday")
    private String birthday;
    @Column(name = "password")
    private String password;
    @Column(name = "password_raw")
    private String passwordRaw;
    private Integer gender;
    private String code;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "issue_by")
    private String issueBy;
    private Long roleId;
    @Column(name = "issue_date")
    private String issueDate;

    @Column(name = "status")
    private Integer status;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "role_id"))
    private Collection<Role> roles;
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

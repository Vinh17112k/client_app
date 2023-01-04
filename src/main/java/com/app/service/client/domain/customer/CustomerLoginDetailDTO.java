package com.app.service.client.domain.customer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoginDetailDTO implements Serializable {
    protected static final int DEFAULT_PAGE = 0;
    protected static final int DEFAULT_PAGE_SIZE = 15;
    /**
     * The Constant serialVersionUID.
     */

    /**
     * The Constant serialVersionUID.
     */

    /**
     * The user id.
     */
    private Integer userId;

    /**
     * The tenant id.
     */
    private Integer tenantId;

    /**
     * The enterprise id.
     */
    private Integer enterpriseId;

    /**
     * The permissions.
     */
    private List<Integer> permissions;

    /**
     * The expired date.
     */
    private Date expiredDate;

    /**
     * The user type.
     */
    private Integer userType;

    /**
     * The isParent.
     */
    private Boolean isParent;

    /**
     * not common user (internal system - true)
     */
    private Boolean isNotHuman;

    /**
     * allowed ip addr
     */
    private String ipAddr;

    private String cpCode;     // internal system code

    /**
     * The service type permissions.
     */
    private List<Integer> serviceTypePermission;

    private boolean isVerify;
}

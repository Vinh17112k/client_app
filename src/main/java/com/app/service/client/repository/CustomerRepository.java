package com.app.service.client.repository;

import com.app.service.client.model.Customer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Cacheable
    Optional<Customer> findByUsernameAndIsDeletedFalse(String username);

    @Cacheable
    Optional<Customer> findByUsername(String username);
    @Query(value = "select * from customer where username =:userName", nativeQuery = true)
    Customer findByUserName(@Param("userName") String userName);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByUsernameAndCustomerIdNotIn(String username, List<Long> userId);

    boolean existsByEmailAndCustomerIdNotIn(String email, List<Long> userId);

    boolean existsByPhoneAndCustomerIdNotIn(String phone, List<Long> userId);
    Customer findCustomerByEmail(String email);

    @Query(value = "select u.username from customer u where (:userName is null or u.username like concat('%',:userName,'%') ) and u.is_deleted = 0 ", nativeQuery = true)
    List<String> getAllUserName(@Param("userName") String userName);

    @Query(value = "select u.code from customer u where (:userCode is null or u.code like concat('%',:userCode,'%') ) and u.is_deleted = 0 ", nativeQuery = true)
    List<String> getAllUserCode(@Param("userCode") String userCode);

    @Query(value = "select u.email from customer u where (:userEmail is null or u.email like concat('%',:userEmail,'%') ) and u.is_deleted = 0 ", nativeQuery = true)
    List<String> getAllUserEmail(@Param("userEmail") String userEmail);

    @Query(value = "select u.phone from customer u where (:phone is null or u.phone like concat('%',:phone,'%') ) and u.is_deleted = 0 ", nativeQuery = true)
    List<String> getAllUserPhone(@Param("phone") String phone);

    @Query(value = "select u.full_name from customer u where (:fullName is null or u.full_name like concat('%',:fullName,'%') ) and u.is_deleted = 0 ", nativeQuery = true)
    List<String> getAllUserFullName(@Param("fullName") String fullName);

    @Modifying
    @Query(value = "INSERT INTO customer "
        + "(created_by,is_deleted, "
        + " birthday, country_id, email,full_name,"
        + "password,password_raw,phone,status,username, created_date, last_modified_date )"
        + " value  "
        + " (:createdBy, :isDeleted, :birthDay, :countryId, :email, :fullName, :password, :passwordRaw, :phone, :status, :userName, current_timestamp,current_timestamp)", nativeQuery = true)
    public int insertCustomerNative(@Param("createdBy") String createdBy, @Param("isDeleted") Boolean isDeleted,
        @Param("birthDay") String birthDay, @Param("countryId") Long countryId,
        @Param("email") String email, @Param("fullName") String fullName,
        @Param("password") String password, @Param("passwordRaw") String passwordRaw,
        @Param("phone") String phone, @Param("status") Integer status, @Param("userName") String userName
    );
    @Query(value = "select c.* from customer c join orders o on o.customer_id = c.customer_id where c.is_deleted = 0 and (:keySearch is null or c.username like lower(concat('%',:keySearch,'%'))) ", nativeQuery = true)
    List<Customer> getCustomerName(@Param("keySearch") String keySearch);
    @Query(value = "select c.username from customer c where c.is_deleted = 0 and c.customer_id =:customerId ", nativeQuery = true)
    String customerName(@Param("customerId") Long customerId);
    @Query(value = "select c.* from customer c where c.is_deleted = 0 and c.customer_id =:customerId ", nativeQuery = true)
    Customer getCustomer(@Param("customerId") Long customerId);
    Optional<Customer> findByEmail(String email);
    @Query(value = "select c.* from customer c join message m on c.customer_id = m.customer_id and m.user_id =:userId", nativeQuery = true)
    List<Customer> getAllCustomerChat(@Param("userId") Long userId);
}
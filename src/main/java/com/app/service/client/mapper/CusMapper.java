package com.app.service.client.mapper;

import com.app.service.client.domain.customer.CustomerCreatorDTO;
import com.app.service.client.domain.customer.CustomerDTO;
import com.app.service.client.model.Customer;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface CusMapper {
    CusMapper INSTANCE = Mappers.getMapper(CusMapper.class);

    Customer toEntity(CustomerDTO userDTO);

    Customer toEntity(CustomerCreatorDTO userCreatorDTO);

    CustomerDTO toDto(Customer user);

    List<CustomerDTO> toDto(List<Customer> user);

    Set<CustomerDTO> toDto(Set<Customer> user);
}

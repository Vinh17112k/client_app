package com.app.service.client.mapper;

import com.app.service.client.domain.productType.ProductTypeDTO;
import com.app.service.client.model.ProductType;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface ProductTypeMapClient {

    ProductTypeMapClient INSTANCE = Mappers.getMapper(
        ProductTypeMapClient.class);

    ProductType DtoToEntity(ProductTypeDTO productDTO);
    ProductType DtoEditToEntity(ProductTypeDTO productDTO);
    ProductTypeDTO EntityToDto(ProductType product);
    List<ProductTypeDTO> listModelToList(List<ProductType> model);

}
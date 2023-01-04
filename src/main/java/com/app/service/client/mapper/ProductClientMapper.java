package com.app.service.client.mapper;

import com.app.service.client.domain.product.ProductAddRequestDTO;
import com.app.service.client.domain.product.ProductDTO;
import com.app.service.client.domain.product.ProductEditRequestDTO;
import com.app.service.client.model.Product;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface ProductClientMapper {

  ProductClientMapper INSTANCE = Mappers.getMapper(ProductClientMapper.class);
  Product DtoToEntity(ProductAddRequestDTO productDTO);
  Product DtoEditToEntity(ProductEditRequestDTO productDTO);
  ProductDTO EntityToDto(Product product);

  List<ProductDTO> toDto(List<Product> products);

  Set<ProductDTO> toDto(Set<Product> products);
  List<ProductDTO> toList(List<Product> products);

}

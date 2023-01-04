package com.app.service.client.mapper;

import com.app.service.client.domain.cartitem.CartItemDTO;
import com.app.service.client.domain.favorite.FavoriteDTO;
import com.app.service.client.model.CartItem;
import com.app.service.client.model.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface FavoriteMapperClient {

    FavoriteMapperClient INSTANCE = Mappers.getMapper(FavoriteMapperClient.class);

    List<FavoriteDTO> toDto(List<Favorite> favorites);
}
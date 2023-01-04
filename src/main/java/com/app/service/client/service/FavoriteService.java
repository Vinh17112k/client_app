package com.app.service.client.service;

import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.domain.favorite.FavoriteDTO;
import com.app.service.client.domain.favorite.FavoriteRequestDTO;

public interface FavoriteService {

    Boolean delete(FavoriteRequestDTO favoriteRequestDTO) throws ValidateException;

    Boolean addToFavorite(FavoriteRequestDTO favoriteRequestDTO) throws ValidateException;

    FavoriteDTO listFavorite(Long customerId) throws ValidateException;
}

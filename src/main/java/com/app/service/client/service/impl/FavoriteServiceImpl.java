package com.app.service.client.service.impl;

import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.config.security.SecurityUtilsClient;
import com.app.service.client.domain.favorite.FavoriteDTO;
import com.app.service.client.domain.favorite.FavoriteRequestDTO;
import com.app.service.client.domain.product.ProductDTO;
import com.app.service.client.mapper.CusMapper;
import com.app.service.client.mapper.FavoriteMapperClient;
import com.app.service.client.mapper.ProductClientMapper;
import com.app.service.client.model.Favorite;
import com.app.service.client.repository.*;
import com.app.service.client.service.FavoriteService;
import com.app.service.client.service.MessageService;
import com.app.service.client.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private FavoriteMapperClient favoriteMapperClient;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductClientMapper productClientMapper;
    @Autowired
    private CusMapper cusMapper;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ImageRepository imageRepository;


    @Override
    public Boolean delete(FavoriteRequestDTO favoriteRequestDTO) throws ValidateException {
        if (DataUtils.isNull(favoriteRequestDTO.getCustomerId())) {
            throw new ValidateException(messageService.getMessage("error.code.username.not.exist"));
        }
        if (SecurityUtilsClient.getCustomerId() != favoriteRequestDTO.getCustomerId()) {
            throw new ValidateException(messageService.getMessage("error.user.not.login"));
        }
        List<Favorite> favorites = favoriteRepository.findByUserAndProduct(
            favoriteRequestDTO.getCustomerId(), favoriteRequestDTO.getProductIds());
        favorites.forEach(f -> f.setIsDeleted(Boolean.TRUE));
        return Boolean.TRUE;
    }

    @Override
    public Boolean addToFavorite(FavoriteRequestDTO favoriteRequestDTO) throws ValidateException {
        if (DataUtils.isNull(favoriteRequestDTO.getCustomerId())) {
            throw new ValidateException(messageService.getMessage("error.code.username.not.exist"));
        }
        if (SecurityUtilsClient.getCustomerId() != favoriteRequestDTO.getCustomerId()) {
            throw new ValidateException(messageService.getMessage("error.user.not.login"));
        }
        if (!productRepository.existsById(favoriteRequestDTO.getProductId())) {
            throw new ValidateException(
                messageService.getMessage("error.code.product.is.not_exsits"));
        }
        if (favoriteRepository.existsByProductIdAndCustomerId(favoriteRequestDTO.getProductId(),
            favoriteRequestDTO.getCustomerId())) {
            throw new ValidateException(messageService.getMessage("error.favorite.product.exists"));
        }
        Favorite favorite = new Favorite();
        favorite.setCustomerId(favoriteRequestDTO.getCustomerId());
        favorite.setProductId(favoriteRequestDTO.getProductId());
        favoriteRepository.save(favorite);
        return Boolean.TRUE;
    }

    @Override
    public FavoriteDTO listFavorite(Long customerId) throws ValidateException {
        if (SecurityUtilsClient.getCustomerId() != customerId) {
            throw new ValidateException(messageService.getMessage("error.user.not.login"));
        }
        List<Long> listId = favoriteRepository.listProductId(customerId);
        List<ProductDTO> products = productClientMapper.toDto(
            productRepository.getProducts(listId));
        FavoriteDTO favoriteDTO = new FavoriteDTO();
        favoriteDTO.setProductDTOS(products);
        setImage(favoriteDTO.getProductDTOS());
        setStar(favoriteDTO.getProductDTOS());
        favoriteDTO.setCustomerDTO(cusMapper.toDto(customerRepository.findById(customerId).get()));
        return favoriteDTO;
    }

    private void setImage(List<ProductDTO> productDTOS) {
        productDTOS.forEach(p -> {
            List<String> filePath = imageRepository.findFilePathByProdutId(p.getId());
            p.setPath(filePath);
        });
    }

    private void setStar(List<ProductDTO> productDTOS) {
        productDTOS.forEach(p -> {
            AtomicInteger count = new AtomicInteger();
            Double star;
            List<Double> stars = commentRepository.starProduct(p.getId());
            if (DataUtils.isNullOrEmpty(stars)) {
                star = 5.00D;
            } else {
                Double sum = stars.stream().reduce(0.0, (x, y) -> {
                    count.incrementAndGet();
                    return x + y;
                });
                star = sum / count.get();
            }
            p.setStar(star);
        });
    }
}
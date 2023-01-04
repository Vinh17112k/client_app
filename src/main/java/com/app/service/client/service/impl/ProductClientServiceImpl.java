package com.app.service.client.service.impl;

import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.domain.product.ProductDTO;
import com.app.service.client.domain.product.ProductSearchRequestDTO;
import com.app.service.client.mapper.MakeMapperClient;
import com.app.service.client.mapper.ProductClientMapper;
import com.app.service.client.mapper.ProductTypeMapClient;
import com.app.service.client.model.Product;
import com.app.service.client.model.Voucher;
import com.app.service.client.repository.*;
import com.app.service.client.service.MessageService;
import com.app.service.client.service.ProductClientService;
import com.app.service.client.utils.DataUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
@Service
@RequiredArgsConstructor
@Data
public class ProductClientServiceImpl implements ProductClientService {

    private final ProductRepository productRepository;
    private final MessageService messageService;
    private final ProductClientMapper productClientMapper;
    private final ImageRepository imageRepository;
    private final MakeRepository makeRepository;
    private final ProductTypeMapClient productTypeMapClient;
    private final MakeMapperClient makeMapperClient;
    private final CommentRepository commentRepository;
    private final VoucherRepository voucherRepository;

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

    @Override
    public ProductDTO detail(Long id) throws ValidateException {
        Product product = productRepository.findById(id).orElseThrow(() -> new ValidateException(
            messageService.getMessage("error.code.product.is.not_exsits")));
        ProductDTO productDTO = new ProductDTO();
        productDTO = productClientMapper.EntityToDto(product);
        productDTO.setProductType(productTypeMapClient.EntityToDto(product.getProductType()));
        productDTO.setMake(
            makeMapperClient.EntityToDto(makeRepository.findById(product.getMake().getId()).get()));
        List<String> filePath = imageRepository.findFilePathByProdutId(productDTO.getId());
        productDTO.setPath(filePath);
        AtomicInteger count = new AtomicInteger();
        Double star;
        List<Double> stars = commentRepository.starProduct(productDTO.getId());
        if (DataUtils.isNullOrEmpty(stars)) {
            star = 5.00D;
        } else {
            Double sum = stars.stream().reduce(0.0, (x, y) -> {
                count.incrementAndGet();
                return x + y;
            });
            star = sum / count.get();
        }
        productDTO.setStar(star);
        Float a = (1.0F - productDTO.getOurPrice() / productDTO.getPrice()) * 100;
        productDTO.setDiscount(Math.round(a) + "%");
//        add voucher list
        List<Voucher> vouchers = voucherRepository.getVoucherProduct(id);
        productDTO.setVoucherList(vouchers);
        return productDTO;
    }

    @Override
    public PageImpl<ProductDTO> productClientPage(ProductSearchRequestDTO searchDTO,
        Pageable pageable) throws ValidateException {
        Page<Product> page;
        List<ProductDTO> productDTOS = new ArrayList<>();
        switch (searchDTO.getEnums()) {
            case PRODUCT_TOP:
                page = getProductTop(pageable);
                productDTOS = productClientMapper.toList(page.getContent());
                setImage(productDTOS);
                setStar(productDTOS);
                break;
            case PRODUCT_LASTEST:
                page = getProductLastest(pageable);
                productDTOS = productClientMapper.toList(page.getContent());
                setImage(productDTOS);
                setStar(productDTOS);
                break;
            case PRODUCT_BY_TYPE:
                log.debug("relative product order by price");
                page = getProductByType(pageable);
                productDTOS = productClientMapper.toList(page.getContent());
                setImage(productDTOS);
                setStar(productDTOS);
                break;
            case PRODUCT_MULTI_SEARCH:
                log.debug("search allow many condition");
                page = getProduct(searchDTO, pageable);
                productDTOS = productClientMapper.toList(page.getContent());
                setImage(productDTOS);
                setStar(productDTOS);
                break;
            default:
                throw new ValidateException(
                    messageService.getMessage("error.suggestion.enums.required"));
        }
        return new PageImpl<>(productDTOS, page.getPageable(),
            page.getTotalElements());
    }

    private Page<Product> getProductTop(Pageable pageable) {
        return productRepository.findAllByActivePage(pageable);
    }

    private Page<Product> getProductLastest(Pageable pageable) {
        return productRepository.findAllByActivePage(pageable);
    }

    private Page<Product> getProductByType(Pageable pageable) {
        return productRepository.findAllByActivePage(pageable);
    }

    private Page<Product> getProduct(ProductSearchRequestDTO searchDTO, Pageable pageable) {
        String name = searchDTO.getName();
        Long fromPrice = searchDTO.getFromPrice();
        Long toPrice = searchDTO.getToPrice();
        List<Long> brandId = searchDTO.getBrandId();
        List<Long> categoryId = searchDTO.getCategoryId();
        List<Long> star = searchDTO.getStar();
        return productRepository.getListProductIn(brandId, categoryId, fromPrice, toPrice, name,
            pageable);
    }
}

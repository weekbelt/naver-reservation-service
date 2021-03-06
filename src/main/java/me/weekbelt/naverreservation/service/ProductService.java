package me.weekbelt.naverreservation.service;

import lombok.RequiredArgsConstructor;
import me.weekbelt.naverreservation.domain.ImageType;
import me.weekbelt.naverreservation.domain.displayInfo.DisplayInfo;
import me.weekbelt.naverreservation.domain.displayInfo.DisplayInfoRepository;
import me.weekbelt.naverreservation.domain.product.*;
import me.weekbelt.naverreservation.domain.productImage.ProductImage;
import me.weekbelt.naverreservation.domain.productImage.ProductImageRepository;
import me.weekbelt.naverreservation.domain.productPrice.ProductPrice;
import me.weekbelt.naverreservation.domain.productPrice.ProductPriceRepository;
import me.weekbelt.naverreservation.web.dto.product.ProductDto;
import me.weekbelt.naverreservation.web.dto.product.ProductImageDto;
import me.weekbelt.naverreservation.web.dto.product.ProductPriceDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final static Integer LIMIT = 4;

    private final DisplayInfoRepository displayInfoRepositoryImpl;
    private final ProductImageRepository productImageRepository;
    private final ProductPriceRepository productPriceRepository;

    public List<ProductDto> findProductDto(Long categoryId, Integer offset) {
        List<DisplayInfo> displayInfoList = displayInfoRepositoryImpl
                .findDisplayInfoWithProductByCategoryId(categoryId, offset, LIMIT);

        return createProductDtos(displayInfoList);
    }

    private List<ProductDto> createProductDtos(List<DisplayInfo> displayInfoList) {
        return displayInfoList.stream()
                .map(displayInfo -> {
                    String saveFileName = getProductImageUrl(displayInfo.getProduct().getId());
                    return ProductFactoryObject.displayInfoToProductDto(displayInfo, saveFileName);
                })
                .collect(Collectors.toList());
    }

    private String getProductImageUrl(Long productId) {
        ProductImage productImage = productImageRepository.findProductImageByProductIdAndType(productId, ImageType.th).get(0);
        return productImage.getSaveFileName();
    }

    public Integer countProductNumByCategoryId(Long categoryId) {
        return displayInfoRepositoryImpl.countDisplayInfoNumberByCategoryId(categoryId);
    }

    public List<ProductImageDto> findProductImageDto(Long productId, ImageType type) {
        List<ProductImage> productImages = productImageRepository.findProductImageByProductIdAndType(productId, type);

        return productImages.stream()
                .map(ProductImageDto::new)
                .collect(Collectors.toList());
    }

    public List<ProductPriceDto> findProductPriceDto(Long productId) {
        List<ProductPrice> productPrices = productPriceRepository.findProductPricesByProductIdOrderByIdDesc(productId);

        return productPrices.stream()
                .map(ProductPriceDto::new)
                .collect(Collectors.toList());
    }
}

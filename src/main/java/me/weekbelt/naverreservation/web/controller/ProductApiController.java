package me.weekbelt.naverreservation.web.controller;

import lombok.RequiredArgsConstructor;
import me.weekbelt.naverreservation.domain.ImageType;
import me.weekbelt.naverreservation.service.CommentService;
import me.weekbelt.naverreservation.service.DisplayInfoService;
import me.weekbelt.naverreservation.service.ProductService;
import me.weekbelt.naverreservation.web.dto.comment.CommentDto;
import me.weekbelt.naverreservation.web.dto.display.DisplayInfoDto;
import me.weekbelt.naverreservation.web.dto.display.DisplayInfoImageDto;
import me.weekbelt.naverreservation.web.dto.display.DisplayInfoResponse;
import me.weekbelt.naverreservation.web.dto.product.ProductDto;
import me.weekbelt.naverreservation.web.dto.product.ProductImageDto;
import me.weekbelt.naverreservation.web.dto.product.ProductPriceDto;
import me.weekbelt.naverreservation.web.dto.product.ProductResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProductApiController {

    private final ProductService productService;
    private final DisplayInfoService displayInfoService;
    private final CommentService commentService;

    @GetMapping("/products")
    public ProductResponse getProductList(@RequestParam(defaultValue = "0") Long categoryId,
                                          @RequestParam(defaultValue = "0") Integer start){

        List<ProductDto> items = productService.findProductDto(categoryId, start);
        Integer totalCount = productService.countProductNumByCategoryId(categoryId);

        return ProductResponse.builder()
                .items(items)
                .totalCount(totalCount)
                .build();
    }

    @GetMapping("/products/{displayInfoId}")
    public DisplayInfoResponse getDisplayInfoResponse(@PathVariable Long displayInfoId, HttpSession httpSession) {
        DisplayInfoResponse displayInfoResponse = makeDisplayInfoResponse(displayInfoId);
        httpSession.setAttribute("displayInfoResponse", displayInfoResponse);
        return displayInfoResponse;
    }

    private DisplayInfoResponse makeDisplayInfoResponse(@PathVariable Long displayInfoId) {
        DisplayInfoDto displayInfo = displayInfoService.findDisplayInfoDto(displayInfoId);
        DisplayInfoImageDto displayInfoImage = displayInfoService.findDisplayInfoImageDto(displayInfoId);

        Long productId = displayInfo.getProductId();
        List<ProductImageDto> productImages = productService.findProductImageDto(productId, ImageType.ma);
        List<CommentDto> comments = commentService.findCommentDto(productId);
        List<ProductPriceDto> productPrices = productService.findProductPriceDto(productId);
        Double averageScore = commentService.findAverageScore(productId);

        return DisplayInfoResponse.builder()
                .displayInfo(displayInfo)
                .displayInfoImage(displayInfoImage)
                .productImages(productImages)
                .comments(comments)
                .productPrices(productPrices)
                .averageScore(averageScore)
                .build();
    }

}

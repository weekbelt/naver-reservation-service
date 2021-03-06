package me.weekbelt.naverreservation.service;

import lombok.RequiredArgsConstructor;
import me.weekbelt.naverreservation.domain.ImageType;
import me.weekbelt.naverreservation.domain.displayInfoImage.DisplayInfoImage;
import me.weekbelt.naverreservation.domain.displayInfoImage.DisplayInfoImageRepository;
import me.weekbelt.naverreservation.domain.productImage.ProductImage;
import me.weekbelt.naverreservation.domain.productImage.ProductImageRepository;
import me.weekbelt.naverreservation.domain.reservationUserCommentImage.ReservationUserCommentImage;
import me.weekbelt.naverreservation.domain.reservationUserCommentImage.ReservationUserCommentImageRepository;
import me.weekbelt.naverreservation.web.dto.display.DisplayInfoImageDto;
import me.weekbelt.naverreservation.web.dto.product.ProductImageDto;
import me.weekbelt.naverreservation.web.dto.reservation.ReservationCommentImageDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ProductImageRepository productImageRepository;
    private final DisplayInfoImageRepository displayInfoImageRepository;
    private final ReservationUserCommentImageRepository reservationUserCommentImageRepository;

    public List<ProductImageDto> getProductImageDto(Long productId, ImageType type) {
        List<ProductImage> productImageList = productImageRepository.findProductImageByProductIdAndType(productId, type);

        return productImageList.stream()
                .map(ProductImageDto::new)
                .collect(Collectors.toList());
    }

    public DisplayInfoImageDto getDisplayInfoImageDto(Long displayInfoId) {
        DisplayInfoImage displayInfoImage = displayInfoImageRepository.findDisplayInfoImageByDisplayInfoId(displayInfoId)
                .orElseThrow(() -> new IllegalArgumentException("전시정보 이미지가 없습니다. id=" + displayInfoId));

        return new DisplayInfoImageDto(displayInfoImage);
    }

    public ReservationCommentImageDto getReservationCommentImageDto(Long imageId) {
        ReservationUserCommentImage reservationUserCommentImage = reservationUserCommentImageRepository.findByReservationUserCommentId(imageId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰 이미지가 없습니다. id=" + imageId));

        return new ReservationCommentImageDto(reservationUserCommentImage);
    }
}

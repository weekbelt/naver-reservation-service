package me.weekbelt.naverreservation.web.dto.comment;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.weekbelt.naverreservation.domain.fileInfo.FileInfo;
import me.weekbelt.naverreservation.domain.reservationUserCommentImage.ReservationUserCommentImage;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class CommentImageDto {

    // reservation_user_comment_image 테이블
    private Long imageId;
    private Long reservationInfoId;
    private Long reservationUserCommentId;
    private Long fileId;

    // file_info 테이블
    private String fileName;
    private String saveFileName;
    private String contentType;
    private boolean deleteFlag;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    public CommentImageDto(ReservationUserCommentImage reservationUserCommentImage) {
        this.imageId = reservationUserCommentImage.getId();
        this.reservationInfoId = reservationUserCommentImage.getReservationInfo().getId();
        this.reservationUserCommentId = reservationUserCommentImage.getReservationUserComment().getId();

        FileInfo fileInfo = reservationUserCommentImage.getFileInfo();
        if (fileInfo != null) {
            this.fileId = reservationUserCommentImage.getFileInfo().getId();
            this.fileName = fileInfo.getFileName();
            this.saveFileName = fileInfo.getSaveFileName();
            this.contentType = fileInfo.getContentType();
            this.deleteFlag = fileInfo.isDeleteFlag();
            this.createDate = fileInfo.getCreateDate();
            this.modifyDate = fileInfo.getModifyDate();
        }
    }

}

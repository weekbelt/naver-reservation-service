package me.weekbelt.naverreservation.web.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.weekbelt.naverreservation.domain.category.Category;

@ToString
@NoArgsConstructor
@Getter @Setter
public class CategoryDto {

    // 카테고리 테이블
    private Long categoryId;
    private String name;

    private Integer count;

    public CategoryDto(Category category, Integer count) {
        this.categoryId = category.getId();
        this.name = category.getName();
        this.count = count;
    }
}

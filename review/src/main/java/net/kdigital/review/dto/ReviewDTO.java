package net.kdigital.review.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kdigital.review.entity.ReviewEntity;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class ReviewDTO {
    private Long reviewNum;
    private String reviewerName;
    private Long movieNum;
    private String reviewText;
    private Double score;
    private LocalDate reviewDate;

    public static ReviewDTO toDTO(ReviewEntity reviewEntity, Long movieNum){
        return ReviewDTO.builder()
                .reviewNum(reviewEntity.getReviewNum())
                .reviewerName(reviewEntity.getReviewerName())
                .movieNum(movieNum)
                .reviewText(reviewEntity.getReviewText())
                .score(reviewEntity.getScore())
                .reviewDate(reviewEntity.getReviewDate())
                .build();
    }
}

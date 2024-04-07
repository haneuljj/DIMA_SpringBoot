package net.kdigital.review.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kdigital.review.dto.ReviewDTO;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder

@Entity
@Table(name = "review")
public class ReviewEntity {
    @SequenceGenerator(
        name = "review_seq"
        ,sequenceName = "review_seq"
        ,initialValue = 1
        ,allocationSize = 1
    )

    @Id
    @GeneratedValue(generator = "review_seq")
    @Column(name = "review_num")
    private Long reviewNum;

    @Column(name = "reviewer_name", nullable = false)
    private String reviewerName;

    @Column(name = "review_text", nullable = false)
    private String reviewText;

    private Double score;
    
    @Column(name = "review_date")
    @CreationTimestamp
    private LocalDate reviewDate;

    // movie테이블과 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_num")
    private MovieEntity movieEntity;

    public static ReviewEntity toEntity(ReviewDTO reviewDTO, MovieEntity movieEntity){
        return ReviewEntity.builder()
                .reviewNum(reviewDTO.getReviewNum())
                .reviewerName(reviewDTO.getReviewerName())
                .reviewText(reviewDTO.getReviewText())
                .score(reviewDTO.getScore())
                .reviewDate(reviewDTO.getReviewDate())
                .movieEntity(movieEntity)
                .build();

    }

}

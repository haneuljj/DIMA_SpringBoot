package net.kdigital.review.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kdigital.review.entity.MovieEntity;
import net.kdigital.review.entity.ReviewEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MovieDTO {
    private Long movieNum;
    private String genre;
    private String movieName;
    private String movieSummary;
    private LocalDate movieDate;
    private List<ReviewEntity> reviewEntity;

    public static MovieDTO toDTO(MovieEntity movieEntity){
        return MovieDTO.builder()
                .movieNum(movieEntity.getMovieNum())
                .genre(movieEntity.getGenre())
                .movieName(movieEntity.getMovieName())
                .movieSummary(movieEntity.getMovieSummary())
                .movieDate(movieEntity.getMovieDate())
                .reviewEntity(movieEntity.getReviewEntity())
                .build();
    }
}

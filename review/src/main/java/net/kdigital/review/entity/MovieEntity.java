package net.kdigital.review.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Collate;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kdigital.review.dto.MovieDTO;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity
@Table(name = "movie")
public class MovieEntity {
    @SequenceGenerator(
        name = "movie_seq"
        ,sequenceName = "movie_seq"
        ,initialValue = 1
        ,allocationSize = 1
    )

    @Id
    @GeneratedValue(generator = "movie_seq")
    @Column(name = "movie_num")
    private Long movieNum;

    @Column(nullable = false)
    private String genre;

    @Column(name = "movie_name", nullable = false)
    private String movieName;

    @Column(name = "movie_summary", nullable = false)
    private String movieSummary;

    @Column(name = "movie_date")
    @CreationTimestamp
    private LocalDate movieDate;

    // review 테이블과 관계 설정
    @JsonIgnore
    @OneToMany(
        mappedBy = "movieEntity",
        cascade = CascadeType.REMOVE,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @OrderBy("review_num desc")
    private List<ReviewEntity> reviewEntity = new ArrayList<>();

    public static MovieEntity toEntity(MovieDTO movieDTO) {
        return MovieEntity.builder()
                .movieNum(movieDTO.getMovieNum())
                .genre(movieDTO.getGenre())
                .movieName(movieDTO.getMovieName())
                .movieSummary(movieDTO.getMovieSummary())
                .build();
    }
}

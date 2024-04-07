package net.kdigital.review.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.kdigital.review.entity.MovieEntity;

public interface MovieRepository extends JpaRepository<MovieEntity, Long>{

    
    List<MovieEntity> findByGenreContainingOrderByMovieDateDesc(String searchGenre);

    List<MovieEntity> findAllByOrderByMovieDateDesc();

    @Query("SELECT m FROM MovieEntity m LEFT JOIN m.reviewEntity r GROUP BY m ORDER BY (SELECT COUNT(r2) FROM ReviewEntity r2 WHERE r2.movieEntity.movieNum = m.movieNum) DESC")
    List<MovieEntity> findAllByOrderByReviewCountDesc();
    
}
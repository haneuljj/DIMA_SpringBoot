package net.kdigital.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kdigital.review.entity.MovieEntity;
import net.kdigital.review.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>{

    List<ReviewEntity> findAllByMovieEntityOrderByReviewNumDesc(MovieEntity movieEntity);

}

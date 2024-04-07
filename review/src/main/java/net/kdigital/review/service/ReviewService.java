package net.kdigital.review.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.kdigital.review.dto.ReviewDTO;
import net.kdigital.review.entity.MovieEntity;
import net.kdigital.review.entity.ReviewEntity;
import net.kdigital.review.repository.MovieRepository;
import net.kdigital.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    public ReviewDTO insertReview(ReviewDTO reviewDTO) {
        Optional<MovieEntity> entity = movieRepository.findById(reviewDTO.getMovieNum());
        if(entity.isPresent()) {
            MovieEntity movieEntity = entity.get();
            ReviewEntity reviewEntity = ReviewEntity.toEntity(reviewDTO, movieEntity);

            ReviewEntity savedEntity = reviewRepository.save(reviewEntity);

            return ReviewDTO.toDTO(savedEntity, reviewDTO.getMovieNum());
        }
        return null;
    
    }

    public List<ReviewDTO> reviewAll(Long movieNum) {
        Optional<MovieEntity> entity = movieRepository.findById(movieNum);
        if(entity.isPresent()) {
            MovieEntity movieEntity = entity.get();
            
            List<ReviewEntity> reviewEntityList = reviewRepository.findAllByMovieEntityOrderByReviewNumDesc(movieEntity);
            
            List<ReviewDTO> reviewDTOList = new ArrayList<>();

            reviewEntityList.forEach((e) -> reviewDTOList.add(ReviewDTO.toDTO(e, movieNum)));

            return reviewDTOList;
        }
        return null;
    }


}

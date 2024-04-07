package net.kdigital.review.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.review.dto.MovieDTO;
import net.kdigital.review.entity.MovieEntity;
import net.kdigital.review.repository.MovieRepository;;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    /**
     * 등록된 영화 정보 전체 출력
     * @return
     */
    public List<MovieDTO> selectAll() {
        List<MovieEntity> entityList = movieRepository.findAll(Sort.by(Sort.Direction.DESC, "movieDate"));
        
        List<MovieDTO> dtoList = new ArrayList<>();
        
		entityList.forEach((entity) -> dtoList.add(MovieDTO.toDTO(entity)));

        return dtoList;
    }
    
    public List<MovieDTO> getGenreList(String searchGenre) {
        List<MovieEntity> entityList = null;

        entityList = movieRepository.findByGenreContainingOrderByMovieDateDesc(searchGenre);
        
        List<MovieDTO> dtoList = new ArrayList<>();
        
		entityList.forEach((entity) -> dtoList.add(MovieDTO.toDTO(entity)));

        //log.info("========dtolist: {}", dtoList.toString());
        return dtoList;
    }

    public List<MovieDTO> sortList(String criteria) {
        List<MovieEntity> entityList = null;

        switch(criteria) {
            case "movieDate":
                entityList = movieRepository.findAllByOrderByMovieDateDesc();
                break;
            case "reviewCount":
                entityList = movieRepository.findAllByOrderByReviewCountDesc();
                break;
        }
        
        List<MovieDTO> dtoList = new ArrayList<>();
        
		entityList.forEach((entity) -> dtoList.add(MovieDTO.toDTO(entity)));

        return dtoList;
    }

    /**
     * 입력받은 영화정보를 테이블에 저장하기
     * @param movieDTO
     */
    public void insertMovie(MovieDTO movieDTO) {
        
        movieRepository.save(MovieEntity.toEntity(movieDTO));
    }

    /**
     * 입력받은 번호에 해당하는 영화 정보 출력하기
     * @param movieNum
     * @return
     */
    public MovieDTO selectOne(Long movieNum) {
        Optional<MovieEntity> entity = movieRepository.findById(movieNum);
        if(entity.isPresent()) {
            MovieEntity movieEntity = entity.get();
            return MovieDTO.toDTO(movieEntity);
        }
        return null;
    }

    @Transactional
    public void updateMovie(MovieDTO movieDTO) {
        Optional<MovieEntity> entity = movieRepository.findById(movieDTO.getMovieNum());
        if(entity.isPresent()) {
            MovieEntity movieEntity = entity.get();
            movieEntity.setGenre(movieDTO.getGenre());
            movieEntity.setMovieName(movieDTO.getMovieName());
            movieEntity.setMovieSummary(movieDTO.getMovieSummary());
        }
    }

    public void deleteOne(Long movieNum) {
        Optional<MovieEntity> entity = movieRepository.findById(movieNum);
        if(entity.isPresent()) {
            MovieEntity movieEntity = entity.get();
            movieRepository.deleteById(movieNum);
        }
    }

    

}

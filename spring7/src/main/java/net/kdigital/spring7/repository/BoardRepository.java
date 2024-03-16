package net.kdigital.spring7.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import net.kdigital.spring7.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long > {
    // findBy컬럼명Containing(String searchWord)
    // => select * from board where 컬럼명 like '%' || searchWord || '%';

    // 검색기능이 있는 메소드 선언 
	List<BoardEntity> findByBoardTitleContaining(String searchWord, Sort sort);
    List<BoardEntity> findByBoardWriterContaining(String searchWord, Sort sort);
    List<BoardEntity> findByBoardContentContaining(String searchWord, Sort sort);
    
}

package net.kdigital.spring7.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kdigital.spring7.entity.BoardEntity;
import net.kdigital.spring7.entity.ReplyEntity;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long>{

    List<ReplyEntity> findAllByBoardEntityOrderByReplyNumDesc(BoardEntity entity);
    
}

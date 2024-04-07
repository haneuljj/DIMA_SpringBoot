package net.kdigital.spring7.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.kdigital.spring7.dto.ReplyDTO;
import net.kdigital.spring7.entity.BoardEntity;
import net.kdigital.spring7.entity.ReplyEntity;
import net.kdigital.spring7.repository.BoardRepository;
import net.kdigital.spring7.repository.ReplyRepository;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    public ReplyDTO replyInsert(ReplyDTO replyDTO) {

        // 댓글의 부모인 게시글이 존재하는지 확인 !
        Optional<BoardEntity> boardEntity = boardRepository.findById(replyDTO.getBoardNum());

        if(boardEntity.isPresent()){
            BoardEntity entity = boardEntity.get();
            ReplyEntity replyEntity = ReplyEntity.toEntity(replyDTO, entity);

            ReplyEntity temp = replyRepository.save(replyEntity);

            return ReplyDTO.toDTO(temp, replyDTO.getBoardNum());
        } else {
            return null;
        }

    }

    public List<ReplyDTO> replyAll(Long boardNum) {
        
        BoardEntity entity = boardRepository.findById(boardNum).get();

        // 부모테이블의 엔티티를 넘겨서 자식테이블들의 정보 가져오기
        // JpaRepository에 메소드 만드는 방법
        List<ReplyEntity> replyEntityList = replyRepository.findAllByBoardEntityOrderByReplyNumDesc(entity);

        // EntityList -> DTOList
        List<ReplyDTO> replyDTOList = new ArrayList<>();
        
        // replyEntityList.forEach((item) -> replyDTOList.add(ReplyDTO.toDTO(item, boardNum)));
        for(ReplyEntity temp : replyEntityList){
            ReplyDTO dto = ReplyDTO.toDTO(temp, boardNum);
            replyDTOList.add(dto);
        }

        return replyDTOList;
    }

    public boolean replyDelete(Long replyNum) {
        replyRepository.deleteById(replyNum);

        // 삭제가 성공적으로 되었다면 아이디로 찾았을때 존재하지 않아야 함
        return !replyRepository.existsById(replyNum); 
    }

    public ReplyDTO replyOne(Long replyNum) {
        Optional<ReplyEntity> entity = replyRepository.findById(replyNum);

        if(entity.isPresent()){
            ReplyEntity replyEntity = entity.get();

            return ReplyDTO.toDTO(replyEntity, replyEntity.getBoardEntity().getBoardNum());
        }

        return null;
    }

    @Transactional
    public ReplyDTO updateReply(Long replyNum, String replyText) {
        Optional<ReplyEntity> entity = replyRepository.findById(replyNum);

        if(entity.isPresent()){
            ReplyEntity replyEntity = entity.get();
            
            replyEntity.setReplyText(replyText);

            return ReplyDTO.toDTO(replyEntity, replyEntity.getBoardEntity().getBoardNum());
        }

        return null;
    }
    
}

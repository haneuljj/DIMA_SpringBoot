package net.kdigital.spring7.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.spring7.dto.BoardDTO;
import net.kdigital.spring7.entity.BoardEntity;
import net.kdigital.spring7.repository.BoardRepository;
import net.kdigital.spring7.util.FileService;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepository;

	// 업로드된 파일이 저장될 디렉토리 경로 읽어오기
	@Value("${spring.servlet.multipart.location}")
	String uploadPath;
	
	public List<BoardDTO> selectAll(String searchItem, String searchWord) {
		// Java Reflection 기능으로도 가능
		List<BoardEntity> entityList = null;
		switch (searchItem) {
			case "boardTitle":
				entityList = boardRepository.findByBoardTitleContaining(searchWord, Sort.by(Sort.Direction.DESC, "createDate"));
				break;
			case "boardWriter":
			entityList = boardRepository.findByBoardWriterContaining(searchWord, Sort.by(Sort.Direction.DESC, "createDate"));
				break;
			case "boardContent":
			entityList = boardRepository.findByBoardContentContaining(searchWord, Sort.by(Sort.Direction.DESC, "createDate"));
				break;
		}

		// List<BoardEntity> entityList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
		List<BoardDTO> dtoList = new ArrayList<>();
		
		// entity를 dto로 변환하여 List에 담는 작업
		entityList.forEach((entity) -> dtoList.add(BoardDTO.toDTO(entity)));
		return dtoList;
	}

	/** 
	 * DB에 게시글 저장
	 * @param boardDTO
	 */
	public void insertBoard(BoardDTO boardDTO) {
		boardDTO.setBoardWriter("강하늘");

		log.info("저장 경로: {}", uploadPath);

		String originalFileName = null;
		String savedFileName = null;

		// 글에 첨부파일이 있으면 파일명 세팅 작업 처리
		if(!boardDTO.getUploadFile().isEmpty()) {
			savedFileName = FileService.saveFile(boardDTO.getUploadFile(), uploadPath);
			originalFileName = boardDTO.getUploadFile().getOriginalFilename();

			boardDTO.setOriginalFileName(originalFileName);
			boardDTO.setSavedFileName(savedFileName);
		}

		BoardEntity entity = BoardEntity.toEntity(boardDTO);
		boardRepository.save(entity);
		
	}

	/**
	 * DB에서 boardNum에 해당하는 글을 조회
	 * @param boardNum
	 * @return
	 */
    public BoardDTO selectOne(Long boardNum) {
        Optional<BoardEntity> entity = boardRepository.findById(boardNum);
		if(entity.isPresent()) {
			BoardEntity boardEntity = entity.get();
			BoardDTO boardDTO = BoardDTO.toDTO(boardEntity);
			return boardDTO;
		}
		return null;
    }

	/**
	 * DB에서 boardNum에 해당하는 글과 파일을 삭제
	 * @param boardNum
	 */
	@Transactional // 두 개 이상의 쿼리를 날릴 때 트랜잭션 처리 작업 필요
	public void deleteOne(Long boardNum) {

		// 글번호에 해당하는 데이터 읽어와 savedfilename값 가져오기
		Optional<BoardEntity> entity = boardRepository.findById(boardNum); // 쿼리1
		if(entity.isPresent()) {
			BoardEntity boardEntity = entity.get();
			String savedFileName = boardEntity.getSavedFileName();

			log.info("saved file name: {}", savedFileName);

			if(savedFileName != null) {
				String fullPath = uploadPath + "/" + savedFileName;
				FileService.deleteFile(fullPath);
			}

			boardRepository.deleteById(boardNum); // 쿼리2
		}
		
	}

	/**
	 * DB에 데이터 수정 작업 처리
	 * @param boardDTO
	 */
	@Transactional // Update시 Transactional 필수! 조회 후 수정하기 때문
	public void updateOne(BoardDTO boardDTO) {
		/*** 파일 첨부된 경우 ***/
		// 1) 기존 DB에 저장된 파일을 읽어오기
		//	- 이미 저장된 파일이 있다면 그 파일을 삭제하고 새로운 파일로 대체하고 파일 저장
		//	- 저장된 파일 없다면 삭제하지 않고 DB에만 새 파일 저장
		/*** 파일 첨부 안된 경우 ***/
		// 	- 기존 파일 상태는 유지하고 글만 수정

		MultipartFile uploadFile = boardDTO.getUploadFile(); // 첨부파일 가져오기

		String originalFileName = null; // 새로운 첨부파일의 업로드된 이름
		String savedFileName = null; // 새로운 첨부파일의 저장된 이름
		String oldSavedFileName = null; // 기존 업로드된 파일의 저장 파일명

		// 첨부 파일이 있는 경우 - 첨부파일의 이름 값 세팅
		if(!uploadFile.isEmpty()) {
			originalFileName = uploadFile.getOriginalFilename(); // 업로드 파일의 원래 이름 가져오기
			savedFileName = FileService.saveFile(uploadFile, uploadPath); // 일단 업로드된 파일을 하드에 저장
		}

		// DB에서 수정데이터에 해당하는 데이터를 가져옴
		Optional<BoardEntity> entity = boardRepository.findById(boardDTO.getBoardNum());

		if(entity.isPresent()) {
			BoardEntity boardEntity = entity.get();
			oldSavedFileName = boardEntity.getSavedFileName(); // 기존DB에 저장된 저장파일명 가져오기

			// 기존 파일이 있고, 업로드한 파일도 있다면
			if(oldSavedFileName != null && !uploadFile.isEmpty()) {
				// 기존 파일은 하드에서 삭제
				String fullPath = uploadPath + '/' + oldSavedFileName;
				FileService.deleteFile(fullPath);
				// DB에 새로운 파일이름들 저장 - 첫번째 if문에 걸려서 값이 저장되어 null이 아님
				boardEntity.setOriginalFileName(originalFileName);
				boardEntity.setSavedFileName(savedFileName);
			}

			// 기존파일은 없고 업로드된 파일이 있는 경우
			else if(oldSavedFileName == null && !uploadFile.isEmpty()) {
				// 파일 삭제는 필요 없고 DB에 새로운 파일 이름 저장
				boardEntity.setOriginalFileName(originalFileName);
				boardEntity.setSavedFileName(savedFileName);
			}

			// 첨부파일이 없는 경우 - originalFileName과 savedFileName이 모두 null인 채로 넘어옴
			// 기존 DB 건드리지 않고 글만 수정해야함
			boardEntity.setBoardTitle(boardDTO.getBoardTitle());
			boardEntity.setBoardContent(boardDTO.getBoardContent());
			boardEntity.setUpdateDate(LocalDateTime.now());
		}
		
	}

	/**
	 * boardNum에 해당하는 글이 조회되면 조회수 증가
	 * @param boardNum
	 */
	@Transactional
	public void incrementHitCount(Long boardNum) {
		Optional<BoardEntity> entity = boardRepository.findById(boardNum);
		if(entity.isPresent()) {
			BoardEntity boardEntity = entity.get();
			boardEntity.setHitCount(boardEntity.getHitCount() + 1);
		}
	}
}
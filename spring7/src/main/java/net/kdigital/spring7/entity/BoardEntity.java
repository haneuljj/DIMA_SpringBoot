package net.kdigital.spring7.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kdigital.spring7.dto.BoardDTO;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString

@Entity
@Table(name="board")
public class BoardEntity {

	@SequenceGenerator(
			name="board_seq"
			, sequenceName = "board_seq"
			, initialValue = 1
			, allocationSize = 1)
	
	@Id
	@GeneratedValue(generator="board_seq")
	@Column(name="board_num")
	private Long boardNum;
	
	@Column(name="board_writer")
	private String boardWriter;

	@Column(name="board_title")
	private String boardTitle;

	@Column(name="board_content")
	private String boardContent;

	@Column(name="hit_count")
	private int hitCount;

	@Column(name="favorite_count")
	private int favoriteCount;

	@Column(name="create_date")
	@CreationTimestamp // 게시글 처음 생성시 자동으로 날짜 세팅
	private LocalDateTime createDate;
	
	@Column(name="update_date")
	@LastModifiedDate // 게시글이 수정된 마지막 날짜/시간 세팅
	private LocalDateTime updateDate;

	// 첨부파일이 있을 때
	@Column(name = "original_file_name")
	private String originalFileName;

	@Column(name = "saved_file_name")
	private String savedFileName;
	
	// DTO를 전달받아 Entity로 반환
	// 날짜는 빌드 하지 않아도 설정한 에노테이션으로 자동 생성됨
	public static BoardEntity toEntity(BoardDTO boardDTO) {
		return BoardEntity.builder()
				.boardNum(boardDTO.getBoardNum())
				.boardWriter(boardDTO.getBoardWriter())
				.boardTitle(boardDTO.getBoardTitle())
				.boardContent(boardDTO.getBoardContent())
				.hitCount(boardDTO.getHitCount())
				.favoriteCount(boardDTO.getFavoriteCount())
				.originalFileName(boardDTO.getOriginalFileName())
				.savedFileName(boardDTO.getSavedFileName())
				.build();
	}
}

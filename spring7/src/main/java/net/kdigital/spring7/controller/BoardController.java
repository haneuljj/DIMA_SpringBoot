package net.kdigital.spring7.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.spring7.dto.BoardDTO;
import net.kdigital.spring7.service.BoardService;
import net.kdigital.spring7.util.PageNavigator;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
	private BoardService boardService;
	
	// 생성자 초기화
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	// 파일의 저장경로 얻어오기
	@Value("${spring.servlet.multipart.location}")
	String uploadPath;

	// 한 페이지당 글의 최대 개수값 가져오기
	@Value("${user.board.pageLimit}")
	int pageLimit;
	
	/**
	 * 글 목록 요청 
	 * 1) index(첫화면)에서 넘어올 경우: searchWord, searchItem없으므로 기본값 세팅
	 * 	  1페이지를 요청한 것임
	 * 2) 목록상황에서 검색하여 넘어올 경우: searchWord, searchItem 값 가지고 넘어옴
	 * 	  1페이지 요청
	 * 3) 목록 화면 하단에서 페이지를 선택할 경우 선택한 값을 사용
	 * @return
	 */
	@GetMapping("/boardList")
	public String boardList(
		@PageableDefault(page=1) Pageable pageable, // 페이징을 해주는 객체, 요청한 페이지가 없으면 1페이지로 요청됨
		@RequestParam(name = "searchItem", defaultValue = "boardTitle") String searchItem,
		@RequestParam(name = "searchWord", defaultValue = "") String searchWord,
		Model model) {

		//List<BoardDTO> dtoList =  boardService.selectAll(searchItem, searchWord);
		Page<BoardDTO> dtoList = boardService.selectAll(pageable, searchItem, searchWord);

		// 페이지 번호 자동계산 해주는 PageNavigator 객체 생성
		int totalPages = (int)dtoList.getTotalPages();
		int page = pageable.getPageNumber();              

		PageNavigator navi = new PageNavigator(pageLimit, page, totalPages);

		model.addAttribute("list", dtoList);
		model.addAttribute("searchItem", searchItem);
		model.addAttribute("searchWord", searchWord);
		model.addAttribute("navi", navi);

		return "board/boardList";
	}
	
	/**
	 * 글쓰기 화면 요청
	 * @return
	 */
	@GetMapping("/boardWrite")
	public String boardWrite() {
		log.info("글쓰기 화면 요청");
		return "board/boardWrite";
	}
	
	/**
	 * DB에 글 등록 요청
	 */
	@PostMapping("/boardWrite")
	public String boardWrite(
			@ModelAttribute BoardDTO boardDTO
			) { 
		boardService.insertBoard(boardDTO);
		return "redirect:/board/boardList";
		
	}

	/**
	 * boardNum에 해당하는 글 한 개 조회
	 * @param boardNum
	 * @param model
	 * @return
	 */
	@GetMapping("/boardDetail")
	public String boardDetail(
		@RequestParam(name = "boardNum") Long boardNum,
		@RequestParam(name = "searchItem") String searchItem,
		@RequestParam(name = "searchWord") String searchWord,
		HttpServletRequest request,
		Model model
	) {

		String contextPath = request.getContextPath();
		
		//selectOne에 조회수 증가 함수 내용 포함시키면 안됨; 글 수정시에도 호출되기때문에 조회가 아닌 수정시에도 조회수 증가됨
		BoardDTO boardDTO = boardService.selectOne(boardNum);
		
		// 조회수 증가
		boardService.incrementHitCount(boardNum);
		model.addAttribute("board", boardDTO);
		model.addAttribute("searchItem", searchItem);
		model.addAttribute("searchWord", searchWord);
		model.addAttribute("contextPath", contextPath);

		return "board/boardDetail";
	}

	/**
	 * boardNum에 해당하는 글 삭제
	 * @param boardNum
	 * @return
	 */
	@GetMapping("/boardDelete")
	public String boardDelete(
		@RequestParam(name = "boardNum") Long boardNum,
		@RequestParam(name = "searchItem") String searchItem,
		@RequestParam(name = "searchWord") String searchWord,
		RedirectAttributes rttr
	) {
		boardService.deleteOne(boardNum);
		
		rttr.addAttribute("searchItem", searchItem);
		rttr.addAttribute("searchWord", searchWord);

		return "redirect:/board/boardList";
	}

	/**
	 * boardNum에 해당하는 글을 수정하기 위한 요청
	 * @param boardNum
	 * @param model
	 * @return
	 */
	@GetMapping("/boardUpdate")
	public String boardUpdate(
		@RequestParam(name = "boardNum") Long boardNum,
		@RequestParam(name = "searchItem") String searchItem,
		@RequestParam(name = "searchWord") String searchWord,
		Model model
	) {
		BoardDTO boardDTO = boardService.selectOne(boardNum);
		model.addAttribute("board", boardDTO);
		model.addAttribute("searchItem", searchItem);
		model.addAttribute("searchWord", searchWord);

		return "board/boardUpdate";
	}

	/**
	 * 전달받은 boardDTO를 받아 서비스단으로 전달
	 * @param boardDTO
	 * @param rttr
	 * @return
	 */
	@PostMapping("/boardUpdate")
	public String boardUpdate(
		@ModelAttribute BoardDTO boardDTO,
		@RequestParam(name = "searchItem") String searchItem,
		@RequestParam(name = "searchWord") String searchWord,
		RedirectAttributes rttr) {
		
		log.info("=================={}", boardDTO.toString());
		boardService.updateOne(boardDTO);
		// redirect시 값을 가지고 리턴해야할경우, rttr사용 필요 	
		rttr.addAttribute("boardNum", boardDTO.getBoardNum());
		rttr.addAttribute("searchItem", searchItem);
		rttr.addAttribute("searchWord", searchWord);
		

		return "redirect:/board/boardDetail";
	}

	/**
	 * 전달 받은 게시판 번호의 파일을 다운로드
	 * @return
	 */
	@GetMapping("/download")
	public String download(
		@RequestParam(name = "boardNum") Long boardNum,
		HttpServletResponse response
	) {
		BoardDTO boardDTO = boardService.selectOne(boardNum);
		String originalFileName = boardDTO.getOriginalFileName();
		String savedFileName = boardDTO.getSavedFileName();

		log.info("original File Name: {}", originalFileName);
		log.info("saved File Name: {}", savedFileName);
		log.info("upload path: {}", uploadPath);
		
		try {
			// 파일이름 인코딩, 파일이름에 한글이 섞여있으면 파일명 깨진채로 다운됨
			String tempName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8.toString());
			// 다운 요청한 사람의 network의 header부분에 세팅, 다운 받는 부분
			// 없으면 다운안되고 그냥 브라우저에서 열림
			response.setHeader("Content-Disposition", "attachment;filename="+tempName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String fullPath = uploadPath + "/" + savedFileName;

		// 스트림 설정 (실제 다운로드)
		// 하드에 있는 파일을 메모리로 끌어올려서 클라이언트에게 내려 보냄
		// 서버 입장에서 파일이 로컬에 있는 경우의 다운로드
		FileInputStream filein = null;
		ServletOutputStream fileout = null;

		try {
			// fullPath 경로에 있는 파일을 메모리에 올리는 것(Input Stream) 설정
			filein = new FileInputStream(fullPath);
			// 파일 내려받아주는 것(Output Stream) 설정: 로컬이 아닌 원격지로 데이터를 보내주기, 응답객체의 메서드 필요 
			fileout = response.getOutputStream();

			// filein에 있는 것을 fileout으로 반복적으로 끝날때까지 내보내기
			FileCopyUtils.copy(filein, fileout); 

			fileout.close(); // 파일을 연 순서의 반대로 닫기
			filein.close();

		} catch (Exception e) {
			e.printStackTrace();
		} 

		return null;
	}
	
	@GetMapping("/increaseFavoriteCount")
	@ResponseBody
	public int increaseFavoriteCount(
		@RequestParam(name = "boardNum") Long boardNum){

		log.info("============ increaseFavoriteCount{}", boardNum);
		int resultCount = boardService.increaseFavoriteCount(boardNum);

		return resultCount;
	}
}

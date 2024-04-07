package net.kdigital.spring7;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.kdigital.spring7.entity.BoardEntity;
import net.kdigital.spring7.repository.BoardRepository;

// 단위 테스트 코드
@SpringBootTest
class Spring7ApplicationTests {

	@Test
	void contextLoads() {
	}
	// 테스트 코드 - 메소드별로 실행, 생성자 주입방식으로 생성안됨- @Autowired사용
	@Autowired // bin을 주입받기 위한 하나의 annotation
	private BoardRepository repository;

	@Test 
	void testInsertBoard() {
		for(int i=0; i<31; ++i) {
			BoardEntity b = new BoardEntity();

			b.setBoardTitle("뿌앵"+(i+1));
			b.setBoardWriter("하느리");
			b.setBoardContent("뿌애애애앵 ㅜㅜ");

			repository.save(b);
		}
	}


}

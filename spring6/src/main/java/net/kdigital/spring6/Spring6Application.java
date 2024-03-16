package net.kdigital.spring6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.kdigital.spring6.dto.Sample;

@SpringBootApplication
public class Spring6Application {

	public static void main(String[] args) {
		SpringApplication.run(Spring6Application.class, args);

		/*
		 * // 1) 기본생성자로 생성
		 * Sample s1 = new Sample();
		 * s1.setSeq(1L);
		 * s1.setUserid("홍길동");
		 * s1.setUserpwd("1234");
		 * s1.setContent("오늘은 목요일");
		 * 
		 * System.out.println(s1);
		 * 
		 * // 2) 오버로딩 생성자로 생성
		 * // 같은 데이터 타입이 있을 경우 주의
		 * Sample s2 = new Sample(2L, "전우치", "1111", "집가고싶다");
		 * System.out.println(s2);
		 * 
		 * // 3) Builder 패턴으로 생성; 어느 속성에 어떤 값을 넣었는지 확실하게 알기 쉬움, 속성 순서 상관없음
		 * Sample s3 = Sample.builder()
		 * .seq(3L)
		 * .userid("손오공")
		 * .userpwd("4567")
		 * .content("안녕하시렵니까?")
		 * .build();
		 * System.out.println(s3);
		 * 
		 * // 4) 클래스 내부에 선언된 Builder 패턴으로 생성
		 * Sample s4 = Sample.builder()
		 * .userid("사오정")
		 * .content("졸리다")
		 * .build();
		 * 
		 * System.out.println(s4);
		 */
	}

}

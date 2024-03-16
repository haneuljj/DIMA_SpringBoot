package net.kdigital.spring6.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder // @AllArgsConstructor가 필요, 내부, 외부 Builder 둘 중 하나만 쓰기
public class Sample {
    private Long seq;
    private String userid;
    private String userpwd;
    private String content;

    // 클래스 안에서 Builder로 생성자 만들기, 모든 속성 안받고 생성가능
    @Builder
    public Sample(String userid, String content) {
        this.userid = userid;
        this.content = content;
    }
}

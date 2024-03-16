package main.java.net.kdigital.spring4.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
// @ModelAttribute로 객체 생성시에는 오버로딩생성자가 아닌 기본생성자 필요
// setter로 값을 생성된 객체에 넣어야함
// @NoArgsConstructor(생성자 안만들면 JVM이 자동생성)와 @Setter는 입력받은 데이터를 얻어오기 위해서 필수
// @Getter: model에 있는 값을 html에 꺼내 쓰려면 필수 !!
public class FriendDTO {
    private String fname; // Client쪽에서 보내는 변수명(html의 name값)과 같아야함 !!
    private int age;
    private String phone;
    private LocalDate birthday;
    private boolean active;

}

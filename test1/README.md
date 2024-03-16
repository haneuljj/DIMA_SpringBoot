## [문제] test1 프로젝트를 작성하시오
1) 포트번호는 8889로 설정
2) 정수 x와 y를 폼으로 입력받아 서버로 전송받고, Controller에서 전송받는 x,y를 더해서 그 결과를 로그로 출력하시오
3) 생성파일
    - MainController.java
    - CalculateController.java
    - index.html : 사용자가 입력할 폼(x, y의 입력상자)
    - result.html : 계산 성공을 알리는 "계산완료" 메시지 출력
    - 로그 메시지 : 12 + 24 = 36

4) 요청받은 데이터를 다시 받아서 보내기
    - result.html 수정 (thymeleaf를 포함시켜 서버에서 x, y, x+y 결과를 받아 출력하시오)
    - CalculateController.java에서는 Model에 x, y, x+y를 담아 response하시오
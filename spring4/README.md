2024년 3월 5일

[학습내용]
- Controller 단에서 객체(FriendDTO)나 파일이 첨부되어 전송될 때 사용하는 Annotation: @ModelAttribute
- 메소드의 매개변수 위치에서 사용
    ex) public String temp(@ModelAttribute FriendDTO friendDTO) {}

- 클라이언트에서 서버로 데이터를 전송할 때 위와 같이 객체로 받을 경우, 기본생성자가 호출된다
- 그리고 값을 넣을 때에는 Setter가 자동 호출되므로 DTO 객체를 이용해 데이터를 받을 때에는 반드시 기본생성자와 Setter가 필요하다

- 서버에서 클라이언트로 데이터를 보낼 때에는 보낼 데이터를 담을 모델 객체가 필요
    ex) public String temp(@ModelAttribute FriendDTO friendDTO, Model model) {
        model.addAttribute("변수명", 값);
    }

- html에서 thymeleaf로 데이터를 꺼낼떄에는 Getter가 필요
- 기본생성자, Setter, Getter는 dto를 통해 데이터를 담고 보낼 때 필수


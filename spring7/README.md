2024년 3월 12일 (화)

- 회원 전용 게시판
1. 첫요청
    - 첫화면 요청
2. 회원 관리
    - 회원가입 화면 요청
    - 회원가입 처리 요청
    - 로그인 화면 요청
    - 로그아웃
3. 게시판 관리
    - 게시글 목록 요청
    - 게시글 등록 화면 요청
    - 게시글 등록 처리
    - 게시글 조회 요청
    - 게시글 삭제 요청
    - 게시글 수정 화면 요청
    - 게시글 수정 처리
    - 첨부파일 다운로드
    - 첨부파일 수정
    - 첨부파일 삭제
    - 조회수 / 좋아요 증가
4. 댓글 관리 (JQuery - AJAX로 처리)
    - 댓글 전체 조회
    - 댓글 등록
    - 댓글 삭제
    - 댓글 수정 처리


<<파일 첨부시 작업>>
1) application.properties
    - 디렉토리, 업로드 파일용량 제한 설정
2) boardWrite.html
    - form 태그 옵션 설정, input태그의 name값 설정 및 확인
3) BoardDTO.java에서 파일 받도록 처리
    - MultipartFile 받을 수 있도록 처리
    - 이름 추출 작업 처리 (OriginalFileName / SavedFileName)


<<게시글 겁색 기능 처리>>
1) 글제목 / 작성자로 검색시 쿼리문
    - SELECT * FROM board
      WHERE boardTitle/boardWriter LIKE '%' || searchWord || '%';
2) boardRepository에서 검색 기능 처리 함수 설정
    - findAll() --> 기본제공되는 전체 조회 기능
    - findBy컬럼명Containing(String searchWord)
    --> 조회 쿼리문
        SELECT * FROM board
        WHERE 컬럼명 LIKE '%' || searchWord || '%';


<<페이징 처리>>



<<회원 관련 로직>>
1. 회원가입
2. 로그인
3. 로그아웃
4. 개인정보수정 

1) Dependency 추가
    - Security
    - Thymeleaf Security
참고) 인증 / 인가
    - 인증(Authentication): 접속자가 회원인지 아닌지 구별 - 아이디와 비밀번호를 통해 인증
    - 인가(Authorization): 인증 받은 회원 중 접근 가능한 페이지를 구별 
2) 설정 
    - 설정 파일 생성 - SecurityConfig.java
    - 인증 절차가 필요한 request와 인증 절차가 필요없는 request를 분리하여 설정
    - 로그인 화면 작성한 후 설정파일에 등록
3) Security시 필요한 클래스 생성
DTO: UserDetails 구현한 LoginUserDetails Class 생성
Service: UserDetailsService 구현한 Service

<Bean>
스프링이 관리하는 객체
-> @Service, @Controller, ServletResponse, ServletRequest, @Bean(함수에다가), @Component(클래스에다가) 

<Cookie & Session>
- 페이지 이동시 계속 갖고 있으면서 현재 접속중인, 페이지 요청한 사용자 식별?
- http ==> stateless, connectionless
- JSessionID를 안 갖고 다니면 페이지 이동시 접속자와의 관계가 끊어짐
- JSessionID를 관리하려면 메모리에 올려 관리, 로그인시 한번만 하면 로그아웃하기 전까지 계속 세션 유지
- 인증된 사용자는 사용자의 서버 컴퓨터에 세션아이디 발급하여 저장됨

- Session: 일정 시간 동안 같은 사용자(브라우저)로부터 들어오는 일련의 요구를 하나의 상태로 보고, 그 상태를 유지시키는 기술이다.
여기서 일정 시간은 방문자가 웹 브라우저를 통해 웹 서버에 접속한 시점부터 웹 브라우저를 종료하여 연결을 끝내는 시점을 말한다.
브라우저가 종료되기 전까지 클라이언트의 요청을 유지하게 해주는 기술
- Cookie: HTTP의 일종으로 사용자가 어떤 웹 사이트를 방문할 경우, 해당 사이트가 사용하고 있는 서버에서 사용자의 컴퓨터에 저장하는 작은 기록 정보 파일

<회원가입>
- 회원가입시 이미 존재하는 아이디인지 체크 --> ajax로 작성
- 입력할때마다 (keyup event) DB로 요청 보내기
- 사용할 수 없다면(사용중인 아이디라면) 빨간색으로 경고 출력
- 사용가능한 아이디라면 파란색으로 출력

- 사용 불가한 아이디
    1) 글자수 제한: 3~5글자 사이
    2) 이미 사용중인 아이디


---------3/27
<<댓글 수정 방법>>
1) 수정버튼 클릭 --> 댓글 번호 이용해 selectOne
2) 조회된 댓글 내용을 댓글 입력창에 출력
3) 댓글 작성 버튼을 댓글 수정버튼으로 바꾸기 
            --> 댓글 작성시에는 댓글 수정버튼이 disabled, 댓글 수정시에는 댓글 작성버튼이 disabled
4) 댓글수정 버튼 클릭 --> DB에서 댓글을 update
5) 수정된 댓글을 화면에 출력


인증하지 않은 사용자
- 게시글/댓글 확인 가능
- 댓글 입력/수정하는 버튼 숨김
- 게시글 수정/삭제 버튼 숨김
- 목록버튼 보임

인증한 사용자
- 게시글/댓글 확인 가능
- 댓글 입력 버튼 보임
- 자신이 쓴 댓글의 삭제/수정 버튼 보임
- 자신이 쓴 게시글의 수정/삭제 버튼 보임


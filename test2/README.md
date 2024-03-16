2024/03/08

[실습] GuestBook(방명록) 작성하기
1) 주요기능
- 첫화면
- 방명록 글 쓰기
- 전체 방명록 보기
- 방명록 삭제

2) 요청 URL 종류 
    - / : 첫요청, GET방식, MainController
    - /insert : 저장하기 위한 화면요청, GET방식, GuestbookController (데이터 입력을 위한 화면요청)
    - /insert : 입력받은 데이터를 받아서 저장, POST방식, GuestbookController (DB에 저장하기 위한 요청)
    - /list : 목록요청, GET방식, GuestbookController(전체 조회)
    - /deleteOne : 삭제요청, GET방식, GuestbookController(글 한개 삭제)
                   비밀번호 입력받아 비밀번호가 같을 경우에만 삭제 (JS의 prompt()로 처리)
    - /selectOne: 한명 조회 하기, 
    - /update: 데이터 수정을 위한 화면 요청, GET방식, GuestbookController
    - /updateOne: 실제 데이터 수정 처리 요청, POST방식, GuestbookController

3) 추가 작업
    - findAll()시 Sort 하기 - 파라미터 추가
    - 수정 요청시: true/false 값을 화면에 반영
    - 유효성 검증
        (1) js로 유효성 검증하는 방법
        (2) Spring에서 제공하는 Validate를 이용하는 방법 - Annotation으로 처리
            a. dependency 추가 작업
            b. DTO에서 검증 annotation 및 메시지 추가
            c. Controller에서 오류 발생시 해당 오류를 담을 객체(Bind처리)가 필요
            d. 검증 시 오류가 발생한 경우에는 View단에 그 사실을 알리고 다시 입력받아야함
            e. 오류가 없으면 저장 및 수정작업 실시
    - updateProc에서 JPQL로 하지 않고 예전 방식으로 변경: find해서 set방식으로 업데이트하기

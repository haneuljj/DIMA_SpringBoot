2024/03/07

# Dependencies
- spring data jpa
- lombok
- spring web
- oracle driver
- spring web devtools
- thymeleaf

# 학습내용
1) 클라이언트로부터 받은 데이터를 DB에 CRUD
2) 요청 URL 종류 
    - / : 첫요청, GET방식, MainController
    - /insert : 저장하기 위한 화면요청, GET방식, FriendController (데이터 입력을 위한 화면요청)
    - /insert : 입력받은 데이터를 받아서 저장, POST방식, FriendController (DB에 저장하기 위한 요청)
    - /selectOne : 조회요청, GET방식, FriendController (1명 조회-sequence번호로 조회)
    - /list : 목록요청, GET방식, FriendController(전체 조회)
    - /deletOne : 삭제 요청
    - /update : 데이터 수정을 위한 화면요청, GET방식, FriendController (데이터 수정을 위한 화면요청)
                DB에서 수정하고자하는 데이터를 조회한 후 화면에 출력
    - /updateProc : 수정처리요청, POST방식, FriendController
    - /selectOne: 조회(1명)
    - /deleteOne: 삭제(1명) GET방식, FriendController에서 데이터 삭제 요청

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
    - updateProc에서 JPQL로 하지 않고 예전 방식으로 변경
    

* 참고: Builder 패턴으로 객체 생성하는 방법
    - lombok에서 제공하는 @Builder 이용하여 생성


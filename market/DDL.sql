/* 회원정보 테이블 */
DROP TABLE market_member;

CREATE TABLE market_member
(
    member_id VARCHAR2(20) PRIMARY KEY      -- 사용자 아이디
    , member_pw VARCHAR2(100) NOT NULL      -- 암호화된 로그인 비밀번호
    , member_name VARCHAR2(20) NOT NULL     -- 사용자 이름
    , phone VARCHAR2(20) NOT NULL           -- 전화번호
    , enabled CHAR(1) DEFAULT '1' CHECK(enabled IN ('1', '0'))  -- 계정상태 (1:사용가능, 0: 사용불가)
    , rolename VARCHAR2(20) DEFAULT 'ROLE_USER'  -- 사용자 권한, 모두 'ROLE_USER'
);

/* 게시판 글 정보 테이블 */
DROP TABLE market_board;
DROP SEQUENCE market_board_seq;

CREATE TABLE market_board
(
    board_num NUMBER PRIMARY KEY        -- 글 번호
    , member_id VARCHAR2(20) NOT NULL   -- 작성자 아이디
    , title VARCHAR2(200) NOT NULL      -- 제목
    , contents VARCHAR2(2000) NOT NULL  -- 상품 소개글 내용
    , input_date DATE DEFAULT sysdate   -- 작성일
    , category VARCHAR2(50)             -- 상품분류 (computer / camera / car)
    , soldout CAHR(1) DEFAULT 'N' CHECK(soldout IN ('N', 'Y'))  -- 판매완료여부 ('N': 판매중, 'Y': 판매완료)
    , buyer_id VARCHAR2(20)             -- 구매자 아이디
);

CREATE SEQUENCE market_board_seq;

/* 게시판 댓글 정보 테이블 */
DROP TABLE market_comment;
DROP SEQUENCE market_reply_seq;

CREATE TABLE market_comment
(
    content_num NUMBER PRIMARY KEY      -- 일련번호
    , board_num NUMBER REFERENCES market_board(board_num) ON DELETE CASCADE   -- 판매글 본문 번호
    , member_id VARCHAR2(20) NOT NULL   -- 작성자 아이디
    , comment_text VARCHAR2(500)        -- Comment 내용
    , input_date DATE DEFAULT sysdate   -- 작성일
);

CREATE SEQUENCE market_reply_seq;

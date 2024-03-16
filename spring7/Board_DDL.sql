-- 2024년 3월 12일
-- 회원전용 게시판 테이블

-- 객체 삭제 명령
drop table reply;
drop table board;
drop table members;

drop sequence reply;
drop sequence board_seq;
drop sequence members;

-- 1) 회원 테이블



-- 2) 게시판 테이블
create table board
(
    board_num           number constraint board_seq primary key
    , board_writer      varchar2(20) constraint board_writer not null
    , board_title       varchar2(200) default '제목없음'
    , board_content     varchar2(4000) 
    , hit_count         number default 0
    , favorite_count    number default 0
    , create_Date       date default sysdate
    , update_Date       date
    
);

create sequence board_seq;

-- 첨부파일을 위한 컬럼 추가
ALTER TABLE board ADD originalFileName VARCHAR2(200);
ALTER TABLE board ADD savedFileName VARCHAR2(200);

ALTER TABLE board DROP COLUMN originalFileName;
ALTER TABLE board DROP COLUMN savedFileName;

-- 3) 댓글 테이블

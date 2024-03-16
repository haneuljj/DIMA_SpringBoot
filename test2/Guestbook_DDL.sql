drop table guestbook;
drop sequence guestbook_seq;

create table guestbook
(
    guest_seq NUMBER PRIMARY KEY         -- 일련번호
    , guest_name VARCHAR2(50) NOT NULL   -- 글쓴이
    , guest_pwd VARCHAR2(20) NOT NULL    -- 비밀번호
    , guest_text VARCHAR2(2000)         -- 글내용
    , regdate DATE DEFAULT SYSDATE      -- 글쓴날짜
);

create sequence guestbook_seq;

select * from guestbook;
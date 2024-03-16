/* spring6 */
drop table friend;
drop sequence friend_seq;

create table friend
(
    freind_seq NUMBER PRIMARY KEY
    , fname VARCHAR2(30) NOT NULL
    , age NUMBER(3) DEFAULT 1
    , phone VARCHAR2(20) UNIQUE
    , birthday DATE DEFAULT sysdate
    , active CHAR(1) DEFAULT '1'
);

create sequence friend_seq;

select * from friend;
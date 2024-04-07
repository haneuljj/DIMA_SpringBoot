-- movie table
DROP TABLE movie;
DROP SEQUENCE movie_seq;

CREATE TABLE movie
(
    movie_num NUMBER PRIMARY KEY        -- 영화 번호 
    , genre VARCHAR2(50) NOT NULL       -- 영화 장르
    , movie_name VARCHAR2(50) NOT NULL  -- 영화 이름
    , movie_summary VARCHAR2(2000) NOT NULL -- 영화 내용
    , movie_date DATE DEFAULT sysdate       -- 영화 정보 등록일
);

CREATE SEQUENCE movie_seq;

-- review table
DROP TABLE review;
DROP SEQUENCE review_seq;

CREATE TABLE review
(
    review_num NUMBER PRIMARY KEY           -- 리뷰 번호
    , reviewer_name VARCHAR2(50) NOT NULL    -- 리뷰 작성자 이름
    , movie_num NUMBER REFERENCES movie(movie_num) ON DELETE CASCADE NOT NULL -- 영화 정보 기본키
    , review_text VARCHAR2(2000) NOT NULL   -- 리뷰 내용
    , score NUMBER DEFAULT 0                  -- 영화 평점
    , review_date DATE DEFAULT sysdate  -- 리뷰 정보 등록일
);

CREATE SEQUENCE review_seq;


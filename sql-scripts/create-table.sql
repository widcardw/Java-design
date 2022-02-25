create
database online_consulting;

use
online_consulting;

create table tbl_user
(
    id         int auto_increment,
    username   varchar(100) not null,
    nickname   varchar(100) not null,
    password   varchar(100) not null,
    level      int          not null,
    is_teacher boolean      not null,
    constraint tbl_user_pk
        primary key (id)
);

create unique index tbl_user_username_uindex
    on tbl_user (username);



create table tbl_course
(
    id          int not null auto_increment,
    course_name varchar(100) not null,
    primary key (id)
);

create table tbl_sc
(
    user_id int not null,
    course_id  int not null,
    score      int not null,
    primary key (user_id, course_id)
);

create table tbl_post
(
    id             int auto_increment,
    title          varchar(100)  not null,
    content        varchar(2000) not null,
    author_id      int           not null,
    question_score int           not null,
    create_date    datetime      not null,
    constraint tbl_post_pk
        primary key (id)
);

create table tbl_comment
(
    id           int auto_increment,
    user_id      int           not null,
    content      varchar(2000) not null,
    answer_score int           not null,
    post_id      int           not null,
    constraint tbl_comment_pk
        primary key (id)
);

create table tbl_tag
(
    id       int auto_increment,
    tag_name varchar(100) not null,
    constraint tbl_tags_pk
        primary key (id)
);

create table tbl_tag_relation
(
    post_id int not null,
    tag_id  int not null,
    constraint tbl_tag_relation_pk
        primary key (post_id, tag_id)
);



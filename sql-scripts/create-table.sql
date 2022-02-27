create
database online_consulting;

use
online_consulting;

create table tbl_comment
(
    id           int auto_increment
        primary key,
    user_id      int      not null,
    content      text     not null,
    answer_score int      not null,
    post_id      int      not null,
    create_date  datetime null
);

create table tbl_course
(
    id          int auto_increment
        primary key,
    course_name varchar(100) not null
);

create table tbl_post
(
    id             int auto_increment
        primary key,
    title          varchar(100) not null,
    content        text         not null,
    author_id      int          not null,
    question_score int          not null,
    create_date    datetime     not null,
    course_id      int          not null
);

create table tbl_sc
(
    user_id   int not null,
    course_id int not null,
    score     int not null,
    primary key (user_id, course_id)
);

create table tbl_tag
(
    id        int auto_increment
        primary key,
    tag_name  varchar(100) not null,
    course_id int          not null
);

create table tbl_tag_relation
(
    post_id int not null,
    tag_id  int not null,
    primary key (post_id, tag_id)
);

create table tbl_user
(
    id         int auto_increment
        primary key,
    username   varchar(100)  not null,
    nickname   varchar(100)  not null,
    password   varchar(100)  not null,
    level      int default 1 not null,
    is_teacher int           not null,
    constraint tbl_user_username_uindex
        unique (username)
);


create table member
(
    id       bigint      not null auto_increment primary key,
    email    varchar(30) not null default '',
    password varchar(50) not null default '',
    name     varchar(20) not null default '',
)

create table member_vacation
(
    id            bigint not null auto_increment primary key,
    member_id     bigint not null default 0,
    vacation_days int    not null default 0,
    year          int    not null default 0
)

create table member_vacation_usage
(
    id        bigint  not null auto_increment primary key,
    member_id bigint  not null default 0,
    start_at  date    not null default 0,
    end_at    date,
    use_day   decimal(4, 2) null default 0,
    comments  varchar(50),
    is_cancel char(1) not null default 'N'
)


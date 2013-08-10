create schema if not exists `dp_project` default character set latin1;
use `dp_project`;

drop table if exists `dp_project`.`bookstore`;

create table if not exists `dp_project`.`bookstore`(
`ISBN` varchar(128) NOT NULL,
`bookname` varchar(256) NOT NULL,
`authors` varchar(256),
`edition` int,
`price` float,
`quantities` int,
primary key (`ISBN`)
)
default character set = utf8;
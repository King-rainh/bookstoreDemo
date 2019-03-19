drop database bookstore;
create database bookstore;
use bookstore;

create table user(
    id int primary key auto_increment,
    email varchar(255) unique,
    password varchar(32) not null,
    role varchar(20)
);

create table address(
    id int primary key auto_increment,
    userId int ,
    consignee varchar(50) not null,
    phone varchar(20) not null ,
    location varchar(255) not null,
    createTime datetime,
    isDefault boolean,
    foreign key(userId) references user(id)
);

create table category(
    id int primary key auto_increment,
    name varchar(50) not null
);

create table book(
    id int primary key auto_increment,
    categoryId int,
    name varchar(100) not null,
    price double,
    author varchar(50) not null ,
    press varchar(50) not null,
    publishDate date,
    wordCount int,
    pageCount int,
    isbn varchar(50),
    coverImage varchar(255),
    description text,
    isDeleted boolean default false,
    foreign key(categoryId) references category(id)
);
    
create table orders(
    id varchar(50) primary key,
    userId int,
    addressInfo text not null,
    totalPrice double,
    status varchar(20) not null,
    createTime datetime,
    foreign key(userId) references user(id)
);
    
create table ordersItem(
    id int primary key auto_increment,
    ordersId varchar(50),
    bookId int,
    bookName varchar(100),
    price double,
    count int,
    foreign key(ordersId) references orders(id),
    foreign key(bookId) references book(id)
);
    
insert into user(email,password,role) values('manager@bookstore.com',md5('123456'),'manager');

insert into category(name) values('小说');
insert into category(name) values('文学');
insert into category(name) values('传记');
insert into category(name) values('艺术');
insert into category(name) values('科普/百科');
insert into category(name) values('动漫/幽默');

insert into book(categoryId,name,price,author,publishDate,press,wordCount,pageCount,isbn,coverImage,description)  values(1,'三体全集（全3册）',168.00,'刘慈欣','2011-11-01','重庆出版社',891924,1326,'9787536692930','2016-07-15/santi.jpg','三体人在利用魔法般的科技锁死了地球人的科学之后，庞大的宇宙舰队杀气腾腾地直扑太阳系，意欲清除地球文明。面对前所未有的危局，经历过无数磨难的地球人组建起同样庞大的太空舰队，同时，利用三体人思维透明的致命缺陷，制订了神秘莫测的“面壁计划”，精选出四位“面壁者”。秘密展开对三体人的反击。');
insert into book(categoryId,name,price,author,publishDate,press,wordCount,pageCount,isbn,coverImage,description)  values(5,'时间简史',30.00,'（英）史蒂芬·霍金','2010-07-01','湖南科学技术出版社',109454,206,'9787535710659','2016-07-15/shijianjianshi.jpg','史蒂芬·霍金的《时间简史》，自1988年首版以来的十年岁月里，已成为科学著作的里程碑，在全球销售已近1000万册。首版内容介绍了宇宙本性*前沿的知识。微观和宏观世界观测技术领域方面10年来的进展证明了霍金教授的许多理论预言。他为了把观测的新知识介绍给读者，重写了前言，全面更新的原版的内容，并新增了一章有关虫洞和时间旅行的激动人心的课题。');
insert into book(categoryId,name,price,author,publishDate,press,wordCount,pageCount,isbn,coverImage,description)  values(2,'边城',35.00,'沈从文','2012-02-01','北京燕山出版社',136315,188,'9787540218003','2016-07-15/biancheng.jpg','小说《边城》无疑是沈从文的代表作，写于一九三三年至一九三四年初。这篇作品如沈从文的其他湘西作品，着眼于普通人、善良人的命运变迁，描摹了湘女翠翠阴差阳错的生活悲剧，诚如作者所言：“一切充满了善，然而到处是不凑巧。既然是不凑巧，因之素朴的善终难免产生悲剧。”《边城》写出了一种如梦似幻之美，像摆渡、教子、救人、助人、送葬这些日常小事，在作者来都显得相当理想化，颇有几分“君子田”的气象。当然，矛盾也并非不存在，明眼人一看便知，作者所用的背景材料中便隐伏着社会矛盾的影子。作者亦不曾讳言他的写作意图是支持“民族复兴大业的人”，“给他们一种勇气和信心”。本书分别从沈从文的《湘行散记》、《湘西》、《从文自传》中搜辑名篇，精益求精，和其小说名作《边城》、《萧萧》、《三三》等并行，可说是基本囊括了沈从文作品的精髓，且较能体现沈氏文风的别样神采。');
insert into book(categoryId,name,price,author,publishDate,press,wordCount,pageCount,isbn,coverImage,description)  values(1,'了不起的盖茨比',19.50,'（美）菲茨杰拉德','2011-11-01','清华大学出版社',240240,185,'9787302266761','2016-07-15/liaobuqidegaicibi.jpg','《了不起的盖茨比》是世界文学经典名著，被誉为20世纪最伟大的英文小说之一。故事的主人公盖茨比出身寒微，一次偶然的机会他认识了富家少女黛西，两人一见钟情，私订终身。后来黛西背叛了他，嫁给了有钱人汤姆。盖茨比为了赢得爱情，不择手段聚积金钱。为了追求黛西，盖茨比耗尽了自己的感情和才智，但理想最终还是破灭了，他带着残破的梦离开了人世。盖茨比是了不起的，他用生命谱写了一曲“爵士年代”的哀伤恋歌；盖茨比的悲剧是“美国梦”破灭的典型代表。');
insert into book(categoryId,name,price,author,publishDate,press,wordCount,pageCount,isbn,coverImage,description)  values(1,'三体全集（全3册）2',168.00,'刘慈欣','2011-11-01','重庆出版社',891924,1326,'9787536692930','2016-07-15/santi.jpg','三体人在利用魔法般的科技锁死了地球人的科学之后，庞大的宇宙舰队杀气腾腾地直扑太阳系，意欲清除地球文明。面对前所未有的危局，经历过无数磨难的地球人组建起同样庞大的太空舰队，同时，利用三体人思维透明的致命缺陷，制订了神秘莫测的“面壁计划”，精选出四位“面壁者”。秘密展开对三体人的反击。');
insert into book(categoryId,name,price,author,publishDate,press,wordCount,pageCount,isbn,coverImage,description)  values(1,'三体全集（全3册）3',168.00,'刘慈欣','2011-11-01','重庆出版社',891924,1326,'9787536692930','2016-07-15/santi.jpg','三体人在利用魔法般的科技锁死了地球人的科学之后，庞大的宇宙舰队杀气腾腾地直扑太阳系，意欲清除地球文明。面对前所未有的危局，经历过无数磨难的地球人组建起同样庞大的太空舰队，同时，利用三体人思维透明的致命缺陷，制订了神秘莫测的“面壁计划”，精选出四位“面壁者”。秘密展开对三体人的反击。');
insert into book(categoryId,name,price,author,publishDate,press,wordCount,pageCount,isbn,coverImage,description)  values(1,'三体全集（全3册）4',168.00,'刘慈欣','2011-11-01','重庆出版社',891924,1326,'9787536692930','2016-07-15/santi.jpg','三体人在利用魔法般的科技锁死了地球人的科学之后，庞大的宇宙舰队杀气腾腾地直扑太阳系，意欲清除地球文明。面对前所未有的危局，经历过无数磨难的地球人组建起同样庞大的太空舰队，同时，利用三体人思维透明的致命缺陷，制订了神秘莫测的“面壁计划”，精选出四位“面壁者”。秘密展开对三体人的反击。');
insert into book(categoryId,name,price,author,publishDate,press,wordCount,pageCount,isbn,coverImage,description)  values(1,'三体全集（全3册）5',168.00,'刘慈欣','2011-11-01','重庆出版社',891924,1326,'9787536692930','2016-07-15/santi.jpg','三体人在利用魔法般的科技锁死了地球人的科学之后，庞大的宇宙舰队杀气腾腾地直扑太阳系，意欲清除地球文明。面对前所未有的危局，经历过无数磨难的地球人组建起同样庞大的太空舰队，同时，利用三体人思维透明的致命缺陷，制订了神秘莫测的“面壁计划”，精选出四位“面壁者”。秘密展开对三体人的反击。');
insert into book(categoryId,name,price,author,publishDate,press,wordCount,pageCount,isbn,coverImage,description)  values(1,'三体全集（全3册）6',168.00,'刘慈欣','2011-11-01','重庆出版社',891924,1326,'9787536692930','2016-07-15/santi.jpg','三体人在利用魔法般的科技锁死了地球人的科学之后，庞大的宇宙舰队杀气腾腾地直扑太阳系，意欲清除地球文明。面对前所未有的危局，经历过无数磨难的地球人组建起同样庞大的太空舰队，同时，利用三体人思维透明的致命缺陷，制订了神秘莫测的“面壁计划”，精选出四位“面壁者”。秘密展开对三体人的反击。');
insert into book(categoryId,name,price,author,publishDate,press,wordCount,pageCount,isbn,coverImage,description)  values(1,'三体全集（全3册）7',168.00,'刘慈欣','2011-11-01','重庆出版社',891924,1326,'9787536692930','2016-07-15/santi.jpg','三体人在利用魔法般的科技锁死了地球人的科学之后，庞大的宇宙舰队杀气腾腾地直扑太阳系，意欲清除地球文明。面对前所未有的危局，经历过无数磨难的地球人组建起同样庞大的太空舰队，同时，利用三体人思维透明的致命缺陷，制订了神秘莫测的“面壁计划”，精选出四位“面壁者”。秘密展开对三体人的反击。');
insert into book(categoryId,name,price,author,publishDate,press,wordCount,pageCount,isbn,coverImage,description)  values(1,'三体全集（全3册）8',168.00,'刘慈欣','2011-11-01','重庆出版社',891924,1326,'9787536692930','2016-07-15/santi.jpg','三体人在利用魔法般的科技锁死了地球人的科学之后，庞大的宇宙舰队杀气腾腾地直扑太阳系，意欲清除地球文明。面对前所未有的危局，经历过无数磨难的地球人组建起同样庞大的太空舰队，同时，利用三体人思维透明的致命缺陷，制订了神秘莫测的“面壁计划”，精选出四位“面壁者”。秘密展开对三体人的反击。');
insert into book(categoryId,name,price,author,publishDate,press,wordCount,pageCount,isbn,coverImage,description)  values(1,'三体全集（全3册）9',168.00,'刘慈欣','2011-11-01','重庆出版社',891924,1326,'9787536692930','2016-07-15/santi.jpg','三体人在利用魔法般的科技锁死了地球人的科学之后，庞大的宇宙舰队杀气腾腾地直扑太阳系，意欲清除地球文明。面对前所未有的危局，经历过无数磨难的地球人组建起同样庞大的太空舰队，同时，利用三体人思维透明的致命缺陷，制订了神秘莫测的“面壁计划”，精选出四位“面壁者”。秘密展开对三体人的反击。');
insert into book(categoryId,name,price,author,publishDate,press,wordCount,pageCount,isbn,coverImage,description)  values(1,'三体全集（全3册）10',168.00,'刘慈欣','2011-11-01','重庆出版社',891924,1326,'9787536692930','2016-07-15/santi.jpg','三体人在利用魔法般的科技锁死了地球人的科学之后，庞大的宇宙舰队杀气腾腾地直扑太阳系，意欲清除地球文明。面对前所未有的危局，经历过无数磨难的地球人组建起同样庞大的太空舰队，同时，利用三体人思维透明的致命缺陷，制订了神秘莫测的“面壁计划”，精选出四位“面壁者”。秘密展开对三体人的反击。');
insert into book(categoryId,name,price,author,publishDate,press,wordCount,pageCount,isbn,coverImage,description)  values(1,'三体全集（全3册）11',168.00,'刘慈欣','2011-11-01','重庆出版社',891924,1326,'9787536692930','2016-07-15/santi.jpg','三体人在利用魔法般的科技锁死了地球人的科学之后，庞大的宇宙舰队杀气腾腾地直扑太阳系，意欲清除地球文明。面对前所未有的危局，经历过无数磨难的地球人组建起同样庞大的太空舰队，同时，利用三体人思维透明的致命缺陷，制订了神秘莫测的“面壁计划”，精选出四位“面壁者”。秘密展开对三体人的反击。');
insert into book(categoryId,name,price,author,publishDate,press,wordCount,pageCount,isbn,coverImage,description)  values(1,'三体全集（全3册）12',168.00,'刘慈欣','2011-11-01','重庆出版社',891924,1326,'9787536692930','2016-07-15/santi.jpg','三体人在利用魔法般的科技锁死了地球人的科学之后，庞大的宇宙舰队杀气腾腾地直扑太阳系，意欲清除地球文明。面对前所未有的危局，经历过无数磨难的地球人组建起同样庞大的太空舰队，同时，利用三体人思维透明的致命缺陷，制订了神秘莫测的“面壁计划”，精选出四位“面壁者”。秘密展开对三体人的反击。');
insert into book(categoryId,name,price,author,publishDate,press,wordCount,pageCount,isbn,coverImage,description)  values(1,'三体全集（全3册）13',168.00,'刘慈欣','2011-11-01','重庆出版社',891924,1326,'9787536692930','2016-07-15/santi.jpg','三体人在利用魔法般的科技锁死了地球人的科学之后，庞大的宇宙舰队杀气腾腾地直扑太阳系，意欲清除地球文明。面对前所未有的危局，经历过无数磨难的地球人组建起同样庞大的太空舰队，同时，利用三体人思维透明的致命缺陷，制订了神秘莫测的“面壁计划”，精选出四位“面壁者”。秘密展开对三体人的反击。');
insert into book(categoryId,name,price,author,publishDate,press,wordCount,pageCount,isbn,coverImage,description)  values(1,'三体全集（全3册）14',168.00,'刘慈欣','2011-11-01','重庆出版社',891924,1326,'9787536692930','2016-07-15/santi.jpg','三体人在利用魔法般的科技锁死了地球人的科学之后，庞大的宇宙舰队杀气腾腾地直扑太阳系，意欲清除地球文明。面对前所未有的危局，经历过无数磨难的地球人组建起同样庞大的太空舰队，同时，利用三体人思维透明的致命缺陷，制订了神秘莫测的“面壁计划”，精选出四位“面壁者”。秘密展开对三体人的反击。');
insert into book(categoryId,name,price,author,publishDate,press,wordCount,pageCount,isbn,coverImage,description)  values(1,'三体全集（全3册）15',168.00,'刘慈欣','2011-11-01','重庆出版社',891924,1326,'9787536692930','2016-07-15/santi.jpg','三体人在利用魔法般的科技锁死了地球人的科学之后，庞大的宇宙舰队杀气腾腾地直扑太阳系，意欲清除地球文明。面对前所未有的危局，经历过无数磨难的地球人组建起同样庞大的太空舰队，同时，利用三体人思维透明的致命缺陷，制订了神秘莫测的“面壁计划”，精选出四位“面壁者”。秘密展开对三体人的反击。');
insert into book(categoryId,name,price,author,publishDate,press,wordCount,pageCount,isbn,coverImage,description)  values(1,'三体全集（全3册）16',168.00,'刘慈欣','2011-11-01','重庆出版社',891924,1326,'9787536692930','2016-07-15/santi.jpg','三体人在利用魔法般的科技锁死了地球人的科学之后，庞大的宇宙舰队杀气腾腾地直扑太阳系，意欲清除地球文明。面对前所未有的危局，经历过无数磨难的地球人组建起同样庞大的太空舰队，同时，利用三体人思维透明的致命缺陷，制订了神秘莫测的“面壁计划”，精选出四位“面壁者”。秘密展开对三体人的反击。');
；
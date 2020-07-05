  
insert into projet7.roles value(1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

insert into projet7.users value(1, 'user@gmail.com', '$2a$10$rBH/auEBl4jC2PfdFaodJunzYlyBVsMWc56Q5VtJ9Id7v9/BJXsjq', 'user'),
					   (2, 'admin@gmail.com', '$2a$10$arBPccdH6MQYseCLDuxJ7u2BPFdxZSd.kKpjZQO7UA.y34PEoSzjC', 'admin');

insert into projet7.user_role value(1, 1), (2, 1), (2, 2);

insert into projet7.books value(1, 'JRRTolkien', 'Allen & Unwin','1937-09-21 00:00:00', 'Le Hobbit');
insert into projet7.books value(2, 'JRRTolkien', 'Allen & Unwin','1954-01-01 00:00:00', 'The Lord of the Rings');
insert into projet7.books value(3, 'JRRTolkien', 'Allen & Unwin','1980-01-01 00:00:00', 'Contes et légendes inachevés');
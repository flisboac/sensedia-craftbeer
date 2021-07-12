truncate table beer;
alter sequence sq_beer restart with 2;
insert into beer (id, name, category, ingredients, alcohol_content, price, created_at) values (1, 'a beer 1', 'a beer category', 'beer is made of beer', 'any%', 1.23, current_timestamp), (2, 'a beer 2', 'a beer category', 'beer is made of beer', 'any%', 1.23, current_timestamp);

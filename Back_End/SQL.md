# Final Year Project Database SQL

## Database Setting
```
alter session set nls_date_format='DD-MM-YYYY HH24:MI:SS';
```


## User Table

```
create table fyp_user (
user_id number(5)NOT NULL,
user_username varchar2(20) NOT NULL,
user_email varchar2(50) NOT NULL,
user_phone number(10) NOT NULL,
user_password varchar2(50) NOT NULL,
CONSTRAINT fyp_user_pk PRIMARY KEY (user_id)
);
```
### increase for userID
```
CREATE SEQUENCE userid_increase
  START WITH 10000
  INCREMENT BY 1;
```

### insert data for testing
```
insert into fyp_user VALUES (userid_increase.nextval, 'qwe', 'qwe@qwe',12345678,'qwe');
insert into fyp_user VALUES (userid_increase.nextval, 'asd', 'asd@asd',12345679,'asd');
insert into fyp_user VALUES (userid_increase.nextval, 'zxc', 'zxc@zxc',12345680,'zxc');
```

## Shop Table

```
create table fyp_shop (
shop_id varchar(7) NOT NULL,
shop_name varchar2(50) NOT NULL,
shop_name_zh varchar2(30) NOT NULL,
shop_location char(1) not null,
shop_address varchar2(100) not null,
shop_address_zh varchar2(100) not null,
shop_capacity number(3) not null,
shop_x float not null,
shop_y float not null,
shop_tel number(8) not null,
shop_time char(13) not null,
shop_flag char(1) not null,
CONSTRAINT fyp_shop_pk PRIMARY KEY (shop_id))
```

### increase for shopID

```
CREATE SEQUENCE shopid_increase
  START WITH 1
  INCREMENT BY 1;
```

### insert shop data
#### Kowloon
```
insert into fyp_shop VALUES (
'shop' || to_char(shopid_increase.nextval),
'Fulum Restaurant (Cheung Sha Wan)',
'富臨酒家 (長沙灣店)',
'k',
'Shop 2, G/F & 1/F, Trade Square, 681 Cheung Sha Wan Road, Cheung Sha Wan, KLN',
'長沙灣道 681號貿易廣場地下及1樓2號舖',
60,
22.336956,114.1515134,
23612213,
'06:30–24:00',
1);

insert into fyp_shop VALUES (
'shop' || to_char(shopid_increase.nextval),
'Fulum Restaurant(Yau Tong)',
'富臨酒家-燒鵝大王(油塘店)',
'k',
'Shop 5-6, G/F & Shop 8, 1/F, Canaryside, 8 Shung Shun Street, Yau Tong ,KLN',
'油塘祟信街8號鯉灣天下地下5-6號舖及1樓8號舖',
80,
22.292592,114.2353423,
23791293,
'08:30-23:30',
1);
```
#### Hong Kong
```
insert into fyp_shop VALUES (
'shop' || to_char(shopid_increase.nextval),
'Fulum Palace (Aberdeen)',
'富臨皇宮 (香港仔店)',
'h',
'1/F, Aberdeen Centre Site 3, Nos. 1,3,5 Nam Ning Street, Aberdeen , H.K',
'香港仔南寧街1、3、5號香港仔中心 1字樓',
100,
22.2478809,114.1521025,
25530699,
'05:30-24:00',
1);
```
#### New Territory
```
insert into fyp_shop VALUES (
'shop' || to_char(shopid_increase.nextval),
'Fulum Restaurant (Tuen Mun)',
'富臨酒家 (屯門店)',
'n',
'1/F, Tuen Mun Central Square, 22 Hoi Wing Road, Tuen Mun, N.T.',
'屯門海榮路 22號屯門中央廣場商場地下(部份)及1樓',
90,
22.3812834,113.9693835,
24575277,
'06:30-24:00',
1);
```

## Staff Table

### add staff deatils
```
create table fpy_staff(
staff_id number(4) not null,
staff_name varchar(30) not null,
staff_name_zh varchar2(6) not null,
staff_pw varchar2(30) not null,
staff_gender char(1) not null,
staff_role varchar(20) not null,
staff_role_zh varchar2(20) not null,
staff_permit char(1) not null,
constraint fyp_staff_pk PRIMARY KEY (staff_id));
```

### auto increase staff id
```
CREATE SEQUENCE staffid_increase
  START WITH 1
  INCREMENT BY 1;
```

### add staff details
```
insert into fyp_staff VALUES (staffid_increase.nextval,'qwe','甲乙丙','qwe','m','waiter','服務員',1);
insert into fyp_staff VALUES (staffid_increase.nextval,'asd','陳大文','qwe','f','manager','管理員',2);
```

## Table Details for all Shops

### add table details table
```
create table fyp_table (
table_id varchar(5) not null,
table_shop char(7) not null,
table_number number(3) not null,
table_flag char(1)not null,
CONSTRAINT fyp_table_pk PRIMARY KEY (table_id,table_shop));
```

### insert data for Shop2, Total 272 sit, 38 tables
```
insert into fyp_table values ('t11','shop2',4,1);
insert into fyp_table values ('t12','shop2',8,1);
insert into fyp_table values ('t13','shop2',8,1);
insert into fyp_table values ('t14','shop2',8,1);
insert into fyp_table values ('t15','shop2',4,1);
insert into fyp_table values ('t16','shop2',4,1);
insert into fyp_table values ('t17','shop2',4,1);
insert into fyp_table values ('t18','shop2',12,1);
insert into fyp_table values ('t21','shop2',12,1);
insert into fyp_table values ('t22','shop2',4,1);
insert into fyp_table values ('t23','shop2',4,1);
insert into fyp_table values ('t24','shop2',4,1);
insert into fyp_table values ('t25','shop2',12,1);
insert into fyp_table values ('t26','shop2',4,1);
insert into fyp_table values ('t31','shop2',12,1);
insert into fyp_table values ('t32','shop2',4,1);
insert into fyp_table values ('t33','shop2',4,1);
insert into fyp_table values ('t34','shop2',8,1);
insert into fyp_table values ('t35','shop2',8,1);
insert into fyp_table values ('t36','shop2',8,1);
insert into fyp_table values ('t37','shop2',12,1);
insert into fyp_table values ('t38','shop2',8,1);
insert into fyp_table values ('t39','shop2',12,1);
insert into fyp_table values ('t41','shop2',4,1);
insert into fyp_table values ('t42','shop2',12,1);
insert into fyp_table values ('t43','shop2',12,1);
insert into fyp_table values ('t44','shop2',12,1);
insert into fyp_table values ('t45','shop2',4,1);
insert into fyp_table values ('t46','shop2',4,1);
insert into fyp_table values ('t51','shop2',4,1);
insert into fyp_table values ('t52','shop2',4,1);
insert into fyp_table values ('t53','shop2',12,1);
insert into fyp_table values ('t54','shop2',4,1);
insert into fyp_table values ('t55','shop2',8,1);
insert into fyp_table values ('t56','shop2',4,1);
insert into fyp_table values ('t57','shop2',4,1);
insert into fyp_table values ('t58','shop2',8,1);
insert into fyp_table values ('t59','shop2',8,1);
```

### insert data for shop4, with 280 sits, 40 tables
```
insert into fyp_table values ('t11','shop4',4,1);
insert into fyp_table values ('t12','shop4',8,1);
insert into fyp_table values ('t13','shop4',8,1);
insert into fyp_table values ('t14','shop4',8,1);
insert into fyp_table values ('t15','shop4',4,1);
insert into fyp_table values ('t16','shop4',4,1);
insert into fyp_table values ('t17','shop4',4,1);
insert into fyp_table values ('t18','shop4',12,1);
insert into fyp_table values ('t19','shop4',12,1);
insert into fyp_table values ('t21','shop4',12,1);
insert into fyp_table values ('t22','shop4',4,1);
insert into fyp_table values ('t23','shop4',4,1);
insert into fyp_table values ('t24','shop4',4,1);
insert into fyp_table values ('t25','shop4',12,1);
insert into fyp_table values ('t26','shop4',4,1);
insert into fyp_table values ('t27','shop4',4,1);
insert into fyp_table values ('t28','shop4',4,1);
insert into fyp_table values ('t31','shop4',12,1);
insert into fyp_table values ('t32','shop4',4,1);
insert into fyp_table values ('t33','shop4',4,1);
insert into fyp_table values ('t34','shop4',8,1);
insert into fyp_table values ('t35','shop4',8,1);
insert into fyp_table values ('t36','shop4',8,1);
insert into fyp_table values ('t37','shop4',12,1);
insert into fyp_table values ('t38','shop4',8,1);
insert into fyp_table values ('t41','shop4',4,1);
insert into fyp_table values ('t42','shop4',12,1);
insert into fyp_table values ('t43','shop4',12,1);
insert into fyp_table values ('t44','shop4',12,1);
insert into fyp_table values ('t45','shop4',4,1);
insert into fyp_table values ('t46','shop4',4,1);
insert into fyp_table values ('t51','shop4',4,1);
insert into fyp_table values ('t52','shop4',4,1);
insert into fyp_table values ('t53','shop4',12,1);
insert into fyp_table values ('t54','shop4',4,1);
insert into fyp_table values ('t55','shop4',8,1);
insert into fyp_table values ('t56','shop4',4,1);
insert into fyp_table values ('t57','shop4',4,1);
insert into fyp_table values ('t58','shop4',8,1);
insert into fyp_table values ('t59','shop4',8,1);
```

### insert data for shop5, with 204 sits, 25 tables
```
insert into fyp_table values ('t11','shop5',4,1);
insert into fyp_table values ('t12','shop5',12,1);
insert into fyp_table values ('t13','shop5',12,1);
insert into fyp_table values ('t14','shop5',4,1);
insert into fyp_table values ('t21','shop5',12,1);
insert into fyp_table values ('t22','shop5',4,1);
insert into fyp_table values ('t23','shop5',4,1);
insert into fyp_table values ('t24','shop5',4,1);
insert into fyp_table values ('t25','shop5',12,1);
insert into fyp_table values ('t26','shop5',4,1);
insert into fyp_table values ('t31','shop5',12,1);
insert into fyp_table values ('t32','shop5',4,1);
insert into fyp_table values ('t33','shop5',4,1);
insert into fyp_table values ('t34','shop5',12,1);
insert into fyp_table values ('t35','shop5',12,1);
insert into fyp_table values ('t36','shop5',8,1);
insert into fyp_table values ('t41','shop5',4,1);
insert into fyp_table values ('t42','shop5',12,1);
insert into fyp_table values ('t43','shop5',12,1);
insert into fyp_table values ('t51','shop5',8,1);
insert into fyp_table values ('t52','shop5',8,1);
insert into fyp_table values ('t53','shop5',12,1);
insert into fyp_table values ('t54','shop5',8,1);
insert into fyp_table values ('t55','shop5',8,1);
insert into fyp_table values ('t56','shop5',8,1);
```

### insert for shop6, with 320 sits, 35 tables
```
insert into fyp_table values ('t11','shop6',12,1);
insert into fyp_table values ('t12','shop6',8,1);
insert into fyp_table values ('t13','shop6',8,1);
insert into fyp_table values ('t14','shop6',8,1);
insert into fyp_table values ('t15','shop6',12,1);
insert into fyp_table values ('t16','shop6',8,1);
insert into fyp_table values ('t17','shop6',4,1);
insert into fyp_table values ('t18','shop6',12,1);
insert into fyp_table values ('t21','shop6',12,1);
insert into fyp_table values ('t22','shop6',8,1);
insert into fyp_table values ('t23','shop6',12,1);
insert into fyp_table values ('t24','shop6',8,1);
insert into fyp_table values ('t25','shop6',12,1);
insert into fyp_table values ('t26','shop6',8,1);
insert into fyp_table values ('t31','shop6',12,1);
insert into fyp_table values ('t32','shop6',4,1);
insert into fyp_table values ('t33','shop6',8,1);
insert into fyp_table values ('t34','shop6',8,1);
insert into fyp_table values ('t35','shop6',8,1);
insert into fyp_table values ('t36','shop6',8,1);
insert into fyp_table values ('t37','shop6',12,1);
insert into fyp_table values ('t38','shop6',8,1);
insert into fyp_table values ('t39','shop6',12,1);
insert into fyp_table values ('t41','shop6',4,1);
insert into fyp_table values ('t42','shop6',12,1);
insert into fyp_table values ('t43','shop6',12,1);
insert into fyp_table values ('t44','shop6',12,1);
insert into fyp_table values ('t45','shop6',12,1);
insert into fyp_table values ('t46','shop6',4,1);
insert into fyp_table values ('t51','shop6',4,1);
insert into fyp_table values ('t52','shop6',12,1);
insert into fyp_table values ('t53','shop6',12,1);
insert into fyp_table values ('t54','shop6',8,1);
insert into fyp_table values ('t55','shop6',8,1);
insert into fyp_table values ('t56','shop6',8,1);
```

### update add row of number now
```
update fyp_table set table_now = 12 where table_number = 12;
update fyp_table set table_now = 8 where table_number = 8;
update fyp_table set table_now = 4 where table_number = 4;
```

## Order DataBase

### add order table
```
create table fyp_order(
order_id number(5) not null,
order_sid varchar(7) not null,
order_date date not null,
order_time varchar(30) not null,
order_ppl number(3) not null,
order_area char(1) not null,
order_price float not null,
CONSTRAINT fyp_order_pk PRIMARY KEY (order_id,order_sid,order_time));
```

### auto increase oid id
```
CREATE SEQUENCE oid_increase
  START WITH 1
  INCREMENT BY 1;
```

### add tesing data by add button in Common Main
```
insert into fyp_order values ('11a','shop2',TO_DATE(SYSDATE,'DD-MON-YYYY HH24:MI:SS'),
TO_CHAR(SYSDATE,'DD-MON-YYYY HH24:MI:SS'),3,1,1,0);
insert into fyp_order values ('11b','shop2',TO_DATE('07-MAY-2020'),TO_CHAR(SYSDATE,'DD-MON-YYYY HH24:MI:SS'),3,1,1,0);

select * from fyp_order where order_date = to_date(SYSDATE,'DD-MON-YYYY');
select * from fyp_order where order_date like '%JAN%';
```

### for Takeout order:
```
'to' || to_char(oid_increase.nextval),
```

## Food table
### create Food table
```
create table fyp_food (
food_id varchar(5) not null,
food_cat varchar(10) not null,
food_size char(1) not null,
food_name varchar2(20) not null,
food_name_zh varchar2(20) not null,
food_detail varchar2(100) not null,
food_detail_zh varchar2(100) not null,
food_price float not null,
food_img varchar(30) not null,
food_flag char(1) not null,
CONSTRAINT fyp_food_pk PRIMARY KEY (food_id));
```

### food id increase
```
CREATE SEQUENCE fid_increase
  START WITH 1
  INCREMENT BY 1;
```

### insert data for testing
```
insert into fyp_food values (fid_increase.nextval,'ds','l','Chiuchow Dumpling','潮州蒸粉果','Chaozhou Fenzhou is one of the dim sum in Chinese Cantonese cuisine. Chaozhou flour bun uses peanuts, minced chives, minced pork, shrimps, etc. as fillings, wraps the noodles with skin, and steams it over water. It is usually eaten with chili oil.','潮州粉粿是中國粵菜中的點心之一。潮州粉粿以花生、韮菜碎、豬肉碎、蝦米等為餡料，以澄麵作皮包好，隔水蒸製而成，一般佐以辣椒油食用。',0,'x',1);
insert into fyp_food values (
fid_increase.nextval,'ds','m','Steamed Buns with BBQ pork','鮑汁叉燒包',
'Barbecued bun is one of the most representative Cantonese dim sum in Guangdong, Hong Kong and Macau.',
'叉燒包是廣東和港澳最具代表性的粵式點心之一。',
0,'x',1);
insert into fyp_food values (
fid_increase.nextval,'ds','b','Chicken Rolls','四寶滑雞扎',
'It is a kind of Guangdong dim sum, commonly found in tea houses in Guangdong and Hong Kong.',
'簡稱雞扎，是廣東點心的一種，常見於廣東及香港的茶樓。',
0,'x',1);
insert into fyp_food values (
fid_increase.nextval,'ds','s','FooLum Shrimp Dumplings','富臨蝦餃皇',
'A layer of Cheng noodles wraps one or two shrimps as the main filling, and the size is limited to one bite. Traditional shrimp dumplings are half-moon shaped and spider belly, with a total of thirteen folds.',
'以一層澄麵皮包著一至兩隻蝦為主餡，份量大小多以一口為限。傳統的蝦餃是半月形、蜘蛛肚的，共有十三褶。',
0,'x',1);
insert into fyp_food values (
fid_increase.nextval,'local','b','Rice Rolls with Beef','爽滑牛肉腸',
'Rice Rolls is a kind of Cantonese food made from rice milk. The appearance is a roll, which is made of bran called bran Rice Rolls, and without bran, it is called pull Rice Rolls.',
'拉腸粉，是一種使用米漿作成的廣東食品，外表是一條卷狀物，以布拉成的稱為布拉腸粉，沒有布拉的稱手拉腸粉。',
0,'x',1);
insert into fyp_food values (
fid_increase.nextval,'local','b','Congee with Pork and Century Eggs','皮蛋瘦肉粥',
'Classic Cantonese congee with pork and century eggs, warm and comforting meal. Perfect for breakfast or lunch with noodles and you tiao.',
'經典廣東話粥，包括豬肉和世紀雞蛋，溫暖而舒適的飯菜。 完美搭配早餐或午餐和麵條。 ',
0,'x',1);
insert into fyp_food values (
fid_increase.nextval,'sv','c','Grill Scallop with Vegetables','翡翠珍珠帶子',
'Grill Scallop with Vegetables',
'翡翠珍珠帶子',
88,'x',1);
insert into fyp_food values (
fid_increase.nextval,'sv','c','Fillet with Corns','黃金粟米魚塊',
'Fillet with Corns',
'黃金粟米魚塊',
46,'x',1);
insert into fyp_food values (
fid_increase.nextval,'bt','c','Roast suckling Pig','原隻乳豬拼盤',
'Roast suckling Pig',
'原隻乳豬拼盤',
128,'x',1);
insert into fyp_food values (
fid_increase.nextval,'pot','c','Braised Tofu with Roast Pork','豆腐火腩煲',
'Braised Tofu with Roast Pork',
'豆腐火腩煲',
68,'x',1);
insert into fyp_food values (
fid_increase.nextval,'rice','c','Rice','白飯',
'Rice',
'白飯',
10,'x',1);
insert into fyp_food values (
fid_increase.nextval,'rice','c','Yangzhou Fried Rice','揚州炒飯',
'Rice',
'揚州炒飯',
50,'x',1);
insert into fyp_food values (
fid_increase.nextval,'nood','c','Sichuan Dandan Noodles','四川擔擔麵',
'Sichuan Dandan Noodles',
'擔擔麵是中國四川的特產小吃，得名於挑擔叫賣的傳統。',
50,'x',1);
insert into fyp_food values (
fid_increase.nextval,'drink','c','Orange juice','鮮橙汁',
'Orange juice, 1L.',
'鮮橙汁, 1L.',
30,'x',1);
insert into fyp_food values (
fid_increase.nextval,'drink','c','Soft Drink','汽水',
'Any type of Soft Drink, 330ml.',
'汽水, 330ml.',
12,'x',1);
insert into fyp_food values (
fid_increase.nextval,'des','c','Osmanthus Jelly','宮廷杞子桂花糕',
'Osmanthus Jelly',
'宮廷杞子桂花糕',
23,'x',1);
insert into fyp_food values (
fid_increase.nextval,'sea','o','Steamed Fresh Spotted Garoupa','清蒸海星斑',
'Steamed Fresh Spotted Garoupa(About two pounds)',
'海星斑(約兩斤)',
980,'x',1);
insert into fyp_food values (
fid_increase.nextval,'sea','t','Steamed Fresh Australian lobster','清蒸澳洲龍蝦',
'Steamed Fresh Spotted Garoupa(Count by each tael)',
'澳洲龍蝦(每兩計)',
48,'x',1);
insert into fyp_food values (
fid_increase.nextval,'sea','h','Steamed Fresh American Lobster(with E-Fu Noodle)','清蒸加拿大龍蝦(伊麵底)',
'Steamed Fresh American Lobster(with E-Fu Noodle) Count by per pounds',
'清蒸加拿大龍蝦(伊麵底)每斤計',
198,'x',1);
insert into fyp_food values (
fid_increase.nextval,'drink','c','Beer','啤酒',
'Any type of Beer,330ml.',
'啤酒, 330ml.',
24,'x',1,'z');
```

## Size table and price
### set up table 
```
create table fyp_size (
s_id char(1) not null,
s_price float not null,
s_morn float not null,
s_tea float not null,
s_hoilday float not null,
CONSTRAINT fyp_size_pk PRIMARY KEY (s_id));
```

### insert value
```
insert into fyp_size values ('l',12,8.8,10,15);
insert into fyp_size values ('m',16,12.8,14.5,20);
insert into fyp_size values ('b',24,20,22,30);
insert into fyp_size values ('s',32,32,32,35);
```

## Combo table
### set table
```
create table fyp_combo (
c_id varchar(5) not null,
c_cat varchar(5) not null,
c_name varchar(50) not null,
c_name_zh varchar(50) not null,
c_detail varchar(50) not null,
c_detail_zh varchar2(50) not null,
c_hash varchar(300) not null,
c_price float not null,
c_img varchar(30) not null,
c_flag char(1) not null,
CONSTRAINT fyp_combo_pk PRIMARY KEY (c_id));
```

### combo id increase
```
CREATE SEQUENCE cid_increase
START WITH 1
INCREMENT BY 1;
```

### insert combo details
```
insert into fyp_combo values (cid_increase.nextval,'com',
'Seafood package for Two person','海鮮二人套餐',
'Seafood package for Two person','海鮮二人套餐',
'2:25_29,2:21_22,2:21_22,2:20_16,19,19,2:11_12',
488,'x',1);
insert into fyp_combo values (cid_increase.nextval,'new',
'Easter package for Eight person','復活節八人套餐',
'Easter package for Eight person','復活節八人套餐',
'2:25_29,2:21_22,2:21_22,2:20_16,19,19,2:11_12',
1888,'x',1);
```

## Special Order 
### set up table
```
create table fyp_sitem (
si_id varchar(5) not null,
si_name varchar(30) not null,
si_name_zh varchar2(30) not null,
si_price float not null,
si_flag char(1) not null,
CONSTRAINT fyp_sitem_pk PRIMARY KEY (si_id));

CREATE SEQUENCE siid_increase
START WITH 1
INCREMENT BY 1;
```
### insert data
```
insert into fyp_sitem values (siid_increase.nextval,'plastic bag','膠袋','com',1,1);
insert into fyp_sitem values (siid_increase.nextval,'Plastic lunch box','塑膠飯盒','com',3,1);
insert into fyp_sitem values (siid_increase.nextval,'Less Oil','少油','com',0,1);
insert into fyp_sitem values (siid_increase.nextval,'Less Salt','少鹽','com',0,1);
insert into fyp_sitem values (siid_increase.nextval,'Less Sugar','少糖','com',0,1);
insert into fyp_sitem values (siid_increase.nextval,'Add E-Fu Noodle','伊麵底','sea',20,1);
insert into fyp_sitem values (siid_increase.nextval,'thicker','濃啲','drink',0,1);
insert into fyp_sitem values (siid_increase.nextval,'Less Ice','小冰','drink',0,1);
insert into fyp_sitem values (siid_increase.nextval,'Extra Ice','多冰','drink',0,1);
```

## Special order List 
### setup table 
```
create table fyp_sorder (
so_id varchar(5) not null,
so_o_id varchar(5) not null,
so_si_id varchar(10) not null,
so_price float not null,
CONSTRAINT fyp_sorder_pk PRIMARY KEY (so_id));

CREATE SEQUENCE soid_increase
START WITH 1
INCREMENT BY 1;
```

## order item Table 
### set up table 
```
create table fyp_orderitem (
oi_id varchar(5) not null,
oi_oid varchar(5) not null,
oi_sid varchar(7) not null,
oi_time varchar2(50) not null,
oi_fflag char(1) not null,
oi_cid varchar(20) not null,
oi_change varchar(20) not null,
oi_fid varchar(8) not null,
oi_area char(1) not null,
oi_price float not null,
oi_show char(1) not null,
oi_flag char(1) not null,
CONSTRAINT fyp_orderitem_pk PRIMARY KEY (oi_id));

CREATE SEQUENCE orderitem_increase
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE comboitem_increase
START WITH 1
INCREMENT BY 1;
```

## cat table
### set table and data
```
create table fyp_cat (
cat_id varchar(10) not null,
cat_name varchar(20) not null,
cat_name_zh varchar2(20) not null,
CONSTRAINT fyp_cat_pk primary key (cat_id));

insert into fyp_cat values ('ds','Dim Sum','點心');
insert into fyp_cat values ('sv','Side Dish','小菜');
insert into fyp_cat values ('bt','Siu Mei','燒味');
insert into fyp_cat values ('pot','Pot','鍋');
insert into fyp_cat values ('rice','Rice','飯');
insert into fyp_cat values ('nood','Noodle','麵');
insert into fyp_cat values ('drink','Drink','飲品');
insert into fyp_cat values ('des','Dessert','甜品');
insert into fyp_cat values ('local','Local Food','本地特色');
insert into fyp_cat values ('sea','Seafood','海鮮');
```

## Coupon Table
### set up table
```
create table fyp_coupon (
c_type varchar(5) not null,
c_n varchar(20) not null,
c_nzh varchar2(20) not null,
c_d varchar(50) not null,
c_dzh varchar2(50) not null,
c_value number(3) not null,
c_fid varchar(5) not null,
c_mon number(2) not null,
c_img varchar(50) not null,
CONSTRAINT fyp_coupon_pk primary key(c_type));
```

### testing data
```
insert into fyp_coupon values ('d1','Discount for $20','$20優惠券','Spend for HKD200 amount to use','消費滿HKD200使用',20,'0',6,'x');
insert into fyp_coupon values ('d2','Discount for $50','$50優惠券','Spend for HKD200 amount to use','消費滿HKD200使用',50,'0',6,'x');
insert into fyp_coupon values ('d3','Discount for $100','$100優惠券','Spend for HKD200 amount to use','消費滿HKD200使用',100,'0',12,'x');
insert into fyp_coupon values ('fd','Free for a Drink','免費飲料優惠券','Spend for HKD200 amount to use','消費滿HKD200使用',0,'22',6,'x');
insert into fyp_coupon values ('fb','Free for a Beer','免費啤酒優惠券','Spend for HKD200 amount to use','消費滿HKD200使用',0,'41',6,'x');
```

## User Coupon Table
### set up table
```
create table fyp_userCoupon (
c_id number(5) not null,
c_type varchar(5) not null,
c_uid varchar(10) not null,
c_date date not null,
c_from varchar(7) not null,
c_flag char(1) not null,
CONSTRAINT fyp_userCoupon_pk primary key(c_id));
```

### auto increase
```
CREATE SEQUENCE ucid_increase
START WITH 1
INCREMENT BY 1;
```

### add detail data 
```
alter session set nls_date_format='DD-MM-YYYY HH24:MI:SS';
insert into fyp_userCoupon values (ucid_increase.nextval,'d1','10000',TO_DATE(SYSDATE,'DD-MM-YYYY 12:12:12'),'net','1');
insert into fyp_userCoupon values (ucid_increase.nextval,'d2','guest',TO_DATE('02-04-2019 12:12:12'),'shop2','1');
insert into fyp_userCoupon values (ucid_increase.nextval,'d3','guest',TO_DATE('08-05-2019 12:12:12'),'shop5','1');
insert into fyp_userCoupon values (ucid_increase.nextval,'d1','10001',TO_DATE(SYSDATE,'DD-MM-YYYY HH24:MI:SS'),'net','1');
insert into fyp_userCoupon values (ucid_increase.nextval,'d1','10001',TO_DATE(SYSDATE,'DD-MM-YYYY HH24:MI:SS'),'shop6','1');
```

## Coupon Log
### set up table
```
create table fyp_couponLog (
l_id number(5) not null,
l_cid number(5) not null,
l_uid number(10) not null,
l_date date not null,
l_from varchar(10) not null,
l_sid varchar(10) not null,
l_flag char(1) not null,
CONSTRAINT fyp_couponLog_pk primary key(l_id));

CREATE SEQUENCE clog_increase
START WITH 1
INCREMENT BY 1;

insert into fyp_couponlog values (clog_increase.nextval,1,10000,TO_DATE(SYSDATE),'net','sys','1');
insert into fyp_couponlog values (clog_increase.nextval,3,0,TO_DATE(SYSDATE,'DD-MM-YYYY HH24:MI:SS'),'shop2','1','1');
```

## Material Table
### set up table
```
create table fyp_material (
m_id number(5) not null,
m_n varchar(20) not null,
m_nzh varchar2(20) not null,
m_u varchar(10) not null,
m_l float not null,
m_c varchar(10) not null,
CONSTRAINT fyp_material_pk primary key (m_id));

create table fyp_materialShop (
m_id number(5) not null,
m_sid varchar(10) not null,
m_l float not null,
CONSTRAINT fyp_materialShop_pk primary key (m_id,m_sid));

create table fyp_materialCom (
c_id number(5) not null,
c_n varchar(20) not null,
c_email varchar(20) not null,
c_tel number(8) not null,
c_money float not null,
c_add float not null,
CONSTRAINT fyp_materialCom_pk primary key (c_id));
```

### insert data
```
CREATE SEQUENCE mat_increase
START WITH 1
INCREMENT BY 1;

insert into fyp_material values (mat_increase.nextval,'Egg','雞蛋','Each',50,'egg');
insert into fyp_material values (mat_increase.nextval,'Cola','汽水','Each',50,'cola');
insert into fyp_material values (mat_increase.nextval,'Beer','啤酒','Each',50,'beer');

insert into fyp_materialShop values (1,'shop2',560);
insert into fyp_materialShop values (2,'shop2',50);
insert into fyp_materialShop values (3,'shop2',40);
insert into fyp_materialShop values (1,'shop4',560);
insert into fyp_materialShop values (2,'shop4',50);
insert into fyp_materialShop values (3,'shop4',40);
insert into fyp_materialShop values (1,'shop5',560);
insert into fyp_materialShop values (2,'shop5',50);
insert into fyp_materialShop values (3,'shop5',40);
insert into fyp_materialShop values (1,'shop6',560);
insert into fyp_materialShop values (2,'shop6',50);
insert into fyp_materialShop values (3,'shop6',40);

insert into fyp_materialCom values ('egg','福道家(香港)食品有限公司','wongyat88123456@gmail.com',12344444,5000,2666);
insert into fyp_materialCom values ('cola','可口可樂(香港)有限公司','wongyat88123456@gmail.com',12344445,100,200);
insert into fyp_materialCom values ('beer','青岛啤酒股份有限公司','wongyat88123456@gmail.com',12344446,100,500);
```

## Material transaction record table
### set up table
```
create table FYP_materialTran (
t_id number(5) not null,
t_sid varchar(10) not null,
t_cid varchar(10) not null,
t_date date not null,
t_flag char(1) not null,
CONSTRAINT fyp_materialTran_pk primary key (t_id)
);

CREATE SEQUENCE matran_increase
START WITH 1
INCREMENT BY 1;

insert into fyp_materialTran values (matran_increase.nextval,'shop2','egg',TO_DATE(SYSDATE),'1');
```

## Material Expenses Record Table 
### set up table
```
create table fyp_expenses (
e_id number(5) not null,
e_date date not null,
e_d varchar(80) not null,
e_dzh varchar2(50) not null,
e_v float not null,
CONSTRAINT fyp_expenses_pk primary key (e_id)
);

CREATE SEQUENCE matexp_increase
START WITH 1
INCREMENT BY 1;
```

## set up service Fee table
### set up table
```
create table fyp_serviceFee (
s_id varchar(5) not null,
s_price float not null,
CONSTRAINT fyp_serviceFee_pk primary key(s_id)
);

insert into fyp_servicefee values ('sc',0.1);
insert into fyp_servicefee values ('tc',12);
insert into fyp_servicefee values ('tkc',5);
```

## Profit Table
```
create table fyp_profit (
p_id number(5) not null,
p_oid number(5) not null,
p_sid varchar2(10) not null,
p_ot varchar2(30) not null,
p_val float not null,
p_area varchar(4) not null,
p_method char(1) not null,
p_cardno number(16) not null,
p_date date not null,
p_time varchar2(30) not null,
p_flag char(1) not null,
CONSTRAINT fyp_profit_pk primary key (p_id));

CREATE SEQUENCE pro_increase
START WITH 1
INCREMENT BY 1;

create table fyp_payMethod (
p_id number(5) not null,
p_n varchar(20) not null,
p_nzh varchar2(20) not null,
p_flag char(1) not null,
CONSTRAINT fyp_payMethod_pk primary key (p_id));

CREATE SEQUENCE pmet_increase
START WITH 1
INCREMENT BY 1;

insert into fyp_paymethod values (pmet_increase.nextval,'cash','現金',1);
insert into fyp_paymethod values (pmet_increase.nextval,'Visa','Visa',1);
insert into fyp_paymethod values (pmet_increase.nextval,'Master','Master',1);
insert into fyp_paymethod values (pmet_increase.nextval,'Alipay','支付寶',1);
insert into fyp_paymethod values (pmet_increase.nextval,'Octopus','八達通',1);
```

## set up a Month table for right left join profit and expenses table
```
create table fyp_month (
m_id number(3) not null,
m_p number(1) not null,
CONSTRAINT fyp_month_pk primary key (m_id)
);

insert all 1 to 12 months into it with 0 value of m_p
```

## useful SQl
```
select * from fyp_orderitem order by regexp_substr(oi_id, '^\D*') nulls first, to_number(regexp_substr(oi_id, '\d+')) DESC;
```

```
select oi_id,oi_time,food_name_zh,oi_area,oi_flag from (  
select ff.*,oi.* from fyp_food ff JOIN fyp_orderitem oi ON ff.food_id = oi.oi_fid ) 
where oi_oid = 't13a' and oi_sid = 'shop2' and (oi_flag='1' or oi_flag='3')
order by regexp_substr(oi_id, '^\D*') nulls first, to_number(regexp_substr(oi_id, '\d+')) DESC;
```

```
select m_id, NVL( profit , 0 ) from 
(select to_char( p_date, 'MM' ) a ,sum(p_val) as profit 
from fyp_profit
where EXTRACT ( YEAR FROM p_date ) IN ( 2020 ) 
group by to_char( p_date, 'MM' ) 
order by to_char( p_date, 'MM' ))
right join fyp_month ON fyp_month.m_id = a 
order by m_id;
```

```
select COUNT(*) from(
select oi_time,food_cat from (  
select ff.*,oi.* from fyp_food ff JOIN fyp_orderitem oi ON ff.food_id = oi.oi_fid ) 
where oi_sid = 'shop2' and (oi_flag='1' or oi_flag='2')
and oi_time LIKE ('%-02-%')
order by regexp_substr(oi_id, '^\D*') nulls first, to_number(regexp_substr(oi_id, '\d+')) DESC);
```
# Final Year Project Database SQL

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
shop_id char(7) NOT NULL,
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
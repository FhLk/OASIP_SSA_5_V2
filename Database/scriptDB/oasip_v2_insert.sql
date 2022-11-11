insert into `eventcategory` (`category_id`,`category_name`,`description`,`duration`) values 
(1, 'Project Management Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย project management clinic ในวิชา INT221 integrated project I ให้นักศึกษาเตรียมเอกสารที่เกี่ยวข้องเพื่อแสดง EventBooking ระหว่างขอคำปรึกษา', 30 ),
(2,'DevOps/Infra Clinic','Use this event category for DevOps/Infra clinic.',20),
(3,'Database Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย database clinic ในวิชา INT221 integrated project I',15),
(4,'Client-side Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย client-side clinic ในวิชา INT221 integrated project',30),
(5,'Server-side Clinic',' ',30);


insert into user values 
	("1","OASIP ADMIN","oasip.admin@kmutt.ac.th",1,'2022-08-01 00:00:00','2022-08-01 00:00:00','$argon2id$v=19$m=4096,t=3,p=1$sYXzbUOqBoHY1NfhJ8cjnw$H6+adWySiFPgcUogJK3hEhcF6Y4fusy7tcXYEL+f0cQ'),
    ("2","Olarn Rojanapornpun","olarn.roj@kmutt.ac.th",2,'2022-08-08 15:00:00','2022-08-08 15:00:00','$argon2id$v=19$m=4096,t=3,p=1$Sx7y2jxKZSjpWUV4srd8eg$AMH09iFiPQgAZ00cAdN3Gucqfhx2kRo3tQbHeLSR0RE'),
    ("3","Pichet Limvachiranan","pichet.limv@kmutt.ac.th",2,'2022-08-08 15:00:01','2022-08-08 15:00:01','$argon2id$v=19$m=4096,t=3,p=1$46EB43gQ46Z1/EmdqxtKNA$7m6cWGO2iDlFl/ETDYuYf+ArnSjRnsNwXLIP18DTYQY'),
    ("4","Umaporn Supasitthimethee","umaporn.sup@kmutt.ac.th",2,'2022-08-08 15:00:01','2022-08-16 15:00:01','$argon2id$v=19$m=4096,t=3,p=1$1Z2UK1zC76FIQeLH54GVAQ$qfXcHF31LnuWpt37QAcWyNp8PdbOQ+jjaV1xWXixS0M'),
    ("5","Siam Yamsaengsung","siam.yam@kmutt.ac.th",2,'2022-08-08 15:00:02','2022-08-01 15:00:02','$argon2id$v=19$m=4096,t=3,p=1$C4pPaNWKTnZQX2mPs14jlg$rQ5W5NYKqGOu1B4GkUWq8cFbcg2peFWGjpUMr9Nkm8g'),
    ("6","Sunisa Sathapornvajana","sunisa.sat@kmutt.ac.th",2,'2022-08-08 15:00:03','2022-08-08 15:00:03','$argon2id$v=19$m=4096,t=3,p=1$29/ffaszvjvi3CZO45bSCg$kKpfq5WEswoqa/LfyIZzQaQ6AFdjhyiYjXRCfMiTnwg'),
    ("7","Somchai Jaidee","somchai.jai@kmutt.ac.th",3,'2022-08-08 16:00:00','2022-08-08 16:00:00','$argon2id$v=19$m=4096,t=3,p=1$dmsOy7LPTjmooPu+P2oTZA$NZFTFd3f0K1Sp19aaUwyn3jgiy15yFcXhp8E4/1yXoI'),
    ("8","Komkrid Rakdee","komkrid.rak@mail.kmutt.ac.th",3,'2022-08-08 16:00:00','2022-08-08 16:00:00','$argon2id$v=19$m=4096,t=3,p=1$8W61ZOC5RU7sJP5kKRbSqg$OLwZNPeMqxp+g0Vbn+odcA47XMClFN+IswTueVah7F0'),
    ("9","สมเกียรติ ขยันเรียน กลุ่ม TT-4","somkiat.kay@kmutt.ac.th",3,'2022-08-16 09:00:00','2022-08-16 09:00:00','$argon2id$v=19$m=4096,t=3,p=1$gBqgjspF45FcIKQEw8GmaQ$alrOCZ0YrDqOu8/aZiLDMGZo4vFkSEAXA0YoHhY0BDQ');


insert into `eventbooking` (`booking_id`,`booking_name`,`booking_email`,`start_time`,`booking_duration`,`event_note`,`category`,`user_id`) values 
(1,'Somchai Jaidee (OR-7)','somchai.jai@mail.kmutt.ac.th','2022-05-23 13:00',30,' ',2,7),
(2,'Somsri Rakdee (SJ-3)','somsri.rak@mail.kmutt.ac.th','2022-04-27 09:30',30,' ขอปรึกษาเรื่องเพื่อนไม่ช่วยงาน ',1,null),
(3,'สมเกียรติ ขยันเรียน กลุ่ม TT-4','somkiat.kay@kmutt.ac.th','2022-05-23 16:30',15,' ',3,9);

delete from eventcategory_owner;
insert into `eventcategory_owner` (`eventcategory_category_id`,`user_id_lecturer`) values (1,2),(2,2),(2,5),(3,6),(4,4),(5,3),(5,2);


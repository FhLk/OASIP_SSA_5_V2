insert into `eventcategory` (`category_id`,`category_name`,`description`,`duration`) values 
(1, 'Project Management Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย project management clinic ในวิชา INT221 integrated project I ให้นักศึกษาเตรียมเอกสารที่เกี่ยวข้องเพื่อแสดง EventBooking ระหว่างขอคำปรึกษา', 30 ),
(2,'DevOps/Infra Clinic','Use this event category for DevOps/Infra clinic.',20),
(3,'Database Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย database clinic ในวิชา INT221 integrated project I',15),
(4,'Client-side Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย client-side clinic ในวิชา INT221 integrated project',30),
(5,'Server-side Clinic',' ',30);

select * from user;
delete from user;
insert into user values 
	("1","OASIP ADMIN","oasip.admin@kmutt.ac.th",1,'2022-08-01 00:00:00','2022-08-01 00:00:00','oasipadmin'),
    ("2","Somchai Jaidee","somchai.jai@kmutt.ac.th",2,'2022-08-08 15:00:00','2022-08-08 15:00:00','somchaijai'),
    ("3","Komkrid Rakdee","komkrid.rak@mail.kmutt.ac.th",3,'2022-08-08 15:00:01','2022-08-08 15:00:01','komkridrak'),
    ("4","สมเกียรติ ขยันเรียน","somkiat.kay@kmutt.ac.th",3,'2022-08-16 09:00:00','2022-08-16 09:00:00','somkiat.kay');
    
insert into `eventbooking` (`booking_id`,`booking_name`,`booking_email`,`start_time`,`booking_duration`,`event_note`,`category`) values 
(1,'Somchai Jaidee (OR-7)','somchai.jai@mail.kmutt.ac.th','2022-05-23 13:00',30,' ',2),
(2,'Somsri Rakdee (SJ-3)','somsri.rak@mail.kmutt.ac.th','2022-04-27 09:30',30,' ขอปรึกษาเรื่องเพื่อนไม่ช่วยงาน ',1),
(3,'สมเกียรติ ขยันเรียน กลุ่ม TT-4','somkiat.kay@kmutt.ac.th','2022-05-23 16:30',15,' ',3);

insert into `eventcategory_owner` (`eventcategory_category_id`,`user_id_lecturer`) values (2,2),(3,2);


INSERT INTO patients
(MID, 
lastName, 
firstName,
email,
address1,
address2,
city,
state,
zip,
phone,
eName,
ePhone,
iCName,
iCAddress1,
iCAddress2,
iCCity, 
ICState,
iCZip,
iCPhone,
iCID,
dateofbirth,
mothermid,
fathermid,
bloodtype,
ethnicity,
gender, 
topicalnotes)
VALUES
(685,
'Jareau', 
'Jennifer', 
'popyopzopdoo@gmail.com', 
'12437 AayDee Dr', 
'Suite 146', 
'Boston', 
'MA', 
'02134', 
'234-954-4531', 
'M C', 
'856-956-1956', 
'Wral', 
'1234 Wral Blvd', 
'Suite 6032', 
'Lotte',
'NC', 
'93858', 
'394-395-4506', 
'ChetumNHowe', 
'1932-06-10',
0,
0,
'AB+',
'African American',
'Female',
'')
 ON DUPLICATE KEY UPDATE MID = MID;

INSERT INTO users(MID, password, role, sQuestion, sAnswer) 
			VALUES (685, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'what is your favorite color?', 'blue')
 ON DUPLICATE KEY UPDATE MID = MID;
 /*password: pw*/
 
 /* Some Default Food Diary stuff to be added */
INSERT INTO patientfooddiary(MID,ldate,meal,food,servings,cals,fat,sodium,carbs,fiber,sugar,protein)
VALUES (685,'2012-09-30','Breakfast','Hotdog', 4, 80, 5, 480, 2, 0, 0, 5)
 ON DUPLICATE KEY UPDATE MID = MID;
 
  /* Some Default Food Diary stuff to be added */
INSERT INTO patientfooddiary(MID,ldate,meal,food,servings,cals,fat,sodium,carbs,fiber,sugar,protein)
VALUES (685,'2012-09-30','Lunch','Mango Passion Fruit Juice', 1.2, 130, 0, 25, 32, 0, 29, 1)
 ON DUPLICATE KEY UPDATE MID = MID;
 
   /* Some Default Body Measurement stuff to be added as test */
INSERT INTO patientBodyMeasurements(MID,ldate,weight,height,waist,arms)
VALUES (685,'2012-09-30',140, 70, 34, 60)
 ON DUPLICATE KEY UPDATE MID = MID;
 
    /* Some Default Body Measurement stuff to be added as test */
INSERT INTO patientBodyMeasurements(MID,ldate,weight,height,waist,arms)
VALUES (685,'2013-09-25',150, 71.4, 36, 61)
 ON DUPLICATE KEY UPDATE MID = MID;
 
     /* Some Default Body Measurement stuff to be added as test */
INSERT INTO patientBodyMeasurements(MID,ldate,weight,height,waist,arms)
VALUES (685,'2014-08-2',135, 75, 33, 61)
 ON DUPLICATE KEY UPDATE MID = MID;
 
 
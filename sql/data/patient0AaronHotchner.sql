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
(686,
'Hotchner', 
'Aaron', 
'popjoplop@gmail.com', 
'1247 Richard Dr', 
'Suite 965', 
'Salem', 
'NC', 
'85832', 
'233-657-4532', 
'Supraman', 
'904-234-4335', 
'Place', 
'1234 Place Blvd', 
'Suite 961', 
'Gas',
'NC', 
'98652', 
'333-444-9651', 
'ChetumNHowe', 
'1961-05-10',
0,
0,
'AB+',
'African American',
'Male',
'')
 ON DUPLICATE KEY UPDATE MID = MID;

INSERT INTO users(MID, password, role, sQuestion, sAnswer) 
			VALUES (686, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'what is your favorite color?', 'blue')
 ON DUPLICATE KEY UPDATE MID = MID;
 /*password: pw*/
 
 /* Some Default Food Diary stuff to be added */
INSERT INTO patientfooddiary(MID,ldate,meal,food,servings,cals,fat,sodium,carbs,fiber,sugar,protein)
VALUES (686,'2014-04-13','Snack','Oreos', 53, 140, 7, 90, 21, 1, 13, 0)
 ON DUPLICATE KEY UPDATE MID = MID;
 
  /* Some Default Food Diary stuff to be added */
INSERT INTO patientfooddiary(MID,ldate,meal,food,servings,cals,fat,sodium,carbs,fiber,sugar,protein)
VALUES (686,'2013-05-21','Breakfast','Cheese and Bean Dip', 0.75, 45, 2, 230, 5, 2, 0, 2)
 ON DUPLICATE KEY UPDATE MID = MID;
 
 
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
(687,
'Prentiss', 
'Emily', 
'heyitsmyemail@yahoo.com', 
'1247 Tacobell Dr', 
'Suite 875', 
'Oz', 
'NC', 
'98562', 
'364-123-6522', 
'Peanut', 
'257-657-8573', 
'Location', 
'1234 Wow Blvd', 
'Suite 954', 
'Skyrim',
'NC', 
'97821', 
'333-864-9651', 
'ChetumNHowe', 
'1963-07-11',
0,
0,
'AB+',
'African American',
'Female',
'')
 ON DUPLICATE KEY UPDATE MID = MID;

INSERT INTO users(MID, password, role, sQuestion, sAnswer) 
			VALUES (687, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'what is your favorite color?', 'blue')
 ON DUPLICATE KEY UPDATE MID = MID;
 /*password: pw*/
 
 /* Some Default Food Diary stuff to be added */
INSERT INTO patientfooddiary(MID,ldate,meal,food,servings,cals,fat,sodium,carbs,fiber,sugar,protein)
VALUES (687,'2014-03-16','Lunch','Chocolate Shake', 2, 500, 23.5, 259, 66.5, 0, 42.4, 5.9)
 ON DUPLICATE KEY UPDATE MID = MID;
 
 
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
(684,
'Morgan', 
'Derek', 
'poretasdasp@gmail.com', 
'1247 Aeee Dr', 
'Suite 13306', 
'Radh', 
'NC', 
'27615', 
'133-545-2468', 
'M B', 
'234-456-8653', 
'Snow', 
'1234 Snow Blvd', 
'Suite 9621', 
'Char',
'NC', 
'28562', 
'704-555-9512', 
'ChetumNHowe', 
'1932-05-10',
0,
0,
'AB+',
'African American',
'Male',
'')
 ON DUPLICATE KEY UPDATE MID = MID;

INSERT INTO users(MID, password, role, sQuestion, sAnswer) 
			VALUES (684, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'what is your favorite color?', 'blue')
 ON DUPLICATE KEY UPDATE MID = MID;
 /*password: pw*/

 
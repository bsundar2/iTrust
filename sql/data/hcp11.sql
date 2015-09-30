
INSERT INTO personnel(
MID,
AMID,
role,
lastName, 
firstName, 
address1,
address2,
city,
state,
zip,
phone,
specialty,
email)
VALUES (
9900000025,
null,
'HCP',
'Reid',
'Spencer',
'2110 Tha Circle',
'Apt. 666',
'Gastonia',
'NC',
'28056',
'452-251-7851',
'nutritionist',
'sreid@iTrust.org'
) ON DUPLICATE KEY UPDATE mid = mid;

INSERT INTO users(MID, password, role, sQuestion, sAnswer) VALUES(9900000025, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'HCP', 'second letter?', 'b')
ON DUPLICATE KEY UPDATE mid = mid;

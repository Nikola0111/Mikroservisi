--LOGININFO
INSERT INTO LOGIN_INFO (ID, EMAIL, IS_ACCOUNT_NON_EXPIRED, IS_ACCOUNT_NON_LOCKED, IS_CREDENTIALS_NON_EXPIRED, IS_ENABLED,  PASSWORD, USERNAME) 
VALUES (1, 'admin@gmail.com', true, true, true, true, 'Susa$k0njina', 'admin');

INSERT INTO LOGIN_INFO (ID, EMAIL, IS_ACCOUNT_NON_EXPIRED, IS_ACCOUNT_NON_LOCKED, IS_CREDENTIALS_NON_EXPIRED, IS_ENABLED,  PASSWORD, USERNAME) 
VALUES (2, 'user@gmail.com', true, true, true, true, 'Susa$k0njina', 'user');

INSERT INTO LOGIN_INFO (ID, EMAIL, IS_ACCOUNT_NON_EXPIRED, IS_ACCOUNT_NON_LOCKED, IS_CREDENTIALS_NON_EXPIRED, IS_ENABLED,  PASSWORD, USERNAME) 
VALUES (3, 'kajza@gmail.com', true, true, true, true, 'Susa$k0njina', 'kajza');

INSERT INTO LOGIN_INFO (ID, EMAIL, IS_ACCOUNT_NON_EXPIRED, IS_ACCOUNT_NON_LOCKED, IS_CREDENTIALS_NON_EXPIRED, IS_ENABLED,  PASSWORD, USERNAME) 
VALUES (4, 'agent@gmail.com', true, true, true, true, 'Susa$k0njina', 'agent');

INSERT INTO LOGIN_INFO (ID, EMAIL, IS_ACCOUNT_NON_EXPIRED, IS_ACCOUNT_NON_LOCKED, IS_CREDENTIALS_NON_EXPIRED, IS_ENABLED,  PASSWORD, USERNAME) 
VALUES (5, 'sss', true, true, true, true, 'sss', 'sss');

INSERT INTO LOGIN_INFO (ID, EMAIL, IS_ACCOUNT_NON_EXPIRED, IS_ACCOUNT_NON_LOCKED, IS_CREDENTIALS_NON_EXPIRED, IS_ENABLED,  PASSWORD, USERNAME) 
VALUES (6, 'ddd', true, true, true, true, 'ddd', 'ddd');


--USER
INSERT INTO USER_ENTITY (ID, JMBG, NAME, PHONE_NUMBER, SURNAME, USER_TYPE, LOGIN_INFO) VALUES 
(1, 0111997710268, 'Administrator', 0691408080, 'Administrator', 0, 1);

INSERT INTO USER_ENTITY (ID, JMBG, NAME, PHONE_NUMBER, SURNAME, USER_TYPE, LOGIN_INFO) VALUES 
(2, 0111997710260, 'Korisnik', 0691408082, 'Korisnik', 2, 2);

INSERT INTO USER_ENTITY (ID, JMBG, NAME, PHONE_NUMBER, SURNAME, USER_TYPE, LOGIN_INFO) VALUES 
(3, 0111997710263, 'Korisnik', 0691408082, 'Korisnik', 2, 3);

INSERT INTO USER_ENTITY (ID, JMBG, NAME, PHONE_NUMBER, SURNAME, USER_TYPE, LOGIN_INFO) VALUES 
(4, 0111997710265, 'Agent', 0691408082, 'Agent', 1, 4);

INSERT INTO USER_ENTITY (ID, JMBG, NAME, PHONE_NUMBER, SURNAME, USER_TYPE, LOGIN_INFO) VALUES 
(5, 0111997710273, 'Korisnik', 0691408082, 'Korisnik', 2, 5);

INSERT INTO USER_ENTITY (ID, JMBG, NAME, PHONE_NUMBER, SURNAME, USER_TYPE, LOGIN_INFO) VALUES 
(6, 0111997710265, 'Agent', 0691408082, 'Agent', 1, 6);

--ENDENTITY
INSERT INTO ENDENTITY (ID, ACTIVATED, ADMIN_APPROVED, BLOCKED, NUMBER_OF_ADS, NUMBER_OF_REQUESTS_CANCELED, USER_ID) VALUES
(1, TRUE, TRUE, FALSE, 0, 0, 3);

INSERT INTO ENDENTITY (ID, ACTIVATED, ADMIN_APPROVED, BLOCKED, NUMBER_OF_ADS, NUMBER_OF_REQUESTS_CANCELED, USER_ID) VALUES
(2, FALSE, FALSE, FALSE, 0, 0, 2);

INSERT INTO ENDENTITY (ID, ACTIVATED, ADMIN_APPROVED, BLOCKED, NUMBER_OF_ADS, NUMBER_OF_REQUESTS_CANCELED, USER_ID) VALUES
(3, TRUE, TRUE, FALSE, 0, 0, 5);


--AGENT
INSERT INTO AGENT (ID, ADRESS, BUSINESS_REGISTRATION_NUMBER, FIRST_LOGIN, NUMBER_ADS, USER_ID) VALUES
(1, 'ADRESA', 54343, FALSE, 0, 4);

INSERT INTO AGENT (ID, ADRESS, BUSINESS_REGISTRATION_NUMBER, FIRST_LOGIN, NUMBER_ADS, USER_ID) VALUES
(2, 'ADRESA', 54345, TRUE, 0, 6);



--ADMINISTRATOR
INSERT INTO ADMINISTRATOR (ID, USER_ID) VALUES 
(1, 1);

INSERT INTO END_USER_RENTED_CARS(END_USER_ID, RENTED_CARS) values (1, 1);
INSERT INTO END_USER_RENTED_CARS(END_USER_ID, RENTED_CARS) values (2, 1);
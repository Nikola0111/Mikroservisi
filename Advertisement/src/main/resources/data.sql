INSERT INTO BRAND(ID, NAME, CODE) VALUES (1, 'BMW', 'safku2');
INSERT INTO BRAND(ID, NAME, CODE) VALUES (2, 'Volkswagen', 'safku3');
INSERT INTO BRAND(ID, NAME, CODE) VALUES (3, 'Audi', 'safku4');
INSERT INTO BRAND(ID, NAME, CODE) VALUES (4, 'Skoda', 'safku5');
INSERT INTO BRAND(ID, NAME, CODE) VALUES (5, 'Fiat', 'safku6');
INSERT INTO BRAND(ID, NAME, CODE) VALUES (6, 'Ford', 'safku7');
INSERT INTO BRAND(ID, NAME, CODE) VALUES (7, 'Peugeot', 'safku8');
INSERT INTO BRAND(ID, NAME, CODE) VALUES (8, 'BMW', 'safku9');

INSERT INTO CAR_CLASS(ID, NAME, CODE) VALUES (1, 'Karavan', 'sa12ku2');
INSERT INTO CAR_CLASS(ID, NAME, CODE) VALUES (2, 'Hatchback', 'sa12ku3');
INSERT INTO CAR_CLASS(ID, NAME, CODE) VALUES (3, 'Limousine', 'sa12ku4');
INSERT INTO CAR_CLASS(ID, NAME, CODE) VALUES (4, 'SUV', 'sa12ku5');
INSERT INTO CAR_CLASS(ID, NAME, CODE) VALUES (5, 'Minivan', 'sa12ku6');
INSERT INTO CAR_CLASS(ID, NAME, CODE) VALUES (6, 'Pickup truck', 'sa12ku7');
INSERT INTO CAR_CLASS(ID, NAME, CODE) VALUES (7, 'Van', 'sa12ku8');
INSERT INTO CAR_CLASS(ID, NAME, CODE) VALUES (8, 'Truck', 'sa12ku9');


INSERT INTO FUEL_TYPE(ID, NAME, CODE) VALUES (1, 'Dizel', 'gj71');
INSERT INTO FUEL_TYPE(ID, NAME, CODE) VALUES (2, 'Petrol', 'gj72');
INSERT INTO FUEL_TYPE(ID, NAME, CODE) VALUES (3, 'Electric', 'gj73');
INSERT INTO FUEL_TYPE(ID, NAME, CODE) VALUES (4, 'Hybrid', 'gj74');
INSERT INTO FUEL_TYPE(ID, NAME, CODE) VALUES (5, 'Gas', 'gj75');

INSERT INTO MODEL(ID, NAME, CODE) VALUES (1, 'Polo', 'gsa2');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (2, 'Golf', 'gsa3');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (3, 'Jetta', 'gsa4');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (4, 'Passat', 'gsa5');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (5, 'Tiguan', 'gsa6');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (6, 'A3', 'gsa7');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (7, 'A4', 'gsa8');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (8, 'A6', 'gsa9');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (9, 'A7', 'gsa10');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (10, 'A8', 'gsa11');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (11, 'Punto', 'gsa12');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (12, 'Panda', 'gsa13');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (13, 'Q7', 'gsa14');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (14, 'F150', 'gsa15');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (15, 'Fabia', 'gsa16');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (16, 'M3', 'gsa25');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (17, 'X5', 'gsa26');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (18, 'M5', 'gsa27');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (19, 'Superb', 'gsa17');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (20, 'Octavia', 'gsa18');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (21, 'Golf 6', 'gsa19');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (22, 'Golf 7', 'gsa20');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (23, 'Focus', 'gsa21');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (24, 'Fiesta', 'gsa22');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (25, '206', 'gsa23');
INSERT INTO MODEL(ID, NAME, CODE) VALUES (26, '307', 'gsa24');

INSERT INTO TRANSMISSION_TYPE(ID, NAME, CODE) VALUES (1, 'Automatic', 'asd1');
INSERT INTO TRANSMISSION_TYPE(ID, NAME, CODE) VALUES (2, 'Manual', 'asd2');
INSERT INTO TRANSMISSION_TYPE(ID, NAME, CODE) VALUES (3, 'Sequential', 'asd3');
INSERT INTO TRANSMISSION_TYPE(ID, NAME, CODE) VALUES (4, 'Semi-automatic', 'asd4');

INSERT INTO ADVERTISEMENT(ID, CAR_SEATS, DISCOUNT, NAME, POSTED_BYID, PRICE, PRICE_WITH_DISCOUNT, TRAVELLED, BRAND_ID,
CAR_CLASS_ID, FUEL_TYPE_ID, MODEL_ID, TRANSMISSION_TYPE_ID) 
VALUES (1, 3, 10, 'Nesto', 1, 100, 90, 120, 1, 1, 1, 1, 1);

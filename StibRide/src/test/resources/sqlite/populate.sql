INSERT INTO `STATIONS` (id,name) VALUES (10,'DE BROUCKERE');
INSERT INTO `STATIONS` (id,name) VALUES (11,'GARE CENTRALE');
INSERT INTO `STATIONS` (id,name) VALUES (12,'PARC');
INSERT INTO `STATIONS` (id,name) VALUES (13,'ARTS-LOI');
INSERT INTO `STATIONS` (id,name) VALUES (14,'MAELBEEK');

INSERT INTO `LINES` (id) VALUES (1);
INSERT INTO `LINES` (id) VALUES (2);

INSERT INTO `STOPS` (id_line,id_station,id_order) VALUES (1,10,1);
INSERT INTO `STOPS` (id_line,id_station,id_order) VALUES (2,10,2);
INSERT INTO `STOPS` (id_line,id_station,id_order) VALUES (2,11,3);
INSERT INTO `STOPS` (id_line,id_station,id_order) VALUES (2,13,4);
INSERT INTO `STOPS` (id_line,id_station,id_order) VALUES (1,14,5);
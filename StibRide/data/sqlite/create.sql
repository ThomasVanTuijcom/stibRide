DROP TABLE IF EXISTS `STATIONS`;
CREATE TABLE IF NOT EXISTS `STATIONS` (
	`id`	INTEGER,
	`name`	TEXT NOT NULL,
	PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS `LINES`;
CREATE TABLE IF NOT EXISTS `LINES` (
	`id`	INTEGER,
	PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS `STOPS`;
CREATE TABLE IF NOT EXISTS `STOPS` (
	`id_line`	INTEGER NOT NULL,
	`id_station`	INTEGER NOT NULL,
	`id_order`	INTEGER NOT NULL,
	FOREIGN KEY(`id_line`) REFERENCES `LINES`(`id`),
	FOREIGN KEY(`id_station`) REFERENCES `STATIONS`(`id`),
	PRIMARY KEY(`id_line`,`id_station`)
);

DROP TABLE IF EXISTS `FAVORIS`;
CREATE TABLE IF NOT EXISTS `FAVORIS` (
    `name` VARCHAR(50) PRIMARY KEY,
    `id_origin` INTEGER NOT NULL,
    `id_destination` INTEGER NOT NULL,
    FOREIGN KEY(`id_origin`) REFERENCES `STATIONS`(`id`),
    FOREIGN KEY(`id_destination`) REFERENCES `STATIONS`(`id`),
);
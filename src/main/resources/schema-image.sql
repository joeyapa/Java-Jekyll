CREATE TABLE IF NOT EXIST tbl_image (
	id				VARCHAR (52) 	NOT NULL 	PRIMARY KEY,
	status			VARCHAR (2)		NOT NULL	DEFAULT('AA'),
	blob			BLOB			NOT NULL,
	created_date	DATETIME,
	created_by		VARCHAR (32),
	version_no		INTEGER			NOT NULL 	DEFAULT(1)
);

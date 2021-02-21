/**
 * 
 */
CREATE TABLE IF NOT EXIST tbl_user (
	id				VARCHAR (52) 	NOT NULL 	PRIMARY KEY,
	status			VARCHAR (2)		NOT NULL	DEFAULT('AA'),
	json			TEXT			NOT NULL,
	version_no		INTEGER			NOT NULL 	DEFAULT(1),
	lastupdate_by	VARCHAR (32),
	lastupdate_date	DATETIME		NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	create_by		VARCHAR (32),
	create_date		DATETIME		NOT NULL	DEFAULT CURRENT_TIMESTAMP
);
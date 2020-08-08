CREATE TABLE IF NOT EXIST tbl_page (
	page_id			VARCHAR (52) 	NOT NULL 	PRIMARY KEY,
	status			VARCHAR (2)		NOT NULL	DEFAULT('AA'),
	created_date	DATETIME,
	created_by		VARCHAR (32),
	version_no		INTEGER			NOT NULL 	DEFAULT(1)
);

CREATE TABLE IF NOT EXIST tbl_page_detail (
	page_detail_id	VARCHAR (52) 	NOT NULL 	PRIMARY KEY,
	page_id			VARCHAR (52) 	NOT NULL 	,
	status			VARCHAR (2)		NOT NULL	DEFAULT('AA'),
	content			TEXT,
	created_date	DATETIME,
	created_by		VARCHAR (32),
	version_no		INTEGER			NOT NULL 	DEFAULT(1)
);
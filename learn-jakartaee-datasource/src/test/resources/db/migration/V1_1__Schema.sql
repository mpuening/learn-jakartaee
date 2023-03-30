CREATE TABLE post(
	id INT NOT NULL,
	publish_date DATE NOT NULL,
 	view_number INT DEFAULT 0,
	PRIMARY KEY (id)
);
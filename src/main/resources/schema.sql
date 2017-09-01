CREATE TABLE IF NOT EXISTS accounts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) UNIQUE NOT NULL,
    password BINARY(60) NOT NULL,
    type ENUM("USER", "ADMIN") NOT NULL,
    status ENUM("ACTIVE", "PENDING") NOT NULL,
    login_times INT NOT NULL DEFAULT 0,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY (email)
)^;

CREATE TABLE IF NOT EXISTS registration_tokens (
    id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    token VARCHAR(50) NOT NULL,
    expires_on DATETIME, -- maybe not null
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE INDEX `registration_token` (`token`)
)^;

CREATE TABLE IF NOT EXISTS authentication_tokens (
    id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    token VARCHAR(50) NOT NULL,
    expires_on DATETIME, -- maybe not null
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	  UNIQUE INDEX `authentication_token` (`token`)
)^;

CREATE TABLE IF NOT EXISTS adverts (
	id INT(11) NOT NULL AUTO_INCREMENT,
	category SMALLINT(6) NOT NULL,
	maker SMALLINT(6) NOT NULL,
	first_register SMALLINT(6) NOT NULL,
	price MEDIUMINT(9) NOT NULL,
	power_cp TINYINT(3) UNSIGNED NOT NULL,
	power_kW TINYINT(4) NOT NULL,
	kilometer MEDIUMINT(9) NOT NULL,
	fuel_type SMALLINT(6) NOT NULL,
	driving_mode SMALLINT(6) NOT NULL,
	transmission SMALLINT(6) NOT NULL,
	cubic_capacity SMALLINT(6) NOT NULL,
	colour SMALLINT(6) NOT NULL,
	feature SMALLINT(6) NOT NULL,
	tva BIT(1) NOT NULL,
	vendor SMALLINT(6) NOT NULL,
	PRIMARY KEY (id)
)^;

CREATE TABLE IF NOT EXISTS reset_password_tokens (
	id INT(11) NOT NULL AUTO_INCREMENT,
	account_id INT(11) NOT NULL,
	password BINARY(60) NOT NULL,
	token VARCHAR(50) NOT NULL,
	expires_on DATETIME NULL DEFAULT NULL,
	created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (id),
	UNIQUE INDEX reset_password_token (token)
)^;

DROP FUNCTION IF EXISTS geo_distance^;
CREATE FUNCTION geo_distance(
        lat1 FLOAT, lon1 FLOAT,
        lat2 FLOAT, lon2 FLOAT
     ) RETURNS FLOAT
    NO SQL DETERMINISTIC
BEGIN
    RETURN 111000.045 * DEGREES(ACOS(COS(RADIANS(lat1)) * COS(RADIANS(lat2)) * COS(RADIANS(lon2) - RADIANS(lon1)) +SIN(RADIANS(lat1)) * SIN(RADIANS(lat2))));
END^;


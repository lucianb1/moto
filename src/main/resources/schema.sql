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
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)^;

CREATE TABLE IF NOT EXISTS authentication_tokens (
    id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    token VARCHAR(50) NOT NULL,
    expires_on DATETIME, -- maybe not null
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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


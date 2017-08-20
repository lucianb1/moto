CREATE TABLE IF NOT EXISTS accounts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) UNIQUE NOT NULL,
    password BINARY(60) NOT NULL,
    type ENUM("USER", "PROVIDER", "ADMIN") NOT NULL,
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

CREATE TABLE IF NOT EXISTS users (
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(30),
	account_id INT NOT NULL,
	picture_name VARCHAR(256),
	picture_type VARCHAR(20),
	gender ENUM("MALE", "FEMALE") NOT NULL,
	birthday DATE,
	home_lat DOUBLE,
	home_lon DOUBLE,
	created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)^;

CREATE TABLE IF NOT EXISTS providers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    name VARCHAR(30),
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    status VARCHAR(50) NOT NULL, -- TODO make this enum after the statuses are clear
    average_score FLOAT NOT NULL DEFAULT 0,
    recommended_by INT NOT NULL DEFAULT 0,
    reviews_count INT NOT NULL DEFAULT 0,
    description VARCHAR(256),
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)^;

CREATE TABLE IF NOT EXISTS users_favourite_providers(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    provider_id INT NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)^;

CREATE TABLE IF NOT EXISTS category_parents (
    id INT PRIMARY KEY AUTO_INCREMENT,
    image_name VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    gender BIT NOT NULL
)^;


INSERT INTO category_parents (id, name, image_name ,gender)
SELECT * FROM (
	SELECT 1, "Hair Care","1.jpg", 0 FROM DUAL UNION ALL
	SELECT 2, "Hair Care","1.jpg", 1 FROM DUAL UNION ALL
	SELECT 3, "Nail Care","2.jpg", 0 FROM DUAL UNION ALL
	SELECT 4, "Nail Care","2.jpg", 1 FROM DUAL UNION ALL
	SELECT 5, "Make up",  "3.jpg", 1 FROM DUAL UNION ALL
	SELECT 6, "Eyebrows", "4.jpg", 0 FROM DUAL UNION ALL
	SELECT 7, "Eyebrows", "4.jpg", 1 FROM DUAL UNION ALL
	SELECT 8, "Eyelashes","5.jpg", 0 FROM DUAL UNION ALL
	SELECT 9, "Eyelashes","5.jpg", 1 FROM DUAL
) AS t WHERE (SELECT COUNT(id) FROM category_parents) = 0^;

CREATE TABLE IF NOT EXISTS categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    parent_id INT NOT NULL
)^;

INSERT INTO categories (name, parent_id)
SELECT * FROM (
	SELECT "Haircut & brushing" AS name, 2 AS parent_id FROM DUAL UNION ALL
	SELECT "Coloring", 2 FROM DUAL UNION ALL
	SELECT "Highlights", 2 FROM DUAL UNION ALL
	SELECT "Curling", 2 FROM DUAL UNION ALL
	SELECT "Straightening", 2 FROM DUAL UNION ALL
	SELECT "Blowout", 2 FROM DUAL UNION ALL
	SELECT "Styling", 2 FROM DUAL UNION ALL
	SELECT "Extensions", 2 FROM DUAL UNION ALL
	SELECT "Haircut & brushing", 1 FROM DUAL UNION ALL
	SELECT "Coloring", 1 FROM DUAL UNION ALL
	SELECT "Kids haircut", 1 FROM DUAL UNION ALL
	SELECT "Spa Manicure", 4 FROM DUAL UNION ALL
	SELECT "Pedicure", 4 FROM DUAL UNION ALL
	SELECT "Spa Pedicure", 4 FROM DUAL UNION ALL
	SELECT "Manicure", 3 FROM DUAL UNION ALL
	SELECT "SPA manicure", 3 FROM DUAL UNION ALL
	SELECT "Manicure with classic nail polish", 4 FROM DUAL UNION ALL
	SELECT "Nail art", 3 FROM DUAL UNION ALL
	SELECT "SPA pedicure", 3 FROM DUAL UNION ALL
	SELECT "Basic makeup", 5 FROM DUAL UNION ALL
	SELECT "Party makeup", 5 FROM DUAL UNION ALL
	SELECT "Wedding Day (Bride) makeup", 5 FROM DUAL UNION ALL
	SELECT "Artistic makeup", 5 FROM DUAL UNION ALL
	SELECT "Threading", 7 FROM DUAL UNION ALL
	SELECT "Tinting", 7 FROM DUAL UNION ALL
	SELECT "Shaping", 7 FROM DUAL UNION ALL
	SELECT "Permanent eyebrow makeup", 7 FROM DUAL UNION ALL
	SELECT "Shaping", 7 FROM DUAL UNION ALL
	SELECT "Eyelash extensions synthetic (single lash)", 6 FROM DUAL UNION ALL
	SELECT "Eyelash coloring", 9 FROM DUAL UNION ALL
	SELECT "Eyelash curling", 9 FROM DUAL UNION ALL
	SELECT "Permanent mascara", 9 FROM DUAL
) AS t WHERE (SELECT COUNT(id) FROM categories) = 0^;

CREATE TABLE IF NOT EXISTS provider_features (
    id INT PRIMARY KEY AUTO_INCREMENT,
    provider_id INT NOT NULL,
    category_id INT NOT NULL,
    UNIQUE (provider_id, category_id)
)^;

CREATE TABLE IF NOT EXISTS provider_locations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    provider_id INT NOT NULL,
    lon DOUBLE NOT NULL,
    lat DOUBLE NOT NULL,
    address VARCHAR(256) NOT NULL,
    address_details VARCHAR(256),
    remote_distance DOUBLE NOT NULL,
    has_saloon BIT NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)^;

CREATE TABLE IF NOT EXISTS category_suggestions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    is_parent BIT NOT NULL,
    parent_id INT,
    gender BIT,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)^;

CREATE TABLE IF NOT EXISTS provider_pictures (
    id INT PRIMARY KEY AUTO_INCREMENT,
    provider_id INT NOT NULL,
    picture_name VARCHAR(256) NOT NULL,
    is_primary BIT NOT NULL DEFAULT 0,
    picture_type VARCHAR(10) NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)^;

CREATE TABLE IF NOT EXISTS demands(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    gender BIT NOT NULL,
    is_full_capable BIT NOT NULL,
    only_at_home BIT NOT NULL,
    address VARCHAR(150),
    lat DOUBLE NOT NULL,
    lon DOUBLE NOT NULL,
    remote_distance INT NOT NULL,
    comment VARCHAR(100),
    location_details VARCHAR(150),
    is_expired BIT DEFAULT 0,
    picture_name VARCHAR(256) ,
    status ENUM("NEW", "CHAT","IN_BOOKING" ,"BOOKED", "DONE", "EXPIRED", "REJECTED", "CANCELLED") DEFAULT "NEW",
    booked_to_provider_id INT, -- only filled when the booked was done
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)^;

CREATE TABLE IF NOT EXISTS demand_categories(
    id INT PRIMARY KEY AUTO_INCREMENT,
    demand_id INT NOT NULL,
    category_id INT NOT NULL,
    UNIQUE(demand_id,category_id)
)^;

CREATE TABLE IF NOT EXISTS demand_intervals(
    id INT PRIMARY KEY AUTO_INCREMENT,
    demand_id INT NOT NULL,
    start_hour VARCHAR(30) NOT NULL,
    end_hour VARCHAR(30) NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)^;


CREATE TABLE IF NOT EXISTS interval_dates(
    id INT PRIMARY KEY AUTO_INCREMENT,
    date DATE NOT NULL,
    interval_id INT NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)^;

CREATE TABLE IF NOT EXISTS matched_demands( -- matched by area and categories
    id INT PRIMARY KEY AUTO_INCREMENT,
    provider_id INT NOT NULL,
    demand_id INT NOT NULL,
    status ENUM("MATCHED", "ACCEPTED", "DENIED", "BOOKED_WITH_ME", "DONE", "CANCELLED") NOT NULL DEFAULT "MATCHED", -- with me = me as a provider
    is_suspended BIT NOT NULL DEFAULT 0, -- once an user send a booking for approval, all the others get suspended
    unseen_messages_from_provider INT NOT NULL DEFAULT 0, -- positive only when ACCEPTED
    unseen_messages_to_provider INT NOT NULL DEFAULT 0, -- positive only when ACCEPTED
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY (provider_id, demand_id)
)^;

CREATE TABLE IF NOT EXISTS bookings(
    id INT PRIMARY KEY AUTO_INCREMENT,
    matched_demand_id INT NOT NULL,
    date DATETIME NOT NULL,
    price DOUBLE NOT NULL,
    seen_by_user BIT NOT NULL DEFAULT 0,
    status ENUM("PENDING", "ACCEPTED", "REJECTED") DEFAULT "PENDING",
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)^;

CREATE TABLE IF NOT EXISTS provider_reports (
    id INT PRIMARY KEY AUTO_INCREMENT,
    provider_id INT NOT NULL,
    user_id INT NOT NULL,
    causes VARCHAR(256) NOT NULL, -- json list of ids e.g [1, 2, 3]
    description VARCHAR(256),
    attachment_name VARCHAR(100),
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY (provider_id, user_id)
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


CREATE TABLE IF NOT EXISTS demand_chat_messages (
    id INT PRIMARY KEY AUTO_INCREMENT,
    matched_demand_id INT NOT NULL,
    to_provider BIT NOT NULL,
    message VARCHAR(256),
    image_name VARCHAR(100),
    seen BIT NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY (image_name)
)^;

CREATE TABLE IF NOT EXISTS contact_us_messages (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL,
    subject VARCHAR(100) NOT NULL,
    message VARCHAR(256) NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)^;


CREATE TABLE IF NOT EXISTS demand_reviews (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    demand_id INT NOT NULL,
    provider_id INT NOT NULL,
    comment VARCHAR(256),
    reply VARCHAR(256),
    score SMALLINT NOT NULL DEFAULT 0,
    recommend BIT NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, demand_id)
)^;


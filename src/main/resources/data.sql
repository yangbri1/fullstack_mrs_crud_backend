-- preload H2 DB w/ pre-existing data (records to respective entity DB table)
-- 'data.sql' SQL file must follow this file path: src > main > resources > 'data.sql' (HERE!)

-- this is a Hibernate feature where Spring Boot will automatically detect and run 'data.sql' after schema is created (depending on '@Entity' classes)
-- set up to trigger in `application.properties` file

-- inclusion of this SQL command should prevent Spring Boot from running (this checks if 'data.sql' is even being read)
-- INVALID SQL HERE; 

-- Insert moderators first (as mod_id is a FOREIGN_KEY in jobs DB so it needs to be initialized beforehand)
MERGE INTO moderators (mod_id, username, email) KEY(mod_id) VALUES (1, 'Alice', 'alice@wonderland.com');
MERGE INTO moderators (mod_id, username, email) KEY(mod_id) VALUES (2, 'Bob', 'bob@saget.com');
MERGE INTO moderators (mod_id, username, email) KEY(mod_id) VALUES (3, 'Charlie', 'charlie@chocolate.com');
MERGE INTO moderators (mod_id, username, email) KEY(mod_id) VALUES (4, 'Diana', 'diana@autumn.com');
MERGE INTO moderators (mod_id, username, email) KEY(mod_id) VALUES (5, 'Eve', 'eve@christmas.com');
MERGE INTO moderators (mod_id, username, email) KEY(mod_id) VALUES (6, 'Frank', 'frank@ocean.com');

-- 'Job' Entity Class existing records to be loaded onto 'jobs' DB table on Spring Boot startup
-- Aside: H2 is case-sensitive for column names so "yearOfExperience", "costOfLivingIndex", etc. other camelcased fields need double quotes to show H2 to use it exactly as it is ...
-- ... as well as "\yearOfExperience\" for each camelCased fields  in 'Job' entity class for it to work --- otw use underscores & change '@Column' names to underscores in 'Job' class
INSERT INTO jobs (title, description, company, location, year_of_experience, work_arrangement, yearly_salary, email, mod_id, created_on) VALUES 
('Alpha Tester', 'Test out in-house games before release', 'EA', 'Los Angeles', 3, 'HYBRID', 45000.0, 'testing@ea.com', 3, CURRENT_TIMESTAMP),
('Backend Developer', 'Build and maintain RESTful APIs', 'Spotify', 'New York City', 4, 'REMOTE', 120000.0, 'careers@spotify.com', 4, CURRENT_TIMESTAMP),
('Bay Harbor Butcher', 'Diving enthusiast', 'Miami Metro Police Department', 'Miami', 3, 'ONSITE', 95000.0, 'mpd@bayharbor.com', 5, CURRENT_TIMESTAMP),
('Jack the Ripper', 'Make clean surgical incisions when extracting parts', 'London Ward', 'London', 10, 'ONSITE', 85000.0, 'daytime@surgeon.edu', 5, CURRENT_TIMESTAMP),
('Muffin Man', 'Bake goods are on another level', 'Local Bakery', 'Venice Beach', 5, 'HYBRID', 130000.0, 'peopleofnyc@nyc.net', 6, CURRENT_TIMESTAMP);
-- 'created_on' field typically would NOT be included like 'job_id' if we were to allow the program to generate it ...
-- ... however 'created_on' was included & SQL's 'CURRENT_TIMESTAMP' so 'jobs' DB table could showcase time when 'Job' entity class was loaded (not null values) 
-- Side Note: constructor back in 'Job' class DN need 'createdOn' field as it could still be automatically populated by Hibernate whenever a new 'Job' record is POST (invoke .save(), .persist(), etc. [JPA actions])

-- H2 will still handle new moderator 'mod_id' generation but they will start at 3xxx
-- ALTER TABLE moderators ALTER COLUMN mod_id RESTART WITH 3000;
-- Other entity class records could go below
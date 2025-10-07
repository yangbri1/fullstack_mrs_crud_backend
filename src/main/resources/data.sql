-- preload H2 DB w/ pre-existing data (records to respective entity DB table)
-- 'data.sql' SQL file must follow this file path: src > main > resources > 'data.sql' (HERE!)

-- this is a Hibernate feature where Spring Boot will automatically detect and run 'data.sql' after schema is created (depending on '@Entity' classes)
-- set up to trigger in `application.properties` file

INSERT INTO jobs (title, description, company, location, yearOfExperience, costOfLivingIndex, yearlySalary, email, modId, createdOn)
VALUES 
('Alpha Tester', 'Test out in-house games before release', 'EA', 'Los Angeles', 3, 120, 45000.0, 'testing@ea.com', 3, CURRENT_TIMESTAMP),
('Backend Developer', 'Build and maintain RESTful APIs', 'Spotify', 'New York City', 4, 135, 120000.0, 'careers@spotify.com', 4, CURRENT_TIMESTAMP),
('UI/UX Designer', 'Design sleek user interfaces', 'Adobe', 'San Francisco', 2, 150, 95000.0, 'ux@adobe.com', 5, CURRENT_TIMESTAMP),
('Data Analyst', 'Analyze large sets of marketing data', 'Meta', 'Austin', 3, 110, 85000.0, 'analytics@meta.com', 6, CURRENT_TIMESTAMP),
('DevOps Engineer', 'Ensure CI/CD pipelines and infrastructure scaling', 'Netflix', 'Seattle', 5, 125, 130000.0, 'devops@netflix.com', 7, CURRENT_TIMESTAMP);

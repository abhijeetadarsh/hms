USE hms_db;

CREATE TABLE User (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) UNIQUE NOT NULL,
  password BINARY(60) NOT NULL, 
  email VARCHAR(100) UNIQUE NOT NULL, 
  name VARCHAR(100) NOT NULL, 
  gender ENUM('MALE', 'FEMALE', 'OTHER') NOT NULL, 
  contact_number VARCHAR(15), 
  address TEXT, 
  role ENUM('ADMIN', 'DOCTOR', 'PATIENT', 'STAFF') DEFAULT 'PATIENT', 
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
);

CREATE TABLE Patient (
  id INT PRIMARY KEY, 
  dob DATE NOT NULL, 
  FOREIGN KEY (id) REFERENCES User(id) ON DELETE CASCADE 
);

CREATE TABLE Doctor (
  id INT PRIMARY KEY, 
  specialization VARCHAR(100) NOT NULL, 
  FOREIGN KEY (id) REFERENCES User(id) ON DELETE CASCADE 
);

CREATE TABLE Staff (
  id INT PRIMARY KEY, 
  department ENUM('NURSING', 'RECEPTION', 'TECHNICAL', 'CLEANING', 'SECURITY') NOT NULL, 
  FOREIGN KEY (id) REFERENCES User(id) ON DELETE CASCADE 
);

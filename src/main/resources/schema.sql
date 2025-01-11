
CREATE TABLE IF NOT EXISTS User (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) UNIQUE NOT NULL,
  password BINARY(60) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  name VARCHAR(100) NOT NULL,
  gender ENUM('MALE', 'FEMALE', 'OTHER') NOT NULL,
  contactNumber VARCHAR(15),
  address TEXT,
  role ENUM('ADMIN', 'DOCTOR', 'PATIENT', 'STAFF') DEFAULT 'PATIENT',
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Patient (
  id INT PRIMARY KEY,
  dob DATE NOT NULL,
  FOREIGN KEY (id) REFERENCES User(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Doctor (
  id INT PRIMARY KEY,
  specialization VARCHAR(100) NOT NULL,
  FOREIGN KEY (id) REFERENCES User(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Staff (
  id INT PRIMARY KEY,
  department ENUM('NURSING', 'RECEPTION', 'TECHNICAL', 'CLEANING', 'SECURITY') NOT NULL,
  FOREIGN KEY (id) REFERENCES User(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Appointment (
  id INT AUTO_INCREMENT PRIMARY KEY,
  patientId INT NOT NULL,
  doctorId INT NOT NULL,
  appointmentDate DATETIME NOT NULL,
  status ENUM('SCHEDULED', 'COMPLETED', 'CANCELLED') DEFAULT 'SCHEDULED',
  reason TEXT,
  notes TEXT,
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (patientId) REFERENCES Patient(id) ON DELETE CASCADE,
  FOREIGN KEY (doctorId) REFERENCES Doctor(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS MedicalRecord (
  id INT AUTO_INCREMENT PRIMARY KEY,
  patientId INT NOT NULL,
  doctorId INT NOT NULL,
  diagnosis TEXT NOT NULL,
  prescription TEXT,
  notes TEXT,
  visitDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (patientId) REFERENCES Patient(id) ON DELETE CASCADE,
  FOREIGN KEY (doctorId) REFERENCES Doctor(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Medication (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description TEXT,
  dosage VARCHAR(50),
  recordId INT NOT NULL,
  FOREIGN KEY (recordId) REFERENCES MedicalRecord(id) ON DELETE CASCADE
);
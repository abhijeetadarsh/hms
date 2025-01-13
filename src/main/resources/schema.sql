-- Drop tables with foreign key dependencies first
DROP TABLE IF EXISTS medical_record CASCADE;
DROP TABLE IF EXISTS prescription CASCADE;
DROP TABLE IF EXISTS laboratory_test CASCADE;
DROP TABLE IF EXISTS billing CASCADE;
DROP TABLE IF EXISTS appointment CASCADE;
DROP TABLE IF EXISTS doctor_schedule CASCADE;
DROP TABLE IF EXISTS doctor CASCADE;
DROP TABLE IF EXISTS department CASCADE;
DROP TABLE IF EXISTS receptionist CASCADE;
DROP TABLE IF EXISTS staff CASCADE;
DROP TABLE IF EXISTS patient CASCADE;

-- Drop the base table last
DROP TABLE IF EXISTS user CASCADE;

-- Drop any remaining standalone tables
DROP TABLE IF EXISTS medicine_inventory CASCADE;

-- Create the base USER table
CREATE TABLE user (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT true
);

-- Create STAFF table which inherits from USER
CREATE TABLE staff (
    user_id BIGINT UNSIGNED NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    join_date DATE NOT NULL,
    contact VARCHAR(20) NOT NULL,
    address TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- Create RECEPTIONIST table which inherits from STAFF
CREATE TABLE receptionist (
    user_id BIGINT UNSIGNED NOT NULL PRIMARY KEY,
    shift VARCHAR(20) NOT NULL,
    desk_location VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES staff(user_id) ON DELETE CASCADE
);

-- Create PATIENT table which inherits from USER
CREATE TABLE patient (
    user_id BIGINT UNSIGNED NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    dob DATE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    address TEXT NOT NULL,
    phone VARCHAR(20) NOT NULL,
    blood_group VARCHAR(5),
    emergency_contact VARCHAR(100),
    allergies TEXT,
    medical_history TEXT,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- Create DEPARTMENT table
CREATE TABLE department (
    department_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    location VARCHAR(100) NOT NULL
);

-- Create DOCTOR table which inherits from STAFF
CREATE TABLE doctor (
    user_id BIGINT UNSIGNED NOT NULL PRIMARY KEY,
    department_id BIGINT UNSIGNED NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    qualification TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES staff(user_id) ON DELETE CASCADE,
    FOREIGN KEY (department_id) REFERENCES department(department_id)
);

-- Create DOCTOR_SCHEDULE table
CREATE TABLE doctor_schedule (
    schedule_id SERIAL PRIMARY KEY,
    doctor_user_id BIGINT UNSIGNED NOT NULL,
    day_of_week VARCHAR(10) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    max_appointments INTEGER NOT NULL,
    is_available BOOLEAN DEFAULT true,
    FOREIGN KEY (doctor_user_id) REFERENCES doctor(user_id) ON DELETE CASCADE
);

-- Create APPOINTMENT table
CREATE TABLE appointment (
    appointment_id SERIAL PRIMARY KEY,
    patient_user_id BIGINT UNSIGNED,
    doctor_user_id BIGINT UNSIGNED,
    booked_by_user_id BIGINT UNSIGNED,
    appointment_time TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    reason TEXT NOT NULL,
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    token_number INTEGER,
    consultation_fee DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (patient_user_id) REFERENCES patient(user_id) ON DELETE SET NULL,
    FOREIGN KEY (doctor_user_id) REFERENCES doctor(user_id) ON DELETE SET NULL,
    FOREIGN KEY (booked_by_user_id) REFERENCES user(user_id) ON DELETE SET NULL
);

-- Create MEDICAL_RECORD table
CREATE TABLE medical_record (
    record_id SERIAL PRIMARY KEY,
    patient_user_id BIGINT UNSIGNED NOT NULL,
    doctor_user_id BIGINT UNSIGNED NOT NULL,
    record_date DATE NOT NULL,
    diagnosis TEXT,
    treatment TEXT,
    notes TEXT,
    vitals TEXT,
    FOREIGN KEY (patient_user_id) REFERENCES patient(user_id),
    FOREIGN KEY (doctor_user_id) REFERENCES doctor(user_id)
);

-- Create PRESCRIPTION table
CREATE TABLE prescription (
    prescription_id SERIAL PRIMARY KEY,
    patient_user_id BIGINT UNSIGNED NOT NULL,
    doctor_user_id BIGINT UNSIGNED NOT NULL,
    appointment_id BIGINT UNSIGNED,
    prescribed_date DATE NOT NULL,
    medications TEXT NOT NULL,
    dosage TEXT NOT NULL,
    instructions TEXT,
    FOREIGN KEY (patient_user_id) REFERENCES patient(user_id),
    FOREIGN KEY (doctor_user_id) REFERENCES doctor(user_id),
    FOREIGN KEY (appointment_id) REFERENCES appointment(appointment_id)
);

-- Create LABORATORY_TEST table
CREATE TABLE laboratory_test (
    test_id SERIAL PRIMARY KEY,
    appointment_id BIGINT UNSIGNED NOT NULL,
    test_name VARCHAR(100) NOT NULL,
    test_cost DECIMAL(10,2) NOT NULL,
    test_time TIMESTAMP NOT NULL,
    test_status VARCHAR(20) NOT NULL,
    result TEXT,
    normal_range TEXT,
    FOREIGN KEY (appointment_id) REFERENCES appointment(appointment_id) ON DELETE CASCADE
);

-- Create MEDICINE_INVENTORY table
CREATE TABLE medicine_inventory (
    medicine_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    stock_quantity INTEGER NOT NULL,
    expiry_date DATE NOT NULL,
    manufacturer VARCHAR(100) NOT NULL
);

-- Create BILLING table
CREATE TABLE billing (
    bill_id SERIAL PRIMARY KEY,
    patient_user_id BIGINT UNSIGNED NOT NULL,
    appointment_id BIGINT UNSIGNED NOT NULL,
    bill_date DATE NOT NULL,
    consultation_fee DECIMAL(10,2) NOT NULL,
    medicine_charges DECIMAL(10,2) DEFAULT 0.00,
    lab_charges DECIMAL(10,2) DEFAULT 0.00,
    total_amount DECIMAL(10,2) NOT NULL,
    payment_status VARCHAR(20) NOT NULL,
    payment_method VARCHAR(50),
    FOREIGN KEY (patient_user_id) REFERENCES patient(user_id) ON DELETE CASCADE,
    FOREIGN KEY (appointment_id) REFERENCES appointment(appointment_id) ON DELETE CASCADE
);

-- Add indexes for frequently accessed columns
CREATE INDEX idx_appointment_patient ON appointment(patient_user_id);
CREATE INDEX idx_appointment_doctor ON appointment(doctor_user_id);
CREATE INDEX idx_appointment_date ON appointment(appointment_time);
CREATE INDEX idx_medical_record_patient ON medical_record(patient_user_id);
CREATE INDEX idx_prescription_patient ON prescription(patient_user_id);
CREATE INDEX idx_prescription_doctor ON prescription(doctor_user_id);
CREATE INDEX idx_doctor_department ON doctor(department_id);

-- Add constraints for enum-like columns
ALTER TABLE appointment
ADD CONSTRAINT valid_appointment_status
CHECK (status IN ('Scheduled', 'Completed', 'Cancelled', 'No-Show'));

ALTER TABLE billing
ADD CONSTRAINT valid_payment_status
CHECK (payment_status IN ('Pending', 'Paid', 'Cancelled', 'Refunded'));

ALTER TABLE laboratory_test
ADD CONSTRAINT valid_test_status
CHECK (test_status IN ('Pending', 'In Progress', 'Completed', 'Cancelled'));
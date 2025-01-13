-- Generate Users
INSERT INTO user (username, password, email) VALUES
('doc1', 'hash1', 'doc1@hospital.com'),
('doc2', 'hash2', 'doc2@hospital.com'),
('rec1', 'hash3', 'rec1@hospital.com'),
('rec2', 'hash4', 'rec2@hospital.com'),
('pat1', 'hash5', 'pat1@hospital.com'),
('pat2', 'hash6', 'pat2@hospital.com'),
('pat3', 'hash7', 'pat3@hospital.com'),
('pat4', 'hash8', 'pat4@hospital.com'),
('pat5', 'hash9', 'pat5@hospital.com'),
('pat6', 'hash10', 'pat6@hospital.com');

-- Generate Staff
INSERT INTO staff (user_id, name, join_date, contact, address) VALUES
(1, 'Dr. John Smith', '2023-01-15', '+1234567890', '123 Medical Ave'),
(2, 'Dr. Sarah Jones', '2023-02-20', '+1234567891', '456 Health St'),
(3, 'Mary Johnson', '2023-03-10', '+1234567892', '789 Hospital Rd'),
(4, 'Robert Wilson', '2023-04-05', '+1234567893', '321 Care Lane');

-- Generate Departments
INSERT INTO department (name, description, location) VALUES
('Cardiology', 'Heart and cardiovascular care', 'Building A, Floor 2'),
('Orthopedics', 'Bone and joint care', 'Building B, Floor 1');

-- Generate Doctors
INSERT INTO doctor (user_id, department_id, specialization, qualification) VALUES
(1, 1, 'Cardiologist', 'MD, Cardiology'),
(2, 2, 'Orthopedic Surgeon', 'MD, Orthopedics');

-- Generate Receptionists
INSERT INTO receptionist (user_id, shift, desk_location) VALUES
(3, 'Morning', 'Main Lobby'),
(4, 'Evening', 'Emergency Wing');

-- Generate Patients
INSERT INTO patient (user_id, name, dob, gender, address, phone, blood_group) VALUES
(5, 'Alice Brown', '1980-05-15', 'Female', '111 Patient St', '+1234567894', 'A+'),
(6, 'Bob Davis', '1975-08-22', 'Male', '222 Patient St', '+1234567895', 'O-'),
(7, 'Carol White', '1990-03-30', 'Female', '333 Patient St', '+1234567896', 'B+'),
(8, 'David Miller', '1985-11-12', 'Male', '444 Patient St', '+1234567897', 'AB+'),
(9, 'Eve Wilson', '1995-07-25', 'Female', '555 Patient St', '+1234567898', 'A-'),
(10, 'Frank Thomas', '1982-09-18', 'Male', '666 Patient St', '+1234567899', 'O+');

-- Generate Doctor Schedules
INSERT INTO doctor_schedule (doctor_user_id, day_of_week, start_time, end_time, max_appointments) VALUES
(1, 'Monday', '09:00', '17:00', 8),
(1, 'Wednesday', '09:00', '17:00', 8),
(2, 'Tuesday', '10:00', '18:00', 6),
(2, 'Thursday', '10:00', '18:00', 6);

-- Generate Appointments
INSERT INTO appointment (patient_user_id, doctor_user_id, booked_by_user_id, appointment_time, status, reason, token_number, consultation_fee) VALUES
(5, 1, 3, '2024-01-15 09:30:00', 'Completed', 'Heart checkup', 1, 150.00),
(6, 2, 3, '2024-01-16 10:30:00', 'Scheduled', 'Knee pain', 2, 200.00),
(7, 1, 4, '2024-01-17 11:00:00', 'Scheduled', 'Chest pain', 3, 150.00),
(8, 2, 4, '2024-01-18 14:00:00', 'Cancelled', 'Back pain', 4, 200.00);

-- Generate Medical Records
INSERT INTO medical_record (patient_user_id, doctor_user_id, record_date, diagnosis, treatment) VALUES
(5, 1, '2024-01-15', 'Mild hypertension', 'Prescribed blood pressure medication'),
(6, 2, '2024-01-16', 'Osteoarthritis', 'Physical therapy recommended'),
(7, 1, '2024-01-17', 'Angina', 'Prescribed nitroglycerin');

-- Generate Prescriptions
INSERT INTO prescription (patient_user_id, doctor_user_id, appointment_id, prescribed_date, medications, dosage, instructions) VALUES
(5, 1, 1, '2024-01-15', 'Amlodipine', '5mg', 'Once daily'),
(6, 2, 2, '2024-01-16', 'Ibuprofen', '400mg', 'Twice daily'),
(7, 1, 3, '2024-01-17', 'Nitroglycerin', '0.4mg', 'As needed for chest pain');

-- Generate Laboratory Tests
INSERT INTO laboratory_test (appointment_id, test_name, test_cost, test_time, test_status, result) VALUES
(1, 'Blood Pressure', 50.00, '2024-01-15 10:00:00', 'Completed', '140/90'),
(2, 'X-Ray Knee', 150.00, '2024-01-16 11:00:00', 'Pending', NULL),
(3, 'ECG', 200.00, '2024-01-17 11:30:00', 'Pending', NULL);

-- Generate Medicine Inventory
INSERT INTO medicine_inventory (name, type, unit_price, stock_quantity, expiry_date, manufacturer) VALUES
('Amlodipine 5mg', 'Tablet', 2.50, 1000, '2025-12-31', 'Pfizer'),
('Ibuprofen 400mg', 'Tablet', 1.50, 2000, '2025-06-30', 'GSK'),
('Nitroglycerin 0.4mg', 'Sublingual', 3.00, 500, '2025-03-31', 'Merck');

-- Generate Billing
INSERT INTO billing (patient_user_id, appointment_id, bill_date, consultation_fee, medicine_charges, lab_charges, total_amount, payment_status, payment_method) VALUES
(5, 1, '2024-01-15', 150.00, 25.00, 50.00, 225.00, 'Paid', 'Credit Card'),
(6, 2, '2024-01-16', 200.00, 15.00, 150.00, 365.00, 'Pending', NULL),
(7, 3, '2024-01-17', 150.00, 30.00, 200.00, 380.00, 'Pending', NULL);
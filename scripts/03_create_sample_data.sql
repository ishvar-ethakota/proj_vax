-- Insert sample data for testing
USE vaccine_reminder_db;

-- Insert sample users (parents and doctors)
INSERT INTO users (email, password_hash, first_name, last_name, phone_number, role, preferred_language) VALUES
('parent1@example.com', '$2a$10$example_hash_1', 'Rajesh', 'Kumar', '+91-9876543210', 'PARENT', 'hi'),
('parent2@example.com', '$2a$10$example_hash_2', 'Priya', 'Sharma', '+91-9876543211', 'PARENT', 'en'),
('doctor1@example.com', '$2a$10$example_hash_3', 'Dr. Suresh', 'Reddy', '+91-9876543212', 'DOCTOR', 'te'),
('doctor2@example.com', '$2a$10$example_hash_4', 'Dr. Meera', 'Patel', '+91-9876543213', 'DOCTOR', 'en');

-- Insert sample children
INSERT INTO children (parent_id, first_name, last_name, date_of_birth, gender, blood_group) VALUES
(1, 'Aarav', 'Kumar', '2023-06-15', 'MALE', 'O+'),
(1, 'Ananya', 'Kumar', '2021-03-20', 'FEMALE', 'A+'),
(2, 'Arjun', 'Sharma', '2023-12-10', 'MALE', 'B+');

-- Create vaccination schedules for the children (this would normally be done by application logic)
-- For child 1 (Aarav Kumar, born 2023-06-15)
INSERT INTO vaccination_schedules (child_id, vaccine_id, due_date, status) VALUES
(1, 1, '2023-06-15', 'COMPLETED'), -- BCG at birth
(1, 2, '2023-06-15', 'COMPLETED'), -- Hepatitis B at birth
(1, 3, '2023-08-01', 'COMPLETED'), -- OPV-1 at 1.5 months
(1, 4, '2023-08-01', 'COMPLETED'), -- DPT-1 at 1.5 months
(1, 5, '2023-08-01', 'COMPLETED'), -- Hepatitis B-1 at 1.5 months
(1, 6, '2023-08-01', 'COMPLETED'), -- Hib-1 at 1.5 months
(1, 7, '2023-09-01', 'COMPLETED'), -- OPV-2 at 2.5 months
(1, 8, '2023-09-01', 'COMPLETED'), -- DPT-2 at 2.5 months
(1, 9, '2023-09-01', 'COMPLETED'), -- Hib-2 at 2.5 months
(1, 10, '2023-10-01', 'PENDING'), -- OPV-3 at 3.5 months
(1, 11, '2023-10-01', 'PENDING'), -- DPT-3 at 3.5 months
(1, 12, '2023-10-01', 'PENDING'), -- Hepatitis B-2 at 3.5 months
(1, 13, '2023-10-01', 'PENDING'), -- Hib-3 at 3.5 months
(1, 14, '2023-10-01', 'PENDING'), -- IPV at 3.5 months
(1, 15, '2024-03-15', 'PENDING'); -- Measles-1 at 9 months

-- Insert doctor-patient relationships
INSERT INTO doctor_patients (doctor_id, child_id) VALUES
(3, 1), -- Dr. Suresh assigned to Aarav
(3, 2), -- Dr. Suresh assigned to Ananya
(4, 3); -- Dr. Meera assigned to Arjun

-- Insert sample vaccination records
INSERT INTO vaccination_records (schedule_id, doctor_id, vaccination_date, batch_number, notes) VALUES
(1, 3, '2023-06-15', 'BCG001', 'Vaccination completed successfully'),
(2, 3, '2023-06-15', 'HEP001', 'No adverse reactions observed'),
(3, 3, '2023-08-01', 'OPV001', 'Child cooperative during vaccination'),
(4, 3, '2023-08-01', 'DPT001', 'Mild fever expected, advised paracetamol'),
(5, 3, '2023-08-01', 'HEP002', 'Second dose administered'),
(6, 3, '2023-08-01', 'HIB001', 'No complications');

-- Insert sample reminders
INSERT INTO reminders (schedule_id, reminder_date, message, status) VALUES
(10, '2023-09-25', 'Reminder: OPV-3 vaccination due on 2023-10-01 for Aarav Kumar', 'SENT'),
(11, '2023-09-25', 'Reminder: DPT-3 vaccination due on 2023-10-01 for Aarav Kumar', 'SENT'),
(15, '2024-03-10', 'Reminder: Measles-1 vaccination due on 2024-03-15 for Aarav Kumar', 'PENDING');

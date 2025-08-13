-- Add doctor-patient relationship table
CREATE TABLE IF NOT EXISTS doctor_patients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    child_id BIGINT NOT NULL,
    assigned_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (doctor_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (child_id) REFERENCES children(id) ON DELETE CASCADE,
    UNIQUE KEY unique_doctor_child (doctor_id, child_id)
);

-- Insert sample doctor-patient relationships
INSERT INTO doctor_patients (doctor_id, child_id) VALUES
(2, 1), -- Dr. Smith assigned to Aarav
(2, 2), -- Dr. Smith assigned to Priya
(3, 3); -- Dr. Patel assigned to Arjun

-- Update vaccination_records table to include doctor information
ALTER TABLE vaccination_records 
ADD COLUMN administered_by BIGINT,
ADD COLUMN notes TEXT,
ADD FOREIGN KEY (administered_by) REFERENCES users(id);

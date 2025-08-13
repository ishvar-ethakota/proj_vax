-- Vaccine Reminder System Database Schema
-- MySQL Database Setup

-- Create database
CREATE DATABASE IF NOT EXISTS vaccine_reminder_db;
USE vaccine_reminder_db;

-- Users table (parents and doctors)
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    role ENUM('PARENT', 'DOCTOR') NOT NULL DEFAULT 'PARENT',
    preferred_language VARCHAR(10) DEFAULT 'en',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Children profiles table
CREATE TABLE children (
    id INT PRIMARY KEY AUTO_INCREMENT,
    parent_id INT NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender ENUM('MALE', 'FEMALE', 'OTHER') NOT NULL,
    blood_group ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL,
    photo_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Vaccines information table
CREATE TABLE vaccines (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    importance TEXT,
    side_effects TEXT,
    age_in_months INT NOT NULL, -- Age when vaccine should be given
    is_mandatory BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Vaccination schedules table (auto-calculated from DOB)
CREATE TABLE vaccination_schedules (
    id INT PRIMARY KEY AUTO_INCREMENT,
    child_id INT NOT NULL,
    vaccine_id INT NOT NULL,
    due_date DATE NOT NULL,
    status ENUM('PENDING', 'COMPLETED', 'OVERDUE', 'SKIPPED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (child_id) REFERENCES children(id) ON DELETE CASCADE,
    FOREIGN KEY (vaccine_id) REFERENCES vaccines(id) ON DELETE CASCADE,
    UNIQUE KEY unique_child_vaccine (child_id, vaccine_id)
);

-- Vaccination records table (actual vaccination history)
CREATE TABLE vaccination_records (
    id INT PRIMARY KEY AUTO_INCREMENT,
    schedule_id INT NOT NULL,
    doctor_id INT,
    vaccination_date DATE NOT NULL,
    batch_number VARCHAR(100),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (schedule_id) REFERENCES vaccination_schedules(id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES users(id) ON DELETE SET NULL
);

-- SMS reminders table
CREATE TABLE reminders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    schedule_id INT NOT NULL,
    reminder_date DATE NOT NULL,
    message TEXT NOT NULL,
    status ENUM('PENDING', 'SENT', 'FAILED') DEFAULT 'PENDING',
    sent_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (schedule_id) REFERENCES vaccination_schedules(id) ON DELETE CASCADE
);

-- Multi-language support table
CREATE TABLE translations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    language_code VARCHAR(10) NOT NULL,
    translation_key VARCHAR(200) NOT NULL,
    translation_value TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_lang_key (language_code, translation_key)
);

-- Doctor-Patient relationship table
CREATE TABLE doctor_patients (
    id INT PRIMARY KEY AUTO_INCREMENT,
    doctor_id INT NOT NULL,
    child_id INT NOT NULL,
    assigned_date DATE DEFAULT (CURRENT_DATE),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (doctor_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (child_id) REFERENCES children(id) ON DELETE CASCADE,
    UNIQUE KEY unique_doctor_child (doctor_id, child_id)
);

-- Create indexes for better performance
CREATE INDEX idx_children_parent_id ON children(parent_id);
CREATE INDEX idx_schedules_child_id ON vaccination_schedules(child_id);
CREATE INDEX idx_schedules_due_date ON vaccination_schedules(due_date);
CREATE INDEX idx_schedules_status ON vaccination_schedules(status);
CREATE INDEX idx_reminders_date ON reminders(reminder_date);
CREATE INDEX idx_reminders_status ON reminders(status);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);

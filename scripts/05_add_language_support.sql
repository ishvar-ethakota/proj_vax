-- Add language preference to users table
ALTER TABLE users 
ADD COLUMN preferred_language VARCHAR(5) DEFAULT 'en';

-- Add language support to vaccine information
CREATE TABLE IF NOT EXISTS vaccine_translations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vaccine_id BIGINT NOT NULL,
    language_code VARCHAR(5) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    importance TEXT,
    side_effects TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (vaccine_id) REFERENCES vaccines(id) ON DELETE CASCADE,
    UNIQUE KEY unique_vaccine_language (vaccine_id, language_code)
);

-- Insert sample vaccine translations
INSERT INTO vaccine_translations (vaccine_id, language_code, name, description, importance, side_effects) VALUES
-- BCG translations
(1, 'hi', 'बीसीजी (तपेदिक)', 'तपेदिक से बचाव के लिए टीका', 'तपेदिक एक गंभीर संक्रामक रोग है जो फेफड़ों को प्रभावित करता है', 'हल्का बुखार, इंजेक्शन स्थल पर सूजन'),
(1, 'te', 'బీసీజీ (క్షయవ్యాధి)', 'క్షయవ్యాధి నుండి రక్షణ కోసం వ్యాక్సిన్', 'క్షయవ్యాధి ఊపిరితిత్తులను ప్రభావితం చేసే తీవ్రమైన అంటు వ్యాధి', 'తేలికపాటి జ్వరం, ఇంజెక్షన్ ప్రాంతంలో వాపు'),

-- Hepatitis B translations
(2, 'hi', 'हेपेटाइटिस बी', 'हेपेटाइटिस बी वायरस से बचाव', 'लीवर की गंभीर बीमारी से बचाता है', 'हल्का दर्द, थकान'),
(2, 'te', 'హెపటైటిస్ బీ', 'హెపటైటిస్ బీ వైరస్ నుండి రక్షణ', 'కాలేయ తీవ్ర వ్యాధి నుండి రక్షిస్తుంది', 'తేలికపాటి నొప్పి, అలసట'),

-- OPV translations
(3, 'hi', 'मौखिक पोलियो वैक्सीन', 'पोलियो वायरस से बचाव', 'पोलियो से होने वाले पक्षाघात से बचाता है', 'बहुत कम दुष्प्रभाव'),
(3, 'te', 'నోటి పోలియో వ్యాక్సిన్', 'పోలియో వైరస్ నుండి రక్షణ', 'పోలియో వల్ల వచ్చే పక్షవాతం నుండి రక్షిస్తుంది', 'చాలా తక్కువ దుష్ప్రభావాలు');

-- Update existing users with default language
UPDATE users SET preferred_language = 'en' WHERE preferred_language IS NULL;

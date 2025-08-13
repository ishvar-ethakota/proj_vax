-- Insert standard vaccine data
USE vaccine_reminder_db;

-- Insert standard vaccines with their schedules
INSERT INTO vaccines (name, description, importance, side_effects, age_in_months, is_mandatory) VALUES
('BCG', 'Bacillus Calmette-Guérin vaccine', 'Protects against tuberculosis (TB), especially severe forms in children', 'Mild fever, swelling at injection site, small scar formation', 0, TRUE),
('Hepatitis B (Birth)', 'Hepatitis B vaccine - first dose', 'Prevents hepatitis B infection which can cause liver damage', 'Mild fever, soreness at injection site', 0, TRUE),
('OPV-1', 'Oral Polio Vaccine - first dose', 'Prevents poliomyelitis (polio) which can cause paralysis', 'Very rare: mild fever', 1.5, TRUE),
('DPT-1', 'Diphtheria, Pertussis, Tetanus - first dose', 'Prevents diphtheria, whooping cough, and tetanus', 'Fever, fussiness, swelling at injection site', 1.5, TRUE),
('Hepatitis B-1', 'Hepatitis B vaccine - second dose', 'Continues protection against hepatitis B', 'Mild fever, soreness at injection site', 1.5, TRUE),
('Hib-1', 'Haemophilus influenzae type b - first dose', 'Prevents serious bacterial infections', 'Mild fever, redness at injection site', 1.5, TRUE),
('OPV-2', 'Oral Polio Vaccine - second dose', 'Continues polio protection', 'Very rare: mild fever', 2.5, TRUE),
('DPT-2', 'Diphtheria, Pertussis, Tetanus - second dose', 'Continues protection against DPT diseases', 'Fever, fussiness, swelling at injection site', 2.5, TRUE),
('Hib-2', 'Haemophilus influenzae type b - second dose', 'Continues protection against Hib', 'Mild fever, redness at injection site', 2.5, TRUE),
('OPV-3', 'Oral Polio Vaccine - third dose', 'Completes primary polio vaccination series', 'Very rare: mild fever', 3.5, TRUE),
('DPT-3', 'Diphtheria, Pertussis, Tetanus - third dose', 'Completes primary DPT vaccination series', 'Fever, fussiness, swelling at injection site', 3.5, TRUE),
('Hepatitis B-2', 'Hepatitis B vaccine - third dose', 'Completes primary hepatitis B series', 'Mild fever, soreness at injection site', 3.5, TRUE),
('Hib-3', 'Haemophilus influenzae type b - third dose', 'Completes primary Hib vaccination series', 'Mild fever, redness at injection site', 3.5, TRUE),
('IPV', 'Inactivated Polio Vaccine', 'Additional polio protection', 'Mild soreness at injection site', 3.5, TRUE),
('Measles-1', 'Measles vaccine - first dose', 'Prevents measles infection', 'Mild fever, rash, temporary joint pain', 9, TRUE),
('DPT Booster-1', 'DPT Booster - first booster', 'Boosts immunity against DPT diseases', 'Fever, swelling at injection site', 16, TRUE),
('OPV Booster', 'OPV Booster dose', 'Boosts polio immunity', 'Very rare: mild fever', 16, TRUE),
('Measles-2', 'Measles vaccine - second dose', 'Ensures complete measles protection', 'Mild fever, rash', 16, TRUE),
('DPT Booster-2', 'DPT Booster - second booster', 'Maintains long-term DPT immunity', 'Fever, swelling at injection site', 60, TRUE),
('TT', 'Tetanus Toxoid', 'Prevents tetanus infection', 'Mild soreness at injection site', 120, TRUE);

-- Insert sample translations for multi-language support
INSERT INTO translations (language_code, translation_key, translation_value) VALUES
-- English translations
('en', 'welcome_message', 'Welcome to Vaccine Reminder System'),
('en', 'child_profile', 'Child Profile'),
('en', 'vaccine_schedule', 'Vaccine Schedule'),
('en', 'upcoming_vaccines', 'Upcoming Vaccines'),
('en', 'completed_vaccines', 'Completed Vaccines'),
('en', 'add_child', 'Add Child'),
('en', 'login', 'Login'),
('en', 'logout', 'Logout'),
('en', 'dashboard', 'Dashboard'),

-- Hindi translations
('hi', 'welcome_message', 'टीकाकरण रिमाइंडर सिस्टम में आपका स्वागत है'),
('hi', 'child_profile', 'बच्चे की प्रोफाइल'),
('hi', 'vaccine_schedule', 'टीकाकरण कार्यक्रम'),
('hi', 'upcoming_vaccines', 'आगामी टीके'),
('hi', 'completed_vaccines', 'पूर्ण टीके'),
('hi', 'add_child', 'बच्चा जोड़ें'),
('hi', 'login', 'लॉगिन'),
('hi', 'logout', 'लॉगआउट'),
('hi', 'dashboard', 'डैशबोर्ड'),

-- Telugu translations
('te', 'welcome_message', 'వ్యాక్సిన్ రిమైండర్ సిస్టమ్‌కు స్వాగతం'),
('te', 'child_profile', 'పిల్లల ప్రొఫైల్'),
('te', 'vaccine_schedule', 'వ్యాక్సిన్ షెడ్యూల్'),
('te', 'upcoming_vaccines', 'రాబోయే వ్యాక్సిన్‌లు'),
('te', 'completed_vaccines', 'పూర్తయిన వ్యాక్సిన్‌లు'),
('te', 'add_child', 'పిల్లవాడిని జోడించండి'),
('te', 'login', 'లాగిన్'),
('te', 'logout', 'లాగ్అవుట్'),
('te', 'dashboard', 'డాష్‌బోర్డ్');

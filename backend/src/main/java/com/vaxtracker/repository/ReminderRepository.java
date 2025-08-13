boolean existsByVaccinationScheduleAndReminderDate(VaccinationSchedule vaccinationSchedule, LocalDate reminderDate);

List<Reminder> findByUserEmailOrderByReminderDateDesc(String email);

List<Reminder> findByVaccinationScheduleChildParentEmail(String email);

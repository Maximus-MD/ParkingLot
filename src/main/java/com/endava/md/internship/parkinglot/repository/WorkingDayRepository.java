package com.endava.md.internship.parkinglot.repository;

import com.endava.md.internship.parkinglot.model.WorkingDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.Optional;

@Repository
public interface WorkingDayRepository extends JpaRepository<WorkingDay, Integer> {
    Optional<WorkingDay> findByDayName(DayOfWeek dayName);
}

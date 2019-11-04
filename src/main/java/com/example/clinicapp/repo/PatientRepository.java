package com.example.clinicapp.repo;

import com.example.clinicapp.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий пациентов
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    /**
     * Находит пациента по его фамилии
     *
     * @param surname строка с которой начинается фамилия
     * @return список пациентов
     */
    List<Patient> findBySurnameStartsWithIgnoreCase(String surname);
}

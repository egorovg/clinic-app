package com.example.clinicapp.repo;

import com.example.clinicapp.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий врачей
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    /**
     * Находит врача по его фамилии
     *
     * @param surname строка с которой начинается фамилия
     * @return список врачей
     */
    List<Doctor> findBySurnameStartsWithIgnoreCase(String surname);
}

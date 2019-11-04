package com.example.clinicapp.service;

import com.example.clinicapp.entity.Doctor;

import java.util.List;
import java.util.Optional;

/**
 * Сервис врачей
 */
public interface DoctorService {
    /**
     * Находит всех врачей
     *
     * @return список врачей
     */
    List<Doctor> findAll();

    /**
     * Находит врача по ID
     *
     * @param id ID
     * @return врача
     */
    Optional<Doctor> findById(Long id);

    /**
     * Находит врача по его фамилии
     *
     * @param surname строка с которой начинается фамилия
     * @return список врачей
     */
    List<Doctor> findBySurnameStartsWithIgnoreCase(String surname);

    /**
     * Сохраняет врача в БД
     *
     * @param doctor врач
     */
    void save(Doctor doctor);

    /**
     * Удаляет врача из БД
     *
     * @param doctor
     */
    void delete(Doctor doctor);
}

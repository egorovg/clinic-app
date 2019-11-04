package com.example.clinicapp.service;

import com.example.clinicapp.entity.Patient;

import java.util.List;
import java.util.Optional;

/**
 * Сервис пациентов
 */
public interface PatientService {
    /**
     * Находит всех пациентов
     *
     * @return список пациентов
     */
    List<Patient> findAll();

    /**
     * Находит пациента по ID
     *
     * @param id ID
     * @return пациента
     */
    Optional<Patient> findById(Long id);

    /**
     * Находит пациента по его фамилии
     *
     * @param surname строка с которой начинается фамилия
     * @return список пациентов
     */
    List<Patient> findBySurnameStartsWithIgnoreCase(String surname);

    /**
     * Сохраняет пациента в БД
     *
     * @param patient пациент
     */
    void save(Patient patient);

    /**
     * Удаляет пациента из БД
     *
     * @param patient пациент
     */
    void delete(Patient patient);
}

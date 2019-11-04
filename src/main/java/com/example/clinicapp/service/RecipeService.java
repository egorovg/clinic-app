package com.example.clinicapp.service;

import com.example.clinicapp.entity.Priority;
import com.example.clinicapp.entity.Recipe;

import java.util.List;
import java.util.Optional;

/**
 * Сервис рецептов
 */
public interface RecipeService {
    /**
     * Находит все рецепты
     *
     * @return
     */
    List<Recipe> findAll();

    /**
     * Находит рецепт по ID
     *
     * @param id ID
     * @return рецепт
     */
    Optional<Recipe> findById(Long id);

    /**
     * Находит рецепты по описанию
     *
     * @param description строка с которой начинается описание
     * @return список рецептов
     */
    List<Recipe> findByDescriptionStartsWithIgnoreCase(String description);

    /**
     * Находит рецепты по фамилии пациента
     *
     * @param surname строка с которой начинается фамилия
     * @return список рецептов
     */
    List<Recipe> findByPatientSurnameStartsWithIgnoreCase(String surname);

    /**
     * Находит рецепты по приоритету
     *
     * @param priority приоритет
     * @return список рецептов
     */
    List<Recipe> findByPriority(Priority priority);

    /**
     * Сохраняет рецепт в БД
     *
     * @param recipe рецепт
     */
    void save(Recipe recipe);

    /**
     * Удаляет рецепт из БД
     *
     * @param recipe рецепт
     */
    void delete(Recipe recipe);
}

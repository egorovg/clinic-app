package com.example.clinicapp.repo;

import com.example.clinicapp.entity.Priority;
import com.example.clinicapp.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий рецептов
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
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
}

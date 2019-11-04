package com.example.clinicapp.service;

import com.example.clinicapp.entity.Priority;
import com.example.clinicapp.entity.Recipe;
import com.example.clinicapp.repo.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public List<Recipe> findByDescriptionStartsWithIgnoreCase(String description) {
        return recipeRepository.findByDescriptionStartsWithIgnoreCase(description);
    }

    @Override
    public List<Recipe> findByPatientSurnameStartsWithIgnoreCase(String surname) {
        return recipeRepository.findByPatientSurnameStartsWithIgnoreCase(surname);
    }

    @Override
    public List<Recipe> findByPriority(Priority priority) {
        return recipeRepository.findByPriority(priority);
    }

    @Override
    public void save(Recipe recipe) {
        recipeRepository.save(recipe);

    }

    @Override
    public void delete(Recipe recipe) {
        recipeRepository.delete(recipe);
    }
}

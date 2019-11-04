package com.example.clinicapp.entity;

import com.example.clinicapp.constant.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 3, max = 20, message = Constants.VALID_NAME_SIZE_MESSAGE)
    @Pattern(regexp = Constants.VALID_NAME_BY_REGEX)
    private String name;

    @NotEmpty
    @Size(min = 3, max = 20, message = Constants.VALID_NAME_SIZE_MESSAGE)
    @Pattern(regexp = Constants.VALID_NAME_BY_REGEX)
    private String surname;

    @NotEmpty
    @Size(min = 3, max = 20, message = Constants.VALID_NAME_SIZE_MESSAGE)
    @Pattern(regexp = Constants.VALID_NAME_BY_REGEX)
    private String patronymic;


    @NotEmpty
    @Pattern(regexp = Constants.VALID_PHONE_BY_REGEX, message = Constants.VALID_PHONE_MESSAGE)
    private String phone;

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Recipe> recipes = new HashSet<Recipe>();

    public Patient() {
    }

    public Patient(String name, String surname, String patronymic, String phone) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public int getCountRecipes() {
        return recipes.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return Objects.equals(getId(), patient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return surname + " " + name.substring(0, 1) + "." + patronymic.substring(0, 1) + ".";
    }
}

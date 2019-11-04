package com.example.clinicapp.constant;

public class Constants {
    public static final String VALID_NAME_SIZE_MESSAGE = "Введите не менее 3 и не более 20 символов.";
    public static final String VALID_NAME_BY_REGEX = "^[а-яА-ЯёЁa-zA-Z]+$";
    public static final String VALID_PHONE_MESSAGE = "Введите номер в 10-и значном формате. Например \"927XXXXXXX\"";
    public static final String VALID_PHONE_BY_REGEX = "(^$|[0-9]{10})";
    public static final String VALID_DESCRIPTION_SIZE_MESSAGE = "Введите не менее 5 и не более 300 символов.";
    public static final String DOCTOR_DELETE_ERROR = "Невозможно удалить врача у которого есть рецепты.";
    public static final String PATIENT_DELETE_ERROR = "Невозможно удалить пациента у которого есть рецепты.";
    public static final String VALID_RECIPE_FORM_MESSAGE = "Поля \"Пациент\",\"Врач\",\"Приоритет\" не могут быть пустыми.";
}

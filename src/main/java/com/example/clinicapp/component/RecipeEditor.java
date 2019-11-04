package com.example.clinicapp.component;

import com.example.clinicapp.constant.Constants;
import com.example.clinicapp.entity.Doctor;
import com.example.clinicapp.entity.Patient;
import com.example.clinicapp.entity.Priority;
import com.example.clinicapp.entity.Recipe;
import com.example.clinicapp.service.DoctorService;
import com.example.clinicapp.service.PatientService;
import com.example.clinicapp.service.RecipeService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class RecipeEditor extends Dialog implements KeyNotifier {
    private RecipeService recipeService;
    private PatientService patientService;
    private DoctorService doctorService;
    private Recipe recipe;
    private ComboBox<Patient> patientComboBox;
    private ComboBox<Doctor> doctorComboBox;
    private ComboBox<Priority> priorityComboBox;
    private TextArea description;
    private DatePicker createDate;
    private DatePicker expiryDate;
    private Button save;
    private Button cancel;
    private Button delete;
    private Button ok;
    private Dialog dialog;
    private VerticalLayout fields;
    private VerticalLayout dialogLayout;
    private HorizontalLayout actions;
    private Binder<Recipe> binder;
    private ChangeHandler changeHandler;

    @Autowired
    public RecipeEditor(RecipeService recipeService, PatientService patientService, DoctorService doctorService) {
        this.recipeService = recipeService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.patientComboBox = new ComboBox<>("Выберете пациента");
        this.doctorComboBox = new ComboBox<>("Выберите врача");
        this.priorityComboBox = new ComboBox<>("Приоритет");
        this.description = new TextArea("Описание");
        this.createDate = new DatePicker("Дата создания");
        this.expiryDate = new DatePicker("Дата окончания");
        this.save = new Button("Сохранить", VaadinIcon.CHECK.create());
        this.cancel = new Button("Отменить");
        this.delete = new Button("Удалить", VaadinIcon.TRASH.create());
        this.ok = new Button("ОК");
        this.dialog = new Dialog();
        this.fields = new VerticalLayout(description, patientComboBox, doctorComboBox, createDate, expiryDate, priorityComboBox);
        this.dialogLayout = new VerticalLayout();
        this.actions = new HorizontalLayout(save, cancel, delete);
        this.binder = new BeanValidationBinder<>(Recipe.class);
        description.setSizeFull();
        patientComboBox.setSizeFull();
        doctorComboBox.setSizeFull();
        createDate.setSizeFull();
        expiryDate.setSizeFull();
        priorityComboBox.setSizeFull();

        dialogLayout.add(new Label(Constants.VALID_RECIPE_FORM_MESSAGE), ok);
        dialogLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        ok.addClickListener(e -> dialog.close());
        dialog.add(dialogLayout);
        binder.bindInstanceFields(this);

        this.add(fields, actions);
        this.setCloseOnEsc(true);
        this.setCloseOnOutsideClick(true);

        binder.readBean(recipe);
        patientComboBox.setRequired(true);
        doctorComboBox.setRequired(true);
        priorityComboBox.setRequired(true);

        patientComboBox.addValueChangeListener(p -> recipe.setPatient(patientComboBox.getValue()));
        doctorComboBox.addValueChangeListener(d -> recipe.setDoctor(doctorComboBox.getValue()));
        priorityComboBox.addValueChangeListener(p -> recipe.setPriority(priorityComboBox.getValue()));

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> {
            if (validateRecipeForm()) {
                save();
            } else dialog.open();
        });

        save.addClickListener(e -> {
            if (validateRecipeForm()) {
                save();
            } else dialog.open();
        });
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> {
            setOpened(false);
            recipe = null;
        });
    }

    private void delete() {
        recipeService.delete(recipe);
        changeHandler.onChange();
    }

    private void save() {
        recipeService.save(recipe);
        changeHandler.onChange();
    }

    public void editRecipe(Recipe r) {
        patientComboBox.setItems(patientService.findAll());
        doctorComboBox.setItems(doctorService.findAll());
        priorityComboBox.setItems(Priority.values());
        if (r == null) {
            setOpened(false);
            return;
        }
        boolean persisted = r.getId() != null;
        if (persisted) {
            recipe = recipeService.findById(r.getId()).get();
            patientComboBox.setValue(recipe.getPatient());
            doctorComboBox.setValue(recipe.getDoctor());
            priorityComboBox.setValue(recipe.getPriority());
        } else {
            recipe = r;
            patientComboBox.setValue(null);
            doctorComboBox.setValue(null);
            priorityComboBox.setValue(null);
        }
        delete.setVisible(persisted);
        binder.setBean(recipe);
        setOpened(true);
        description.focus();
    }

    private boolean validateRecipeForm() {
        return binder.validate().isOk() && patientComboBox.getValue() != null && doctorComboBox.getValue() != null && priorityComboBox.getValue() != null;
    }

    public void setChangeHandler(RecipeEditor.ChangeHandler h) {
        changeHandler = h;
    }

    public interface ChangeHandler {
        void onChange();
    }
}

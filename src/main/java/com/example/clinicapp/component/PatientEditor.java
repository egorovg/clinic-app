package com.example.clinicapp.component;

import com.example.clinicapp.constant.Constants;
import com.example.clinicapp.entity.Patient;
import com.example.clinicapp.service.PatientService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class PatientEditor extends Dialog implements KeyNotifier {
    private PatientService patientService;
    private Patient patient;
    private TextField name;
    private TextField surname;
    private TextField patronymic;
    private TextField phone;
    private Button save;
    private Button cancel;
    private Button delete;
    private Button ok;
    private Dialog dialog;
    private VerticalLayout dialogLayout;
    private VerticalLayout fields;
    private HorizontalLayout actions;
    private Binder<Patient> binder;
    private ChangeHandler changeHandler;

    @Autowired
    public PatientEditor(PatientService service) {
        this.patientService = service;
        this.name = new TextField("Имя");
        this.surname = new TextField("Фамилия");
        this.patronymic = new TextField("Отчество");
        this.phone = new TextField("Телефон");
        this.save = new Button("Сохранить", VaadinIcon.CHECK.create());
        this.cancel = new Button("Отмена");
        this.delete = new Button("Удалить", VaadinIcon.TRASH.create());
        this.ok = new Button("ОК");
        this.dialog = new Dialog();
        this.dialogLayout = new VerticalLayout();
        this.fields = new VerticalLayout(name, surname, patronymic, phone);
        this.actions = new HorizontalLayout(save, cancel, delete);
        this.binder = new BeanValidationBinder<>(Patient.class);
        this.add(fields, actions);
        this.setCloseOnEsc(true);
        this.setCloseOnOutsideClick(true);
        name.setSizeFull();
        surname.setSizeFull();
        patronymic.setSizeFull();
        phone.setSizeFull();

        dialogLayout.add(new Label(Constants.PATIENT_DELETE_ERROR), ok);
        dialogLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        ok.addClickListener(e -> dialog.close());
        dialog.add(dialogLayout);
        binder.bindInstanceFields(this);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> {
            if (binder.validate().isOk()) {
                save();
            }
        });

        save.addClickListener(e -> {
            if (binder.validate().isOk()) {
                save();
            }
        });
        delete.addClickListener(e -> {
            if (patient != null && patient.getCountRecipes() == 0) delete();
            else dialog.open();
        });
        cancel.addClickListener(e -> {
            setOpened(false);
            patient = null;
        });
    }

    private void delete() {
        patientService.delete(patient);
        changeHandler.onChange();
    }

    private void save() {
        patientService.save(patient);
        changeHandler.onChange();
    }

    public void editPatient(Patient p) {
        if (p == null) {
            setOpened(false);
            return;
        }
        boolean persisted = p.getId() != null;
        if (persisted) {
            patient = patientService.findById(p.getId()).get();
        } else {
            patient = p;
        }
        delete.setVisible(persisted);
        binder.setBean(patient);
        setOpened(true);
        name.focus();
    }

    public void setChangeHandler(PatientEditor.ChangeHandler h) {
        changeHandler = h;
    }

    public interface ChangeHandler {
        void onChange();
    }
}

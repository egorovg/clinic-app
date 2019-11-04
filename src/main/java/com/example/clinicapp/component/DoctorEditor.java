package com.example.clinicapp.component;

import com.example.clinicapp.constant.Constants;
import com.example.clinicapp.entity.Doctor;
import com.example.clinicapp.service.DoctorService;
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
public class DoctorEditor extends Dialog implements KeyNotifier {

    private DoctorService doctorService;
    private Doctor doctor;
    private TextField name;
    private TextField surname;
    private TextField patronymic;
    private TextField specialization;
    private Button save;
    private Button delete;
    private Button cancel;
    private Button ok;
    private Dialog dialog;
    private VerticalLayout fields;
    private VerticalLayout dialogLayout;
    private HorizontalLayout actions;
    private Binder<Doctor> binder;
    private ChangeHandler changeHandler;

    @Autowired
    public DoctorEditor(DoctorService service) {
        this.doctorService = service;
        this.name = new TextField("Имя");
        this.surname = new TextField("Фамилия");
        this.patronymic = new TextField("Отчество");
        this.specialization = new TextField("Специализация");
        this.save = new Button("Сохранить", VaadinIcon.CHECK.create());
        this.cancel = new Button("Отмена");
        this.delete = new Button("Удалить", VaadinIcon.TRASH.create());
        this.ok = new Button("ОК");
        this.dialog = new Dialog();
        this.fields = new VerticalLayout(name, surname, patronymic, specialization);
        this.dialogLayout = new VerticalLayout();
        this.actions = new HorizontalLayout(save, cancel, delete);
        this.binder = new BeanValidationBinder<>(Doctor.class);
        this.setCloseOnEsc(true);
        this.setCloseOnOutsideClick(true);
        this.add(fields, actions);

        name.setSizeFull();
        surname.setSizeFull();
        patronymic.setSizeFull();
        specialization.setSizeFull();
        binder.bindInstanceFields(this);

        dialogLayout.add(new Label(Constants.DOCTOR_DELETE_ERROR), ok);
        dialogLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        ok.addClickListener(e -> dialog.close());
        dialog.add(dialogLayout);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        this.addKeyPressListener(Key.ENTER, e -> {
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
            if (doctor != null && doctor.getCountRecipes() == 0) {
                delete();
            } else dialog.open();
        });
        cancel.addClickListener(e -> {
            setOpened(false);
            doctor = null;
        });
    }

    private void delete() {
        doctorService.delete(doctor);
        changeHandler.onChange();
    }

    private void save() {
        doctorService.save(doctor);
        changeHandler.onChange();
    }

    public void editDoctor(Doctor d) {
        if (d == null) {
            setOpened(false);
            return;
        }
        boolean persisted = d.getId() != null;
        if (persisted) {
            doctor = doctorService.findById(d.getId()).get();
        } else {
            doctor = d;
        }
        delete.setVisible(persisted);
        binder.setBean(doctor);
        setOpened(true);
        name.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    public interface ChangeHandler {
        void onChange();
    }
}

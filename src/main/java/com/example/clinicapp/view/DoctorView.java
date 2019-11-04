package com.example.clinicapp.view;

import com.example.clinicapp.component.DoctorEditor;
import com.example.clinicapp.entity.Doctor;
import com.example.clinicapp.service.DoctorService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@Route(value = "", layout = MenuView.class)
public class DoctorView extends Div {

    private Grid<Doctor> grid;
    private TextField filter;
    private DoctorService doctorService;
    private DoctorEditor doctorEditor;
    private Button addNewBtn;
    private HorizontalLayout actions;

    @Autowired
    public DoctorView(DoctorService service, DoctorEditor editor) {
        this.doctorService = service;
        this.doctorEditor = editor;
        this.grid = new Grid<>(Doctor.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Добавить врача", VaadinIcon.PLUS.create());
        this.actions = new HorizontalLayout(filter, addNewBtn);
        this.add(actions, grid, editor);

        grid.setHeightByRows(true);
        grid.setColumns("name", "surname", "patronymic", "specialization");
        grid.getColumnByKey("name").setHeader("Имя");
        grid.getColumnByKey("surname").setHeader("Фамилия");
        grid.getColumnByKey("patronymic").setHeader("Отчество");
        grid.getColumnByKey("specialization").setHeader("Специализация");
        grid.asSingleSelect().addValueChangeListener(d -> doctorEditor.editDoctor(d.getValue()));

        filter.setPlaceholder("Фильтр по фамилии");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(d -> listDoctors(d.getValue()));

        addNewBtn.addClickListener(e -> doctorEditor.editDoctor(new Doctor("", "", "", "")));

        doctorEditor.setChangeHandler(() -> {
            doctorEditor.setOpened(false);
            listDoctors(filter.getValue());
        });

        listDoctors(null);
    }

    private void listDoctors(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(doctorService.findAll());
        } else {
            if (doctorService.findBySurnameStartsWithIgnoreCase(filterText) != null)
                grid.setItems(doctorService.findBySurnameStartsWithIgnoreCase(filterText));
        }
    }
}


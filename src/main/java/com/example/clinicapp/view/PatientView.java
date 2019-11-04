package com.example.clinicapp.view;

import com.example.clinicapp.component.PatientEditor;
import com.example.clinicapp.entity.Patient;
import com.example.clinicapp.service.PatientService;
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

@Route(value = "patient", layout = MenuView.class)
public class PatientView extends Div {

    private Grid<Patient> grid;
    private TextField filter;
    private PatientService patientService;
    private PatientEditor patientEditor;
    private Button addNewBtn;
    private HorizontalLayout actions;

    @Autowired
    public PatientView(PatientService service, PatientEditor editor) {
        this.patientService = service;
        this.patientEditor = editor;
        this.grid = new Grid<>(Patient.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Добавить пациента", VaadinIcon.PLUS.create());
        this.actions = new HorizontalLayout(filter, addNewBtn);
        this.add(actions, grid, editor);

        grid.setHeightByRows(true);
        grid.setColumns("name", "surname", "patronymic", "phone");
        grid.getColumnByKey("name").setHeader("Имя");
        grid.getColumnByKey("surname").setHeader("Фамилия");
        grid.getColumnByKey("patronymic").setHeader("Отчество");
        grid.getColumnByKey("phone").setHeader("Телефон");
        grid.asSingleSelect().addValueChangeListener(p -> editor.editPatient(p.getValue()));

        filter.setPlaceholder("Фильтр по фамилии");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(p -> listPatients(p.getValue()));

        addNewBtn.addClickListener(e -> patientEditor.editPatient(new Patient("", "", "", "")));
        patientEditor.setChangeHandler(() -> {
            patientEditor.setOpened(false);
            listPatients(filter.getValue());
        });

        listPatients(null);
    }

    private void listPatients(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(patientService.findAll());
        } else {
            if (patientService.findBySurnameStartsWithIgnoreCase(filterText) != null)
                grid.setItems(patientService.findBySurnameStartsWithIgnoreCase(filterText));
        }
    }
}

package com.example.clinicapp.view;

import com.example.clinicapp.entity.Doctor;
import com.example.clinicapp.service.DoctorService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@Route(value = "stats", layout = MenuView.class)
public class StatsView extends Div {

    private Grid<Doctor> grid;
    private TextField filter;
    private DoctorService doctorService;
    private HorizontalLayout actions;

    @Autowired
    public StatsView(DoctorService service) {
        this.doctorService = service;
        this.grid = new Grid<>(Doctor.class, false);
        this.filter = new TextField();
        this.actions = new HorizontalLayout(filter);
        this.add(actions, grid);

        grid.addColumn(Doctor::toString).setHeader("Врач");
        grid.addColumn("specialization").setHeader("Специализация");
        grid.addColumn(Doctor::getCountRecipes).setHeader("Количество рецептов");
        grid.setHeightByRows(true);

        filter.setPlaceholder("Фильтр по фамилии");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(d -> listDoctors(d.getValue()));

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

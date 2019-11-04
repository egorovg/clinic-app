package com.example.clinicapp.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.RouterLayout;

public class MenuView extends Div implements RouterLayout {
    public MenuView() {
        this.addMenuElement(DoctorView.class, "Врачи");
        this.addMenuElement(PatientView.class, "Пациенты");
        this.addMenuElement(RecipeView.class, "Рецепты");
        this.addMenuElement(StatsView.class, "Статистика");
    }

    private void addMenuElement(Class<? extends Component> navigationTarget, String name) {
        Button button = new Button(name);
        button.addClickListener(e -> getUI().get().navigate(navigationTarget));
        add(button);
    }
}

package com.example.clinicapp.view;

import com.example.clinicapp.component.RecipeEditor;
import com.example.clinicapp.entity.Priority;
import com.example.clinicapp.entity.Recipe;
import com.example.clinicapp.service.RecipeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Route(value = "recipe", layout = MenuView.class)
public class RecipeView extends Div {

    private Grid<Recipe> grid;
    private TextField filter;
    private RecipeService recipeService;
    private RecipeEditor recipeEditor;
    private Button addNewBtn;
    private ComboBox<String> filterComboBox;
    private ComboBox<Priority> filterComboBoxForPriority;
    private HorizontalLayout actions;

    @Autowired
    public RecipeView(RecipeService service, RecipeEditor editor) {
        this.recipeService = service;
        this.recipeEditor = editor;
        this.grid = new Grid<>(Recipe.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Добавить рецепт", VaadinIcon.PLUS.create());
        this.filterComboBox = new ComboBox<>();
        this.filterComboBoxForPriority = new ComboBox<>();
        this.actions = new HorizontalLayout(filter, filterComboBoxForPriority, filterComboBox, addNewBtn);
        this.add(actions, grid, editor);

        grid.setHeightByRows(true);
        grid.setColumns("description", "patient", "doctor", "createDate", "expiryDate", "priority");
        grid.getColumnByKey("description").setHeader("Описание");
        grid.getColumnByKey("patient").setHeader("Пациент");
        grid.getColumnByKey("doctor").setHeader("Врач");
        grid.getColumnByKey("createDate").setHeader("Дата создания");
        grid.getColumnByKey("expiryDate").setHeader("Дата окончания");
        grid.getColumnByKey("priority").setHeader("Приоритет");
        grid.asSingleSelect().addValueChangeListener(r -> recipeEditor.editRecipe(r.getValue()));

        filterComboBoxForPriority.setVisible(false);
        filterComboBoxForPriority.setItems(Priority.values());
        filterComboBoxForPriority.setWidth("220px");
        filterComboBoxForPriority.setPlaceholder("Выберите приоритет");
        filterComboBoxForPriority.addValueChangeListener(f -> {
            if (filterComboBoxForPriority.getValue() != null)
                listRecipesWithPriorityFilter();
            else grid.setItems(recipeService.findAll());
        });

        filterComboBox.setItems("Описание", "Приоритет", "Пациент");
        filterComboBox.setValue("Описание");
        filterComboBox.addValueChangeListener(f -> {
            grid.setItems(recipeService.findAll());
            if (filterComboBox.getValue() != null)
                filter(filterComboBox.getValue());
            else {
                filter.setPlaceholder("Выберите фильтр");
                filter.setVisible(true);
                filterComboBoxForPriority.setVisible(false);
            }
        });

        filter.setPlaceholder("Фильтр по описанию");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(r -> listRecipes(r.getValue(), filterComboBox.getValue()));

        addNewBtn.addClickListener(e -> recipeEditor.editRecipe(new Recipe("", null, null, LocalDate.now(), LocalDate.now().plusDays(1), null)));

        recipeEditor.setChangeHandler(() -> {
            recipeEditor.setOpened(false);
            listRecipes(filter.getValue(), filterComboBox.getValue());
        });

        listRecipes(null, null);
    }

    private void filter(String filterComboBoxValue) {
        System.out.println(filterComboBoxValue);
        if (filterComboBoxValue.equals("Описание")) {
            filter.setPlaceholder("Фильтр по описанию");
            filter.setVisible(true);
            filterComboBoxForPriority.setVisible(false);
        } else if (filterComboBoxValue.equals("Приоритет")) {
            filter.setVisible(false);
            filterComboBoxForPriority.setVisible(true);
        } else if (filterComboBoxValue.equals("Пациент")) {
            filter.setPlaceholder("Фильтр по пациенту");
            filter.setVisible(true);
            filterComboBoxForPriority.setVisible(false);
        } else filter.setPlaceholder("Выберите фильтр");
    }

    private void listRecipesWithPriorityFilter() {
        if (recipeService.findByPriority(Priority.valueOf(filterComboBoxForPriority.getValue().toString())) != null) {
            grid.setItems(recipeService.findByPriority(Priority.valueOf(filterComboBoxForPriority.getValue().toString())));
        }
    }

    private void listRecipes(String filterText, String filterType) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(recipeService.findAll());
        } else {
            if (filterType.equals("Описание")) {
                if (recipeService.findByDescriptionStartsWithIgnoreCase(filterText) != null)
                    grid.setItems(recipeService.findByDescriptionStartsWithIgnoreCase(filterText));
            } else if (filterType.equals("Пациент")) {
                if (recipeService.findByPatientSurnameStartsWithIgnoreCase(filterText) != null)
                    grid.setItems(recipeService.findByPatientSurnameStartsWithIgnoreCase(filterText));
            }
        }
    }
}

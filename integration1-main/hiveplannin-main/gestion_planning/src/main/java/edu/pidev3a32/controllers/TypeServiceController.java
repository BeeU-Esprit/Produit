package edu.pidev3a32.controllers;


import edu.pidev3a32.entities.TypeService;
import edu.pidev3a32.services.TypeServiceService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.sql.SQLException;
import java.util.List;

public class TypeServiceController {

    @FXML
    private ComboBox<TypeService> serviceComboBox;

    @FXML
    private Button addButton;

    private TypeServiceService typeServiceService;

    public TypeServiceController() {
        this.typeServiceService = new TypeServiceService();
    }

    @FXML
    public void initialize() {
        try {
            // Remplir la ComboBox avec les types de services récupérés depuis la base de données
            List<TypeService> typeServices = typeServiceService.getAllTypeServices();
            serviceComboBox.getItems().addAll(typeServices);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Ajouter un écouteur d'événements pour le bouton d'ajout
        addButton.setOnAction(event -> {
            TypeService selectedService = serviceComboBox.getSelectionModel().getSelectedItem();
            if (selectedService != null) {
                try {
                    // Ajouter le service sélectionné
                    typeServiceService.addTypeService(selectedService);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

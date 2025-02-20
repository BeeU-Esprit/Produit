package edu.pidev3a32.controllers;


import edu.pidev3a32.entities.Abonnement;
import edu.pidev3a32.services.AbonnementService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.Date;

public class AddAbonnementController {

    @FXML
    private TextField nomServiceField;
    @FXML
    private TextField typeAbonnementField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField statusAbonnementField;

    private AbonnementService abonnementService = new AbonnementService();

    @FXML
    public void handleAddAbonnement() {
        String nomService = nomServiceField.getText();
        String typeAbonnement = typeAbonnementField.getText();
        Date date = java.sql.Date.valueOf(dateField.getValue());
        String statusAbonnement = statusAbonnementField.getText();

        Abonnement abonnement = new Abonnement(0, nomService, typeAbonnement, date, statusAbonnement, true);
        // abonnementService.addAbonnement(abonnement);

        // Message de succès
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText("Abonnement ajouté avec succès");
        alert.showAndWait();

    }
}

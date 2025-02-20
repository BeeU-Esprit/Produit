package edu.pidev3a32.controllers;


import edu.pidev3a32.services.AbonnementService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class DeleteAbonnementController {

    @FXML
    private TextField idAbonnementField;

    private AbonnementService abonnementService = new AbonnementService();

    @FXML
    public void handleDeleteAbonnement() {
        try {
            int idAbonnement = Integer.parseInt(idAbonnementField.getText());
            abonnementService.deleteAbonnement(idAbonnement);

            // Message de succès
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText("Abonnement supprimé avec succès");
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de la suppression de l'abonnement");
            alert.showAndWait();
        }
    }
}

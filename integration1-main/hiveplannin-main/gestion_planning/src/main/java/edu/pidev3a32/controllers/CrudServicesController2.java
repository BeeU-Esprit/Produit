package edu.pidev3a32.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CrudServicesController2 {

    @FXML
    private Button ajouterServiceButton; // fx:id is important!
    @FXML
    private Button modifierServiceButton;
    @FXML
    private Button supprimerServiceButton;

    @FXML
    private void handleAddAbonnement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddAbonnement.fxml")); // Correct, relative path
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter Service");
            stage.show();

        } catch (IOException e) {
            showError("Error", "Could not open Ajouter Service window: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateAbonnement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateAbonnement.fxml .fxml")); // Correct, relative path
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Service");
            stage.show();

        } catch (IOException e) {
            showError("Error", "Could not open Modifier Service window: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteAbonnement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DeleteAbonnement.fxml .fxml")); // Correct, relative path
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Supprimer Service");
            stage.show();

        } catch (IOException e) {
            showError("Error", "Could not open Supprimer Service window: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
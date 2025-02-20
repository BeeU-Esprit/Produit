/*package edu.emmapi.controllers;

import edu.emmapi.entities.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class SupprimerServiceController {

    @FXML
    private Label confirmationLabel;

    @FXML
    private Button supprimerButton;

    @FXML
    private Button annulerButton;

    private Service serviceToDelete; // Le service à supprimer

    // Méthode pour initialiser le contrôleur
    public void initialize() {
        // Vous pouvez ajouter une logique ici pour initialiser la page, si nécessaire
    }

    // Cette méthode est appelée pour configurer la suppression d'un service
    public void setServiceToDelete(Service service) {
        this.serviceToDelete = service;
        confirmationLabel.setText("Êtes-vous sûr de vouloir supprimer le service : " + service.getNomService() + " ?");
    }

    // Méthode pour gérer la suppression du service
    @FXML
    private void supprimerService(ActionEvent event) {
        if (serviceToDelete != null) {
            // Supprimer le service
            // Vous pouvez ajouter ici la logique pour supprimer le service de la base de données ou de la liste
            showAlert("Succès", "Le service a été supprimé avec succès.", AlertType.INFORMATION);

            // Fermer la fenêtre ou retourner à la page précédente (si vous avez une logique pour ça)
        } else {
            showAlert("Erreur", "Aucun service à supprimer.", AlertType.ERROR);
        }
    }

    // Méthode pour annuler l'opération de suppression
    @FXML
    private void annuler(ActionEvent event) {
        // Fermer la fenêtre ou revenir à l'écran précédent
        showAlert("Annulation", "Suppression annulée.", AlertType.INFORMATION);
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}*/
package edu.pidev3a32.controllers;


import edu.pidev3a32.entities.Service;
import edu.pidev3a32.services.ServiceService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.SQLException;


public class SupprimerServiceController {

    @FXML
    private TableView<Service> serviceTable;

    @FXML
    private Button supprimerButton;

    @FXML
    private Button annulerButton;

    private Service serviceToDelete; // Service à supprimer

    // Méthode pour initialiser la TableView avec les données
// Méthode pour initialiser la TableView avec les données
    public void initialize() {
        // Ajout d'exemples de services
        serviceTable.getItems().add(new Service(1, "Service A", "Description A", "Type A"));
        serviceTable.getItems().add(new Service(2, "Service B", "Description B", "Type B"));

        // Ajouter un gestionnaire pour la sélection d'une ligne
        serviceTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                serviceToDelete = newSelection;
            }
        });
    }


    // Méthode pour supprimer un service
    @FXML
    private void supprimerService(ActionEvent event) throws SQLException {
        if (serviceToDelete != null) {
            // Logique pour supprimer le service (par exemple de la base de données ou de la liste)
            new ServiceService().deleteService(serviceToDelete.getId());
            serviceTable.getItems().remove(serviceToDelete); // Supprimer de la TableView

            showAlert("Succès", "Le service a été supprimé avec succès.", AlertType.INFORMATION);
            closeWindow();
        } else {
            showAlert("Erreur", "Veuillez sélectionner un service à supprimer.", AlertType.ERROR);
        }
    }

    // Méthode pour annuler la suppression
    @FXML
    private void annuler(ActionEvent event) {
        closeWindow();
    }

    // Méthode pour afficher des alertes
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Fermer la fenêtre actuelle
    private void closeWindow() {
        Stage stage = (Stage) supprimerButton.getScene().getWindow();
        stage.close();
    }
}


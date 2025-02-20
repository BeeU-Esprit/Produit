package edu.pidev3a32.controllers;


import edu.pidev3a32.entities.Service;
import edu.pidev3a32.services.ServiceService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.SQLException;

public class UpdateServiceController {

    @FXML
    private TableView<Service> serviceTable;

    @FXML
    private TableColumn<Service, Integer> idColumn;

    @FXML
    private TableColumn<Service, String> nameColumn;

    @FXML
    private TableColumn<Service, String> descriptionColumn;

    @FXML
    private TableColumn<Service, String> typeColumn;

    @FXML
    private TextField nomServiceField;

    @FXML
    private TextField descriptionServiceField;

    @FXML
    private TextField typeServiceField;

    @FXML
    private Button updateButton;

    @FXML
    private Button cancelButton;

    private ObservableList<Service> servicesList = FXCollections.observableArrayList();
    private Service selectedService;

    // Méthode d'initialisation
    public void initialize() {
        // Exemples de services à afficher dans la TableView
        servicesList.add(new Service(1, "Service A", "Description A", "Type A"));
        servicesList.add(new Service(2, "Service B", "Description B", "Type B"));

        // Initialiser les colonnes de la TableView
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomService()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypeService()));

        // Remplir la TableView avec les services
        serviceTable.setItems(servicesList);

        // Ajouter un listener pour la sélection d'un service dans la TableView
        serviceTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedService = newValue;
                fillForm(selectedService);
            }
        });
    }

    // Remplir le formulaire avec les données du service sélectionné
    private void fillForm(Service service) {
        nomServiceField.setText(service.getNomService());
        descriptionServiceField.setText(service.getDescription());
        typeServiceField.setText(service.getTypeService());
    }

    // Méthode pour mettre à jour le service
    @FXML
    private void updateService(ActionEvent event) throws SQLException {
        if (selectedService != null) {
            // Mettre à jour les informations du service sélectionné
            selectedService.setNomService(nomServiceField.getText());
            selectedService.setDescription(descriptionServiceField.getText());
            selectedService.setTypeService(typeServiceField.getText());
            new ServiceService().updateService(selectedService);
            // Rafraîchir la TableView pour refléter les changements
            serviceTable.refresh();

            // Afficher une alerte de succès
            showAlert("Succès", "Le service a été mis à jour avec succès.", AlertType.INFORMATION);
            closeWindow();
        } else {
            showAlert("Erreur", "Veuillez sélectionner un service à mettre à jour.", AlertType.ERROR);
        }
    }

    // Méthode pour annuler la mise à jour
    @FXML
    private void cancelUpdate(ActionEvent event) {
        closeWindow();
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour fermer la fenêtre
    private void closeWindow() {
        Stage stage = (Stage) updateButton.getScene().getWindow();
        stage.close();
    }
}

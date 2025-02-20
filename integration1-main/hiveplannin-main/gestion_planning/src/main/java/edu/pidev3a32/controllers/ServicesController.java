package edu.pidev3a32.controllers;


import edu.pidev3a32.entities.Service;
import edu.pidev3a32.tools.MyConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServicesController {

    @FXML
    private TextField nomServiceField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField typeServiceField;
    @FXML
    private TableView<Service> servicesTable;
    @FXML
    private TableColumn<Service, Integer> idServiceColumn;
    @FXML
    private TableColumn<Service, String> nomServiceColumn;
    @FXML
    private TableColumn<Service, String> descriptionColumn;
    @FXML
    private TableColumn<Service, String> typeServiceColumn;
    @FXML
    private TableColumn<Service, Boolean> estDisponibleColumn;
    @FXML
    private Button homeButton;
    @FXML
    private Button attButton;
    @FXML
    private Button marksButton;
    @FXML
    private Button logoutButton;

    @FXML
    private TextField nomServiceFieldUpdate;
    @FXML
    private TextField descriptionFieldUpdate;
    @FXML
    private TextField typeServiceFieldUpdate;
    @FXML
    private Button updateButton;

    private Service selectedService;
    private Connection conn;

    public void initialize() {
        conn = MyConnection.getInstance().getCnx();
        configurerTableau();
        chargerServices();

        servicesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            selectedService = newValue;
            if (newValue != null) {
                nomServiceFieldUpdate.setText(newValue.getNomService());
                descriptionFieldUpdate.setText(newValue.getDescription());
                typeServiceFieldUpdate.setText(newValue.getTypeService());
            } else {
                clearUpdateFields();
            }
        });
    }

    private void configurerTableau() {
        idServiceColumn.setCellValueFactory(new PropertyValueFactory<>("idService"));
        nomServiceColumn.setCellValueFactory(new PropertyValueFactory<>("nomService"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeServiceColumn.setCellValueFactory(new PropertyValueFactory<>("typeService"));
        //estDisponibleColumn.setCellValueFactory(new PropertyValueFactory<>("estDisponible"));
    }

    private void chargerServices() {
        String query = "SELECT * FROM Service";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            servicesTable.getItems().clear();
            while (rs.next()) {
                Service service = new Service(
                        rs.getInt("idService"),
                        rs.getString("nomService"),
                        rs.getString("typeService"),
                        rs.getString("description")

                );
                servicesTable.getItems().add(service);
            }
        } catch (SQLException e) {
            showError("Error loading services", e.getMessage());
        }
    }

    @FXML
    private void ajouterService() {
        String nomService = nomServiceField.getText().trim();
        String description = descriptionField.getText().trim();
        String typeService = typeServiceField.getText().trim();

        if (nomService.isEmpty() || description.isEmpty() || typeService.isEmpty()) {
            showError("Input Validation", "All fields must be filled in!");
            return;
        }

        String query = "INSERT INTO Service (nomService, description, typeService) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomService);
            stmt.setString(2, description);
            stmt.setString(3, typeService);
            stmt.executeUpdate();
            chargerServices();
            clearFields();
        } catch (SQLException e) {
            showError("Error adding service", e.getMessage());
        }
    }

    @FXML
    private void modifierService() {
        if (selectedService == null) {
            showError("No Selection", "Please select a service to modify.");
            return;
        }

        String nomService = nomServiceFieldUpdate.getText().trim();
        String description = descriptionFieldUpdate.getText().trim();
        String typeService = typeServiceFieldUpdate.getText().trim();

        if (nomService.isEmpty() || description.isEmpty() || typeService.isEmpty()) {
            showError("Input Validation", "All fields must be filled in!");
            return;
        }

        String query = "UPDATE Service SET nomService = ?, description = ?, typeService = ? WHERE idService = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomService);
            stmt.setString(2, description);
            stmt.setString(3, typeService);
            stmt.setInt(4, selectedService.getId());
            stmt.executeUpdate();
            chargerServices();
            clearUpdateFields();
            showAlert("Success", "Service updated successfully.");
        } catch (SQLException e) {
            showError("Error modifying service", e.getMessage());
        }
    }


    @FXML
    private void supprimerService() {
        Service selectedService = servicesTable.getSelectionModel().getSelectedItem();
        if (selectedService == null) {
            showError("No selection", "Please select a service to delete.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this service?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                String query = "DELETE FROM Service WHERE idService = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, selectedService.getId());
                    stmt.executeUpdate();
                    chargerServices();
                    clearFields();
                } catch (SQLException e) {
                    showError("Error deleting service", e.getMessage());
                }
            }
        });
    }

    @FXML
    private void onClickMenu(ActionEvent event) {
        String buttonId = ((Button) event.getSource()).getId();

        switch (buttonId) {
            case "homeButton":
                System.out.println("Home button clicked");
                break;
            case "attButton":
                System.out.println("Services button clicked");
                break;
            case "marksButton":
                System.out.println("Abonnement button clicked");
                break;
            case "logoutButton":
                System.out.println("Sign out button clicked");
                break;
            default:
                break;
        }
    }

    private void clearFields() {
        nomServiceField.clear();
        descriptionField.clear();
        typeServiceField.clear();
    }

    private void clearUpdateFields() {
        nomServiceFieldUpdate.clear();
        descriptionFieldUpdate.clear();
        typeServiceFieldUpdate.clear();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
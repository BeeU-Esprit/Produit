package edu.pidev3a32.controllers;


import edu.pidev3a32.entities.Abonnement;
import edu.pidev3a32.services.AbonnementService;
import edu.pidev3a32.tools.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssocierServiceAbonnementController {

    @FXML
    private TextField idServiceField;

    @FXML
    private TextField idAbonnementField;

    @FXML
    private TableView<Abonnement> abonnementTable;

    @FXML
    private TableColumn<Abonnement, Integer> idAbonnementColumn;
    @FXML
    private TableColumn<Abonnement, String> nomServiceColumn;
    @FXML
    private TableColumn<Abonnement, String> typeAbonnementColumn;
    @FXML
    private TableColumn<Abonnement, String> dateColumn;
    @FXML
    private TableColumn<Abonnement, String> statusAbonnementColumn;
    @FXML
    private TableColumn<Abonnement, Boolean> estDisponibleColumn;

    private Connection conn;
    private AbonnementService abonnementService;
    private ObservableList<Abonnement> abonnementList = FXCollections.observableArrayList();

    public void initialize() {
        conn = MyConnection.getInstance().getCnx();
        abonnementService = new AbonnementService();

        idAbonnementColumn.setCellValueFactory(new PropertyValueFactory<>("idAbonnement"));
        nomServiceColumn.setCellValueFactory(new PropertyValueFactory<>("nomService"));
        typeAbonnementColumn.setCellValueFactory(new PropertyValueFactory<>("typeAbonnement"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusAbonnementColumn.setCellValueFactory(new PropertyValueFactory<>("statusAbonnement"));
        //estDisponibleColumn.setCellValueFactory(new PropertyValueFactory<>("estDisponible"));

        loadAbonnementData();
    }

    @FXML
    private void associerServiceAbonnement(ActionEvent event) {
        try {
            String idServiceStr = idServiceField.getText();
            String idAbonnementStr = idAbonnementField.getText();

            if (idServiceStr.isEmpty() || idAbonnementStr.isEmpty()) {
                showAlert("Error", "Please fill in both ID fields.");
                return;
            }

            int idService = Integer.parseInt(idServiceStr);
            int idAbonnement = Integer.parseInt(idAbonnementStr);

            //  (1) Get the nomService from the Abonnement table
            String getNomServiceQuery = "SELECT nomService FROM Abonnement WHERE idAbonnement = ?";
            String nomService = null;
            try (PreparedStatement nomServiceStmt = conn.prepareStatement(getNomServiceQuery)) {
                nomServiceStmt.setInt(1, idAbonnement);
                ResultSet rs = nomServiceStmt.executeQuery();
                if (rs.next()) {
                    nomService = rs.getString("nomService");
                }
            }
            if (nomService == null) {
                showAlert("Error", "Abonnement not found.");
                return;
            }


            // (2) Insert into AbonnementService
            String query = "INSERT INTO AbonnementService (idAbonnement, idService) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, idAbonnement);
                stmt.setInt(2, idService);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    showAlert("Success", "Service associated with Abonnement successfully!");
                    loadAbonnementData();
                    idServiceField.clear();
                    idAbonnementField.clear();
                } else {
                    showAlert("Error", "Failed to associate service with abonnement.");
                }
            }

        } catch (NumberFormatException ex) {
            showAlert("Error", "Invalid input. Please enter numbers for IDs.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database error: " + e.getMessage());
        }
    }


    private void loadAbonnementData() {
        abonnementList.clear();
        try {
            String query = "SELECT a.idAbonnement, a.nomService, a.typeAbonnement, a.date, a.statusAbonnement, a.estDisponible " +
                    "FROM Abonnement a"; // Requête pour récupérer les abonnements

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Abonnement abonnement = new Abonnement();
                    abonnement.setIdAbonnement(rs.getInt("idAbonnement"));
                    abonnement.setNomService(rs.getString("nomService"));
                    abonnement.setTypeAbonnement(rs.getString("typeAbonnement"));
                    //abonnement.setDate(rs.getString("date"));
                    abonnement.setStatusAbonnement(rs.getString("statusAbonnement"));
                    abonnement.setEstDisponible(rs.getBoolean("estDisponible"));

                    abonnementList.add(abonnement);
                }
            }

            abonnementTable.setItems(abonnementList);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement des données : " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
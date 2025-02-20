/*package edu.emmapi.controllers;

import edu.emmapi.entities.Abonnement;
import edu.emmapi.services.AbonnementService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.Date;

public class UpdateAbonnementController {

    @FXML
    private TextField idAbonnementField;
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
    public void handleUpdateAbonnement() {
        try {
            int idAbonnement = Integer.parseInt(idAbonnementField.getText());
            String nomService = nomServiceField.getText();
            String typeAbonnement = typeAbonnementField.getText();
            Date date = java.sql.Date.valueOf(dateField.getValue());
            String statusAbonnement = statusAbonnementField.getText();

            Abonnement abonnement = new Abonnement(idAbonnement, nomService, typeAbonnement, date, statusAbonnement, true);
            abonnementService.updateAbonnement(abonnement);

            // Message de succès
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText("Abonnement mis à jour avec succès");
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de la mise à jour de l'abonnement");
            alert.showAndWait();
        }
    }
}
package edu.emmapi.controllers;

import edu.emmapi.entities.Abonnement;
import edu.emmapi.services.AbonnementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.sql.SQLException;
import java.util.List;

public class UpdateAbonnementController {

    @FXML
    private TextField idAbonnementField;
    @FXML
    private TextField nomServiceField;
    @FXML
    private TextField typeAbonnementField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField statusAbonnementField;
    @FXML
    private ListView<String> abonnementListView;  // Liste des abonnements affichée
    @FXML
    private TextField searchField;  // Champ de recherche

    private AbonnementService abonnementService = new AbonnementService();
    private ObservableList<String> abonnementsList = FXCollections.observableArrayList();  // Liste observable

    // Méthode pour afficher tous les abonnements dans le ListView
    private void loadAbonnements() {
        try {
            List<Abonnement> abonnements = abonnementService.getAllAbonnements();
            abonnementsList.clear();  // Vider la liste avant de la remplir à nouveau
            for (Abonnement abonnement : abonnements) {
                abonnementsList.add(abonnement.getIdAbonnement() + " - " + abonnement.getNomService());
            }
            abonnementListView.setItems(abonnementsList);  // Remplir la liste
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les abonnements.");
        }
    }

    @FXML
    public void initialize() {
        loadAbonnements();  // Charger les abonnements au démarrage
    }

    // Fonction de sélection d'un abonnement dans la liste
    @FXML
    public void handleSelectAbonnement() {
        String selectedAbonnement = abonnementListView.getSelectionModel().getSelectedItem();
        if (selectedAbonnement != null) {
            String[] parts = selectedAbonnement.split(" - ");
            int id = Integer.parseInt(parts[0]);
            try {
                Abonnement abonnement = abonnementService.getAllAbonnements().stream()
                        .filter(a -> a.getIdAbonnement() == id)
                        .findFirst()
                        .orElse(null);

                if (abonnement != null) {
                    // Remplir les champs avec les informations de l'abonnement sélectionné
                    idAbonnementField.setText(String.valueOf(abonnement.getIdAbonnement()));
                    nomServiceField.setText(abonnement.getNomService());
                    typeAbonnementField.setText(abonnement.getTypeAbonnement());
                    dateField.setValue(java.time.LocalDate.ofInstant(abonnement.getDate().toInstant(), java.time.ZoneId.systemDefault()));
                    statusAbonnementField.setText(abonnement.getStatusAbonnement());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de récupérer l'abonnement.");
            }
        }
    }

    // Fonction de recherche d'abonnement
    @FXML
    public void handleSearchAbonnement(KeyEvent event) {
        String query = searchField.getText().toLowerCase();
        ObservableList<String> filteredList = FXCollections.observableArrayList();

        for (String abonnement : abonnementsList) {
            if (abonnement.toLowerCase().contains(query)) {
                filteredList.add(abonnement);
            }
        }

        abonnementListView.setItems(filteredList);  // Mettre à jour la liste affichée
    }

    // Fonction pour mettre à jour l'abonnement
    @FXML
    public void handleUpdateAbonnement() {
        try {
            int idAbonnement = Integer.parseInt(idAbonnementField.getText());
            String nomService = nomServiceField.getText();
            String typeAbonnement = typeAbonnementField.getText();
            java.util.Date date = java.sql.Date.valueOf(dateField.getValue());
            String statusAbonnement = statusAbonnementField.getText();

            Abonnement abonnement = new Abonnement(idAbonnement, nomService, typeAbonnement, date, statusAbonnement, true);
            abonnementService.updateAbonnement(abonnement);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Abonnement mis à jour avec succès!");
            loadAbonnements();  // Recharger la liste des abonnements

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de mettre à jour l'abonnement.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "L'ID de l'abonnement doit être un nombre.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite.");
        }
    }

    // Fonction pour afficher des alertes
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}*/
package edu.pidev3a32.controllers;


import edu.pidev3a32.entities.Abonnement;
import edu.pidev3a32.services.AbonnementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

import java.sql.SQLException;
import java.util.List;

public class UpdateAbonnementController {

    @FXML
    private TextField idAbonnementField;
    @FXML
    private TextField nomServiceField;
    @FXML
    private TextField typeAbonnementField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField statusAbonnementField;
    @FXML
    private ListView<Abonnement> abonnementListView;  // Liste des abonnements affichée
    @FXML
    private TextField searchField;  // Champ de recherche

    private AbonnementService abonnementService = new AbonnementService();
    private ObservableList<Abonnement> abonnementsList = FXCollections.observableArrayList();  // Liste observable

    // Méthode pour afficher tous les abonnements dans le ListView
    private void loadAbonnements() {
        try {
            List<Abonnement> abonnements = abonnementService.getAllAbonnements();
            abonnementsList.clear();  // Vider la liste avant de la remplir à nouveau
            abonnementsList.addAll(abonnements);  // Ajouter tous les abonnements récupérés
            abonnementListView.setItems(abonnementsList);  // Remplir la liste avec les abonnements
            abonnementListView.setCellFactory(new Callback<ListView<Abonnement>, ListCell<Abonnement>>() {
                @Override
                public ListCell<Abonnement> call(ListView<Abonnement> param) {
                    return new ListCell<Abonnement>() {
                        @Override
                        protected void updateItem(Abonnement item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null && !empty) {
                                setText(item.getIdAbonnement() + " - " + item.getNomService() + " (" + item.getTypeAbonnement() + ")");
                            } else {
                                setText(null);
                            }
                        }
                    };
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les abonnements.");
        }
    }

    @FXML
    public void initialize() {
        loadAbonnements();  // Charger les abonnements au démarrage
    }

    // Fonction de sélection d'un abonnement dans la liste
    @FXML
    public void handleSelectAbonnement() {
        Abonnement selectedAbonnement = abonnementListView.getSelectionModel().getSelectedItem();
        if (selectedAbonnement != null) {
            // Remplir les champs avec les informations de l'abonnement sélectionné
            idAbonnementField.setText(String.valueOf(selectedAbonnement.getIdAbonnement()));
            nomServiceField.setText(selectedAbonnement.getNomService());
            typeAbonnementField.setText(selectedAbonnement.getTypeAbonnement());
            dateField.setValue(java.time.LocalDate.ofInstant(selectedAbonnement.getDate().toInstant(), java.time.ZoneId.systemDefault()));
            statusAbonnementField.setText(selectedAbonnement.getStatusAbonnement());
        }
    }

    // Fonction de recherche d'abonnement
    @FXML
    public void handleSearchAbonnement(KeyEvent event) {
        String query = searchField.getText().toLowerCase();
        ObservableList<Abonnement> filteredList = FXCollections.observableArrayList();

        for (Abonnement abonnement : abonnementsList) {
            if (abonnement.getNomService().toLowerCase().contains(query) || abonnement.getTypeAbonnement().toLowerCase().contains(query)) {
                filteredList.add(abonnement);
            }
        }

        abonnementListView.setItems(filteredList);  // Mettre à jour la liste affichée
    }

    // Fonction pour mettre à jour l'abonnement
    @FXML
    public void handleUpdateAbonnement() {
        try {
            int idAbonnement = Integer.parseInt(idAbonnementField.getText());
            String nomService = nomServiceField.getText();
            String typeAbonnement = typeAbonnementField.getText();
            java.util.Date date = java.sql.Date.valueOf(dateField.getValue());
            String statusAbonnement = statusAbonnementField.getText();

            Abonnement abonnement = new Abonnement(idAbonnement, nomService, typeAbonnement, date, statusAbonnement, true);
            abonnementService.updateAbonnement(abonnement);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Abonnement mis à jour avec succès!");
            loadAbonnements();  // Recharger la liste des abonnements

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de mettre à jour l'abonnement.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "L'ID de l'abonnement doit être un nombre.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite.");
        }
    }

    // Fonction pour afficher des alertes
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


package edu.pidev3a32.controllers;

import edu.pidev3a32.entities.produit;
import edu.pidev3a32.services.ProduitService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class produitpage1 {

    @FXML
    private ListView<String> listProduit;
    @FXML
    private Button btnAjouter, btnSupprimer, btnModifier, btnEnregistrer, naviguerverscategorie;
    @FXML
    private TextField nomProduitField, prixField;
    @FXML
    private DatePicker dateField;

    private final ProduitService produitService = new ProduitService();
    private produit produitSelectionne;

    @FXML
    public void initialize() {
        chargerProduits();
        btnEnregistrer.setVisible(false);  // Cacher le bouton d'enregistrement au démarrage
    }

    private void chargerProduits() {
        List<produit> produitsList = produitService.afficherProduit();  // Correction ici
        ObservableList<String> produitsFormatList = FXCollections.observableArrayList();

        for (produit p : produitsList) {
            String produitFormat = String.format(
                    "🛒 Nom: %s | 📦 Catégorie: %d | 💰 Prix: %.2f TND | 📅 Date: %s | 🏢 Fournisseur: %s",
                    p.getNom_Produit(), p.getCategorie(), p.getPrix(), p.getDate(), p.getFournisseur()
            );
            produitsFormatList.add(produitFormat);
        }

        listProduit.setItems(produitsFormatList);
    }

    @FXML
    private void modifierProduit(ActionEvent actionEvent) {
        int selectedIndex = listProduit.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            List<produit> produitsList = produitService.afficherProduit(); // Correction ici
            produitSelectionne = produitsList.get(selectedIndex);

            nomProduitField.setText(produitSelectionne.getNom_Produit());
            prixField.setText(String.valueOf(produitSelectionne.getPrix()));
            dateField.setValue(LocalDate.parse(produitSelectionne.getDate()));

            btnModifier.setDisable(true);
            btnEnregistrer.setVisible(true);
        } else {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Veuillez sélectionner un produit à modifier.");
        }
    }

    @FXML
    private void enregistrerModification(ActionEvent actionEvent) {
        if (produitSelectionne != null) {
            try {
                if (nomProduitField.getText().isEmpty() || prixField.getText().isEmpty() || dateField.getValue() == null) {
                    showAlert(Alert.AlertType.WARNING, "Champs vides", "Veuillez remplir tous les champs !");
                    return;
                }

                if (!isNumeric(prixField.getText())) {
                    showAlert(Alert.AlertType.ERROR, "Erreur de validation", "Le prix doit être un nombre valide !");
                    return;
                }

                if (!isDateValid(dateField.getValue())) {
                    showAlert(Alert.AlertType.ERROR, "Erreur de validation", "La date doit être entre le 01/01/2023 et aujourd'hui.");
                    return;
                }

                produitSelectionne.setNom_Produit(nomProduitField.getText());
                produitSelectionne.setPrix(Float.parseFloat(prixField.getText()));
                produitSelectionne.setDate(dateField.getValue().toString());

                produitService.modifierProduit(produitSelectionne);
                chargerProduits();
                resetFields();

                btnModifier.setDisable(false);
                btnEnregistrer.setVisible(false);

                showAlert(Alert.AlertType.INFORMATION, "Succès", "Les modifications ont été enregistrées avec succès !");
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer un prix valide !");
            }
        }
    }

    @FXML
    private void supprimerProduit(ActionEvent actionEvent) {
        int selectedIndex = listProduit.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            List<produit> produitsList = produitService.afficherProduit(); // Correction ici
            produit selectedProduit = produitsList.get(selectedIndex);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setContentText("Voulez-vous vraiment supprimer ce produit ?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    produitService.supprimerProduit(selectedProduit);
                    chargerProduits();
                    resetFields();
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Produit supprimé avec succès !");
                }
            });
        } else {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Veuillez sélectionner un produit à supprimer.");
        }
    }

    private boolean isDateValid(LocalDate date) {
        if (date == null) {
            return false;
        }
        LocalDate minDate = LocalDate.of(2023, 1, 1);
        LocalDate today = LocalDate.now();
        return !date.isBefore(minDate) && !date.isAfter(today);
    }

    private boolean isNumeric(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void resetFields() {
        nomProduitField.clear();
        prixField.clear();
        dateField.setValue(null);
        produitSelectionne = null;
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    @FXML
    public void ouvrirAjouterProduit(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterproduit.fxml"));
            Parent parent = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajouter Produit");
            stage.setScene(new Scene(parent));
            stage.show();

            Stage currentStage = (Stage) btnAjouter.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ouvrirpage2(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/page2.fxml"));
            Parent parent = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Liste Catégorie");
            stage.setScene(new Scene(parent));
            stage.show();

            Stage currentStage = (Stage) naviguerverscategorie.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

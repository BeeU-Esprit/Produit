package edu.pidev3a32.controllers;

import edu.pidev3a32.entities.categorieproduit;
import edu.pidev3a32.services.ProduitCategorie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AjouterCategorie {

    @FXML private TextField txtNomCategorie;
    @FXML private TextField txtDescriptionCategorie;
    @FXML private Button btnChoisirImage;
    @FXML private ImageView imageViewCategorie;
    @FXML private Button btnAjouterCategorie;
    @FXML private Button btnRetour;

    private ProduitCategorie categorieService = new ProduitCategorie();
    private File selectedFile = null;
    private Stage previousStage; // Pour cacher Page2 lors de l'ouverture

    public void setPreviousStage(Stage previousStage) {
        this.previousStage = previousStage;
    }

    @FXML
    private void choisirImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png"));
        selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            imageViewCategorie.setImage(new Image(selectedFile.toURI().toString()));
        }
    }

    @FXML
    private void enregistrerCategorie() {
        String nom = txtNomCategorie.getText().trim();
        String description = txtDescriptionCategorie.getText().trim();

        if (nom.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le nom de la catégorie est obligatoire.");
            return;
        }

        byte[] imageData = null;
        if (selectedFile != null) {
            try {
                imageData = new FileInputStream(selectedFile).readAllBytes();
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger l'image.");
                return;
            }
        }

        // Création de la nouvelle catégorie
        categorieproduit nouvelleCategorie = new categorieproduit(nom, description, imageData);
        categorieService.ajouterCategorieProduit(nouvelleCategorie);

        showAlert(Alert.AlertType.INFORMATION, "Succès", "Catégorie ajoutée avec succès !");

        // Fermer la fenêtre actuelle et rouvrir Page2
        fermerFenetre();
        ouvrirPage2();
    }

    @FXML
    private void retourPage2() {
        fermerFenetre();
        ouvrirPage2();
    }

    private void fermerFenetre() {
        Stage stage = (Stage) btnAjouterCategorie.getScene().getWindow();
        stage.close();
    }

    private void ouvrirPage2() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Page2.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Gestion des Catégories");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
}

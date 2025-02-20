package edu.pidev3a32.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CrudServicesController {

    @FXML
    private Button ajouterServiceButton;
    @FXML
    private Button modifierServiceButton;
    @FXML
    private Button supprimerServiceButton;

    @FXML
    private void ajouterService(ActionEvent event) {
        try {
            // Charger le FXML pour "ajouter service" et l'ouvrir dans une nouvelle fenêtre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouter_service.fxml")); // Chemin correct
            Parent root = loader.load();

            // Récupérer le contrôleur pour ajouterService.fxml si nécessaire :
            // AjouterServiceController controller = loader.getController();
            // Vous pouvez maintenant accéder au contrôleur et à ses éléments si besoin.

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter Service"); // Définir le titre
            stage.show(); // Ou stage.showAndWait() si vous voulez bloquer la fenêtre parent

            System.out.println("Ajouter Service cliqué"); // Garder pour l'instant, supprimer plus tard
        } catch (IOException e) {
            afficherErreur("Erreur", "Impossible d'ouvrir la fenêtre Ajouter Service : " + e.getMessage());
            e.printStackTrace(); // Utile pour le débogage
        }
    }

    @FXML
    private void modifierService(ActionEvent event) {
        try {
            // Similaire à ajouterService, charger le FXML pour modifier et l'ouvrir dans une nouvelle fenêtre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifier_service.fxml")); // Chemin correct
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Service"); // Définir le titre
            stage.show();

            System.out.println("Modifier Service cliqué");
        } catch (IOException e) {
            afficherErreur("Erreur", "Impossible d'ouvrir la fenêtre Modifier Service : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void supprimerService(ActionEvent event) {
        try {
            // Charger le FXML pour supprimer service ou gérer la logique de suppression directement ici.
            // ... (Implémentation pour ouvrir supprimerService.fxml ou gérer la logique de suppression) ...
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/supprimer_service.fxml")); // Chemin correct
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Supprimer Service"); // Définir le titre
            stage.show();

            System.out.println("Supprimer Service cliqué");
        } catch (IOException e) {
            afficherErreur("Erreur", "Impossible d'ouvrir la fenêtre Supprimer Service : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    private void associerAbonnementService(ActionEvent event) {
        try {
            // Charger le FXML pour "associer abonnement service" et l'ouvrir dans une nouvelle fenêtre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/associerabonnement_service.fxml")); // Chemin correct
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Associer Abonnement et Service"); // Définir le titre
            stage.show();

            System.out.println("Associer Abonnement et Service cliqué");
        } catch (IOException e) {
            afficherErreur("Erreur", "Impossible d'ouvrir la fenêtre Associer Abonnement et Service : " + e.getMessage());
            e.printStackTrace();
        }
    }


}


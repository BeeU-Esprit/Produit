/*package edu.pidev3a32.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {
    @FXML
    private Hyperlink courslab;

    @FXML
    private Hyperlink detaillab;

    @FXML
    private Hyperlink participantlab;

    @FXML
    private Hyperlink planninglab;
    @FXML
    private Hyperlink serviceid;

    @FXML
    private Hyperlink abonnementid;

    @FXML
    private void openServiceDashboard(ActionEvent event) {
        openDashboard("/dashboard.fxml", "Service Dashboard"); // Utilisez dashboard.fxml pour le service
    }

    @FXML
    private void openAbonnementDashboard(ActionEvent event) {
        openDashboard("/dashboard2.fxml", "Abonnement Dashboard"); // Utilisez dashboard2.fxml pour l'abonnement
    }

    // Méthode openDashboard corrigée
    private void openDashboard(String fxmlPath, String title) {
        try {
            System.out.println("FXML Location: " + getClass().getResource(fxmlPath)); // Utilisez fxmlPath

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath)); // Utilisez fxmlPath
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
            // TODO: Afficher une alerte à l'utilisateur
        }
    }

    @FXML // Assurez-vous que cette méthode est annotée avec @FXML
    public void openServiceDashboard2(ActionEvent actionEvent) {
        openDashboard("/dashboard.fxml", "Service Dashboard"); // Utilisez dashboard.fxml pour le service
    }
}*/
package edu.pidev3a32.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {
    public Hyperlink produitpage;
    @FXML
    private Hyperlink courslab;

    @FXML
    private Hyperlink detaillab;

    @FXML
    private Hyperlink participantlab;

    @FXML
    private Hyperlink planninglab;

    @FXML
    private Hyperlink serviceid;

    @FXML
    private Hyperlink abonnementid;

    @FXML
    private void openServiceDashboard(ActionEvent event) {
        openDashboard("/dashboard.fxml", "Service Dashboard");
    }

    @FXML
    private void openAbonnementDashboard(ActionEvent event) {
        openDashboard("/dashboard2.fxml", "Abonnement Dashboard");
    }

    @FXML
    private void openCoursLab(ActionEvent event) {
        openDashboard("/Ajouter_Cours.fxml", "Cours Lab");
    }

    @FXML
    private void openDetailLab(ActionEvent event) {
        openDashboard("/Ajouter_Cours_Participant.fxml", "Détail Lab");
    }

    @FXML
    private void openParticipantLab(ActionEvent event) {
        openDashboard("/Ajouter_Participant.fxml", "Participant Lab");
    }

    @FXML
    private void openPlanningLab(ActionEvent event) {
        openDashboard("/Ajouter_Planning.fxml", "Planning Lab");
    }

    private void openDashboard(String fxmlPath, String title) {
        try {
            System.out.println("FXML Location: " + getClass().getResource(fxmlPath));

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
            // TODO: Afficher une alerte à l'utilisateur
        }
    }

    @FXML
    public void openServiceDashboard2(ActionEvent actionEvent) {
        openDashboard("/dashboard.fxml", "Service Dashboard");
    }

    public void openproduitLab(ActionEvent actionEvent) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/page1.fxml"));
                Parent parent = loader.load();

                // Ouvrir une nouvelle fenêtre pour ajouter un produit
                Stage stage = new Stage();
                stage.setTitle("Ajouter Produit");
                stage.setScene(new Scene(parent));
                stage.show();
                Stage currentStage = (Stage) produitpage.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}
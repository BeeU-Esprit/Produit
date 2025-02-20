
package edu.pidev3a32.controllers;

import edu.pidev3a32.entities.Planning;
import edu.pidev3a32.services.PlanningService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Detail_P {

    @FXML
    private Label courslabel;

    @FXML
    private Label datelabel;

    @FXML
    private Button modifierbuttonplanning;

    @FXML
    private Button suppplanningbutton;

    @FXML
    private Label tyepeactlabel;

    private Planning currentPlanning; // Stocker le planning courant
    private PlanningService planningService; // Service pour gérer les plannings

    // Méthode pour définir les détails du planning
    public void setPlanningDetails(Planning planning) {
        currentPlanning = planning; // Stocker le planning pour la modification
        courslabel.setText(String.valueOf(planning.getCours())); // Assurez-vous que ceci est un String
        datelabel.setText(planning.getDate_planning().toString()); // Formatez la date si nécessaire
        tyepeactlabel.setText(planning.getType_activite()); // Assurez-vous que ceci est un String

        // Si vous avez un ID à afficher dans un label (exemple, si vous avez un label idLabel)
        // idLabel.setText(String.valueOf(planning.getId_planning())); // Exemple pour afficher l'ID
    }

    // Gestionnaire de l'événement pour le bouton modifier
    @FXML
    private void handleModifierButton() {
        try {
            // Charger le fichier FXML de Modifier_P
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modifier_p.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur de Modifier_P
            Modifier_P modifierController = loader.getController();

            // Passer les détails du planning au contrôleur
            modifierController.setPlanningDetails(
                    currentPlanning.getId_planning(), // ID du planning
                    String.valueOf(currentPlanning.getCours()), // Cours
                    currentPlanning.getDate_planning().toString(), // Date
                    currentPlanning.getType_activite(), // Type
                    currentPlanning.getStatus() // Statut
            );

            // Créer une nouvelle scène et afficher la fenêtre
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Planning");
            stage.show();

            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) modifierbuttonplanning.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur lors de l'ouverture de la fenêtre de modification", e.getMessage());
        }
    }

    // Méthode pour gérer la suppression du planning
    @FXML
    private void handleSuppressionButton() {
        // Ajouter une confirmation avant la suppression
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de Suppression");
        confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce planning ?");
        confirmationAlert.setContentText("Cette action est irréversible.");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                try {
                    // Assurez-vous que le service est correctement instancié
                    planningService = new PlanningService(); // Instancier PlanningService

                    // Supprimer le planning de la base de données
                    planningService.deleteplanning(currentPlanning);  // Utilisez la méthode deletePlanning avec l'objet planning

                    // Afficher une alerte de succès
                    showAlert("Suppression réussie", "Le planning a été supprimé avec succès.");

                    // Fermer la fenêtre de détail
                    Stage currentStage = (Stage) suppplanningbutton.getScene().getWindow();
                    currentStage.close();

                    // Rafraîchir la fenêtre de la liste des plannings
                    refreshPlanningList();
                } catch (Exception e) {
                    showAlert("Erreur lors de la suppression", e.getMessage());
                }
            }
        });
    }

    // Méthode pour supprimer le planning (exemple de logique)
    private void deletePlanning(Planning planning) {
        // Implémentez la logique pour supprimer le planning, par exemple :
        // planningService.deletePlanning(planning);
        System.out.println("Planning supprimé avec l'ID : " + planning.getId_planning());
    }

    // Méthode utilitaire pour afficher les alertes
    private void showAlert(String headerText, String contentText) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Erreur");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    // Méthode pour rafraîchir la liste des plannings dans l'interface
    private void refreshPlanningList() {
        try {
            // Charger la page de la liste des plannings
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/list_planning.fxml"));
            Parent listRoot = loader.load();

            // Obtenir le contrôleur de la liste des plannings
            List_Planning listController = loader.getController();
            listController.refreshPlanningList(); // Rafraîchir la liste des plannings

            // Créer une nouvelle scène pour afficher la liste mise à jour
            Stage stage = new Stage();
            stage.setScene(new Scene(listRoot));
            stage.setTitle("Liste des Plannings");
            stage.show();
        } catch (Exception e) {
            showAlert("Erreur lors du chargement de la liste des plannings", e.getMessage());
        }
    }
}


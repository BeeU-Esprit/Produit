/*package edu.pidev3a32.controllers;

import edu.pidev3a32.entities.Planning;
import edu.pidev3a32.entities.Cours;
import edu.pidev3a32.services.PlanningService;
import edu.pidev3a32.services.CoursService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Ajouterr_P {

    @FXML
    private Button ajouter_planning;

    @FXML
    private RadioButton confirmeButton;

    @FXML
    private ComboBox<Cours> courstextfield;

    @FXML
    private DatePicker date_PlanningTextfield;

    @FXML
    private RadioButton enattentebutton;
    @FXML
    private Label labelcours;

    @FXML
    private Label labelparticipants;

    @FXML
    private TextField typeactivitetextfield;

    private PlanningService planningService;

    @FXML
    public void initialize() {
        planningService = new PlanningService();
        loadCourses();
    }

    private void loadCourses() {
        CoursService coursService = new CoursService();
        List<Cours> courses = coursService.getAllCourses(); // Assurez-vous que cette méthode existe
        courstextfield.getItems().addAll(courses);
    }

    @FXML
    void addplanning(ActionEvent event) {
        // Récupérer les valeurs des champs
        String typeActivite = typeactivitetextfield.getText().trim();
        Date datePlanning;

        // Vérifier si une date a été sélectionnée
        if (date_PlanningTextfield.getValue() == null) {
            showAlert("Veuillez sélectionner une date.");
            return;
        }

        // Convertir la date sélectionnée
        datePlanning = Date.valueOf(date_PlanningTextfield.getValue());
        Cours cours = courstextfield.getValue();
        String status = confirmeButton.isSelected() ? "Confirmé" : "En Attente";

        // Validation des entrées
        if (typeActivite.isEmpty()) {
            showAlert("Le champ 'Type d'Activité' est obligatoire.");
            return;
        }
        if (cours == null) {
            showAlert("Veuillez sélectionner un cours.");
            return;
        }
        if (datePlanning.before(Date.valueOf(java.time.LocalDate.now()))) {
            showAlert("La date de planning ne peut pas être dans le passé.");
            return;
        }

        // Créer l'objet Planning
        Planning planning = new Planning(typeActivite, datePlanning, status, cours.getId_Cours());

        // Ajouter le planning via le service
        try {
            planningService.addplanning(planning);
            showAlert("Planning ajouté avec succès !");

            // Réinitialiser les champs après l'ajout
            clearFields();

            // Charger la nouvelle scène
            switchToPlanningListScene();

            // Fermer l'interface d'ajout
            closeCurrentStage(event);
        } catch (SQLException e) {
            showAlert("Erreur lors de l'ajout du planning : " + e.getMessage());
        }
    }

    private void switchToPlanningListScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/list_planning.fxml")); // Chemin vers votre FXML
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir le stage actuel
            Stage stage = (Stage) ajouter_planning.getScene().getWindow();

            // Changer la scène
            stage.setScene(scene);
            stage.setTitle("Liste des Plannings");
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur lors du chargement de la liste des plannings : " + e.getMessage());
        }
    }

    private void closeCurrentStage(ActionEvent event) {
        // Fermer la fenêtre actuelle
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    private void clearFields() {
        typeactivitetextfield.clear();
        date_PlanningTextfield.setValue(null);
        courstextfield.setValue(null);
        confirmeButton.setSelected(false);
        enattentebutton.setSelected(false);
    }
}*/
package edu.pidev3a32.controllers;

import edu.pidev3a32.entities.Planning;
import edu.pidev3a32.entities.Cours;
import edu.pidev3a32.services.PlanningService;
import edu.pidev3a32.services.CoursService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent; // Import pour les événements de souris

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Ajouterr_P {

    @FXML
    private Button ajouter_planning;

    @FXML
    private RadioButton confirmeButton;

    @FXML
    private ComboBox<Cours> courstextfield;

    @FXML
    private DatePicker date_PlanningTextfield;

    @FXML
    private RadioButton enattentebutton;

    @FXML
    private Label labelcours;

    @FXML
    private Label labelparticipants;

    @FXML
    private TextField typeactivitetextfield;

    private PlanningService planningService;

    @FXML
    public void initialize() {
        planningService = new PlanningService();
        loadCourses();
    }

    private void loadCourses() {
        CoursService coursService = new CoursService();
        List<Cours> courses = coursService.getAllCourses(); // Assurez-vous que cette méthode existe
        courstextfield.getItems().addAll(courses);
    }

    @FXML
    void addplanning(ActionEvent event) {
        // Récupérer les valeurs des champs
        String typeActivite = typeactivitetextfield.getText().trim();
        Date datePlanning;

        // Vérifier si une date a été sélectionnée
        if (date_PlanningTextfield.getValue() == null) {
            showAlert("Veuillez sélectionner une date.");
            return;
        }

        // Convertir la date sélectionnée
        datePlanning = Date.valueOf(date_PlanningTextfield.getValue());
        Cours cours = courstextfield.getValue();
        String status = confirmeButton.isSelected() ? "Confirmé" : "En Attente";

        // Validation des entrées
        if (typeActivite.isEmpty()) {
            showAlert("Le champ 'Type d'Activité' est obligatoire.");
            return;
        }
        if (cours == null) {
            showAlert("Veuillez sélectionner un cours.");
            return;
        }
        if (datePlanning.before(Date.valueOf(java.time.LocalDate.now()))) {
            showAlert("La date de planning ne peut pas être dans le passé.");
            return;
        }

        // Créer l'objet Planning
        Planning planning = new Planning(typeActivite, datePlanning, status, cours.getId_Cours());

        // Ajouter le planning via le service
        try {
            planningService.addplanning(planning);
            showAlert("Planning ajouté avec succès !");

            // Réinitialiser les champs après l'ajout
            clearFields();

            // Charger la nouvelle scène
            switchToPlanningListScene();

            // Fermer l'interface d'ajout
            closeCurrentStage(event);
        } catch (SQLException e) {
            showAlert("Erreur lors de l'ajout du planning : " + e.getMessage());
        }
    }

    private void switchToPlanningListScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/list_plannning.fxml")); // Chemin vers votre FXML
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir le stage actuel
            Stage stage = (Stage) ajouter_planning.getScene().getWindow();

            // Changer la scène
            stage.setScene(scene);
            stage.setTitle("Liste des Plannings");
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur lors du chargement de la liste des plannings : " + e.getMessage());
        }
    }

    private void closeCurrentStage(ActionEvent event) {
        // Fermer la fenêtre actuelle
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    private void clearFields() {
        typeactivitetextfield.clear();
        date_PlanningTextfield.setValue(null);
        courstextfield.setValue(null);
        confirmeButton.setSelected(false);
        enattentebutton.setSelected(false);
    }

    // Méthode pour gérer le clic sur le label des cours
    @FXML
    private void handleCoursLabelClick(MouseEvent event) {
        switchToScene("/Ajouter_Cours.fxml", event);  // Naviguer vers la page Ajouter Cours
    }

    // Méthode pour gérer le clic sur le label des participants
    @FXML
    private void handleParticipantsLabelClick(MouseEvent event) {
        switchToScene("/Ajouter_Participant.fxml", event);  // Naviguer vers la page Ajouter Participant
    }

    // Méthode générique pour changer de scène
    private void switchToScene(String fxmlFilePath, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur lors du changement de scène : " + e.getMessage());
        }
    }
}

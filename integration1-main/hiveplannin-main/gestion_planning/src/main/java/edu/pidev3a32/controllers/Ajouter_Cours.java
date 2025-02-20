
/*package edu.pidev3a32.controllers;

import edu.pidev3a32.entities.Cours;
import edu.pidev3a32.services.CoursService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Ajouter_Cours {

    @FXML
    private Button buttonajoutercours;

    @FXML
    private TextField dureecourstextfield;

    @FXML
    private TextField etatcourstextfield;

    @FXML
    private TextField nomcourstextfield;
    @FXML
    private Label labelcours;
    @FXML
    private Label labelparticipant;  // Participant label

    @FXML
    private Label labelplanning;  // Planning label

    // Method to handle the "Ajouter Cours" button action
    @FXML
    void addcours(ActionEvent event) {
        if (!isInputValid()) {
            return;
        }

        String nomCours = nomcourstextfield.getText().trim();
        Integer dureeCours = validateDuree();
        if (dureeCours == null) return;  // If validation fails, exit

        String etatCours = etatcourstextfield.getText().trim();
        if (!isValidEtatCours(etatCours)) {
            showAlert("L'état du cours doit être 'Ouvert' ou 'Fermé'.");
            return;
        }

        Cours cours = new Cours();
        cours.setNom_Cours(nomCours);
        cours.setDuree(dureeCours);
        cours.setEtat_Cours(etatCours);

        CoursService coursService = new CoursService();
        try {
            coursService.addcours(cours);
            showAlert("Cours ajouté avec succès !");

            // Switch to the "List_Cours" interface and close the current one
            switchToCoursListScene(event);
            closeCurrentWindow(event);  // Close the current window

        } catch (Exception e) {
            showAlert("Erreur lors de l'ajout du cours : " + e.getMessage());
        }
    }

    private void switchToCoursListScene(ActionEvent event) {
        try {
            // Load the "List_Cours" scene (replace with your actual FXML file path)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours.fxml"));
            Parent root = loader.load();

            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show(); // Display the new scene
        } catch (IOException e) {
            showAlert("Erreur lors de la navigation vers la liste des cours : " + e.getMessage());
        }
    }

    private void closeCurrentWindow(javafx.event.Event event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close(); // Close the current window
    }

    @FXML
    private void handleLabelPlanningClick(MouseEvent event) {
        switchToAjouterPlanningScene(); // Navigate to the ajouter planning scene
    }

    // New method for labelparticipant click event
    @FXML
    private void handleLabelParticipantClick(MouseEvent event) {
        switchToAjouterParticipantScene();  // Navigate to the Ajouter Participant scene
        closeCurrentWindow(event);  // Close the current window (Ajouter Cours)
    }

    private void switchToAjouterParticipantScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Ajouter_Participant.fxml"));  // Adjust path as needed
            Parent root = loader.load();
            Stage stage = (Stage) labelparticipant.getScene().getWindow();  // Get current window
            stage.setScene(new Scene(root));  // Switch scene
            stage.show();  // Display new scene
        } catch (Exception e) {
            showAlert("Erreur lors du changement de scène vers Ajouter Participant : " + e.getMessage());
        }
    }

    private boolean isInputValid() {
        if (nomcourstextfield.getText().trim().isEmpty() ||
                dureecourstextfield.getText().trim().isEmpty() ||
                etatcourstextfield.getText().trim().isEmpty()) {
            showAlert("Veuillez remplir tous les champs obligatoires.");
            return false;
        }

        String nomCours = nomcourstextfield.getText().trim();
        if (!isValidNomCours(nomCours)) {
            showAlert("Le nom du cours ne doit pas contenir de chiffres ou de caractères spéciaux.");
            return false;
        }

        return true;
    }

    private boolean isValidNomCours(String nom) {
        return nom.matches("^[a-zA-Z\\s]+$"); // Only allows letters and spaces
    }

    private Integer validateDuree() {
        try {
            Integer dureeCours = Integer.valueOf(dureecourstextfield.getText().trim());
            if (dureeCours <= 0) {
                showAlert("La durée doit être un nombre entier positif.");
                return null;
            }
            return dureeCours;
        } catch (NumberFormatException e) {
            showAlert("La durée doit être un nombre entier.");
            return null;
        }
    }

    private boolean isValidEtatCours(String etat) {
        return etat.equalsIgnoreCase("Ouvert") || etat.equalsIgnoreCase("Fermé");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    private void switchToAjouterPlanningScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Ajouter_Planning.fxml"));  // Adjust path as needed
            Parent root = loader.load();
            Stage stage = (Stage) labelplanning.getScene().getWindow();  // Get current window
            stage.setScene(new Scene(root));  // Switch scene
            stage.show();  // Display new scene
        } catch (Exception e) {
            showAlert("Erreur lors du changement de scène vers Ajouter Planning : " + e.getMessage());
        }
    }
}*/

/*package edu.pidev3a32.controllers;

import edu.pidev3a32.entities.Cours;
import edu.pidev3a32.services.CoursService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Ajouter_Cours {

    @FXML
    private Button buttonajoutercours;

    @FXML
    private TextField dureecourstextfield;

    @FXML
    private TextField etatcourstextfield;

    @FXML
    private TextField nomcourstextfield;
    @FXML
    private Label deatillabel;
    @FXML
    private Label labelcours;

    @FXML
    private Label labelparticipant;  // Participant label

    @FXML
    private Label labelplanning;  // Planning label

    // Method to handle the "Ajouter Cours" button action
    @FXML
    void addcours(ActionEvent event) {
        if (!isInputValid()) {
            return;
        }

        String nomCours = nomcourstextfield.getText().trim();
        Integer dureeCours = validateDuree();
        if (dureeCours == null) return;  // If validation fails, exit

        String etatCours = etatcourstextfield.getText().trim();
        if (!isValidEtatCours(etatCours)) {
            showAlert("L'état du cours doit être 'Ouvert' ou 'Fermé'.");
            return;
        }

        Cours cours = new Cours();
        cours.setNom_Cours(nomCours);
        cours.setDuree(dureeCours);
        cours.setEtat_Cours(etatCours);

        CoursService coursService = new CoursService();
        try {
            coursService.addcours(cours);
            showAlert("Cours ajouté avec succès !");

            // Switch to the "List_Cours" interface and close the current one
            switchToCoursListScene(event);
            closeCurrentWindow(event);  // Close the current window

        } catch (Exception e) {
            showAlert("Erreur lors de l'ajout du cours : " + e.getMessage());
        }
    }

    // Method to switch to "List_Cours" scene
    private void switchToCoursListScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur lors de la navigation vers la liste des cours : " + e.getMessage());
        }
    }

    // Close the current window
    private void closeCurrentWindow(javafx.event.Event event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();  // Close the current window
    }

    // Handle the label click to navigate to planning
    @FXML
    private void handleLabelPlanningClick(MouseEvent event) {
        switchToScene("/Ajouter_Planning.fxml", event); // Navigate to Ajouter Planning
    }

    // Handle the label click to navigate to participant
    @FXML
    private void handleLabelParticipantClick(MouseEvent event) {
        switchToScene("/Ajouter_Participant.fxml", event);  // Navigate to Ajouter Participant
    }

    // Handle the label click to navigate to courses
    @FXML
    private void handleLabelCoursClick(MouseEvent event) {
        switchToScene("/cours.fxml", event);  // Navigate to Ajouter Cours
    }
    @FXML
    private void handleLabeldetailClick(MouseEvent event) {
        switchToScene("/Ajouter_Cours_Participant.fxml", event);  // Navigate to Ajouter Cours
    }
    // Common method to switch to the desired scene
    private void switchToScene(String fxmlFilePath, javafx.event.Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // Get the current window
            stage.setScene(new Scene(root));  // Switch scene
            stage.show();  // Display new scene
        } catch (IOException e) {
            showAlert("Erreur lors du changement de scène : " + e.getMessage());
        }
    }

    // Validate input fields
    private boolean isInputValid() {
        if (nomcourstextfield.getText().trim().isEmpty() ||
                dureecourstextfield.getText().trim().isEmpty() ||
                etatcourstextfield.getText().trim().isEmpty()) {
            showAlert("Veuillez remplir tous les champs obligatoires.");
            return false;
        }

        String nomCours = nomcourstextfield.getText().trim();
        if (!isValidNomCours(nomCours)) {
            showAlert("Le nom du cours ne doit pas contenir de chiffres ou de caractères spéciaux.");
            return false;
        }

        return true;
    }

    // Validate course name (only letters and spaces allowed)
    private boolean isValidNomCours(String nom) {
        return nom.matches("^[a-zA-Z\\s]+$");
    }

    // Validate course duration
    private Integer validateDuree() {
        try {
            Integer dureeCours = Integer.valueOf(dureecourstextfield.getText().trim());
            if (dureeCours <= 0) {
                showAlert("La durée doit être un nombre entier positif.");
                return null;
            }
            return dureeCours;
        } catch (NumberFormatException e) {
            showAlert("La durée doit être un nombre entier.");
            return null;
        }
    }

    // Validate course status
    private boolean isValidEtatCours(String etat) {
        return etat.equalsIgnoreCase("Ouvert") || etat.equalsIgnoreCase("Fermé");
    }

    // Show an alert dialog
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }
}*/


package edu.pidev3a32.controllers;

import edu.pidev3a32.entities.Cours;
import edu.pidev3a32.services.CoursService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Ajouter_Cours {

    @FXML
    private Button buttonajoutercours;

    @FXML
    private TextField dureecourstextfield;

    @FXML
    private TextField etatcourstextfield;

    @FXML
    private TextField nomcourstextfield;

    @FXML
    private Label deatillabel;

    @FXML
    private Label labelcours;

    @FXML
    private Label labelparticipant;

    @FXML
    private Label labelplanning;

    // Méthode pour ajouter un cours
    @FXML
    void addcours(ActionEvent event) {
        if (!isInputValid()) {
            return;
        }

        String nomCours = nomcourstextfield.getText().trim();
        Integer dureeCours = validateDuree();
        if (dureeCours == null) return;

        String etatCours = etatcourstextfield.getText().trim();
        if (!isValidEtatCours(etatCours)) {
            showAlert("L'état du cours doit être 'Ouvert' ou 'Fermé'.");
            return;
        }

        Cours cours = new Cours();
        cours.setNom_Cours(nomCours);
        cours.setDuree(dureeCours);
        cours.setEtat_Cours(etatCours);

        CoursService coursService = new CoursService();
        try {
            coursService.addcours(cours);
            showAlert("Cours ajouté avec succès !");

            // Passer à la scène "List_Cours" et fermer la scène actuelle
            switchToCoursListScene(event);
            closeCurrentWindow(event);

        } catch (Exception e) {
            showAlert("Erreur lors de l'ajout du cours : " + e.getMessage());
        }
    }

    // Méthode pour changer de scène (List_Cours)
    private void switchToCoursListScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // Affiche l'erreur dans la console pour le débogage
            showAlert("Erreur lors de la navigation vers la liste des cours : " + e.getMessage());
        }
    }

    // Fermer la scène actuelle
    private void closeCurrentWindow(javafx.event.Event event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    // Gestion du clic sur le label "Planning"
    @FXML
    private void handleLabelPlanningClick(MouseEvent event) {
        switchToScene("/Ajouter_Planning.fxml", event);
    }

    // Gestion du clic sur le label "Participant"
    @FXML
    private void handleLabelParticipantClick(MouseEvent event) {
        switchToScene("/Ajouter_Participant.fxml", event);
    }

    // Gestion du clic sur le label "Cours"
    @FXML
    private void handleLabelCoursClick(MouseEvent event) {
        switchToScene("/cours.fxml", event);
    }

    // Gestion du clic sur le label "Detail"
    @FXML
    private void handleLabeldetailClick(MouseEvent event) {
        switchToScene("/Ajouter_Cours_Participant.fxml", event);
    }

    // Méthode commune pour changer de scène
    private void switchToScene(String fxmlFilePath, javafx.event.Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // Affiche l'erreur dans la console pour le débogage
            showAlert("Erreur lors du changement de scène : " + e.getMessage());
        }
    }

    // Validation des champs de saisie
    private boolean isInputValid() {
        if (nomcourstextfield.getText().trim().isEmpty() ||
                dureecourstextfield.getText().trim().isEmpty() ||
                etatcourstextfield.getText().trim().isEmpty()) {
            showAlert("Veuillez remplir tous les champs obligatoires.");
            return false;
        }

        String nomCours = nomcourstextfield.getText().trim();
        if (!isValidNomCours(nomCours)) {
            showAlert("Le nom du cours ne doit pas contenir de chiffres ou de caractères spéciaux.");
            return false;
        }

        return true;
    }

    // Validation du nom du cours
    private boolean isValidNomCours(String nom) {
        return nom.matches("^[a-zA-Z\\s]+$");
    }

    // Validation de la durée du cours
    private Integer validateDuree() {
        try {
            Integer dureeCours = Integer.valueOf(dureecourstextfield.getText().trim());
            if (dureeCours <= 0) {
                showAlert("La durée doit être un nombre entier positif.");
                return null;
            }
            return dureeCours;
        } catch (NumberFormatException e) {
            showAlert("La durée doit être un nombre entier.");
            return null;
        }
    }

    // Validation de l'état du cours
    private boolean isValidEtatCours(String etat) {
        return etat.equalsIgnoreCase("Ouvert") || etat.equalsIgnoreCase("Fermé");
    }

    // Affichage d'un message d'alerte
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }
}











package edu.pidev3a32.controllers;


import edu.pidev3a32.entities.Commande;
import edu.pidev3a32.entities.CommandeProduits;
import edu.pidev3a32.entities.produit;
import edu.pidev3a32.services.CommandeProduitsService;
import edu.pidev3a32.services.CommandeService;
import edu.pidev3a32.services.ProduitService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



import javafx.event.ActionEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AjoutCommande {

    @FXML
    private ListView<String> produitListView;
    @FXML private ListView<String> commandeList;
    @FXML private TextField commandeUtilisateur;
    @FXML private DatePicker commandeDate;
    @FXML private ComboBox<String> commandeStatus;

    private final ProduitService produitService = new ProduitService();
    private final CommandeService commandeService = new CommandeService();


    @FXML
    private void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 500));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
     //   loadProduits();
        loadCommandes();
        commandeStatus.getItems().addAll("Pending", "Validated", "Canceled");

        // Add listener to populate fields when selecting a commande
        commandeList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateCommandeFields(newSelection);
            }
        });
    }

    private void populateCommandeFields(String selectedCommande) {
        try {
            // Extracting the ID from the string "Commande #12 - Validated"
            int idCommande = Integer.parseInt(selectedCommande.split("#")[1].split(" - ")[0].trim());

            // Fetch the full commande details
            Commande commande = commandeService.getCommandeById(idCommande);
            if (commande != null) {
                commandeUtilisateur.setText(String.valueOf(commande.getIdUtilisateur())); // Set user ID
                commandeDate.setValue(commande.getDateDeCommande()); // Set date
                commandeStatus.setValue(commande.getStatus()); // Set status
            }
        } catch (Exception e) {
            showAlert("Erreur", "Impossible de charger les détails de la commande.");
        }
    }

/*

    private void loadProduits() {
        produitListView.getItems().clear();
        List<Produit> produits = produitService.getAllProduits();
        for (Produit produit : produits) {
            produitListView.getItems().add(produit.getNomProduit() + " (ID: " + produit.getIdProduit() + ")");
        }
    }
*/
    private void loadCommandes() {
        commandeList.getItems().clear();
        List<Commande> commandes = commandeService.getAllCommandes();
        for (Commande commande : commandes) {
            commandeList.getItems().add("Commande #" + commande.getIdCommande() + " - " + commande.getStatus());
        }
    }

    @FXML
    private void createCommande() {
        if (commandeUtilisateur.getText().isEmpty() || commandeDate.getValue() == null || commandeStatus.getValue() == null) {
            showAlert("Erreur", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        try {
            LocalDate dateDeCommande = commandeDate.getValue();
            int idUtilisateur = Integer.parseInt(commandeUtilisateur.getText().trim());
            String status = commandeStatus.getValue();

            // Create Commande
            Commande commande = new Commande(dateDeCommande, idUtilisateur, status);
            int idCommande = commandeService.ajouterCommande(commande);

            if (idCommande != -1) {
                System.out.println("Commande added successfully with ID: " + idCommande);

                // Add Products to CommandeProduits table
                CommandeProduitsService commandeProduitsService = new CommandeProduitsService();

                for (String selectedItem : produitListView.getSelectionModel().getSelectedItems()) {
                    int productId = Integer.parseInt(selectedItem.split(" ")[1].replaceAll("[^0-9]", ""));
                    int produit = produitService.getProduitById(productId);

                    if (produit != 0) {
                        CommandeProduits commandeProduits = new CommandeProduits(idCommande, productId, 1, produit);
                        commandeProduitsService.ajouterCommandeProduit(commandeProduits);
                        System.out.println("Product " + productId + " added to CommandeProduits");
                    } else {
                        System.out.println("Produit ID " + productId + " not found.");
                    }
                }
            } else {
                showAlert("Erreur", "Échec de l'ajout de la commande.");
            }
        } catch (NumberFormatException e) {
            showAlert("Erreur", "L'ID utilisateur doit être un nombre valide.");
            e.printStackTrace();
        }
    }



    @FXML
    private void refreshCommandes() {
        System.out.println("🔄 Rafraîchissement des commandes...");
        loadCommandes(); // Reloads the list of commandes
        showAlert("Info", "Liste des commandes rafraîchie avec succès !");
    }




    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}

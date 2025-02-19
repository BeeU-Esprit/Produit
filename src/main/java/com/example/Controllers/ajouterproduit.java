package com.example.Controllers;

import com.example.entities.Produit;
import com.example.service.CategorieService;
import com.example.service.ProduitService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ajouterproduit {
    @FXML
    private TextField Categorie;
    @FXML
    private ComboBox<String> FournisseurComboBox;
    @FXML
    private TextField NomProduit;
    @FXML
    private TextField Prix;
    @FXML
    private TextField Stock;
    @FXML
    private Button btnsauvgarde;
    @FXML
    private DatePicker Date;

    private final CategorieService categorieService = new CategorieService();

    private static final String API_KEY = "AIzaSyBC1q3nAf8GUMkZy-fYTRh9B7vhkyBADQE";
    private static final String SEARCH_ENGINE_ID = "1256d1da04f58449a";

    @FXML
    public void initialize() {
        FournisseurComboBox.setDisable(true);
        Categorie.textProperty().addListener((observable, oldValue, newValue) -> remplirFournisseur(newValue));
    }

    @FXML
    void ajouterProduit(ActionEvent event) {
        ProduitService produitService = new ProduitService();

        try {
            if (NomProduit.getText().isEmpty() || Categorie.getText().isEmpty() ||
                    Prix.getText().isEmpty() || Stock.getText().isEmpty() ||
                    Date.getValue() == null || FournisseurComboBox.getSelectionModel().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Champs vides", "Veuillez remplir tous les champs !");
                return;
            }

            if (!isDateValid(Date.getValue())) {
                showAlert(Alert.AlertType.ERROR, "Erreur de validation",
                        "La date sélectionnée doit être entre le 01/01/2023 et aujourd'hui.");
                return;
            }

            int categorieId = Integer.parseInt(Categorie.getText());
            float prix = Float.parseFloat(Prix.getText());
            int stock = Integer.parseInt(Stock.getText());
            String dateProduit = Date.getValue().toString();
            String fournisseurChoisi = FournisseurComboBox.getSelectionModel().getSelectedItem();

            Produit produit = new Produit(NomProduit.getText(), categorieId, prix, stock, dateProduit, fournisseurChoisi);
            produitService.ajouterProduit(produit);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Le produit a été ajouté avec succès !");
            resetFields();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez entrer des valeurs numériques valides pour Catégorie, Prix et Stock !");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", e.getMessage());
        }
    }

    @FXML
    public void sauvgarderproduit(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/page1.fxml"));
            Parent parent = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Gestion des Produits");
            stage.setScene(new Scene(parent));
            stage.show();

            Stage currentStage = (Stage) btnsauvgarde.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isDateValid(LocalDate date) {
        return date != null && !date.isBefore(LocalDate.of(2023, 1, 1)) && !date.isAfter(LocalDate.now());
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    private void resetFields() {
        NomProduit.clear();
        Categorie.clear();
        Prix.clear();
        Stock.clear();
        Date.setValue(null);
        FournisseurComboBox.getItems().clear();
        FournisseurComboBox.setDisable(true);
    }

    private void remplirFournisseur(String categorieText) {
        if (categorieText == null || categorieText.isEmpty()) {
            Platform.runLater(() -> {
                FournisseurComboBox.getItems().clear();
                FournisseurComboBox.setPromptText("Sélectionnez un fournisseur");
                FournisseurComboBox.setDisable(true);
            });
            return;
        }

        try {
            int categorieId = Integer.parseInt(categorieText);
            String categorieNom = categorieService.getNomCategorieById(categorieId);

            System.out.println("🔎 Recherche pour la catégorie : " + categorieNom);

            if (categorieNom == null || categorieNom.trim().isEmpty()) {
                Platform.runLater(() -> {
                    FournisseurComboBox.getItems().clear();
                    FournisseurComboBox.setPromptText("⚠️ Catégorie inconnue !");
                    FournisseurComboBox.setDisable(true);
                });
                return;
            }

            new Thread(() -> {
                List<String> fournisseurs = rechercherFournisseursGoogle(categorieNom);

                Platform.runLater(() -> {
                    FournisseurComboBox.getItems().clear();
                    if (!fournisseurs.isEmpty()) {
                        FournisseurComboBox.getItems().addAll(fournisseurs);
                        FournisseurComboBox.setDisable(false);
                        FournisseurComboBox.setPromptText("Sélectionnez un fournisseur");
                    } else {
                        FournisseurComboBox.getItems().add("Fournisseur généraliste en Tunisie");
                        FournisseurComboBox.setPromptText("⚠️ Aucun fournisseur spécifique trouvé !");
                        FournisseurComboBox.setDisable(false);
                    }
                });

            }).start();

        } catch (NumberFormatException e) {
            Platform.runLater(() -> {
                FournisseurComboBox.getItems().clear();
                FournisseurComboBox.setPromptText("⚠️ Entrez un ID de catégorie valide !");
                FournisseurComboBox.setDisable(true);
            });
        }
    }

    private List<String> rechercherFournisseursGoogle(String categorie) {
        List<String> fournisseurs = new ArrayList<>();

        try {
            String encodedQuery = URLEncoder.encode("Fournisseur spécialisé en " + categorie + " Tunisie", StandardCharsets.UTF_8);
            String urlString = "https://www.googleapis.com/customsearch/v1?q=" + encodedQuery +
                    "&key=" + API_KEY + "&cx=" + SEARCH_ENGINE_ID;

            System.out.println("🔍 URL de requête : " + urlString);

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = conn.getResponseCode();
            System.out.println("🔍 Code réponse HTTP : " + responseCode);

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject json = new JSONObject(response.toString());
                JSONArray items = json.optJSONArray("items");

                if (items != null) {
                    for (int i = 0; i < Math.min(items.length(), 5); i++) {
                        JSONObject item = items.getJSONObject(i);
                        String title = item.optString("title", "Fournisseur inconnu");
                        fournisseurs.add(title);
                    }
                    System.out.println("✅ Fournisseurs trouvés : " + fournisseurs);
                } else {
                    System.out.println("⚠️ Aucun fournisseur trouvé.");
                }

            } else {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
                StringBuilder errorResponse = new StringBuilder();
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    errorResponse.append(errorLine);
                }
                errorReader.close();

                System.err.println("🛑 Erreur API : " + errorResponse.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fournisseurs;
    }
}
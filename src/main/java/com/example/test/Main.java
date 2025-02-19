package com.example.test;

import com.example.entities.Produit;
import com.example.entities.categorieproduit; // Correction du nom de l'entité
import com.example.service.ProduitService;
import com.example.service.ProduitCategorie; // Correction du nom du service

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Création d'une instance de CategorieProduitService
        ProduitCategorie categorieService = new ProduitCategorie();

        try {
            // 1. Lire une image et la convertir en tableau de bytes
            byte[] imageBytes = Files.readAllBytes(Paths.get("c:/2.png"));

            // 2. Ajouter une nouvelle catégorie
           // categorieproduit nouvelleCategorie = new categorieproduit(0, "musculation", imageBytes);
//categorieService.ajouterCategorieProduit(nouvelleCategorie);
            System.out.println("Catégorie ajoutée avec succès.");

            // 3. Afficher toutes les catégories
            System.out.println("\nListe des catégories :");
            List<categorieproduit> categories = categorieService.afficherCategorieProduit();
            for (categorieproduit c : categories) {
                System.out.println("ID: " + c.getId() + ", Nom: " + c.getNomcategorie());
            }

        } catch (Exception e) {
            System.err.println("Une erreur s'est produite : " + e.getMessage());
        }

        ProduitService produitService = new ProduitService();

        // Créer un nouveau produit
        Produit nouveauProduit = new Produit(
                "   Alter",   // Nom du produit
                4,                  // ID de la catégorie (doit exister dans CategorieProduit)
                251,           // Prix
                10,                 // Stock disponible
                "2025-12-31",       // Date (par exemple, date d'expiration ou ajout)
                "pxeke"              // Fournisseur
        );

        // Ajouter le produit
       // produitService.ajouterProduit(nouveauProduit);
    }
}
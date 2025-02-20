package edu.pidev3a32.services;

import edu.pidev3a32.tools.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CategorieService {

    private Connection connection;

    public CategorieService() {
        this.connection = MyConnection.getInstance().getCnx();
    }

    // Récupérer toutes les catégories avec leur ID
    public Map<Integer, String> getCategories() {
        Map<Integer, String> categories = new HashMap<>();
        String sql = "SELECT id, nomcategorie FROM categorieproduit";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nomcategorie");
                categories.put(id, nom);
            }

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération des catégories !");
            e.printStackTrace();
        }

        return categories;
    }
    public String getNomCategorieById(int id) {
        String sql = "SELECT nomcategorie FROM categorieproduit WHERE id = ?";
        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("nomcategorie"); // Retourne le nom de la catégorie
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération du nom de la catégorie !");
            e.printStackTrace();
        }
        return null; // Retourne null si aucune catégorie n'est trouvée
    }

    // Vérifier si une catégorie existe
    public boolean categorieExiste(int id) {
        return getCategories().containsKey(id);
    }
}


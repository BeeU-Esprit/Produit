package edu.pidev3a32.services;

import edu.pidev3a32.entities.CommandeProduits;
import edu.pidev3a32.tools.MyConnection;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeProduitsService {
    private final Connection connection;

    public CommandeProduitsService() {
        this.connection = MyConnection.getInstance().getCnx();
    }

    public void ajouterCommandeProduit(CommandeProduits commandeProduits) {
        String query = "INSERT INTO commande_produits (idCommande, idProduit, quantite, prixUnitaire) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, commandeProduits.getIdCommande());
            pstmt.setInt(2, commandeProduits.getIdProduit());
            pstmt.setInt(3, commandeProduits.getQuantite());
            pstmt.setDouble(4, commandeProduits.getPrixUnitaire());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CommandeProduits> getProduitsByCommande(int idCommande) {
        List<CommandeProduits> produitsList = new ArrayList<>();
        String query = "SELECT * FROM commande_produits WHERE idCommande = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, idCommande);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                produitsList.add(new CommandeProduits(
                        rs.getInt("idCommande"),
                        rs.getInt("idProduit"),
                        rs.getInt("quantite"),
                        rs.getDouble("prixUnitaire")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produitsList;
    }
}

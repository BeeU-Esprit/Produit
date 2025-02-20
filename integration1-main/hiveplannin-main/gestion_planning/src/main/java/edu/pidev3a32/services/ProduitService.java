package edu.pidev3a32.services;
import edu.pidev3a32.entities.produit;
import edu.pidev3a32.interfaces.Iproduit;
import edu.pidev3a32.tools.MyConnection;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ProduitService implements Iproduit {
    private final Connection connection;

    public ProduitService() {
        this.connection = MyConnection.getInstance().getCnx();
    }






    @Override
    public void ajouterProduit(produit p) throws SQLException {
        String query = "INSERT INTO produit (Nom_Produit, Categorie, Prix, Stock_Dispo, Date, Fournisseur) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, p.getNom_Produit());
            statement.setInt(2, p.getCategorie());
            statement.setFloat(3, p.getPrix());
            statement.setInt(4, p.getStock_disponible());
            statement.setString(5, p.getDate());
            statement.setString(6, p.getFournisseur());

            statement.executeUpdate();
            System.out.println("Produit ajouté !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerProduit(produit p) {
        String query = "DELETE FROM produit WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, p.getId());  // Utilisation de getId() au lieu de p.id
            statement.executeUpdate();
            System.out.println("Produit supprimée !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifierProduit(produit p) {
        String query = "UPDATE produit SET Nom_Produit = ?, Categorie = ?, Prix = ?, Stock_Dispo = ?, Date = ?, Fournisseur = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, p.getNom_Produit());
            statement.setInt(2, p.getCategorie());
            statement.setFloat(3, p.getPrix());
            statement.setInt(4, p.getStock_disponible());
            statement.setString(5, p.getDate());
            statement.setString(6, p.getFournisseur());
            statement.setInt(7, p.getId());

            statement.executeUpdate();
            System.out.println("Produit modifié !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<produit> afficherProduit() {  // Retourne une liste de Produit
        List<produit> produits = new ArrayList<>();
        String query = "SELECT * FROM produit";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                produit p = new produit();
                p.setId(resultSet.getInt("id"));
                p.setNom_Produit(resultSet.getString("Nom_Produit"));
                p.setCategorie(resultSet.getInt("Categorie"));
                p.setPrix(resultSet.getFloat("Prix"));
                p.setStock_disponible(resultSet.getInt("Stock_Dispo"));
                p.setDate(resultSet.getString("Date"));
                p.setFournisseur(resultSet.getString("Fournisseur"));
                produits.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }






    public int getProduitById(int productId) {
        String query = "SELECT id FROM produit WHERE id = ?";
        int id = -1; // Valeur par défaut si aucun produit n'est trouvé

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}


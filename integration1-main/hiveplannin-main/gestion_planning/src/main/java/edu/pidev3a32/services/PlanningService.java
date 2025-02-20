package edu.pidev3a32.services;

import edu.pidev3a32.entities.Planning;
import edu.pidev3a32.interfaces.Planning_Service;
import edu.pidev3a32.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanningService implements Planning_Service<Planning> {

    @Override
    public void addplanning(Planning planning) throws SQLException {
        String requete = "INSERT INTO planning (type_activite, date_planning, status, cours) VALUES (?, ?, ?, ?)";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            pst.setString(1, planning.getType_activite());
            pst.setDate(2, planning.getDate_planning());
            pst.setString(3, planning.getStatus());
            pst.setInt(4, planning.getCours());

            int rowsAffected = pst.executeUpdate();
            System.out.println("Planning ajouté : " + rowsAffected + " ligne(s) affectée(s)");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du planning : " + e.getMessage());
            throw e; // Relancer l'exception pour la gestion en amont
        }
    }

    /*@Override
    public void updateplanning(Planning planning) throws SQLException {
        String requete = "UPDATE planning SET type_activite = ?, date_planning = ?, status = ?, cours = ? WHERE id_planning = ?";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            pst.setString(1, planning.getType_activite());
            pst.setDate(2, planning.getDate_planning());
            pst.setString(3, planning.getStatus());
            pst.setInt(4, planning.getCours());
            pst.setInt(5, planning.getId_planning()); // Set the ID for updating

            int rowsAffected = pst.executeUpdate();
            System.out.println("Planning modifié : " + rowsAffected + " ligne(s) affectée(s)");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour du planning : " + ex.getMessage());
            throw ex; // Relancer l'exception pour la gestion en amont
        }
    }*/

    @Override
    public void deleteplanning(Planning planning) throws SQLException {
        String requete = "DELETE FROM planning WHERE id_planning = ?";

        // Vérifiez que l'objet planning n'est pas null et que l'ID est valide
        if (planning == null || planning.getId_planning() <= 0) {
            System.err.println("Planning invalide ou ID non valide.");
            throw new IllegalArgumentException("Planning invalide ou ID non valide.");
        }

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            pst.setInt(1, planning.getId_planning());
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Planning supprimé : " + rowsAffected + " ligne(s) affectée(s)");
            } else {
                System.out.println("Aucun planning trouvé avec l'ID spécifié.");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du planning : " + ex.getMessage());
            throw ex; // Relancer l'exception pour la gestion en amont
        }
    }




    @Override
    public List<Planning> getAllDataplannig() throws SQLException {
        List<Planning> results = new ArrayList<>();
        String requete = "SELECT * FROM planning";

        try (Connection conn = MyConnection.getInstance().getCnx();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(requete)) {

            while (rs.next()) {
                Planning planning = new Planning(
                        rs.getInt("id_planning"),
                        rs.getString("type_activite"),
                        rs.getDate("date_planning"),
                        rs.getString("status"),
                        rs.getInt("cours")
                );
                results.add(planning);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des plannings : " + e.getMessage());
            throw e; // Relancer l'exception pour la gestion en amont
        }
        return results;
    }

    // Méthode pour obtenir tous les plannings
    public List<Planning> getAllPlannings() throws SQLException {
        return getAllDataplannig(); // Cette méthode peut être utilisée directement
    }
    public void updateplanning(Planning planning) throws SQLException {
        // Requête SQL de mise à jour
        String requete = "UPDATE planning SET type_activite = ?, date_planning = ?, status = ?, cours = ? WHERE id_planning = ?";

        // Essayer de se connecter et d'exécuter la requête
        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            // Remplissage des paramètres dans la requête préparée
            pst.setString(1, planning.getType_activite());
            pst.setDate(2, planning.getDate_planning());
            pst.setString(3, planning.getStatus());
            pst.setInt(4, planning.getCours());
            pst.setInt(5, planning.getId_planning()); // ID pour la mise à jour

            // Exécution de la mise à jour
            int rowsAffected = pst.executeUpdate();

            // Vérification du nombre de lignes affectées
            if (rowsAffected > 0) {
                System.out.println("Planning modifié avec succès : " + rowsAffected + " ligne(s) affectée(s).");
            } else {
                System.out.println("Aucune ligne affectée, vérifiez l'ID du planning.");
            }

        } catch (SQLException ex) {
            // Gestion des erreurs
            System.err.println("Erreur lors de la mise à jour du planning : " + ex.getMessage());
            throw ex; // Relancer l'exception pour la gestion en amont
        }
    }

}
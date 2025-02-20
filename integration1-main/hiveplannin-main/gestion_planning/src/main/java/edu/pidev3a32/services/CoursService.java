/*package edu.pidev3a32.services;

import edu.pidev3a32.entities.Cours;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import edu.pidev3a32.interfaces.Cours_Service;
import edu.pidev3a32.tools.MyConnection;

public class CoursService implements Cours_Service<Cours> {

    @Override
    public void addcours(Cours cours) throws SQLException {
        String requete = "INSERT INTO Cours (Nom_Cours, duree, Etat_Cours) VALUES (?, ?, ?)";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pstmt = conn.prepareStatement(requete)) {

            // Set parameters
            pstmt.setString(1, cours.getNom_Cours());
            pstmt.setInt(2, cours.getDuree());
            pstmt.setString(3, cours.getEtat_Cours());

            // Execute the query
            pstmt.executeUpdate();
            System.out.println("Cours ajouté");
        } catch (SQLException e) {
            System.err.println("Error adding course: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void updatecour(Cours cours) {
        String requete = "UPDATE Cours SET Nom_Cours = ?, duree = ?, Etat_Cours = ? WHERE id_Cours = ?";
        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            pst.setString(1, cours.getNom_Cours());
            pst.setInt(2, cours.getDuree());
            pst.setString(3, cours.getEtat_Cours());
            pst.setInt(4, cours.getId_Cours());

            pst.executeUpdate();
            System.out.println("Cours modifié !");
        } catch (SQLException ex) {
            System.err.println("Error updating course: " + ex.getMessage());
        }
    }

    @Override
    public void deletecour(Cours cours) {
        String requete = "DELETE FROM Cours WHERE id_Cours = ?";
        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            pst.setInt(1, cours.getId_Cours());
            pst.executeUpdate();
            System.out.println("Cours supprimé !");
        } catch (SQLException ex) {
            System.err.println("Error deleting course: " + ex.getMessage());
        }
    }

    @Override
    public List<Cours> getAllDatacour() {
        List<Cours> results = new ArrayList<>();
        String requete = "SELECT * FROM Cours";

        try (Connection conn = MyConnection.getInstance().getCnx();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(requete)) {

            while (rs.next()) {
                Cours p = new Cours();
                p.setId_Cours(rs.getInt("id_Cours"));
                p.setNom_Cours(rs.getString("Nom_Cours"));
                p.setDuree(rs.getInt("duree"));
                p.setEtat_Cours(rs.getString("Etat_Cours"));

                results.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving courses: " + e.getMessage());
        }
        return results;
    }

    // New method to get all courses
    public List<Cours> getAllCourses() {
        return getAllDatacour(); // This method can be used directly
    }
}*/
package edu.pidev3a32.services;

/*import edu.pidev3a32.entities.Cours;
import edu.pidev3a32.interfaces.Cours_Service;
import edu.pidev3a32.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursService implements Cours_Service<Cours> {

    // Ajouter un cours
    @Override
    public void addcours(Cours cours) throws SQLException {
        String requete = "INSERT INTO Cours (Nom_Cours, duree, Etat_Cours) VALUES (?, ?, ?)";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pstmt = conn.prepareStatement(requete)) {

            pstmt.setString(1, cours.getNom_Cours());
            pstmt.setInt(2, cours.getDuree());
            pstmt.setString(3, cours.getEtat_Cours());

            pstmt.executeUpdate();
            System.out.println("Cours ajouté");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du cours: " + e.getMessage());
            throw e;  // Relancer l'exception
        }
    }

    // Mettre à jour un cours
    @Override
    public void updatecour(Cours cours) {
        String requete = "UPDATE Cours SET Nom_Cours = ?, duree = ?, Etat_Cours = ? WHERE id_Cours = ?";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            pst.setString(1, cours.getNom_Cours());
            pst.setInt(2, cours.getDuree());
            pst.setString(3, cours.getEtat_Cours());
            pst.setInt(4, cours.getId_Cours());

            pst.executeUpdate();
            System.out.println("Cours modifié !");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour du cours: " + ex.getMessage());
        }
    }

    // Supprimer un cours
    @Override
    public void deletecour(Cours cours) {
        String requete = "DELETE FROM Cours WHERE id_Cours = ?";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            pst.setInt(1, cours.getId_Cours());
            pst.executeUpdate();
            System.out.println("Cours supprimé !");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du cours: " + ex.getMessage());
        }
    }

    // Récupérer tous les cours
    @Override
    public List<Cours> getAllDatacour() {
        List<Cours> results = new ArrayList<>();
        String requete = "SELECT * FROM Cours";

        try (Connection conn = MyConnection.getInstance().getCnx();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(requete)) {

            while (rs.next()) {
                Cours p = new Cours();
                p.setId_Cours(rs.getInt("id_Cours"));
                p.setNom_Cours(rs.getString("Nom_Cours"));
                p.setDuree(rs.getInt("duree"));
                p.setEtat_Cours(rs.getString("Etat_Cours"));

                results.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des cours: " + e.getMessage());
        }
        return results;
    }

    // Récupérer un cours par son nom
    public Cours getCoursByName(String courseName) throws SQLException {
        String requete = "SELECT * FROM Cours WHERE Nom_Cours = ?";
        Cours cours = null;

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            pst.setString(1, courseName);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                cours = new Cours();
                cours.setId_Cours(rs.getInt("id_Cours"));
                cours.setNom_Cours(rs.getString("Nom_Cours"));
                cours.setDuree(rs.getInt("duree"));
                cours.setEtat_Cours(rs.getString("Etat_Cours"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du cours par nom: " + e.getMessage());
            throw e;  // Relancer l'exception
        }

        return cours;
    }

    // Cette méthode permet de récupérer tous les cours comme une simple méthode de raccourci
    public List<Cours> getAllCourses() {
        return getAllDatacour();
    }
}*/


import edu.pidev3a32.entities.Cours;
import edu.pidev3a32.entities.Participant;
import edu.pidev3a32.interfaces.Cours_Service;
import edu.pidev3a32.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursService implements Cours_Service<Cours> {

    // Ajouter un cours
    @Override
    public void addcours(Cours cours) throws SQLException {
        String requete = "INSERT INTO Cours (Nom_Cours, duree, Etat_Cours) VALUES (?, ?, ?)";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pstmt = conn.prepareStatement(requete)) {

            pstmt.setString(1, cours.getNom_Cours());
            pstmt.setInt(2, cours.getDuree());
            pstmt.setString(3, cours.getEtat_Cours());

            pstmt.executeUpdate();
            System.out.println("Cours ajouté");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du cours: " + e.getMessage());
            throw e;  // Relancer l'exception
        }
    }

    // Mettre à jour un cours
    @Override
    public void updatecour(Cours cours) {
        String requete = "UPDATE Cours SET Nom_Cours = ?, duree = ?, Etat_Cours = ? WHERE id_Cours = ?";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            pst.setString(1, cours.getNom_Cours());
            pst.setInt(2, cours.getDuree());
            pst.setString(3, cours.getEtat_Cours());
            pst.setInt(4, cours.getId_Cours());

            pst.executeUpdate();
            System.out.println("Cours modifié !");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour du cours: " + ex.getMessage());
        }
    }

    // Supprimer un cours
    @Override
    public void deletecour(Cours cours) {
        String requete = "DELETE FROM Cours WHERE id_Cours = ?";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            pst.setInt(1, cours.getId_Cours());
            pst.executeUpdate();
            System.out.println("Cours supprimé !");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du cours: " + ex.getMessage());
        }
    }

    // Récupérer tous les cours
    @Override
    public List<Cours> getAllDatacour() {
        List<Cours> results = new ArrayList<>();
        String requete = "SELECT * FROM Cours";

        try (Connection conn = MyConnection.getInstance().getCnx();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(requete)) {

            while (rs.next()) {
                Cours p = new Cours();
                p.setId_Cours(rs.getInt("id_Cours"));
                p.setNom_Cours(rs.getString("Nom_Cours"));
                p.setDuree(rs.getInt("duree"));
                p.setEtat_Cours(rs.getString("Etat_Cours"));

                results.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des cours: " + e.getMessage());
        }
        return results;
    }

    // Récupérer un cours par son nom
    public Cours getCoursByName(String courseName) throws SQLException {
        String requete = "SELECT * FROM Cours WHERE Nom_Cours = ?";
        Cours cours = null;

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            pst.setString(1, courseName);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                cours = new Cours();
                cours.setId_Cours(rs.getInt("id_Cours"));
                cours.setNom_Cours(rs.getString("Nom_Cours"));
                cours.setDuree(rs.getInt("duree"));
                cours.setEtat_Cours(rs.getString("Etat_Cours"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du cours par nom: " + e.getMessage());
            throw e;  // Relancer l'exception
        }

        return cours;
    }

    // Récupérer les participants inscrits à un cours donné
    public List<Participant> getParticipantsForCours(int id_cours) throws SQLException {
        List<Participant> participants = new ArrayList<>();
        String requete = "SELECT p.* FROM Participant p " +
                "INNER JOIN Cours_Participant cp ON p.id= cp.id " +
                "WHERE cp.id_cours = ?";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            pst.setInt(1, id_cours);  // Lier l'ID du cours à la requête

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Participant participant = new Participant();
                participant.setId(rs.getInt("id_Participant"));
                participant.setNom(rs.getString("Nom"));
                participant.setPrenom(rs.getString("Prenom"));
                // Ajouter d'autres propriétés du participant ici si nécessaire

                participants.add(participant);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des participants : " + e.getMessage());
            throw e;
        }

        return participants;
    }

    // Cette méthode permet de récupérer tous les cours comme une simple méthode de raccourci
    public List<Cours> getAllCourses() {
        return getAllDatacour();
    }
}


/*package edu.pidev3a32.services;


import edu.pidev3a32.entities.Participant;
import edu.pidev3a32.interfaces.Participant_Service;
import edu.pidev3a32.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantService implements Participant_Service<Participant> {

    // Ajouter un participant
    @Override
    public void addparticipant(Participant participant) throws SQLException {
        String query = "INSERT INTO Participant (Nom, Prenom, Age, Adresse, Num_Telephone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Définir les paramètres
            pstmt.setString(1, participant.getNom());
            pstmt.setString(2, participant.getPrenom());
            pstmt.setInt(3, participant.getAge());
            pstmt.setString(4, participant.getAdresse());
            pstmt.setString(5, participant.getTel()); // Assurez-vous que tel est une String

            // Exécuter la requête
            pstmt.executeUpdate();
            System.out.println("Participant ajouté !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du participant : " + e.getMessage());
            throw e; // Propager l'exception pour une gestion ultérieure
        }
    }

    // Mettre à jour un participant
    @Override
    public void updateparticipant(Participant participant) throws SQLException {
        String query = "UPDATE Participant SET Nom = ?, Prenom = ?, Age = ?, Adresse = ?, Num_Telephone = ? WHERE id = ?";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(query)) {

            // Définir les paramètres
            pst.setString(1, participant.getNom());
            pst.setString(2, participant.getPrenom());
            pst.setInt(3, participant.getAge());
            pst.setString(4, participant.getAdresse());
            pst.setString(5, participant.getTel()); // Assurez-vous que tel est une String
            pst.setInt(6, participant.getId()); // Utiliser getId() pour obtenir l'ID du participant

            // Exécuter la mise à jour
            pst.executeUpdate();
            System.out.println("Participant modifié !");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour du participant : " + ex.getMessage());
            throw ex; // Propager l'exception pour une gestion ultérieure
        }
    }

    // Supprimer un participant
    @Override
    public void deleteparticipant(Participant participant) throws SQLException {
        String query = "DELETE FROM Participant WHERE id= ?";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, participant.getId()); // Utiliser l'ID pour identifier le participant à supprimer
            pst.executeUpdate();
            System.out.println("Participant supprimé !");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du participant : " + ex.getMessage());
            throw ex; // Propager l'exception pour une gestion ultérieure
        }
    }

    // Récupérer tous les participants
    public List<Participant> getAllDataparticipant() throws SQLException { // Renommer pour une meilleure clarté
        List<Participant> participants = new ArrayList<>();
        String query = "SELECT * FROM Participant";

        try (Connection conn = MyConnection.getInstance().getCnx();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                // Créer un Participant à partir des données récupérées
                Participant participant = new Participant(
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getInt("Age"),
                        rs.getString("Adresse"),
                        rs.getString("Num_Telephone")
                );
                participant.setId(rs.getInt("id")); // Assigner l'ID du participant

                participants.add(participant); // Ajouter le participant à la liste
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des participants : " + e.getMessage());
            throw e; // Propager l'exception pour une gestion ultérieure
        }
        return participants; // Retourner la liste des participants
    }

    // Récupérer les participants d'un cours spécifique
    public List<Participant> getParticipantsForCours(int coursId) throws SQLException {
        List<Participant> participants = new ArrayList<>();

        // Obtenir la connexion via MyConnection
        Connection connection = MyConnection.getInstance().getCnx();

        // Requête SQL pour obtenir les participants par le cours
        String sql = "SELECT p.id, p.prenom, p.nom, p.age, p.adresse, p.Num_Telephone " +
                "FROM participant p " +
                "INNER JOIN cours_participant cp ON p.id = cp.id " + // Correction du join
                "WHERE cp.id_cours = ?";

        System.out.println("Exécution de la requête SQL pour le cours ID : " + coursId);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Définir le paramètre de la requête SQL
            statement.setInt(1, coursId);

            // Exécuter la requête
            ResultSet resultSet = statement.executeQuery();

            // Vérifier si des résultats sont récupérés
            if (!resultSet.isBeforeFirst()) { // Si aucun résultat n'est trouvé
                System.out.println("Aucun participant trouvé pour ce cours.");
            }

            // Parcourir les résultats et les ajouter à la liste des participants
            while (resultSet.next()) {
                // Extraire les informations nécessaires
                int id = resultSet.getInt("id");
                String prenom = resultSet.getString("prenom");
                String nom = resultSet.getString("nom");
                int age = resultSet.getInt("age");
                String adresse = resultSet.getString("adresse");
                String numTelephone = resultSet.getString("Num_Telephone"); // Utiliser 'Num_Telephone' ici

                // Créer un objet Participant avec toutes les informations
                Participant participant = new Participant(nom, prenom, age, adresse, numTelephone);
                participant.setId(id); // Assigner l'ID du participant

                participants.add(participant);  // Ajouter le participant à la liste
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors de la récupération des participants pour le cours.", e);
        }

        // Afficher le nombre de participants récupérés
        System.out.println("Nombre de participants trouvés : " + participants.size());

        return participants;
    }
    public void deleteParticipantsFromCourse(int courseId) throws SQLException {
        String requete = "DELETE FROM Participant WHERE id_Cours = ?";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            // Lier l'id du cours à la requête
            pst.setInt(1, courseId);

            // Exécuter la requête pour supprimer les participants
            pst.executeUpdate();
            System.out.println("Tous les participants ont été supprimés pour le cours avec id: " + courseId);
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression des participants : " + ex.getMessage());
            throw ex; // Relever l'exception si vous voulez la gérer plus loin
        }
    }
}*/
/*package edu.pidev3a32.services;

import edu.pidev3a32.entities.Participant;
import edu.pidev3a32.interfaces.Participant_Service;
import edu.pidev3a32.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantService implements Participant_Service<Participant> {

    // Ajouter un participant
    @Override
    public void addparticipant(Participant participant) throws SQLException {
        String query = "INSERT INTO Participant (Nom, Prenom, Age, Adresse, Num_Telephone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Définir les paramètres
            pstmt.setString(1, participant.getNom());
            pstmt.setString(2, participant.getPrenom());
            pstmt.setInt(3, participant.getAge());
            pstmt.setString(4, participant.getAdresse());
            pstmt.setString(5, participant.getTel()); // Assurez-vous que tel est une String

            // Exécuter la requête
            pstmt.executeUpdate();
            System.out.println("Participant ajouté !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du participant : " + e.getMessage());
            throw e; // Propager l'exception pour une gestion ultérieure
        }
    }

    // Mettre à jour un participant
    @Override
    public void updateparticipant(Participant participant) throws SQLException {
        String query = "UPDATE Participant SET Nom = ?, Prenom = ?, Age = ?, Adresse = ?, Num_Telephone = ? WHERE id = ?";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(query)) {

            // Définir les paramètres
            pst.setString(1, participant.getNom());
            pst.setString(2, participant.getPrenom());
            pst.setInt(3, participant.getAge());
            pst.setString(4, participant.getAdresse());
            pst.setString(5, participant.getTel()); // Assurez-vous que tel est une String
            pst.setInt(6, participant.getId()); // Utiliser getId() pour obtenir l'ID du participant

            // Exécuter la mise à jour
            pst.executeUpdate();
            System.out.println("Participant modifié !");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour du participant : " + ex.getMessage());
            throw ex; // Propager l'exception pour une gestion ultérieure
        }
    }

    // Supprimer un participant
    @Override
    public void deleteparticipant(Participant participant) throws SQLException {
        String query = "DELETE FROM Participant WHERE id = ?";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, participant.getId()); // Utiliser l'ID pour identifier le participant à supprimer
            pst.executeUpdate();
            System.out.println("Participant supprimé !");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du participant : " + ex.getMessage());
            throw ex; // Propager l'exception pour une gestion ultérieure
        }
    }

    // Récupérer tous les participants
    public List<Participant> getAllDataparticipant() throws SQLException {
        List<Participant> participants = new ArrayList<>();
        String query = "SELECT * FROM Participant";

        try (Connection conn = MyConnection.getInstance().getCnx();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                // Créer un Participant à partir des données récupérées
                Participant participant = new Participant(
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getInt("Age"),
                        rs.getString("Adresse"),
                        rs.getString("Num_Telephone")
                );
                participant.setId(rs.getInt("id")); // Assigner l'ID du participant

                participants.add(participant); // Ajouter le participant à la liste
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des participants : " + e.getMessage());
            throw e; // Propager l'exception pour une gestion ultérieure
        }
        return participants; // Retourner la liste des participants
    }

    // Récupérer les participants d'un cours spécifique
    public List<Participant> getParticipantsForCours(int coursId) throws SQLException {
        List<Participant> participants = new ArrayList<>();

        // Obtenir la connexion via MyConnection
        Connection connection = MyConnection.getInstance().getCnx();

        // Requête SQL pour obtenir les participants par le cours
        String sql = "SELECT p.id, p.prenom, p.nom, p.age, p.adresse, p.Num_Telephone " +
                "FROM participant p " +
                "INNER JOIN cours_participant cp ON p.id = cp.id_participant " + // Correction de la jointure
                "WHERE cp.id_cours = ?";

        System.out.println("Exécution de la requête SQL pour le cours ID : " + coursId);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Définir le paramètre de la requête SQL
            statement.setInt(1, coursId);

            // Exécuter la requête
            ResultSet resultSet = statement.executeQuery();

            // Parcourir les résultats et les ajouter à la liste des participants
            while (resultSet.next()) {
                // Extraire les informations nécessaires
                int id = resultSet.getInt("id");
                String prenom = resultSet.getString("prenom");
                String nom = resultSet.getString("nom");
                int age = resultSet.getInt("age");
                String adresse = resultSet.getString("adresse");
                String numTelephone = resultSet.getString("Num_Telephone");

                // Créer un objet Participant avec toutes les informations
                Participant participant = new Participant(nom, prenom, age, adresse, numTelephone);
                participant.setId(id); // Assigner l'ID du participant

                participants.add(participant);  // Ajouter le participant à la liste
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des participants pour le cours : " + e.getMessage());
            throw new SQLException("Erreur lors de la récupération des participants pour le cours.", e);
        }

        // Afficher le nombre de participants récupérés
        System.out.println("Nombre de participants trouvés : " + participants.size());

        return participants;
    }

    // Supprimer tous les participants d'un cours spécifique
    public void deleteParticipantsFromCourse(int courseId) throws SQLException {
        String requete = "DELETE FROM cours_participant WHERE id_cours = ?"; // Correction de la requête

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            // Lier l'id du cours à la requête
            pst.setInt(1, courseId);

            // Exécuter la requête pour supprimer les participants
            pst.executeUpdate();
            System.out.println("Tous les participants ont été supprimés pour le cours avec id: " + courseId);
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression des participants : " + ex.getMessage());
            throw ex; // Relever l'exception si vous voulez la gérer plus loin
        }
    }
}*/
/*package edu.pidev3a32.services;

import edu.pidev3a32.entities.Participant;
import edu.pidev3a32.interfaces.Participant_Service;
import edu.pidev3a32.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantService implements Participant_Service<Participant> {

    // Ajouter un participant
    @Override
    public void addparticipant(Participant participant) throws SQLException {
        String query = "INSERT INTO Participant (Nom, Prenom, Age, Adresse, Num_Telephone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Définir les paramètres
            pstmt.setString(1, participant.getNom());
            pstmt.setString(2, participant.getPrenom());
            pstmt.setInt(3, participant.getAge());
            pstmt.setString(4, participant.getAdresse());
            pstmt.setString(5, participant.getTel()); // Assurez-vous que tel est une String

            // Exécuter la requête
            pstmt.executeUpdate();
            System.out.println("Participant ajouté !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du participant : " + e.getMessage());
            throw e; // Propager l'exception pour une gestion ultérieure
        }
    }

    // Mettre à jour un participant
    @Override
    public void updateparticipant(Participant participant) throws SQLException {
        String query = "UPDATE Participant SET Nom = ?, Prenom = ?, Age = ?, Adresse = ?, Num_Telephone = ? WHERE id = ?";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(query)) {

            // Définir les paramètres
            pst.setString(1, participant.getNom());
            pst.setString(2, participant.getPrenom());
            pst.setInt(3, participant.getAge());
            pst.setString(4, participant.getAdresse());
            pst.setString(5, participant.getTel()); // Assurez-vous que tel est une String
            pst.setInt(6, participant.getId()); // Utiliser getId() pour obtenir l'ID du participant

            // Exécuter la mise à jour
            pst.executeUpdate();
            System.out.println("Participant modifié !");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour du participant : " + ex.getMessage());
            throw ex; // Propager l'exception pour une gestion ultérieure
        }
    }

    // Supprimer un participant
    @Override
    public void deleteparticipant(Participant participant) throws SQLException {
        String query = "DELETE FROM Participant WHERE id = ?";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, participant.getId()); // Utiliser l'ID pour identifier le participant à supprimer
            pst.executeUpdate();
            System.out.println("Participant supprimé !");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du participant : " + ex.getMessage());
            throw ex; // Propager l'exception pour une gestion ultérieure
        }
    }

    // Récupérer tous les participants
    public List<Participant> getAllDataparticipant() throws SQLException {
        List<Participant> participants = new ArrayList<>();
        String query = "SELECT * FROM Participant";

        try (Connection conn = MyConnection.getInstance().getCnx();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                // Créer un Participant à partir des données récupérées
                Participant participant = new Participant(
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getInt("Age"),
                        rs.getString("Adresse"),
                        rs.getString("Num_Telephone")
                );
                participant.setId(rs.getInt("id")); // Assigner l'ID du participant

                participants.add(participant); // Ajouter le participant à la liste
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des participants : " + e.getMessage());
            throw e; // Propager l'exception pour une gestion ultérieure
        }
        return participants; // Retourner la liste des participants
    }

    // Récupérer les participants d'un cours spécifique
    public List<Participant> getParticipantsForCours(int coursId) throws SQLException {
        List<Participant> participants = new ArrayList<>();

        String sql = "SELECT p.id, p.prenom, p.nom, p.age, p.adresse, p.Num_Telephone " +
                "FROM Participant p " +
                "INNER JOIN Cours_Participant cp ON p.id = cp.id_participant " +
                "WHERE cp.id_cours = ?";

        try (Connection connection = MyConnection.getInstance().getCnx();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Définir le paramètre de la requête SQL
            statement.setInt(1, coursId);

            // Exécuter la requête
            ResultSet resultSet = statement.executeQuery();

            // Parcourir les résultats et les ajouter à la liste des participants
            while (resultSet.next()) {
                // Extraire les informations nécessaires
                int id = resultSet.getInt("id");
                String prenom = resultSet.getString("prenom");
                String nom = resultSet.getString("nom");
                int age = resultSet.getInt("age");
                String adresse = resultSet.getString("adresse");
                String numTelephone = resultSet.getString("Num_Telephone");

                // Créer un objet Participant avec toutes les informations
                Participant participant = new Participant(nom, prenom, age, adresse, numTelephone);
                participant.setId(id); // Assigner l'ID du participant

                participants.add(participant);  // Ajouter le participant à la liste
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des participants pour le cours : " + e.getMessage());
            throw new SQLException("Erreur lors de la récupération des participants pour le cours.", e);
        }

        System.out.println("Nombre de participants trouvés : " + participants.size());
        return participants;
    }

    // Supprimer tous les participants d'un cours spécifique
    public void deleteParticipantsFromCourse(int courseId) throws SQLException {
        String requete = "DELETE FROM Cours_Participant WHERE id_cours = ?"; // Correction de la requête

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            // Lier l'id du cours à la requête
            pst.setInt(1, courseId);

            // Exécuter la requête pour supprimer les participants
            pst.executeUpdate();
            System.out.println("Tous les participants ont été supprimés pour le cours avec id: " + courseId);
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression des participants : " + ex.getMessage());
            throw ex; // Relever l'exception si vous voulez la gérer plus loin
        }
    }

    // Supprimer un participant d'un cours spécifique
    public void deleteParticipantFromCourse(int courseId, int participantId) throws SQLException {
        String requete = "DELETE FROM Cours_Participant WHERE id_cours = ? AND id_participant = ?";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            pst.setInt(1, courseId);
            pst.setInt(2, participantId);
            pst.executeUpdate();
            System.out.println("Participant avec ID " + participantId + " supprimé du cours avec ID " + courseId);
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du participant du cours : " + ex.getMessage());
            throw ex; // Relever l'exception si vous voulez la gérer plus loin
        }
    }
}*/
package edu.pidev3a32.services;

import edu.pidev3a32.entities.Participant;
import edu.pidev3a32.interfaces.Participant_Service;
import edu.pidev3a32.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantService implements Participant_Service<Participant> {

    // Ajouter un participant
    @Override
    public void addparticipant(Participant participant) throws SQLException {
        String query = "INSERT INTO Participant (Nom, Prenom, Age, Adresse, Num_Telephone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Définir les paramètres
            pstmt.setString(1, participant.getNom());
            pstmt.setString(2, participant.getPrenom());
            pstmt.setInt(3, participant.getAge());
            pstmt.setString(4, participant.getAdresse());
            pstmt.setString(5, participant.getTel()); // Assurez-vous que tel est une String

            // Exécuter la requête
            pstmt.executeUpdate();
            System.out.println("Participant ajouté !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du participant : " + e.getMessage());
            throw e; // Propager l'exception pour une gestion ultérieure
        }
    }

    // Mettre à jour un participant
    @Override
    public void updateparticipant(Participant participant) throws SQLException {
        String query = "UPDATE Participant SET Nom = ?, Prenom = ?, Age = ?, Adresse = ?, Num_Telephone = ? WHERE id = ?";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(query)) {

            // Définir les paramètres
            pst.setString(1, participant.getNom());
            pst.setString(2, participant.getPrenom());
            pst.setInt(3, participant.getAge());
            pst.setString(4, participant.getAdresse());
            pst.setString(5, participant.getTel()); // Assurez-vous que tel est une String
            pst.setInt(6, participant.getId()); // Utiliser getId() pour obtenir l'ID du participant

            // Exécuter la mise à jour
            pst.executeUpdate();
            System.out.println("Participant modifié !");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour du participant : " + ex.getMessage());
            throw ex; // Propager l'exception pour une gestion ultérieure
        }
    }

    // Supprimer un participant
    @Override
    public void deleteparticipant(Participant participant) throws SQLException {
        String query = "DELETE FROM Participant WHERE id = ?";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, participant.getId()); // Utiliser l'ID pour identifier le participant à supprimer
            pst.executeUpdate();
            System.out.println("Participant supprimé !");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du participant : " + ex.getMessage());
            throw ex; // Propager l'exception pour une gestion ultérieure
        }
    }

    // Récupérer tous les participants
    public List<Participant> getAllDataparticipant() throws SQLException {
        List<Participant> participants = new ArrayList<>();
        String query = "SELECT * FROM Participant";

        try (Connection conn = MyConnection.getInstance().getCnx();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                // Créer un Participant à partir des données récupérées
                Participant participant = new Participant(
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getInt("Age"),
                        rs.getString("Adresse"),
                        rs.getString("Num_Telephone")
                );
                participant.setId(rs.getInt("id")); // Assigner l'ID du participant

                participants.add(participant); // Ajouter le participant à la liste
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des participants : " + e.getMessage());
            throw e; // Propager l'exception pour une gestion ultérieure
        }
        return participants; // Retourner la liste des participants
    }

    // Récupérer les participants d'un cours spécifique
    public List<Participant> getParticipantsForCours(int coursId) throws SQLException {
        List<Participant> participants = new ArrayList<>();

        String sql = "SELECT p.id, p.prenom, p.nom, p.age, p.adresse, p.Num_Telephone " +
                "FROM Participant p " +
                "INNER JOIN Cours_Participant cp ON p.id = cp.id_participant " +
                "WHERE cp.id_cours = ?";

        try (Connection connection = MyConnection.getInstance().getCnx();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Définir le paramètre de la requête SQL
            statement.setInt(1, coursId);

            // Exécuter la requête
            ResultSet resultSet = statement.executeQuery();

            // Parcourir les résultats et les ajouter à la liste des participants
            while (resultSet.next()) {
                // Extraire les informations nécessaires
                int id = resultSet.getInt("id");
                String prenom = resultSet.getString("prenom");
                String nom = resultSet.getString("nom");
                int age = resultSet.getInt("age");
                String adresse = resultSet.getString("adresse");
                String numTelephone = resultSet.getString("Num_Telephone");

                // Créer un objet Participant avec toutes les informations
                Participant participant = new Participant(nom, prenom, age, adresse, numTelephone);
                participant.setId(id); // Assigner l'ID du participant

                participants.add(participant);  // Ajouter le participant à la liste
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des participants pour le cours : " + e.getMessage());
            throw new SQLException("Erreur lors de la récupération des participants pour le cours.", e);
        }

        System.out.println("Nombre de participants trouvés : " + participants.size());
        return participants;
    }

    // Supprimer tous les participants d'un cours spécifique
    public void deleteParticipantsFromCourse(int courseId) throws SQLException {
        String requete = "DELETE FROM Cours_Participant WHERE id_cours = ?"; // Corrigez la requête pour supprimer toutes les associations

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            // Lier l'id du cours à la requête
            pst.setInt(1, courseId);

            // Exécuter la requête pour supprimer les participants
            pst.executeUpdate();
            System.out.println("Tous les participants ont été supprimés pour le cours avec id: " + courseId);
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression des participants : " + ex.getMessage());
            throw ex; // Relever l'exception si vous voulez la gérer plus loin
        }
    }

    // Supprimer un participant d'un cours spécifique
    public void deleteParticipantFromCourse(int courseId, int participantId) throws SQLException {
        String requete = "DELETE FROM Cours_Participant WHERE id_cours = ? AND id_participant = ?";

        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement pst = conn.prepareStatement(requete)) {

            pst.setInt(1, courseId);
            pst.setInt(2, participantId);
            pst.executeUpdate();
            System.out.println("Participant avec ID " + participantId + " supprimé du cours avec ID " + courseId);
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du participant du cours : " + ex.getMessage());
            throw ex; // Relever l'exception si vous voulez la gérer plus loin
        }
    }
}
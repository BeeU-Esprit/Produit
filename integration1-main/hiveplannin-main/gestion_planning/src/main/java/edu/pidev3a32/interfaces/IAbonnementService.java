package edu.pidev3a32.interfaces;



import edu.pidev3a32.entities.Abonnement;

import java.sql.SQLException;
import java.util.List;

public interface IAbonnementService {
    void addAbonnement(Abonnement abonnement) throws SQLException;
    void deleteAbonnement(int idAbonnement) throws SQLException;
    void updateAbonnement(Abonnement abonnement) throws SQLException;
    List<Abonnement> getAllAbonnements() throws SQLException;
}
package edu.pidev3a32.interfaces;
import edu.pidev3a32.entities.produit;
import edu.pidev3a32.entities.produit;

import java.sql.SQLException;
import java.util.List;
public interface Iproduit {
    void ajouterProduit(produit p)throws SQLException;
    void supprimerProduit(produit p);
    void modifierProduit(produit p);

    public List<produit> afficherProduit();
}




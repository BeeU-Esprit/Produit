package com.example.interfaces;
import com.example.entities.Produit;

import java.sql.SQLException;
import java.util.List;
public interface IProduitservice {
    void ajouterProduit(Produit p)throws SQLException;
    void supprimerProduit(Produit p);
    void modifierProduit(Produit p);
    public List<Produit> afficherProduit();
}




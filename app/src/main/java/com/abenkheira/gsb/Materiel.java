package com.abenkheira.gsb;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abenkheira on 09/03/2018.
 */

class Materiel {
    private String numSerie;
    private String libelle;
    private String marque;
    private String categorie;

    public Materiel(String numSerie, String libelle, String marque, String categorie) {
        this.numSerie = numSerie;
        this.libelle = libelle;
        this.marque = marque;
        this.categorie = categorie;
    }

    public Materiel(JSONObject jsonObject){
        try {
            this.numSerie = jsonObject.getString("numeroSerie");
            this.libelle = jsonObject.getString("libMat");
            this.marque = jsonObject.getString("libMar");
            this.categorie = jsonObject.getString("libCat");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getNumSerie() {
        return numSerie;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getMarque() {
        return marque;
    }

    public String getCategorie() {
        return categorie;
    }

    @Override
    public String toString() {
        return "Numéros de série:       "+this.numSerie+"\nCatégorie:       "+this.categorie+"\nMarque:       "+this.marque+"\nLibéllé:       "+this.libelle;
    }
}

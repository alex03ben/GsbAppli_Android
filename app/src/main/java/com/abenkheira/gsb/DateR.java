package com.abenkheira.gsb;

/**
 * Created by abenkheira on 09/03/2018.
 */

public class DateR {
    private String annee;
    private String mois;
    private String jours;

    public DateR(String annee, String mois, String jours) {
        this.annee = annee;
        this.mois = mois;
        this.jours = jours;
    }

    public DateR(String date){
        String[] maDate = date.split("-");
        this.annee = maDate[0];
        this.mois = maDate[1];
        this.jours = maDate[2];
    }

    public String getAnnee() {
        return annee;
    }

    public String getMois() {
        return mois;
    }

    public String getJours() {
        return jours;
    }


    @Override
    public String toString() {
        return this.annee+"-"+this.mois+"-"+this.jours;
    }
}

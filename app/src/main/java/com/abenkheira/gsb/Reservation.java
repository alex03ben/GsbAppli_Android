package com.abenkheira.gsb;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abenkheira on 09/03/2018.
 */

public class Reservation {
    private int id;
    private DateR dateDebut;
    private DateR dateFin;
    private String libMat;

    public Reservation(int id, DateR dateDebut, DateR dateFin, String libMat) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.libMat = libMat;
    }

    public Reservation(JSONObject jsonObject){
        try {
            this.id = jsonObject.getInt("id");
            this.dateDebut = new DateR(jsonObject.getString("date_Debut"));
            this.dateFin = new DateR(jsonObject.getString("date_Fin"));
            this.libMat = jsonObject.getString("libelle");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public DateR getDateDebut() {
        return dateDebut;
    }

    public DateR getDateFin() {
        return dateFin;
    }

    public String getUnMateriel() {
        return libMat;
    }

    @Override
    public String toString() {
        return "Debut : "+this.dateDebut+"\nFin : "+this.dateFin+"\nMateriel : "+this.libMat;
    }
}

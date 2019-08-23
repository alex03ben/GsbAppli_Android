package com.abenkheira.gsb;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by abenkheira on 09/03/2018.
 */

public class User {
    private String id;
    private String nom;
    private String prenom;
    private String login;
    private String mdp;
    private String adresse;
    private int cp;
    private String ville;
    private String dateEmbauche;
    private ArrayList<Reservation> reservationActuel;
    private ArrayList<Reservation> reservationFutur;
    private ArrayList<Reservation> reservationPassé;

    public User(String id, String nom, String prenom, String login, String mdp, String adresse, int cp, String ville, String dateEmbauche, ArrayList<Reservation> reservationActuel, ArrayList<Reservation> reservationFutur, ArrayList<Reservation> reservationPassé) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.mdp = mdp;
        this.adresse = adresse;
        this.cp = cp;
        this.ville = ville;
        this.dateEmbauche = dateEmbauche;
        this.reservationActuel = reservationActuel;
        this.reservationFutur = reservationFutur;
        this.reservationPassé = reservationPassé;
    }

    public User(JSONObject json){
        try {
            this.id = json.getString("id");
            this.nom = json.getString("nom");
            this.prenom = json.getString("prenom");
            this.login = json.getString("login");
            this.mdp = json.getString("mdp");
            this.adresse = json.getString("adresse");
            this.cp = json.getInt("cp");
            this.ville = json.getString("ville");
            this.dateEmbauche = json.getString("dateEmbauche");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getMdp() {
        return mdp;
    }

    public ArrayList<Reservation> getReservationActuel() {
        return reservationActuel;
    }

    public ArrayList<Reservation> getReservationFutur() {
        return reservationFutur;
    }

    public ArrayList<Reservation> getReservationPassé() {
        return reservationPassé;
    }

    @Override
    public String toString() {
        return this.id+" "+this.login+" "+this.mdp;
    }
}

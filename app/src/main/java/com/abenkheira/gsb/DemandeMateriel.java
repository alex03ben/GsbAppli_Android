package com.abenkheira.gsb;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class DemandeMateriel extends AppCompatActivity {

    private GestionWebService gwb;
    private Spinner listeCategorie;
    private EditText editTextDescDemande;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande_materiel);
        listeCategorie = findViewById(R.id.spinnerDemandeCategorie);
        editTextDescDemande = findViewById(R.id.edit_textDemandeDesc);
        Button envoyerDemande = findViewById(R.id.buttonEnvoyerDemande);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //pour bloquer la rotation de l'ecran

        gwb = new GestionWebService(){ //Recupère les catégories avec le webservice pour le spinner
            @Override
            protected void onPostExecute(Object o) {
                chargeSpinner(o);
            }
        };
        gwb.execute("uc=getCategorie");


        envoyerDemande.setOnClickListener(new View.OnClickListener() { //Détecte l'event clique bouton pour l'envoie
            @Override
            public void onClick(View view) {
                envoieDemande();
            }
        });

    }

    private void envoieDemande() { //Procédure executé par le clique sur "Envoie"
        String chaineTemp = String.valueOf(editTextDescDemande.getText()).replace(" ", "*"); //Permet de remplacer les espaces par "*" pour envoyer au webservice
        gwb = new GestionWebService(){
            @Override
            protected void onPostExecute(Object o) {
                confirmationEnvoie();//Permet de savoir si l'envoie est réussi
            }
        };
        gwb.execute("uc=envoieDemande&desc="+chaineTemp+"&libCat="+listeCategorie.getSelectedItem().toString());
    }

    private void confirmationEnvoie() { //Renvoie sur le menu principale en avertissant l'utilisateurs de l'envoie de la demande
        Intent activityListeDispo = new Intent(this, MenuActivity.class);
        startActivity(activityListeDispo);
        Toast.makeText(this, "Demande de materiel envoyé !", Toast.LENGTH_LONG).show();
    }

    private void chargeSpinner(Object o) { //Charge mon spinner avec toutes les catégories
        ArrayList<String> lesCategories = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(o.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                String cat = jsonArray.getJSONObject(i).getString("libelle");
                lesCategories.add(cat);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listeCategorie.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lesCategories));
    }
}

package com.abenkheira.gsb;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class FiltreRechercheResActivity extends AppCompatActivity {
    private GestionWebService gwb;
    private DateR dateDebutFiltre;
    private DateR dateFinFiltre;
    private String valeurSpinner;
    private Spinner listeCategorie;
    private TextView dateDebut;
    private TextView dateFin;
    private int jourCur, moisCur, anneeCur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtre_recherche_res);
        Button valider = findViewById(R.id.buttonValideFiltre);
        dateDebut = findViewById(R.id.textViewChoixDateDebut);
        dateFin = findViewById(R.id.textViewChoixDateFin);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //pour bloquer la rotation de l'ecran

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {    //Lance la procédure qui va exécuter l'activity suivante en lui envoyant les données
                chercheReservation();
            }
        });

        dateDebut.setOnClickListener(new View.OnClickListener() { //Ouvre un calendrier lors d'un clique sur l'editText
            @Override
            public void onClick(View view) {
                ouvreCalendrier(dateDebut, "debut");
            }
        });

        dateFin.setOnClickListener(new View.OnClickListener() { //Ouvre un calendrier lors d'un clique sur l'editText
            @Override
            public void onClick(View view) {
                ouvreCalendrier(dateFin, "fin");
            }
        });

        gwb = new GestionWebService() {
            @Override
            protected void onPostExecute(Object o) {
                chargerSpinner(o);
            }
        };
        gwb.execute("uc=getCategorie");
    }

    private void ouvreCalendrier(final TextView champDate, final String choix) { //Procédure exécuté lors d'un clique sur dateDebut ou dateFin pour ouvrir un calendrier
        Calendar c = Calendar.getInstance();
        jourCur = c.get(Calendar.DAY_OF_MONTH); //place dans des variables la date d'aujourd'hui
        moisCur = c.get(Calendar.MONTH);
        anneeCur = c.get(Calendar.YEAR);

        DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, anneeCur); //place le calendrier à la date d'aujourd'hui
                c.set(Calendar.MONTH, moisCur);
                c.set(Calendar.DAY_OF_MONTH, jourCur);
                if(Objects.equals(choix, "debut")){
                    dateDebutFiltre = new DateR(String.valueOf(i), String.valueOf(i1+1), String.valueOf(i2));
                    champDate.setText(dateDebutFiltre.toString());
                }else {
                    dateFinFiltre = new DateR(String.valueOf(i), String.valueOf(i1+1), String.valueOf(i2));
                    champDate.setText(dateFinFiltre.toString());
                }
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, datePickerListener, anneeCur, moisCur, jourCur);
        datePickerDialog.getDatePicker();
        datePickerDialog.show();
    }

    private void chargerSpinner(Object o) { //Je charge mon spinner avec les valeurs récupéré dans ma base
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
        listeCategorie = findViewById(R.id.spinnerListeCategorie);
        listeCategorie.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lesCategories));
    }

    private void chercheReservation() { //Envoie sur la deuxième activity (Pour afficher la liste des reservations possible) si il n'y a pas d'erreur
        valeurSpinner = listeCategorie.getSelectedItem().toString();

        //condition pour vérifier si les champs son remplis
        if (Objects.equals(String.valueOf(dateDebut.getText()), "Choisir...") || Objects.equals(String.valueOf(dateFin.getText()), "Choisir...")) {
            Toast.makeText(this, "ERREUR : Veuillez remplir tout les champs s'il vous plait !", Toast.LENGTH_LONG).show();
        } else {
            Intent activityListeDispo = new Intent(this, AfficheListeReservation.class);
            activityListeDispo.putExtra("getDateDebutFiltre", dateDebutFiltre.toString());
            activityListeDispo.putExtra("getDateFinFiltre", dateFinFiltre.toString());
            activityListeDispo.putExtra("getValeurSpinner", valeurSpinner);
            startActivity(activityListeDispo);
        }
    }
}

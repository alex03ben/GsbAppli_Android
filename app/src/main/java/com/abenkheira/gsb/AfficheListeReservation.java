package com.abenkheira.gsb;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class AfficheListeReservation extends AppCompatActivity {
    private GestionWebService gwb;
    private ListView listView;
    private ArrayList<Materiel> lesMaterielDispo;
    private AlertDialog.Builder confirmLaReservation; //La boite de dialogue qui permet de confirmer avant de réserver
    private String valSpin;
    private String valDateDebut;
    private String valDateFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_liste_reservation);
        listView = findViewById(R.id.listViewMateriel);

        valSpin = getIntent().getStringExtra("getValeurSpinner");  //je récupère les variables de l'activity précédente
        valDateDebut = getIntent().getStringExtra("getDateDebutFiltre");
        valDateFin = getIntent().getStringExtra("getDateFinFiltre");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //pour bloquer la rotation de l'ecran

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //Permet de détecter les clique sur les éléments de la liste
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cliqueItemListe(adapterView, view, i, l);
            }
        });

        gwb = new GestionWebService() { //Récupère la liste des matériel disponible
            @Override
            protected void onPostExecute(Object o) {
                chargeListeMateriel(o);
            }
        };
        gwb.execute("uc=getMateriel&category=" + valSpin + "&dateFinRes=" + valDateFin + "&dateDebutRes=" + valDateDebut);
    }

    private void cliqueItemListe(AdapterView<?> adapterView, View view, final int idItem, long l) { //Procédure appelé lors d'un clique sur un élément de la liste
        final Materiel itemSelect = lesMaterielDispo.get(idItem);
        confirmLaReservation = new AlertDialog.Builder(this);
        confirmLaReservation.setMessage("Voulez vous réserver ce materiel ?\n\n" + itemSelect.toString())
                .setTitle("Confirmation")
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() { //Si fenêtre dialog validé
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gwb = new GestionWebService() {     //ajoute une réservation dans la base
                            @Override
                            protected void onPostExecute(Object o) {
                                refreshList(o, idItem);
                            }
                        };
                        gwb.execute("uc=setReservation&dateDebutRes=" + valDateDebut + "&dateFinRes=" + valDateFin + "&numSerie=" + itemSelect.getNumSerie());
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() { //Si annuler
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        confirmLaReservation.create();
        confirmLaReservation.show();
    }

    private void refreshList(Object o, int i) { //refresh la liste lors de la validation de la fenêtre dialog
        lesMaterielDispo.remove(i);
        listView.setAdapter(new ArrayAdapter<Materiel>(this, android.R.layout.simple_list_item_1, lesMaterielDispo));
        Toast.makeText(this, "Materiel ajouté !", Toast.LENGTH_LONG).show();
    }

    private void chargeListeMateriel(Object o) {
        lesMaterielDispo = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(o.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                Materiel m = new Materiel(jsonArray.getJSONObject(i));
                lesMaterielDispo.add(m);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listView.setAdapter(new ArrayAdapter<Materiel>(this, android.R.layout.simple_list_item_1, lesMaterielDispo));
    }
}

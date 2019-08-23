package com.abenkheira.gsb;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ListeMesReservation extends AppCompatActivity {
    private GestionWebService gwb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_mes_reservation);
        TextView titreActivity = findViewById(R.id.textViewMesRes); //Recupère l'id du titre pour le changer en fonction du bouton cliqué
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //pour bloquer la rotation de l'ecran

        switch (getIntent().getStringExtra("choixMesRes")){
            case "EnCours":                                             //Si clique bouton "en cours"
                titreActivity.setText("Réservations en cours");
                gwb = new GestionWebService(){
                    @Override
                    protected void onPostExecute(Object o) {
                        chargeListeRes(o);
                    }
                };
                gwb.execute("uc=getMesReservationEnCours");
                break;

            case "Futur":                                             //Si clique bouton "futur"
                titreActivity.setText("Réservations futurs");
                gwb = new GestionWebService(){
                    @Override
                    protected void onPostExecute(Object o) {
                        chargeListeRes(o);
                    }
                };
                gwb.execute("uc=getMesReservationFutur");
                break;

            case "Ancien":                                             //Si clique bouton "Ancien"
                titreActivity.setText("Réservations anciennes");
                gwb = new GestionWebService(){
                    @Override
                    protected void onPostExecute(Object o) {
                        chargeListeRes(o);
                    }
                };
                gwb.execute("uc=getMesReservationAncienne");
                break;
        }
    }

    private void chargeListeRes(Object o) { //Charge ma liste avec mes reservations
        ListView listeMesRes = findViewById(R.id.listViewMesRes);
        ArrayList<Reservation> mesReservation = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(o.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                Reservation r = new Reservation(jsonArray.getJSONObject(i));
                mesReservation.add(r);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listeMesRes.setAdapter(new ArrayAdapter<Reservation>(this, android.R.layout.simple_list_item_1, mesReservation));
    }
}

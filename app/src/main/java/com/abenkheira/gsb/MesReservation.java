package com.abenkheira.gsb;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MesReservation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_reservation);

        Button ReservEnCours  = findViewById(R.id.buttonResEnCours);    //
        Button ReservFutur = findViewById(R.id.buttonResFutur);         //Recupère l'id des bouton sur mon activity
        Button ReservAnc = findViewById(R.id.buttonResAnci);            //


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //pour bloquer la rotation de l'ecran

        ReservEnCours.setOnClickListener(new View.OnClickListener() {  //Permet de détecter le clique
            @Override
            public void onClick(View view) {
                afficheMesReserv("EnCours"); //Lance l'activity pour afficher mes reservations en fonction de "choixMesRes"
            }
        });

        ReservFutur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afficheMesReserv("Futur");
            }
        });

        ReservAnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afficheMesReserv("Ancien");
            }
        });
    }

    public void afficheMesReserv(String choixMesRes){ //La fonction qui permet de lancer l'activity mes reservations avec : "EnCours", "Futur" ou "Ancien"
        Intent activityMesRes = new Intent(this, ListeMesReservation.class);
        activityMesRes.putExtra("choixMesRes", choixMesRes);
        startActivity(activityMesRes);
    }


}

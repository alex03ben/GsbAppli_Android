package com.abenkheira.gsb;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button afficheFiltre = findViewById(R.id.buttonRecherche);
        Button afficheMesRes = findViewById(R.id.buttonMesReserv);
        Button afficheDemandeMat = findViewById(R.id.buttonDemandeMat);
        Button deconnexion = findViewById(R.id.buttonDeco);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //pour bloquer la rotation de l'ecran

        afficheFiltre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeFiltre();
            }
        });

        afficheMesRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeMesRes();
            }
        });

        afficheDemandeMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeDemandeMat();
            }
        });

        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeDeco();
            }
        });


    }

    private void executeDeco() {
        GestionWebService gwb;
        gwb = new GestionWebService(){
            @Override
            protected void onPostExecute(Object o) {
                confirmDeco();
            }
        };
        gwb.execute("uc=deconnexion");
        Intent activityMenu = new Intent(this, ConnexionActivity.class);
        startActivity(activityMenu);
    }

    private void confirmDeco() {
        Toast.makeText(this, "Vous êtes maintenant déconnecté", Toast.LENGTH_SHORT).show();
    }

    private void executeDemandeMat() {
        Intent activityMenu = new Intent(this, DemandeMateriel.class);
        startActivity(activityMenu);
    }

    private void executeMesRes() {
        Intent activityMenu = new Intent(this, MesReservation.class);
        startActivity(activityMenu);
    }

    private void executeFiltre() {
        Intent activityMenu = new Intent(this, FiltreRechercheResActivity.class);
        startActivity(activityMenu);
    }
}

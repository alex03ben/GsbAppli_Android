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
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ConnexionActivity extends AppCompatActivity {
    private GestionWebService gwb;
    private static User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        Button seConnecter = findViewById(R.id.buttonSeConnecter);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //pour bloquer la rotation de l'ecran

        seConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeActivity();
            }
        });
    }

    private void traiterConnexion(Object o){ //vérifie si le login et le mdp sont correctes pour lancer l'activity
        try {
            if (Objects.equals(o.toString(), "false")){
                Toast.makeText(this, "ERREUR : mot de passe ou login invalide !", Toast.LENGTH_LONG).show();
            }else {
                u = new User(new JSONObject(o.toString()));
                Intent activityMenu = new Intent(this, MenuActivity.class);
                startActivity(activityMenu);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void executeActivity(){
        String login = ((EditText)findViewById(R.id.editLogin)).getText().toString();
        String mdp = ((EditText)findViewById(R.id.editPassword)).getText().toString();

        gwb = new GestionWebService(){
            @Override
            protected void onPostExecute(Object o) {
                traiterConnexion(o);
            }
        };
        gwb.execute("uc=getConnexionUser&login="+login+"&mdp="+mdp);
    }
}

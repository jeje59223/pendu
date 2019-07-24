package com.example.pendu2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Accueil_Main extends AppCompatActivity {
    private Button valider;
    private  EditText nomJoueur;
    private Button scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_main);

        valider = findViewById(R.id.btn_player);
        nomJoueur = findViewById(R.id.et_player);
        scores = findViewById(R.id.btn_score);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = nomJoueur.getText().toString();

                Intent intent = new Intent(getApplicationContext(), Game_Activity.class);

                intent.putExtra("nomJ",nom);


                startActivity(intent);
                finish();
            }
        });

        scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ScoresActivity.class);

                startActivity(i);

            }
        });
    }
}

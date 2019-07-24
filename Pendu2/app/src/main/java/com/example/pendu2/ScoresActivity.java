package com.example.pendu2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScoresActivity extends AppCompatActivity {

    private Button accueil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_player_main);

        accueil = findViewById(R.id.btn_accueil);

        accueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Accueil_Main.class);

                startActivity(i);
            }
        });
    }
}

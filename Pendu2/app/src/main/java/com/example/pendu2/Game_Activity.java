package com.example.pendu2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Game_Activity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout container;
    private Button btn_send;
    private TextView tv_Lettres_tapees;
    private ImageView image;
    private EditText et_letter;
    private String word;
    private int found;
    private int error;
    private  int score;
    private String player;
    private List<Character> ListOfLetters = new ArrayList<>();
    private boolean win;
    private List<String> wordList = new ArrayList<>();
    private TextView tv_score;
    private TextView tv_player;
    private String nomJoueur ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        container = (LinearLayout) findViewById(R.id.word_container);
        btn_send = (Button) findViewById(R.id.btn_send);
        et_letter = (EditText) findViewById(R.id.et_letter);
        image = (ImageView) findViewById(R.id.iv_pendu);
        tv_Lettres_tapees = (TextView) findViewById(R.id.tv_lettres_tapees);
        tv_score = (TextView) findViewById(R.id.tv_score);
        score = 0;
        tv_player = (TextView) findViewById(R.id.tv_player);
        player = "";
        initGame();

        btn_send.setOnClickListener(this);

        nomJoueur = getIntent().getStringExtra("nomJ");

        tv_player.setText(nomJoueur);

    }

    /**
     * Permet d'initialiser le jeu pour démarrer une nouvelle partie
     */
    public void initGame() {
        word = generateWord();
        win = false;
        error = 0;
        found = 0;
        tv_score.setText("Score : " + score);
        tv_player.setText(nomJoueur);
        tv_Lettres_tapees.setText("");
        image.setBackgroundResource(R.drawable.first);
        ListOfLetters = new ArrayList<>();
        container.removeAllViews();

        for(int i = 0; i < word.length(); i++) {
            TextView oneLetter = (TextView) getLayoutInflater().inflate(R.layout.textview, null);
            container.addView(oneLetter);
        }
    }

    @Override
    public void onClick(View view) {

        String letterFromInput = et_letter.getText().toString().toUpperCase();
        et_letter.setText("");

        if(letterFromInput.length() > 0 ){
            if(!letterAlReadyUsed(letterFromInput.charAt(0), ListOfLetters)) {
                ListOfLetters.add(letterFromInput.charAt(0));
                checkIfLetterIsInWord(letterFromInput, word);
            }
            // la partie est gagnée :
           /* if(found == word.length()) {
                win = true;
                score++;
               createDialog(win);
               initGame();
            }*/

            // La lettre n'est pas dans le mot
            if(!word.contains(letterFromInput)) {
                error++;
            }
            setImage(error);
            scorePlayer();
            // La partie est perdu
        /*    if(error == 6) {
                win = false;
                createDialog(win);
                score = 0;
                initGame();
            }*/

            // Affichage des lettres entrées
            showAllLetters(ListOfLetters);
        }

    }

    /**
     * Permet de vérifier si une lettre a déjà été saisie et si c'est le cas afficher un message toast
     *
     */
    public boolean letterAlReadyUsed(char a, List<Character> listOfLetters) {
        for(int i = 0; i < listOfLetters.size(); i++) {
            if(listOfLetters.get(i) == a) {
                Toast.makeText(getApplicationContext(), "Vous avez déjà entré cette lettre", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    /**
     * Permet de vérifier si la lettres est présente dans le mot
     *
     */
    public void checkIfLetterIsInWord(String letter, String word) {

        for(int i = 0; i < word.length(); i++ ) {
            if(letter.equals(String.valueOf(word.charAt(i)))){
                TextView tv = (TextView) container.getChildAt(i);
                tv.setText(String.valueOf(word.charAt(i)));
                found++;
            }
        }

    }

    /**
     * Permet d'afficher les lettres tapées sur le côté de l'écran
     *
     */
    public void showAllLetters(List<Character> ListOfLetters) {
        String chaine = "";

        for(int i = 0; i < ListOfLetters.size(); i++) {
            chaine += ListOfLetters.get(i) + "\n";
        }
        if(!chaine.equals("")) {
            tv_Lettres_tapees.setText(chaine);
        }

    }

    /**
     * La gestion des images : quand on fait une erreur on change l'image
     */
    public void setImage(int error){

        switch (error) {
            case 1:
                image.setBackgroundResource(R.drawable.second);
                break;
            case 2:
                image.setBackgroundResource(R.drawable.third);
                break;
            case 3:
                image.setBackgroundResource(R.drawable.fourth);
                break;
            case 4:
                image.setBackgroundResource(R.drawable.fifth);
                break;
            case 5 :
                image.setBackgroundResource(R.drawable.sixth);
                break;
            case 6 :
                image.setBackgroundResource(R.drawable.seventh);
                break;
        }
    }

    /**
     * Permet d'afficher la boite de dialogue
     * @param win
     */
    public void createDialog(final boolean win) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vous avez gagné ! Votre score est de " + score);

        if (!win) {
            builder.setTitle("Vous avez perdu !");
            builder.setMessage("Le mot à trouver était : " + word);
        }
        builder.setPositiveButton(getResources().getString(R.string.rejouer), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(win == true) {
                    initGame();
                }else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });

        builder.create().show();
    }

    /**
     * Permet de récupérer la liste de mots
     * @return
     */
    public List<String> getListOfWords() {

        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(getAssets().open("pendu_liste.txt")));
            String line;
            while ((line = buffer.readLine()) != null) {
                wordList.add(line);
            }
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordList;
    }

    /**
     * Permet le choix aléatoire du mot
     * @return
     */
    public String generateWord() {
        wordList = getListOfWords();
        int random = (int) (Math.floor(Math.random() * wordList.size()));
        String word = wordList.get(random).trim();
        return word;
    }

    /**
     * Permet la gestion des points
     */
    public int scorePlayer() {


        if(found == word.length()) {
            win = true;
            score++;
            createDialog(win);
            initGame();
        }
        // La partie est perdu
        if(error == 6) {
            win = false;
            createDialog(win);
            score = 0;
            initGame();
        }
        return score;

    }


}

package com.simonsays;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    SQLBase db;
    TextView ScoreCo, Nom1, Score1, Nom2, Score2, Nom3, Score3, Nom4, Score4, Nom5, Score5;
    String mailjoueur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        int i=1;

        Nom1=findViewById(R.id.txt_name1_home_id);
        Score1 = findViewById(R.id.txt_score1_home_id);
        Nom2=findViewById(R.id.txt_name2_home_id);
        Score2 = findViewById(R.id.txt_score2_home_id);
        Nom3=findViewById(R.id.txt_name3_home_id);
        Score3 = findViewById(R.id.txt_score3_home_id);
        Nom4=findViewById(R.id.txt_name4_home_id);
        Score4 = findViewById(R.id.txt_score4_home_id);
        Nom5=findViewById(R.id.txt_name5_home_id);
        Score5 = findViewById(R.id.txt_score5_home_id);
        ScoreCo = findViewById(R.id.txt_yourpoints_home_id);
        Intent joueurs = getIntent();
        mailjoueur="";
        Bundle bundle = joueurs.getExtras();
        mailjoueur=bundle.getString("mail");
        db=new SQLBase(getApplicationContext());
        Cursor donnees = db.tri();
        while(donnees.moveToNext()){
            switch(i){
                case 1 :{
                    Nom1.setText(donnees.getString(0) + " " + donnees.getString(1));
                    Score1.setText(donnees.getString(2));
                    break;
                }
                case 2 :{
                    Nom2.setText(donnees.getString(0) + " " + donnees.getString(1));
                    Score2.setText(donnees.getString(2));
                    break;
                }
                case 3 :{
                    Nom3.setText(donnees.getString(0) + " " + donnees.getString(1));
                    Score3.setText(donnees.getString(2));
                    break;
                }
                case 4 :{
                    Nom4.setText(donnees.getString(0) + " " + donnees.getString(1));
                    Score4.setText(donnees.getString(2));
                    break;
                }
                case 5 :{
                    Nom5.setText(donnees.getString(0) + " " + donnees.getString(1));
                    Score5.setText(donnees.getString(2));
                    break;
                }
            }
            i++;
        }
        donnees.close();
        Cursor uncurseur;
        String Yourdonnees = "";
        uncurseur=db.playersScore(mailjoueur);
        while(uncurseur.moveToNext()){
            Yourdonnees=uncurseur.getString(0);
        }
        ScoreCo.setText(Yourdonnees);
    }

    public void startGameEasy(View view) {
        Bundle bundleEasy = new Bundle();
        bundleEasy.putInt("Difficulte",1);
        bundleEasy.putString("Mail",mailjoueur);
        Intent intent = new Intent (HomeActivity.this, LevelOneActivity.class);
        intent.putExtras(bundleEasy);
        startActivity(intent);
    }

    public void startGameHard(View view) {
        Bundle bundleHard = new Bundle();
        bundleHard.putInt("Difficulte",2);
        bundleHard.putString("Mail",mailjoueur);
        Intent intent = new Intent (HomeActivity.this, LevelOneActivity.class);
        intent.putExtras(bundleHard);
        startActivity(intent);
    }

    public void startGameExpert(View view) {
        Bundle bundleExpert = new Bundle();
        bundleExpert.putInt("Difficulte",3);
        bundleExpert.putString("Mail",mailjoueur);
        Intent intent = new Intent (HomeActivity.this, LevelOneActivity.class);
        intent.putExtras(bundleExpert);
        startActivity(intent);
    }

    public void startInfo(View view) {
        Bundle bundleInfo = new Bundle();
        bundleInfo.putString("Mail",mailjoueur);
        Intent intent = new Intent (HomeActivity.this, InfoPageActivity.class);
        intent.putExtras(bundleInfo);
        startActivity(intent);
    }
}
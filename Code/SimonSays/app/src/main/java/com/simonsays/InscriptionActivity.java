package com.simonsays;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class InscriptionActivity extends AppCompatActivity {

    EditText NAME, FIRSTNAME, EMAIL, BIRTHDAY, PASSWORD;
    Button validation;
    RadioGroup SEX;
    RadioButton HOMME, FEMME;
    SQLBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        EMAIL = findViewById(R.id.ptxt_mail_inscription_id);
        NAME = findViewById(R.id.ptxt_fname_inscrption_id);
        FIRSTNAME = findViewById(R.id.ptxt_name_inscription_id);
        BIRTHDAY = findViewById(R.id.ptxt_birthday_inscription_id);
        PASSWORD = findViewById(R.id.ptxt_mdp_inscription_id);
        validation = findViewById(R.id.btn_inscription_id);
        SEX = findViewById(R.id.id_radiogroup_sexe);
        HOMME = findViewById(R.id.rbtn_man_inscription_id);
        FEMME = findViewById(R.id.rbtn_girl_inscription_id);

        db=new SQLBase(getApplicationContext());

    }

    public void validation(View view) {
        String genre="";
        boolean erreur = false;
        Bundle bundle = new Bundle();
        boolean testmail;
        if((EMAIL.getText().toString()).matches("")){
            EMAIL.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.red_dark)));
            erreur = true;
        }
        if((NAME.getText().toString()).matches("")){
            NAME.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.red_dark)));
            erreur = true;
        }
        if((FIRSTNAME.getText().toString()).matches("")){
            FIRSTNAME.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.red_dark)));
            erreur = true;
        }
        if((BIRTHDAY.getText().toString()).matches("")){
            BIRTHDAY.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.red_dark)));
            erreur = true;
        }
        if((PASSWORD.getText().toString()).matches("")){
            PASSWORD.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.red_dark)));
            erreur = true;
        }
        if(!FEMME.isChecked() && !HOMME.isChecked()){
            erreur = true;
            FEMME.setTextColor(getColor(R.color.red_dark));
            HOMME.setTextColor(getColor(R.color.red_dark));
        }
        else{
            if(HOMME.isChecked()){
                genre="Homme";
            }
            else{
                genre="Femme";
            }
        }

        if(erreur){
            Toast.makeText(InscriptionActivity.this,"Champ(s) vide(s) invalide(s) !", Toast.LENGTH_SHORT).show();
        }
        else{
            testmail = db.addPlayer(EMAIL.getText().toString(),NAME.getText().toString(),FIRSTNAME.getText().toString(),BIRTHDAY.getText().toString(),genre,PASSWORD.getText().toString(),0);
            if(testmail){
                bundle.putString("mail",EMAIL.getText().toString());
                Intent intent = new Intent (InscriptionActivity.this, HomeActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Toast.makeText(InscriptionActivity.this,"Email déjà utilisé !", Toast.LENGTH_SHORT).show();
                EMAIL.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.red_dark)));
            }
        }
    }
}
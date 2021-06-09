package com.simonsays;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConnectionActivity extends AppCompatActivity {

    EditText EMAIL, PASSWORD;
    Button Valider;
    SQLBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        EMAIL = findViewById(R.id.ptxtmail_connection_id);
        PASSWORD = findViewById(R.id.pmdp_connection_id);
        Valider = findViewById(R.id.btn_validation_connection_id);
        db=new SQLBase(getApplicationContext());

        Valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                boolean erreur=false;
                boolean connexion = false;
                if((EMAIL.getText().toString()).matches("")){
                    EMAIL.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.red_dark)));
                    erreur = true;
                }
                if((PASSWORD.getText().toString()).matches("")){
                    PASSWORD.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.red_dark)));
                    erreur = true;
                }
                if(erreur==false){
                    connexion=db.connection(EMAIL.getText().toString(),PASSWORD.getText().toString());
                    if(connexion==true){
                        bundle.putString("mail",EMAIL.getText().toString());
                        Intent intent = new Intent (ConnectionActivity.this, HomeActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(ConnectionActivity.this,"Email ou Mot de passe invalide !", Toast.LENGTH_SHORT).show();
                        EMAIL.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.red_dark)));
                        PASSWORD.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.red_dark)));
                    }
                }
                else{
                    Toast.makeText(ConnectionActivity.this,"Champ(s) vide(s) invalide(s) !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
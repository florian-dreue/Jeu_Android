package com.simonsays;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class InfoPageActivity extends AppCompatActivity {

    String mailjoueur;
    Button Retour, Blue;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);

        Retour=findViewById(R.id.btn_info_id);
        Blue=findViewById(R.id.btn_test);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mailjoueur = bundle.getString("Mail");

        Retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle bundleInfo = new Bundle();
                bundleInfo.putString("Mail",mailjoueur);
                Intent intent2 = new Intent (InfoPageActivity.this, HomeActivity.class);
                intent2.putExtras(bundleInfo);
                startActivity(intent2);
            }
        });

    }

    public void onClick(View view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Blue.setTextColor(getColor(R.color.black));
                Blue.setBackgroundColor(getColor(R.color.blue_light));
                new CountDownTimer(500, 1) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        Blue.setTextColor(getColor(R.color.white));
                        Blue.setBackgroundColor(getColor(R.color.blue_dark));
                    }
                }.start();
            }
        }, 1000);
    }
}
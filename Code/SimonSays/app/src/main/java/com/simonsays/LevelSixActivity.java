package com.simonsays;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelSixActivity extends AppCompatActivity {

    Button Valid, Red, Blue, Yellow, Green, Purple, Orange, Cyan, Brown, Teal;
    ImageView Coeur1, Coeur2, Coeur3;
    TextView Score,Round;

    List<Button> btnArray = new ArrayList<Button>();//création d'une liste contenant des boutons
    int nbbtn = 9;//initialisation du nombre de bouton (pour les random)
    int[] couleur = new int[nbbtn * 2];//création d'un tableau contenant les différentes couleurs des boutons
    Button[] btnTable = new Button[11]; //création d'un tableau contenant l'ordre des bouton allumer
    ImageView[] lifesTable = new ImageView[3]; //création d'un tableau contenant les différentes images des coeurs
    int[] btnallu = new int[11];//création d'un tableau contenant le numéro des boutons dans le liste
    int random;
    Random rand = new Random();
    int height = 0; //Création d'une variable contenant le nombre de bouton allumer

    int btnToTest = 0;
    int lifes, startlifes, difficulty, nbbtnmin, nbbtnmax, round; //création de variables contenant réspectivement
    //le nombre de vie, le nombre de vie au début de la partie,la difficulté
    //le nombre de bouton minimum et le nombre de bouton maximum
    String mail;
    SQLBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //fonction éxecuter au lancement de l'activité
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_six);
        //atribution des élément du layout wml au variable de l'activité java
        Valid = findViewById(R.id.btn_start_id);
        Red = findViewById(R.id.btn_red_id);
        Blue = findViewById(R.id.btn_blue_id);
        Yellow = findViewById(R.id.btn_yellow_id);
        Green = findViewById(R.id.btn_green_id);
        Purple = findViewById(R.id.btn_purple_id);
        Orange = findViewById(R.id.btn_orange_id);
        Cyan = findViewById(R.id.btn_cyan_id);
        Brown = findViewById(R.id.btn_brown_id);
        Teal = findViewById(R.id.btn_teal_id);
        Coeur1 = findViewById(R.id.img_coeur1_id_leveltwo);
        Coeur2 = findViewById(R.id.img_coeur2_id_leveltwo);
        Coeur3 = findViewById(R.id.img_coeur3_id_leveltwo);
        Score=findViewById(R.id.txt_score_level_id);
        Round= findViewById(R.id.txt_nbround_id);

        //atribution des images des vies aux différentes case du tableau concu à cet effet
        lifesTable[0] = Coeur3;
        lifesTable[1] = Coeur2;
        lifesTable[2] = Coeur1;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        difficulty = bundle.getInt("Difficulte");//récupération des élements passé en paramètres lors du lancement de la page
        mail = bundle.getString("Mail");

        db=new SQLBase(getApplicationContext());

        //atribution des différents élements en fonction de la difficulté
        switch (difficulty) {
            case 1: {
                lifes = 2;
                Coeur1.setVisibility(View.INVISIBLE);
                nbbtnmax = 10;
                nbbtnmin = 1;
                Score.setText("5");
            }
            break;
            case 2: {
                lifes = 2;
                Coeur1.setVisibility(View.INVISIBLE);
                nbbtnmax = 15;
                nbbtnmin = 3;
                Score.setText("7.5");
            }
            break;
            case 3: {
                lifes = 3;
                nbbtnmax = 20;
                nbbtnmin = 5;
                Score.setText("15");
            }
            break;
        }
        startlifes = lifes;

        //Insertion des boutons dans la liste concu à cete effet
        btnArray.add(Red);
        btnArray.add(Blue);
        btnArray.add(Yellow);
        btnArray.add(Green);
        btnArray.add(Purple);
        btnArray.add(Orange);
        btnArray.add(Cyan);
        btnArray.add(Brown);
        btnArray.add(Teal);

        btnClickableOff();//désactivation des boutons

        //isertion des couleurs dans le tableau concu à cet effet
        couleur[0] = R.color.red_dark;
        couleur[1] = R.color.blue_dark;
        couleur[2] = R.color.yellow_dark;
        couleur[3] = R.color.green_dark;
        couleur[4] = R.color.purple_dark;
        couleur[5] = R.color.orange_dark;
        couleur[6] = R.color.cyan_dark;
        couleur[7] = R.color.brown_dark;
        couleur[8] = R.color.teal_dark;
        couleur[9] = R.color.red_light;
        couleur[10] = R.color.blue_light;
        couleur[11] = R.color.yellow_light;
        couleur[12] = R.color.green_light;
        couleur[13] = R.color.purple_light;
        couleur[14] = R.color.orange_light;
        couleur[15] = R.color.cyan_light;
        couleur[16] = R.color.brown_light;
        couleur[17] = R.color.teal_light;

        //Code éxecutez lorsque l'on clique sur le bouton start
        Valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Valid.setVisibility(View.INVISIBLE);
                round=1;
                Round.setText(String.valueOf(round));
                initialisation();
            }
        });

    }

    public void btnClickableOff(){//fonction qui permet de désactiver le click des boutons
        for(int i=0;i<nbbtn;i++){
            btnArray.get(i).setClickable(false);
        }
    }

    public void btnClickableOn(){//fonction qui permet d'activer le click des boutons
        for(int i=0;i<nbbtn;i++){
            btnArray.get(i).setClickable(true);
        }
    }

    public void memory(int Index) {//fonction qui ajoute un bouton à allumer aléatoirement à la liste
        btnClickableOff();
        random = rand.nextInt(nbbtn);
        btnTable[Index] = btnArray.get(random);
        btnallu[Index] = random;
        height++;
        round++;
        Round.setText(String.valueOf(round));
        allumage();
    }

    public void allumage() {//fonction qui allume les différents boutons de la liste
        int btnAtOn;
        for (btnAtOn = 0; btnAtOn < height; btnAtOn++) {
            int finalBtnAtOn = btnAtOn;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    btnTable[finalBtnAtOn].setTextColor(getColor(R.color.black));
                    btnTable[finalBtnAtOn].setBackgroundColor(getColor(couleur[btnallu[finalBtnAtOn] + nbbtn]));
                    new CountDownTimer(500, 1) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            btnTable[finalBtnAtOn].setTextColor(getColor(R.color.white));
                            btnTable[finalBtnAtOn].setBackgroundColor(getColor(couleur[btnallu[finalBtnAtOn]]));
                        }
                    }.start();
                }
            }, btnAtOn * 1000);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btnClickableOn();
            }
        },btnAtOn*1000);
    }

    public void btnOnClick(View view) {//fonction qui s'exxecute lorsque l'on clique sur l'un des boutons de couleurs
        boolean[] loose = {false};
        if (btnToTest < height) {//si le bouton à tester est inférieur au bouton à allumer
            switch (view.getId()) {//pour chaque cas d'appuie sur un bouton
                case R.id.btn_red_id: {
                    if (btnTable[btnToTest] != Red) {
                        loose[0] = true;
                    }
                    break;
                }
                case R.id.btn_blue_id: {
                    if (btnTable[btnToTest] != Blue) {
                        loose[0] = true;
                    }
                    break;
                }
                case R.id.btn_yellow_id: {
                    if (btnTable[btnToTest] != Yellow) {
                        loose[0] = true;
                    }
                    break;
                }
                case R.id.btn_green_id: {
                    if (btnTable[btnToTest] != Green) {
                        loose[0] = true;
                    }
                    break;
                }
                case R.id.btn_purple_id: {
                    if (btnTable[btnToTest] != Purple) {
                        loose[0] = true;
                    }
                    break;
                }
                case R.id.btn_orange_id: {
                    if (btnTable[btnToTest] != Orange) {
                        loose[0] = true;
                    }
                    break;
                }
                case R.id.btn_cyan_id: {
                    if (btnTable[btnToTest] != Cyan) {
                        loose[0] = true;
                    }
                    break;
                }
                case R.id.btn_brown_id: {
                    if (btnTable[btnToTest] != Brown) {
                        loose[0] = true;
                    }
                    break;
                }
                case R.id.btn_teal_id: {
                    if (btnTable[btnToTest] != Teal) {
                        loose[0] = true;
                    }
                    break;
                }
            }
        }
        if (loose[0]) {//si on as appuyer sur une mauvaise couleur
            btnToTest = 0;//remise du bouton à tester au début de la liste
            if (lifes != 1) {//si on as encore au moins une vie
                Toast.makeText(getApplicationContext(), "Erreur au bouton "+ (btnToTest+1) + " . Réessayez !", Toast.LENGTH_SHORT).show();
                lifesTable[lifes - 1].setVisibility(View.INVISIBLE);//on cache une vie pour le joueur
                lifes--;//on enlève une vie à la variable
                new CountDownTimer(1500,1){
                    @Override
                    public void onTick(long millisUntilFinished) { }

                    @Override
                    public void onFinish() {
                        allumage();//on allume de nouveau les boutons aprés quelque seconde le temps pour l'utilisateur de lire le Toast
                    }
                }.start();
            } else {//si on n'as plus de vie
                gameOver();//on recommence le niveau de 0
            }
        } else {//si on à appuyer sur la bonne couleur
            if (btnToTest + 1 == height) {//si la couleur tester +1 est égale au nombre de bouton allumer
                if (btnToTest + 1 == nbbtnmax) {//si la couleur tester est égale au nombre maximum de bouton allumer
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(LevelSixActivity.this, LevelSevenActivity.class);
                    bundle.putInt("Difficulte", difficulty);//on passe la difficulté en paramètre
                    bundle.putString("Mail", mail);
                    intent.putExtras(bundle);
                    switch(difficulty){
                        case 1 :{
                            db.score(mail,6);
                        }break;
                        case 2 :{
                            db.score(mail,9);
                        }break;
                        case 3 :{
                            db.score(mail,18);
                        }break;
                    }
                    startActivity(intent);//on lance la page suivante
                } else {//si la couleur tester est inférieur au nombre miximum de bouton allumer
                    Toast.makeText(getApplicationContext(), "Vous avez réussi le round " + round + " !", Toast.LENGTH_SHORT).show();
                    new CountDownTimer(1500,1){
                        @Override
                        public void onTick(long millisUntilFinished) { }

                        @Override
                        public void onFinish() {
                            memory(height);//On ajoute une couleur à la chaîne de celles déjà existante quelque temps aprés avoir afficher
                            //le toast pour permettre à l'utilisateur de le lire
                        }
                    }.start();
                    btnToTest = 0;//on remet le bouton à tester à 0

                }
            } else {//si ce n'est pas la dernière couleur de la chaîne on passe à la suivante
                btnToTest++;
            }
        }
    }

    public void gameOver() {//fonction qui remet le niveau et ses variables à 0
        Toast.makeText(getApplicationContext(), "Game Over", Toast.LENGTH_SHORT).show();
        lifes = startlifes;
        for (int i = 0; i < startlifes; i++) {
            lifesTable[i].setVisibility(View.VISIBLE);
        }
        int j = 0;
        while (btnTable[j] != null) {
            btnTable[j] = null;
            j++;
        }
        btnToTest = 0;
        round=1;
        Round.setText(String.valueOf(round));
        Valid.setVisibility(View.VISIBLE);
    }

    public void initialisation() {//fonction qui initialise les premiers bouton
        int compteur = 0;
        while (compteur < nbbtnmin) {//tant que le nombre de bouton dans la liste est inférieur au nombre de bouton de départ
            random = rand.nextInt(nbbtn);
            btnTable[compteur] = btnArray.get(random);
            btnallu[compteur] = random;
            compteur++;
        }
        height = nbbtnmin;
        allumage();
    }
}
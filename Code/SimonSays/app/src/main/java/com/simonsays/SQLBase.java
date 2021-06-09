package com.simonsays;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLBase extends SQLiteOpenHelper{

    public static String BDD_NAME = "SimonBase", TABLE_NAME =  "Joueurs", ID = "Id";
    public static String EMAIL = "email", NAME = "name", FIRSTNAME = "firstname", BIRTHDAY = "birthday", SEX = "sex", PASSWORD = "password", SCORE = "score";

    public SQLBase(@Nullable Context context) {
        super(context, BDD_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                EMAIL + " TEXT PRIMARY KEY, "+
                NAME + " TEXT, " +
                FIRSTNAME + " TEXT, " +
                BIRTHDAY + " TEXT, " +
                SEX + " TEXT, " +
                PASSWORD + " TEXT, " +
                SCORE + " DOUBLE)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    boolean addPlayer(String email, String name, String firstname, String birthday, String sex, String password, int score){
        long result;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(EMAIL, email);
        data.put(NAME, name);
        data.put(FIRSTNAME, firstname);
        data.put(BIRTHDAY, birthday);
        data.put(SEX, sex);
        data.put(PASSWORD, password);
        data.put(SCORE, score);
        String query = "SELECT * FROM Joueurs WHERE email='"+email+"'";
        Cursor mail = db.rawQuery(query,null);
        if(!mail.moveToFirst()){
            result=db.insert(TABLE_NAME,null, data);
        }
        else{
            result=-1;
        }
        if(result!=-1){
            return true;
        }
        else{
            return false;
        }

    }

    boolean connection(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Joueurs WHERE email='"+email+"' AND password='"+password+"'";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }

    Cursor playersScore(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String query = "SELECT score FROM Joueurs WHERE email='"+email+"'";
        cursor = db.rawQuery(query,null);
        return cursor;
    }

    Cursor tri(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result;
        String query = "SELECT name,firstname,score FROM Joueurs ORDER BY score DESC";
        result = db.rawQuery(query,null);
        return result;

    }

    public void score(String mail, double lescore){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Joueurs SET score='"+ lescore + "' WHERE email='"+ mail +"' AND score<'"+ lescore +"'";
        db.execSQL(query);
    }

}

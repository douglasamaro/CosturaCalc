package com.costcalc.calculadoradacostureira;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


public class SplashScreen extends AppCompatActivity {

   public String mnome = "teste";
   public String mvalorHora = "1234";
   public SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Check();
            }
        }, 4000);

       bancoDados = openOrCreateDatabase("DB_DADOS", MODE_PRIVATE, null);
       bancoDados.execSQL("CREATE TABLE IF NOT EXISTS dados(nome VARCHAR, valorHora VARCHAR)");

    }


    private void Check() {
        String selectQuery = "SELECT COUNT(*) FROM dados";
        Cursor cursor = bancoDados.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getInt(0) == 0) {
                bancoDados.execSQL("INSERT INTO dados(nome, valorHora)  VALUES('" + mnome + "', '" + mvalorHora + "')");
                Intent in = new Intent(this, Cadastro.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            } else {
                try {
                    String selectQuery1 = "SELECT nome FROM dados";
                    Cursor cursor1 = bancoDados.rawQuery(selectQuery1, null);
                    cursor1.moveToFirst();
                    @SuppressLint("Range") String snome = cursor1.getString(cursor1.getColumnIndex("nome"));
                    if (snome.equals("teste")) {
                        Intent in = new Intent(this, Cadastro.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(in);
                    } else {
                        Intent in = new Intent(this, Main.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(in);
                    }
                } catch (Exception e) {
                    Log.i(TAG, "douglas first ", e);
                }

            }
        }
    }

}
package com.costcalc.costuracalc;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Cadastro extends AppCompatActivity {

    EditText nome, valorHora;
    //SqlCommands sql = new SqlCommands();
    public SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        bancoDados = openOrCreateDatabase("DB_DADOS", MODE_PRIVATE, null);
        bancoDados.execSQL("CREATE TABLE IF NOT EXISTS dados(nome VARCHAR, valorHora VARCHAR)");

        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String ptempo = extras.getString("valorhora");
                valorHora = findViewById(R.id.valorHora);
                valorHora.setHint("para você recomendamos: R$" + ptempo);
            }
        } catch (Exception e) {
            Log.i(TAG, "Erro budle " + e);
        }


        nome = findViewById(R.id.nome);
        valorHora = findViewById(R.id.valorHora);

    }

    public void Salvar(View view) {
        try {
            if ((nome.getText().toString().equals("")) || (valorHora.getText().toString().equals(""))) {
                Context contexto = getApplicationContext();
                String texto = "preencha todos os campos";
                int duracao = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(contexto, texto, duracao);
                toast.show();
            } else {
                String mnome = nome.getText().toString();
                String mvalorHora = valorHora.getText().toString();
                bancoDados.execSQL("UPDATE dados SET nome = '" + mnome + "', valorHora = '" + mvalorHora + "'");
                Log.i(TAG, "erro: " + mnome + " e " + mvalorHora);
                Intent intent = new Intent(this, Main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Log.i(TAG, "erro igual a zero ");
            }
        } catch (Exception e) {
            Log.i(TAG, "erro function Salvar() ", e);
        }
    }

    public void Tutorial(View view) {
        Intent intent = new Intent(this, Tutorial.class);
        startActivity(intent);
    }
}
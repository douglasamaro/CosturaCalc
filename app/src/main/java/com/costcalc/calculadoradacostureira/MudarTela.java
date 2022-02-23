package com.costcalc.calculadoradacostureira;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class MudarTela extends AppCompatActivity  {

    public String tela;

    public void main(String[] args) {
    }
    public void MostrarMain() {
        Intent in = new Intent(this, Main.class);
        in.setFlags(in.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(in);
        finish();
    }
    public void Cadastrar() {
        Intent in = new Intent(this, Cadastro.class);
        in.setFlags(in.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(in);
        finish();
    }

    public MudarTela(String tela) {
        this.tela = tela;
    }

}

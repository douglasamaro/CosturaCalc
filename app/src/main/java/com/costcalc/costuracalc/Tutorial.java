package com.costcalc.costuracalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Tutorial extends AppCompatActivity {

    EditText salario, horas;
    CheckBox checkboxseg, checkboxter, checkboxqua, checkboxqui, checkboxsex, checkboxsab, checkboxdom;
    TextView resultado;
    Button mybtn;
    double valorDaHoraFinal = 0;
    int dias = 0;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        salario = findViewById(R.id.salario);
        horas = findViewById(R.id.horas);

        checkboxseg = findViewById(R.id.checkboxseg);
        checkboxter = findViewById(R.id.checkboxter);
        checkboxqua = findViewById(R.id.checkboxqua);
        checkboxqui = findViewById(R.id.checkboxqui);
        checkboxsex = findViewById(R.id.checkboxsex);
        checkboxsab = findViewById(R.id.checkboxsab);
        checkboxdom = findViewById(R.id.checkboxdom);

        resultado = findViewById(R.id.resultado);
        mybtn = findViewById(R.id.mybtn);

    }

    public void CalcSalv(View view) {
        dias = 0;
        if (checkboxseg.isChecked()) {
            dias += 1;
        }
        if (checkboxter.isChecked()) {
            dias += 1;
        }
        if (checkboxqua.isChecked()) {
            dias += 1;
        }
        if (checkboxqui.isChecked()) {
            dias += 1;
        }
        if (checkboxsex.isChecked()) {
            dias += 1;
        }
        if (checkboxsab.isChecked()) {
            dias += 1;
        }
        if (checkboxdom.isChecked()) {
            dias += 1;
        }
       if (valorDaHoraFinal == 0) {
            if (salario.getText().toString().equals("") || horas.getText().toString().equals("") || dias == 0) {
                msg = "é preciso que você preencha todos os campos e pelo menos um dia da semana";
                Context contexto = getApplicationContext();
                String texto = msg;
                int duracao = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(contexto, texto, duracao);
                toast.show();
            } else {
                double msalario = Double.parseDouble(salario.getText().toString());
                double mhoras = Double.parseDouble(horas.getText().toString());
                dias = dias * 4;
                valorDaHoraFinal = (msalario / dias) / mhoras;
                resultado.setText(String.format(Locale.US, "A sua hora vale R$" + "%.2f", valorDaHoraFinal));
                mybtn.setText("voltar");

            }
        } else {
            Intent intent = new Intent(this, Cadastro.class);
            intent.putExtra("valorhora", String.format(Locale.US, "%.2f", valorDaHoraFinal));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(Tutorial.this, Cadastro.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        super.onBackPressed();
        startActivity(in);
    }
}
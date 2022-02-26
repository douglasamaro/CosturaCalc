package com.costcalc.costuracalc;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class Main extends AppCompatActivity {
    public SQLiteDatabase bancoDados;
    private RewardedAd mRewardedAd;
    EditText tempo, comprado, pagou, usado, adicionais, lucro;
    TextView title;
    Button calcular;
    private AdView mAdView;
    AdRequest adRequest = new AdRequest.Builder().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                loadRewardedAd();
            }
        });
        mAdView = findViewById(R.id.adView);
        mAdView.loadAd(adRequest);

        bancoDados = openOrCreateDatabase("DB_DADOS", MODE_PRIVATE, null);

        // pegando os inputs
        calcular = findViewById(R.id.calcularcalc);
        title = findViewById(R.id.title);

        tempo = findViewById(R.id.tempo);
        comprado = findViewById(R.id.comprado);
        pagou = findViewById(R.id.pagou);
        usado = findViewById(R.id.usado);
        adicionais = findViewById(R.id.adicionais);
        lucro = findViewById(R.id.lucro);

        try {
            String selectQuery = "SELECT nome FROM dados";
            Cursor cursor = bancoDados.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            @SuppressLint("Range") String mnome = cursor.getString(cursor.getColumnIndex("nome"));
            title.setText("Olá, " + mnome + "!");
        } catch (Exception e) {
            Log.i(TAG, "douglas first ", e);
        }

    }

    // requisitar o anuncio
    private void loadRewardedAd() {
           try {
               RewardedAd.load(this, "ca-app-pub-4526711384034283/3731194005",
                       adRequest, new RewardedAdLoadCallback() {
                           @Override
                           public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                               // Handle the error.
                               Log.d(TAG, loadAdError.getMessage());
                               mRewardedAd = null;
                           }

                           @Override
                           public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                               mRewardedAd = rewardedAd;
                               Log.d(TAG, "Douglas " + "Ad was loaded.");
                               mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                   @Override
                                   public void onAdShowedFullScreenContent() {
                                       // Called when ad is shown.
                                       Log.d(TAG, "Douglas " + "Ad was shown.");
                                   }

                                   @Override
                                   public void onAdFailedToShowFullScreenContent(AdError adError) {
                                       // Called when ad fails to show.
                                       Log.d(TAG, "Douglas " + "Ad failed to show.");
                                   }

                                   @Override
                                   public void onAdDismissedFullScreenContent() {
                                       // Called when ad is dismissed.
                                       // Set the ad reference to null so you don't show the ad a second time.
                                       Log.d(TAG, "Douglas " + "Ad was dismissed.");
                                       loadRewardedAd();
                                   }
                               });
                           }
                       });
           } catch (Exception e) {
               Log.d(TAG, "Douglas " + e);
           }
    }

    // chamada anuncio e mudar tela para resultado, se sucesso
    private void showReward() {
        if (mRewardedAd != null) {
            Intent intent = new Intent(Main.this, Resultados.class);
            mRewardedAd.show(this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.

                    Log.d(TAG, "Douglas " + "The user earned the reward.");
                    intent.putExtra("tempo", tempo.getText().toString());
                    intent.putExtra("comprado", comprado.getText().toString());
                    intent.putExtra("pagou", pagou.getText().toString());
                    intent.putExtra("usado", usado.getText().toString());
                    intent.putExtra("adicionais", adicionais.getText().toString());
                    intent.putExtra("lucro", lucro.getText().toString());
                    startActivity(intent);
                }
            });
        } else {
            Log.d(TAG, "Douglas " + "The rewarded ad wasn't ready yet.");
            Context contexto = getApplicationContext();
            String texto = "você está offline, verifique a conexão com a internet e tente daqui a 10 segundos";
            int duracao = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(contexto, texto, duracao);
            toast.show();
            loadRewardedAd();
        }
    }

    //Cadastro

    public void Editar(View view) {
        try {
            Intent in = new Intent(Main.this, Cadastro.class);

            startActivity(in);
        } catch (Exception e) {
            Log.i(TAG, "Douglas ", e);
        }
    }

    // botão calcular chamada Anuncio
    public void Calcular(View view) {
        try {
            if (tempo.getText().toString().equals("") || comprado.getText().toString().equals("") || pagou.getText().toString().equals("") || usado.getText().toString().equals("") || adicionais.getText().toString().equals("") || lucro.getText().toString().equals("")) {
                Context contexto = getApplicationContext();
                String texto = "preencha todos os campos";
                int duracao = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(contexto, texto, duracao);
                toast.show();
            } else {
                double mcomprado = Double.parseDouble(comprado.getText().toString());
                double musado = Double.parseDouble(usado.getText().toString());
                if (mcomprado < musado){
                    Context contexto = getApplicationContext();
                    String texto = "você não pode usar mais do que comprou";
                    comprado.setBackgroundResource(R.drawable.background_input_warner);
                    usado.setBackgroundResource(R.drawable.background_input_warner);
                    int duracao = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(contexto, texto, duracao);
                    toast.show();
                } else{
                    showReward();
                }
            }
        } catch (Exception e) {
            Context contexto = getApplicationContext();
            String texto = "erro: " + e;
            int duracao = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(contexto, texto, duracao);
            toast.show();
        }
    }
}
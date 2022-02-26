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


import java.util.Locale;

public class Resultados extends AppCompatActivity {

    public double tempo;
    public double comprado;
    public double pagou;
    public double usado;
    public double adicionais;
    public double lucro;
    double valorHora;
    double total;
    double totalGeral;
    double hora;
    double tecido;
    TextView mtecido, mhora, mlucro, madicionais, mtotal, porcent1, porcent2, porcent3, porcent4, resultporcent1, resultporcent2, resultporcent3, resultporcent4;
    public SQLiteDatabase bancoDados;
    int reward = 0;
    private RewardedAd mRewardedAd;
    private AdView mAdView;
    AdRequest adRequest = new AdRequest.Builder().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        //anuncio
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                loadRewardedAd();
            }
        });

        mAdView = findViewById(R.id.adView);
        mAdView.loadAd(adRequest);

        bancoDados = openOrCreateDatabase("DB_DADOS", MODE_PRIVATE, null);

        porcent1 = findViewById(R.id.porcent1);
        porcent2 = findViewById(R.id.porcent2);
        porcent3 = findViewById(R.id.porcent3);
        porcent4 = findViewById(R.id.porcent4);

        resultporcent1 = findViewById(R.id.resultporcent1);
        resultporcent2 = findViewById(R.id.resultporcent2);
        resultporcent3 = findViewById(R.id.resultporcent3);
        resultporcent4 = findViewById(R.id.resultporcent4);

        mtecido = findViewById(R.id.mtecido);
        mhora = findViewById(R.id.mhora);
        mlucro = findViewById(R.id.mlucro);
        madicionais = findViewById(R.id.madicionais);
        mtotal = findViewById(R.id.mtotal);

        try {
            String selectQuery = "SELECT valorHora FROM dados";
            Cursor cursor = bancoDados.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            @SuppressLint("Range") String mvalorHora = cursor.getString(cursor.getColumnIndex("valorHora"));
            valorHora = Double.parseDouble(mvalorHora);
        } catch (Exception e) {
            Log.i(TAG, "douglas first ", e);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
           String ptempo = extras.getString("tempo");
           String pcomprado = extras.getString("comprado");
           String pusado = extras.getString("usado");
           String ppagou = extras.getString("pagou");
           String padicionais = extras.getString("adicionais");
           String plucro = extras.getString("lucro");

            tempo = Double.parseDouble(ptempo);
            comprado = Double.parseDouble(pcomprado);
            pagou = Double.parseDouble(ppagou);
            usado = Double.parseDouble(pusado);
            adicionais = Double.parseDouble(padicionais);
            lucro = Double.parseDouble(plucro);

            Count();
        }
    }

    public void Count() {
        tecido = (pagou / comprado) * usado;
        hora = (valorHora / 60) * tempo;
        total = tecido + hora + adicionais;
        lucro = (total / 100) * lucro;
        totalGeral = lucro + total;
        mtecido.setText(String.format(Locale.US,"R$" + "%.2f", tecido));
        mhora.setText(String.format(Locale.US, "R$" + "%.2f", hora));
        mlucro.setText(String.format(Locale.US, "R$" + "%.2f", lucro));
        madicionais.setText(String.format(Locale.US, "R$" + "%.2f", adicionais));
        mtotal.setText(String.format(Locale.US, "R$" + "%.2f", totalGeral));

        if (lucro > 0 & lucro < 21) {
            porcent1.setText("com 35%");
            porcent2.setText("com 60%");
            porcent3.setText("com 53%");
            porcent4.setText("com 83%");

            resultporcent1.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 35) + total));
            resultporcent2.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 60) + total));
            resultporcent3.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 53) + total));
            resultporcent4.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 83) + total));


        } else {
            if (lucro > 20 & lucro < 41) {
                porcent1.setText("com 13%");
                porcent2.setText("com 58%");
                porcent3.setText("com 91%");
                porcent4.setText("com 72%");

                resultporcent1.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 13) + total));
                resultporcent2.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 58) + total));
                resultporcent3.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 91) + total));
                resultporcent4.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 72) + total));
            } else {
                if (lucro > 40 & lucro < 61) {
                    porcent1.setText("com 32%");
                    porcent2.setText("com 75%");
                    porcent3.setText("com 14%");
                    porcent4.setText("com 87%");

                    resultporcent1.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 32) + total));
                    resultporcent2.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 75) + total));
                    resultporcent3.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 14) + total));
                    resultporcent4.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 87) + total));
                } else {
                    if (lucro > 60 & lucro < 81) {
                        porcent1.setText("com 12%");
                        porcent2.setText("com 51%");
                        porcent3.setText("com 97%");
                        porcent4.setText("com 38%");

                        resultporcent1.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 12) + total));
                        resultporcent2.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 51) + total));
                        resultporcent3.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 97) + total));
                        resultporcent4.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 38) + total));
                    } else {
                        if (lucro > 80 & lucro < 101) {
                            porcent1.setText("com 16%");
                            porcent2.setText("com 44%");
                            porcent3.setText("com 23%");
                            porcent4.setText("com 64%");

                            resultporcent1.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 16) + total));
                            resultporcent2.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 44) + total));
                            resultporcent3.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 23) + total));
                            resultporcent4.setText(String.format(Locale.US, "R$" + "%.2f", ((total / 100) * 64) + total));
                        }
                    }
                }
            }
        }
    }

    public Resultados(){
    }

    public void Mudar(View view) {
        showReward();
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(Resultados.this, Main.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        super.onBackPressed();
    }


    //anuncio
    // requisitar o anuncio
    private void loadRewardedAd() {
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
    }

    // chamada anuncio e mudar tela para resultado, se sucesso
    private void showReward() {
        if (mRewardedAd != null) {
            Intent intent = new Intent(this, Main.class);
            mRewardedAd.show(this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    Log.d(TAG, "Douglas " + "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                    reward = rewardAmount;
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

}

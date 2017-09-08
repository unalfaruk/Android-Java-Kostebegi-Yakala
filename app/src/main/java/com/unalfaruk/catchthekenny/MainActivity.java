package com.unalfaruk.catchthekenny;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView txtSayac;
    TextView txtPuan;
    int puan;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView[] kostebekler;

    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1= (ImageView) findViewById(R.id.imageView);
        imageView2= (ImageView) findViewById(R.id.imageView2);
        imageView3= (ImageView) findViewById(R.id.imageView3);
        imageView4= (ImageView) findViewById(R.id.imageView4);
        imageView5= (ImageView) findViewById(R.id.imageView5);
        imageView6= (ImageView) findViewById(R.id.imageView6);
        imageView7= (ImageView) findViewById(R.id.imageView7);
        imageView8= (ImageView) findViewById(R.id.imageView8);
        imageView9= (ImageView) findViewById(R.id.imageView9);

        kostebekler=new ImageView[]{imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9};

        puan=0;
        txtSayac= (TextView) findViewById(R.id.txtSayac);
        new CountDownTimer(30000,1000){

            @Override
            public void onTick(long millisUntilFinished) {

                txtSayac.setText("Zaman: "+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                txtSayac.setText("Süre bitti.");
                handler.removeCallbacks(runnable);
                for(ImageView kostebek:kostebekler){
                    kostebek.setOnClickListener(null);
                }
            }
        }.start();

        kostebegiGizle();
    }

    public void puanArtir(View view){
        puan++;
        txtPuan= (TextView) findViewById(R.id.txtPuan);
        txtPuan.setText("Puan: "+puan);
    }

    public void kostebegiGizle(){

        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                for(ImageView kostebek:kostebekler){
                    kostebek.setVisibility(View.INVISIBLE);
                }

                Random rastgele=new Random();
                int rastgeleSayi=rastgele.nextInt(8-0);
                kostebekler[rastgeleSayi].setVisibility(View.VISIBLE);

                handler.postDelayed(this,500);
            }
        };
        handler.post(runnable);
    }
}

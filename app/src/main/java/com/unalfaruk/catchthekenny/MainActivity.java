package com.unalfaruk.catchthekenny;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView txtSayac;
    TextView txtPuan;
    int puan;
    long kalanZaman;
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
    Button btnYeniden;
    Button btnDuraklat;
    CountDownTimer zamanlayici;

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

        btnYeniden= (Button) findViewById(R.id.btnYeniden);
        btnDuraklat= (Button) findViewById(R.id.btnDuraklat);

        txtPuan= (TextView) findViewById(R.id.txtPuan);

        kostebekler=new ImageView[]{imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9};

        puan=0;
        txtSayac= (TextView) findViewById(R.id.txtSayac);

        //Bu fonksiyon aldığı parametreye göre oyun süresini başlatıyor.
        zamanlayiciBaslat(30000);
    }

    public void zamanlayiciBaslat(long sure){
        zamanlayici=new CountDownTimer(sure,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                /*Kalan zamanı sürekli bir değişkende tutuyoruz, böylece kullanıcı "Duraklat" dediğinde,
                tekrar devam edeceği zaman zamanlayıcıyı sıfırdan kuracağız ama kaldığı zamandan başlatacağız.*/
                kalanZaman=millisUntilFinished;
                txtSayac.setText("Zaman: "+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                txtSayac.setText("Süre bitti.");
                handler.removeCallbacks(runnable);
                kostebekOnClickKaldir();
                btnYeniden.setVisibility(View.VISIBLE);
                btnDuraklat.setVisibility(View.INVISIBLE);
            }
        };

        zamanlayici.start();

        kostebegiGizle();
    }

    //ImageView olan köstebeklerin onClick fonksiyonu
    public void puanArtir(View view){
        puan++;
        txtPuan.setText("Puan: "+puan);
    }

    //Köstebeklere ait OnClick fonksiyonunu devre dışı bırakıyor.
    public void kostebekOnClickKaldir(){
        for(ImageView kostebek:kostebekler){
            kostebek.setOnClickListener(null);
        }
    }

    //Köstebeklere ait OnClik fonksiyonunu devreye alıyor.
    public void kostebekOnClickEkle(){
        for(ImageView kostebek:kostebekler){
            kostebek.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    puanArtir(v);
                }
            });
        }
    }

    //Rastgele köstebek gösteren fonksiyon
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

    //Yeniden butonunun onClick fonksiyonu
    public void yenidenBaslat(View view){
        //Puanı sıfırlıyoruz.
        puan=0;
        txtPuan.setText("Puan: "+puan);

        //Zamanlayıcıyı tekrar ayarlıyoruz.
        zamanlayiciBaslat(30000);

        //Tıklana "Yeniden" butonunu gizliyoruz.
        view.setVisibility(View.INVISIBLE);

        //Köstebeklere onClick fonksiyonunu ekliyoruz.
        kostebekOnClickEkle();

        btnDuraklat.setVisibility(View.VISIBLE);
    }

    //Duraklat/Devam Et butonunun onClick fonksiyonu
    public void duraklatDevamEt(View view){
        //Tıklanan nesnenin buton olduğunu belirtiyoruz.
        Button tiklananNesne = (Button) view;

        //Butonun yazısını çekiyoruz, yazıya göre işlem yapacağız.
        String komut= (String) tiklananNesne.getText();

        //Duraklat komutu istenmişse
        if(komut.equals("Duraklat")){
            //Geriye sayan sayacımızı durduruyoruz.
            zamanlayici.cancel();
            //Rastgele köstebek gösteren fonksiyonu da durduruyoruz.
            handler.removeCallbacks(runnable);
            //Köstebeklere ait onClick fonksiyonunu da kaldırıyoruz.
            kostebekOnClickKaldir();
            //Butonun yazısını "Devam Et" olarak değiştiriyoruz.
            tiklananNesne.setText("Devam Et");
        }else{
            //Köstebeklere ait onClick fonksiyonunu ekliyoruz.
            kostebekOnClickEkle();
            //Geriye sayan sayacımızı başlatıyoruz.
            zamanlayiciBaslat(kalanZaman);
            //Butonun yazısını "Duraklat olarak değiştiriyoruz.
            tiklananNesne.setText("Duraklat");
        }
    }
}

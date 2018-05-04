package com.example.hoxyu.team3_weatherapp_final;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SlashCreenActivity extends AppCompatActivity {
    TextView  txt_tencongty, txt_tai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash_creen);
        Controls();
        try {
            PackageInfo packageInfo=getPackageManager().getPackageInfo(getPackageName(),0);
            //txt_phienban.setText("Phiên bản: "+packageInfo.versionName);
            txt_tencongty.setText(getString(R.string.namecongty));
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(SlashCreenActivity.this,Detail_Weather_Activity.class);
                    startActivity(intent);
                    finish();
                }
            },2000);
            //Intent intent=new Intent(FlashScreenActivity.this,DangNhapActivity.class);
            //startActivity(intent);
            //Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void Controls() {
        txt_tai=findViewById(R.id.txt_dangtai);
        txt_tencongty=findViewById(R.id.txt_tencongty);
    }
}

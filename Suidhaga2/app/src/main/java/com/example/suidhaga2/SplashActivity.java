package com.example.suidhaga2;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suidhaga2.loginStatus.login_status;

public class SplashActivity extends AppCompatActivity {
    ImageView imgview;
    AnimatorSet set;
    public final static int SPLASH_TIMEOUT = 4000;
    login_status status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int versionCode=BuildConfig.VERSION_CODE;
        String versionName=BuildConfig.VERSION_NAME;
        String url="http://103.255.232.8/Apprise_Versions/"+versionName+".apk";
        setContentView(R.layout.splash_activity);
        status = new login_status(getApplicationContext());
        imgview = findViewById(R.id.imageview);
        //set = (AnimatorSet) AnimatorInflater.loadAnimator(SplashActivity.this, R.animator.flip);
        //set.setTarget(imgview);
        //set.start();
        //Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        //imgview.startAnimation(aniRotate);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (haveNetwork()) {
                    if (status.readlogin_status()) {
                        startActivity(new Intent(SplashActivity.this, PinLock.class));
                        finish();
                    } else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                    //Toast.makeText(SplashActivity.this, "Working", Toast.LENGTH_SHORT).show();

                } else if (!haveNetwork()) {
                    Toast.makeText(SplashActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(SplashActivity.this,InternetConnectivity.class));
                    finish();

                }


            }

        }, SPLASH_TIMEOUT);
    }

    private boolean haveNetwork() {
        boolean have_WIFI = false;
        boolean have_mobileData = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for (NetworkInfo netInfo : networkInfos) {
            if (netInfo.getTypeName().equalsIgnoreCase("WIFI")) {
                if (netInfo.isConnected()) {
                    have_WIFI = true;
                }
            } else if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))
                if (netInfo.isConnected()) {
                    have_mobileData = true;
                }
        }
        return have_mobileData || have_WIFI;

    }
}
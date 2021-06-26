package com.example.GestionDeRessource;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo,slogan;

    private static int SPLASH = 2500;

    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_activity);
        fa = this;
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim =  AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SessionManager session = new SessionManager(MainActivity.this);
                Boolean isLogin = session.checkLogin();
                if(isLogin==false)
                {
                    Intent intent = new Intent(MainActivity.this,Login.class);
                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View,String>(image,"logo_image");
                    pairs[1] = new Pair<View,String>(logo,"logo_text");

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                        startActivity(intent,options.toBundle());
                    }
                }
                else if(isLogin==true)
                {
                    HashMap<String,String> h = session.getUsersDetailsFromSession();
                    String type = h.get("type");

                    if(type.equals("1"))
                    {
                        Intent intent = new Intent(MainActivity.this,DashAdmin.class);
                        startActivity(intent);
                    }
                    else if(type.equals("0"))
                    {
                        Intent intent3 = new Intent(MainActivity.this,DashUser.class);
                        startActivity(intent3);
                    }

                }


            }
        },SPLASH);

    }
}
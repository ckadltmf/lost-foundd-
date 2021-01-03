package com.example.myapplication2.SidePages;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.R;


public class Credits extends AppCompatActivity {

    private Button credits;
    private TextView textView;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_credits);
        textView = findViewById(R.id.Credits);
        textView.setText("Authors \n" + "\nNiv Tal\n" + "\nAvi Haimov\n"+"\nRotem Levy\n"+"\n\nAriel University Project\n"+"\nVersion: 1.0\n"+"\nRelease Date: 13/12/2020\n");

        textView.startAnimation(animation);
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 17000);
    }

}

package com.example.myapplication2;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


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
        textView.setText("Authors \n" + "Niv Tal\n" + "Avi Haimov\n"+"Rotem Levy\n"+"Ariel University Project\n"+"Version: 1.0\n"+"Release Date: 13/12/2020\n");

        textView.startAnimation(animation);

    }

}

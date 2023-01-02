package com.example.brightminds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.brightminds.activities.MainActivity;

public class splashActivity extends AppCompatActivity
{
    TextView textbright, textnote;
    RelativeLayout relativeLayout;
    Animation textAnimation, layoutAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textAnimation = AnimationUtils.loadAnimation(splashActivity.this, R.anim.fall_down);
        layoutAnimation = AnimationUtils.loadAnimation(splashActivity.this, R.anim.bottom_to_top);

        textbright = findViewById(R.id.textbright);
        textnote = findViewById(R.id.textnote);
        relativeLayout = findViewById(R.id.relMain);

        new Handler().postDelayed(() -> {
            relativeLayout.setVisibility(View.VISIBLE);
            relativeLayout.setAnimation(layoutAnimation);

            new Handler().postDelayed(() -> {
                textbright.setVisibility(View.VISIBLE);
                textnote.setVisibility(View.VISIBLE);
                textbright.setAnimation(textAnimation);
                textnote.setAnimation(textAnimation);
            }, 900);
        }, 500);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(splashActivity.this ,MainActivity.class);
            startActivity(intent);
        }, 6000);
    }
}
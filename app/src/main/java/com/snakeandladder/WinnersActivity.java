package com.snakeandladder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class WinnersActivity extends AppCompatActivity {

    private ImageView Share, Back;
    private KonfettiView KonfettiView;
    private TextView Player_Name;

    private String Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winners);

        Intent intent = getIntent();
        Name = intent.getStringExtra("won");

        KonfettiView = findViewById(R.id.konfettiView);
        Back = findViewById(R.id.back);
        Share = findViewById(R.id.share);
        Player_Name = findViewById(R.id.player_name);

        Player_Name.setText(Name);

        KonfettiView.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.BLUE, Color.RED)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(5000L)
                .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                .addSizes(new Size(12, 5f))
                .setPosition(KonfettiView.getX(), Float.valueOf(KonfettiView.getWidth()/2), KonfettiView.getY(), Float.valueOf(KonfettiView.getHeight()/2))
                .streamFor(300, 5000L);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WinnersActivity.this, SelectPlayersActivity.class));
                finish();
            }
        });

        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey, Play Snake and Ladder Game at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                startActivity(new Intent(WinnersActivity.this, SelectPlayersActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        WinnersActivity.super.onBackPressed();
        finish();
    }
}
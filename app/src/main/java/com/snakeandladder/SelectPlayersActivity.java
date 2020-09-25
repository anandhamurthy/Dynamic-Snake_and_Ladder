package com.snakeandladder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SelectPlayersActivity extends AppCompatActivity {

    private ImageView Ladder, Snake;
    private TextView One, Two, Three;

    private RelativeLayout Select_Players;

    private Animation Top_Animation, Bottom_Animation, Right_Animation, Left_Animation;

    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_players);

        soundPlayer = new SoundPlayer(this);

        One = findViewById(R.id.one);
        Two = findViewById(R.id.two);
        Three = findViewById(R.id.three);
        Select_Players = findViewById(R.id.select_players_text);
        Snake = findViewById(R.id.snake);
        Ladder = findViewById(R.id.ladder);

        Top_Animation = AnimationUtils.loadAnimation(SelectPlayersActivity.this, R.anim.top_animation);
        Bottom_Animation = AnimationUtils.loadAnimation(SelectPlayersActivity.this, R.anim.bottom_animation);
        Right_Animation = AnimationUtils.loadAnimation(SelectPlayersActivity.this, R.anim.right_animation);
        Left_Animation = AnimationUtils.loadAnimation(SelectPlayersActivity.this, R.anim.left_animation);

        Ladder.setBackgroundResource(R.drawable.continuous_animation_ladder);
        Snake.setBackgroundResource(R.drawable.continuous_animation_snake);

        AnimationDrawable frameAnimation = (AnimationDrawable) Snake.getBackground();
        frameAnimation.start();

        AnimationDrawable ladderframeAnimation = (AnimationDrawable) Ladder.getBackground();
        ladderframeAnimation.start();

        Ladder.setAnimation(Top_Animation);
        Snake.setAnimation(Right_Animation);
        Select_Players.setAnimation(Top_Animation);
        One.setAnimation(Left_Animation);
        Two.setAnimation(Bottom_Animation);
        Three.setAnimation(Right_Animation);

        One.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPlayer.Play_Move_Sound();
                Intent intent = new Intent(SelectPlayersActivity.this, SinglePlayerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPlayer.Play_Move_Sound();
                Intent intent = new Intent(SelectPlayersActivity.this, DualPlayerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPlayer.Play_Move_Sound();
                Intent intent = new Intent(SelectPlayersActivity.this, TriPlayerActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SelectPlayersActivity.this, WelcomeActivity.class));
        finish();
    }
}
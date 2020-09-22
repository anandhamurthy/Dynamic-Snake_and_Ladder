package com.snakeandladder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView Settings, Profile, Music, Ladder, Snake;
    private TextView Play;
    private RelativeLayout Play_Button;

    private Animation Top_Animation, Bottom_Animation, Right_Animation, Left_Animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Settings = findViewById(R.id.settings);
        Music = findViewById(R.id.music);
        Profile = findViewById(R.id.profile);
        Play = findViewById(R.id.play);
        Play_Button = findViewById(R.id.play_button);
        Snake = findViewById(R.id.snake);

        Ladder = findViewById(R.id.ladder);
        Top_Animation = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.top_animation);
        Bottom_Animation = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.bottom_animation);
        Right_Animation = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.right_animation);
        Left_Animation = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.left_animation);

        Ladder.setBackgroundResource(R.drawable.continuous_animation_ladder);
        Snake.setBackgroundResource(R.drawable.continuous_animation_snake);

        AnimationDrawable frameAnimation = (AnimationDrawable) Snake.getBackground();
        frameAnimation.start();

        AnimationDrawable ladderframeAnimation = (AnimationDrawable) Ladder.getBackground();
        ladderframeAnimation.start();

        Ladder.setAnimation(Top_Animation);
        Snake.setAnimation(Right_Animation);
        Play_Button.setAnimation(Bottom_Animation);

        Settings.setAnimation(Right_Animation);
        Profile.setAnimation(Right_Animation);
        Music.setAnimation(Right_Animation);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                make_directory(Details.app_folder);
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, SettingsActivity.class));
            }
        });
        if (!Music_Of_User()) {
            Music.setImageResource(R.drawable.music_off);
        }else{
            Music.setImageResource(R.drawable.music_on);
        }

        Music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Music_Of_User()) {
                    Music.setImageResource(R.drawable.music_off);
                    savePrefsData(false);
                }else{
                    Music.setImageResource(R.drawable.music_on);
                    savePrefsData(true);
                }
            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, ProfileActivity.class));
            }
        });

        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, SelectPlayersActivity.class);
                intent.putExtra("music",Music_Of_User());
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean Music_Of_User() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isMusic",false);
        return  isIntroActivityOpnendBefore;
    }

    private void savePrefsData(boolean music_needed) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isMusic",music_needed);
        editor.commit();

    }

    private void make_directory(String path){
        File dir = new File(path);
        if(!dir.exists())
        {
            dir.mkdir();


        }
    }

}
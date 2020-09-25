package com.snakeandladder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class SinglePlayerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private TextView Roll, Player_1_Place, Player_1_Name;
    private ImageView Dice;
    private CircleImageView Player_1_Image;

    int present=0;

    int a[][]= new int[100][7];

    int k=0;

    boolean isPlaying=false;

    private ArrayList<Integer> LadderValue, SnakeValue, LadderPlace, SnakePlace;

    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        soundPlayer = new SoundPlayer(this);
        if (Music_Of_User()) {
            soundPlayer.playBGM();
        }

        LadderValue = new ArrayList<>();
        LadderPlace = new ArrayList<>();

        SnakePlace = new ArrayList<>();
        SnakeValue = new ArrayList<>();

        for(int i=1;i<=10;i++){
            if (i%2!=0){
                for (int j=1;j<=10;j++,k++){
                    a[k][0]=i*j;
                    a[k][1]=0;
                    a[k][2]=0;
                    a[k][3]=0;
                    a[k][4]=0;
                    a[k][5]=0;
                }
            }else{
                for (int j=10;j>=1;j--,k++){
                    a[k][0]=i*j;
                    a[k][1]=0;
                    a[k][2]=0;
                    a[k][3]=0;
                    a[k][4]=0;
                    a[k][5]=0;
                }
            }
        }

        Dice = findViewById(R.id.dice);
        Player_1_Place = findViewById(R.id.player_1_place);
        Player_1_Name = findViewById(R.id.player_1_name);
        Roll=findViewById(R.id.roll);
        Player_1_Image = findViewById(R.id.player_1_image);

        File profile = new File(Details.profile_image);
        if (profile.exists()){
            Player_1_Image.setImageDrawable(Drawable.createFromPath(profile.getPath()));
        }

        Roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying)
                {
                    Random random=new Random();
                    int g=random.nextInt(5)+1;//generate random no.
                    soundPlayer.Play_Dice_Sound();
                    changeDice(g);
                    player(g);
                    removeOldLadderSnake();
                    getNewLadder();
                    getNewSnake();
                }

            }
        });
        if (getPlayer1Color()){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
            int player1color = pref.getInt("player_1_color",0);
            Player_1_Name.setTextColor(player1color);
            Drawable myIcon = getResources().getDrawable( R.drawable.rounded);
            myIcon.setColorFilter(player1color, android.graphics.PorterDuff.Mode.SRC_IN);
            Player_1_Place.setTextColor(getResources().getColor(R.color.white));
            Player_1_Place.setBackground(myIcon);
        }
        if (getPlayer1diceColor()){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
            int player1dicecolor = pref.getInt("player_1_dice_color",0);
            Dice.setColorFilter(player1dicecolor, android.graphics.PorterDuff.Mode.SRC_IN);
        }
//        endbtn=findViewById(R.id.btn_end);
//        endbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(SinglePlayerActivity.this,"Game Ends",Toast.LENGTH_SHORT).show();
//                isPlaying=false;
//                a[present-1][1]=0;
//                recyclerView.getAdapter().notifyDataSetChanged();
//            }
//        });

        recyclerView=findViewById(R.id.board);
        SinglePlayerAdapter adapter= new SinglePlayerAdapter(SinglePlayerActivity.this,a,getDrawable(R.drawable.icon));
        recyclerView.setAdapter(adapter);
        getNewLadder();
        getNewSnake();
        startGame();
    }
    private void startGame(){
        isPlaying=true;
        present=1;
        //Toast.makeText(this, "Start Playing", Toast.LENGTH_SHORT).show();
        changeFocus(present);
    }
    private void player(int dieAdd){
        if((present+dieAdd)<=100){
            //To keep the present yellow
            a[present-1][1]=0;
            recyclerView.getAdapter().notifyDataSetChanged();
            present=present+dieAdd;
            Player_1_Place.setText(present+"");
            soundPlayer.Play_Move_Sound();
            for (int i=0;i<LadderPlace.size();i++){
                if (LadderPlace.get(i)==present){

                    getSnakeLadder("ladder");
                    soundPlayer.Play_Ladder_Sound();
                    Toast.makeText(this, "Ladder = "+LadderValue.get(i), Toast.LENGTH_SHORT).show();
                    present+=LadderValue.get(i);
                }
            }
            for (int i=0;i<SnakePlace.size();i++){
                if (SnakePlace.get(i)==present){

                    getSnakeLadder("snake");
                    soundPlayer.Play_Snake_Sound();
                    Toast.makeText(this, "Snake = "+SnakeValue.get(i), Toast.LENGTH_SHORT).show();
                    present-=SnakeValue.get(i);
                }
            }
            Player_1_Place.setText(present+"");
            switch(present) {
                case 100:
                    gameWon();
            }
            changeFocus(present);
        }
    }

    private void getSnakeLadder(String key) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SinglePlayerActivity.this, R.style.LadderSnake);
        View mView = getLayoutInflater().inflate(R.layout.fragment_animation, null);

        LottieAnimationView anim = (LottieAnimationView) mView.findViewById(R.id.animationView);
        if (key.equals("snake"))
            anim.setAnimation(R.raw.snake);
        else
            anim.setAnimation(R.raw.ladder);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        };

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, 5000);

    }


    private void gameWon(){
        a[present-1][1]=0;
        recyclerView.getAdapter().notifyDataSetChanged();
        isPlaying=false;
        soundPlayer.Play_Victory();
        Toast.makeText(this, "You Won", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Player 1 Won", Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(SinglePlayerActivity.this, WinnersActivity.class);
        intent1.putExtra("won","You Won!");
        startActivity(intent1);
    }
    private void changeFocus(int position){
        a[position-1][1]=1;
        recyclerView.getAdapter().notifyDataSetChanged();
    }


    private void changeDice(int digit) {
        switch (digit) {
            case 1:
                Dice.setImageResource(R.drawable.dice_1);
                break;
            case 2:
                Dice.setImageResource(R.drawable.dice_2);
                break;
            case 3:
                Dice.setImageResource(R.drawable.dice_3);
                break;
            case 4:
                Dice.setImageResource(R.drawable.dice_4);
                break;
            case 5:
                Dice.setImageResource(R.drawable.dice_5);
                break;
            case 6:
                Dice.setImageResource(R.drawable.dice_6);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        soundPlayer.stopBGM();
    }

    private boolean getPlayer1Color() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
        int player1color = pref.getInt("player_1_color",0);
        if (player1color!=0)
            return true;
        else
            return false;
    }

    private boolean getPlayer1diceColor() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
        int player1dicecolor = pref.getInt("player_1_dice_color",0);
        if (player1dicecolor!=0)
            return true;
        else
            return false;
    }

    private void getNewLadder(){

        for(int i=0;i<10;i++){
            Random random=new Random();
            int ladder_place = random.nextInt(97-11) + 11;
            int ladder_value = random.nextInt(5) + 1;
            a[ladder_place][2]=1;
            a[ladder_place][3]=ladder_value;
            LadderValue.add(ladder_value);
            LadderPlace.add(ladder_place);
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    private void getNewSnake(){
        for(int i=0;i<10;i++){
            Random random=new Random();
            int snake_place = random.nextInt(97-11) + 11;
            int snake_value = random.nextInt(5) + 1;
            if (LadderPlace.size()!=0){
                if (!LadderPlace.contains(snake_place)){
                    a[snake_place][4]=1;
                    a[snake_place][5]=snake_value;
                    SnakeValue.add(snake_value);
                    SnakePlace.add(snake_place);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }else{
                a[snake_place][4]=1;
                a[snake_place][5]=snake_value;
                SnakeValue.add(snake_value);
                SnakePlace.add(snake_place);
                recyclerView.getAdapter().notifyDataSetChanged();
            }

        }
    }

    private void removeOldLadderSnake(){
        LadderValue.clear();
        LadderPlace.clear();
        SnakePlace.clear();
        SnakeValue.clear();
        int f=0;
        for(int i=1;i<=10;i++){
            if (i%2!=0){
                for (int j=1;j<=10;j++,f++){
                    a[f][2]=0;
                    a[f][3]=0;

                    a[f][4]=0;
                    a[f][5]=0;
                }
            }else{
                for (int j=10;j>=1;j--,f++){
                    a[f][2]=0;
                    a[f][3]=0;

                    a[f][4]=0;
                    a[f][5]=0;
                }
            }
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        startActivity(new Intent(SinglePlayerActivity.this, SelectPlayersActivity.class));
                        finish();
                    }
                }).create().show();
    }

    private boolean Music_Of_User() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isMusic",true);
        return  isIntroActivityOpnendBefore;
    }

}
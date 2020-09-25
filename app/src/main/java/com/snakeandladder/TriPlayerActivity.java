package com.snakeandladder;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class TriPlayerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CircleImageView Player_1_Image, Player_2_Image, Player_3_Image;
    private TextView Roll_1, Roll_2, Roll_3, Player_1_Place, Player_2_Place, Player_3_Place, Player_1_Name, Player_2_Name, Player_3_Name;
    private ImageView Dice;

    int present_1=0, present_2=0, present_3=0;
    int a[][]= new int[100][10];
    int k=0;
    boolean isPlaying_1=false, isPlaying_2=false, isPlaying_3=false;

    private SoundPlayer soundPlayer;

    private ArrayList<Integer> LadderValue, SnakeValue, LadderPlace, SnakePlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tri_player);

        soundPlayer = new SoundPlayer(this);
        if (Music_Of_User()){

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

                    a[k][6]=0;
                    a[k][7]=0;
                }
            }else{
                for (int j=10;j>=1;j--,k++){
                    a[k][0]=i*j;
                    a[k][1]=0;
                    a[k][2]=0;
                    a[k][3]=0;

                    a[k][4]=0;
                    a[k][5]=0;

                    a[k][6]=0;
                    a[k][7]=0;
                }
            }
        }

        Dice = findViewById(R.id.dice);

        Player_1_Name = findViewById(R.id.player_1_name);
        Player_2_Name = findViewById(R.id.player_2_name);
        Player_3_Name = findViewById(R.id.player_3_name);

        Player_1_Place = findViewById(R.id.player_1_place);
        Player_2_Place = findViewById(R.id.player_2_place);
        Player_3_Place = findViewById(R.id.player_3_place);

        Player_1_Image = findViewById(R.id.player_1_image);
        Player_2_Image = findViewById(R.id.player_2_image);
        Player_3_Image = findViewById(R.id.player_3_image);

        Roll_1 = findViewById(R.id.roll_1);
        Roll_2 = findViewById(R.id.roll_2);
        Roll_3 = findViewById(R.id.roll_3);

        File profile = new File(Details.profile_image);
        if (profile.exists()){
            Player_1_Image.setImageDrawable(Drawable.createFromPath(profile.getPath()));
        }

        if (getPlayer1Color()){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
            int player1color = pref.getInt("player_1_color",0);
            Player_1_Name.setTextColor(player1color);
            Drawable myIcon = getResources().getDrawable( R.drawable.rounded);
            myIcon.setColorFilter(player1color, android.graphics.PorterDuff.Mode.SRC_IN);
            Player_1_Place.setTextColor(getResources().getColor(R.color.white));
            Player_1_Place.setBackground(myIcon);
        }

        if (getPlayer2Color()){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
            int player2color = pref.getInt("player_2_color",0);
            Player_2_Name.setTextColor(player2color);
            Drawable myIcon = getResources().getDrawable( R.drawable.rounded);
            myIcon.setColorFilter(player2color, android.graphics.PorterDuff.Mode.SRC_IN);
            Player_2_Place.setTextColor(getResources().getColor(R.color.white));
            Player_2_Place.setBackground(myIcon);
        }

        if (getPlayer3Color()){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
            int player3color = pref.getInt("player_3_color",0);
            Player_3_Name.setTextColor(player3color);
            Drawable myIcon = getResources().getDrawable( R.drawable.rounded);
            myIcon.setColorFilter(player3color, android.graphics.PorterDuff.Mode.SRC_IN);
            Player_3_Place.setTextColor(getResources().getColor(R.color.white));
            Player_3_Place.setBackground(myIcon);
        }

        if (getPlayer1Name()){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
            String player2name = pref.getString("player_1_name","Player 1");
            Player_1_Name.setText(player2name);

        }

        if (getPlayer2Name()){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
            String player2name = pref.getString("player_2_name","Player 2");
            Player_2_Name.setText(player2name);

        }

        if (getPlayer3Name()){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
            String player3name = pref.getString("player_3_name","Player 3");
            Player_3_Name.setText(player3name);
        }

        Roll_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying_1) {
                    Random random=new Random();
                    int g=random.nextInt(5)+1;//generate random no.
                    soundPlayer.Play_Dice_Sound();
                    changeDice(g);
                    player_1(g);

                    if (getPlayer2Color()){
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
                        int player2color = pref.getInt("player_2_color",0);
                        Player_2_Name.setTextColor(player2color);
                        Drawable myIcon = getResources().getDrawable( R.drawable.rounded);
                        myIcon.setColorFilter(player2color, android.graphics.PorterDuff.Mode.SRC_IN);
                        Player_2_Place.setTextColor(getResources().getColor(R.color.white));
                        Player_2_Place.setBackground(myIcon);
                        Dice.setColorFilter(player2color, android.graphics.PorterDuff.Mode.SRC_IN);
                    }else{
                        Player_2_Name.setTextColor(getResources().getColor(R.color.colorBlack));
                    }

                    Player_1_Name.setTextColor(getResources().getColor(R.color.colorGrey));
                    Player_3_Name.setTextColor(getResources().getColor(R.color.colorGrey));
                }
                Roll_1.setVisibility(View.GONE);
                Roll_3.setVisibility(View.GONE);
                Roll_2.setVisibility(View.VISIBLE);
                if (getPlayer2Color()){
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
                    int player2color = pref.getInt("player_2_color",0);
                    Dice.setColorFilter(player2color, android.graphics.PorterDuff.Mode.SRC_IN);
                }
                removeOldLadderSnake();
                getNewLadder();
                getNewSnake();
            }
        });

        Roll_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying_2) {
                    Random random=new Random();
                    int g=random.nextInt(5)+1;//generate random no.
                    soundPlayer.Play_Dice_Sound();
                    changeDice(g);
                    player_2(g);

                    if (getPlayer3Color()){
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
                        int player3color = pref.getInt("player_3_color",0);
                        Player_3_Name.setTextColor(player3color);
                        Drawable myIcon = getResources().getDrawable( R.drawable.rounded);
                        myIcon.setColorFilter(player3color, android.graphics.PorterDuff.Mode.SRC_IN);
                        Player_3_Place.setTextColor(getResources().getColor(R.color.white));
                        Player_3_Place.setBackground(myIcon);
                    }else{
                        Player_3_Name.setTextColor(getResources().getColor(R.color.colorBlack));
                    }

                    Player_2_Name.setTextColor(getResources().getColor(R.color.colorGrey));
                    Player_1_Name.setTextColor(getResources().getColor(R.color.colorGrey));
                }

                Roll_2.setVisibility(View.GONE);
                Roll_1.setVisibility(View.GONE);
                Roll_3.setVisibility(View.VISIBLE);
                if (getPlayer3Color()){
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
                    int player3color = pref.getInt("player_3_color",0);
                    Dice.setColorFilter(player3color, android.graphics.PorterDuff.Mode.SRC_IN);
                }
                removeOldLadderSnake();
                getNewLadder();
                getNewSnake();

            }
        });

        Roll_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying_3) {
                    Random random=new Random();
                    int g=random.nextInt(5)+1;//generate random no.
                    soundPlayer.Play_Dice_Sound();
                    changeDice(g);
                    player_3(g);

                    if (getPlayer1Color()){
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
                        int player1color = pref.getInt("player_1_color",0);
                        Player_1_Name.setTextColor(player1color);
                        Drawable myIcon = getResources().getDrawable( R.drawable.rounded);
                        myIcon.setColorFilter(player1color, android.graphics.PorterDuff.Mode.SRC_IN);
                        Player_1_Place.setTextColor(getResources().getColor(R.color.white));
                        Player_1_Place.setBackground(myIcon);
                    }else{
                        Player_1_Name.setTextColor(getResources().getColor(R.color.colorBlack));
                    }

                    Player_2_Name.setTextColor(getResources().getColor(R.color.colorGrey));
                    Player_3_Name.setTextColor(getResources().getColor(R.color.colorGrey));
                }

                Roll_2.setVisibility(View.GONE);
                Roll_3.setVisibility(View.GONE);
                Roll_1.setVisibility(View.VISIBLE);
                if (getPlayer1diceColor()){
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
                    int player1dicecolor = pref.getInt("player_1_dice_color",0);
                    Dice.setColorFilter(player1dicecolor, android.graphics.PorterDuff.Mode.SRC_IN);
                }
                removeOldLadderSnake();
                getNewLadder();
                getNewSnake();

            }
        });

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
        TriPlayerAdapter adapter= new TriPlayerAdapter(TriPlayerActivity.this,a,getDrawable(R.drawable.icon));
        recyclerView.setAdapter(adapter);
        getNewLadder();
        getNewSnake();
        startGame();
    }
    private void startGame(){
        isPlaying_1=true;
        Player_1_Name.setTextColor(getResources().getColor(R.color.colorBlack));
        Player_2_Name.setTextColor(getResources().getColor(R.color.colorGrey));
        Player_3_Name.setTextColor(getResources().getColor(R.color.colorGrey));
        present_1=1;
        present_2=1;
        present_3=1;
        Toast.makeText(this, "Start Playing", Toast.LENGTH_SHORT).show();
        changeFocus_1(present_1);
    }
    private void player_1(int dieAdd){
        if((present_1+dieAdd)<=100){
            a[present_1-1][1]=0;
            recyclerView.getAdapter().notifyDataSetChanged();
            present_1=present_1+dieAdd;
            Player_1_Place.setText(present_1+"");
            soundPlayer.Play_Move_Sound();
            for (int i=0;i<LadderPlace.size();i++){
                if (LadderPlace.get(i)==present_1){
                    getSnakeLadder("ladder");
                    soundPlayer.Play_Ladder_Sound();
                    Toast.makeText(this, "Ladder = "+LadderValue.get(i), Toast.LENGTH_SHORT).show();
                    present_1+=LadderValue.get(i);
                }
            }
            for (int i=0;i<SnakePlace.size();i++){
                if (SnakePlace.get(i)==present_1){
                    getSnakeLadder("snake");
                    soundPlayer.Play_Snake_Sound();
                    Toast.makeText(this, "Snake = "+SnakeValue.get(i), Toast.LENGTH_SHORT).show();
                    present_1-=SnakeValue.get(i);
                }
            }
            Player_1_Place.setText(present_1+"");
            switch(present_1) {
                case 100:
                    gameWon("player_1");
            }
            changeFocus_1(present_1);
            isPlaying_2=true;
        }
    }
    private void player_2(int dieAdd){
        if((present_2+dieAdd)<=100){
            a[present_2-1][2]=0;
            recyclerView.getAdapter().notifyDataSetChanged();
            present_2=present_2+dieAdd;
            Player_2_Place.setText(present_2+"");
            soundPlayer.Play_Move_Sound();
            for (int i=0;i<LadderPlace.size();i++){
                if (LadderPlace.get(i)==present_2){
                    getSnakeLadder("ladder");
                    soundPlayer.Play_Ladder_Sound();
                    Toast.makeText(this, "Ladder = "+LadderValue.get(i), Toast.LENGTH_SHORT).show();
                    present_2+=LadderValue.get(i);
                }
            }
            for (int i=0;i<SnakePlace.size();i++){
                if (SnakePlace.get(i)==present_2){
                    getSnakeLadder("snake");
                    soundPlayer.Play_Snake_Sound();
                    Toast.makeText(this, "Snake = "+SnakeValue.get(i), Toast.LENGTH_SHORT).show();
                    present_2-=SnakeValue.get(i);
                }
            }
            Player_2_Place.setText(present_2+"");
            switch(present_2) {
                case 100:
                    gameWon("player_2");
            }
            changeFocus_2(present_2);
            isPlaying_3=true;
        }
    }

    private void player_3(int dieAdd){
        if((present_3+dieAdd)<=100){
            a[present_3-1][3]=0;
            recyclerView.getAdapter().notifyDataSetChanged();
            present_3=present_3+dieAdd;
            Player_3_Place.setText(present_3+"");
            soundPlayer.Play_Move_Sound();
            for (int i=0;i<LadderPlace.size();i++){
                if (LadderPlace.get(i)==present_3){
                    getSnakeLadder("ladder");
                    soundPlayer.Play_Ladder_Sound();
                    Toast.makeText(this, "Ladder = "+LadderValue.get(i), Toast.LENGTH_SHORT).show();
                    present_3+=LadderValue.get(i);
                }
            }
            for (int i=0;i<SnakePlace.size();i++){
                if (SnakePlace.get(i)==present_3){
                    getSnakeLadder("snake");
                    soundPlayer.Play_Snake_Sound();
                    Toast.makeText(this, "Snake = "+SnakeValue.get(i), Toast.LENGTH_SHORT).show();
                    present_3-=SnakeValue.get(i);
                }
            }
            Player_3_Place.setText(present_3+"");
            switch(present_3) {
                case 100:
                    gameWon("player_2");
            }
            changeFocus_3(present_3);
            isPlaying_1=true;
        }
    }

    private void gameWon(String key){
        a[present_1-1][1]=0;
        a[present_2-1][2]=0;
        a[present_2-1][3]=0;
        recyclerView.getAdapter().notifyDataSetChanged();
        isPlaying_1=false;isPlaying_2=false;isPlaying_3=false;
        if (key.equals("player_1")) {
            Toast.makeText(this, "Player 1 Won", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(TriPlayerActivity.this, WinnersActivity.class);
            intent1.putExtra("won","Player 1 Won!");
            startActivity(intent1);
        }else if(key.equals("player_2")) {
            Toast.makeText(this, "Player 2 Won", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(TriPlayerActivity.this, WinnersActivity.class);
            intent1.putExtra("won","Player 2 Won!");
            startActivity(intent1);
        }else{
            Toast.makeText(this, "Player 3 Won", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(TriPlayerActivity.this, WinnersActivity.class);
            intent1.putExtra("won","Player 3 Won!");
            startActivity(intent1);
        }

    }
    private void changeFocus_1(int position){
        a[position-1][1]=1;
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void changeFocus_2(int position){
        a[position-1][2]=1;
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void changeFocus_3(int position){
        a[position-1][3]=1;
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

    private boolean getPlayer2Color() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
        int player2color = pref.getInt("player_2_color",0);
        if (player2color!=0)
            return true;
        else
            return false;
    }

    private boolean getPlayer3Color() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
        int player3color = pref.getInt("player_3_color",0);
        if (player3color!=0)
            return true;
        else
            return false;
    }

    private boolean getPlayer3Name() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
        String player3name = pref.getString("player_3_name","");
        if (!player3name.isEmpty())
            return true;
        else
            return false;
    }

    private boolean getPlayer2Name() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
        String player2name = pref.getString("player_2_name","");
        if (!player2name.isEmpty())
            return true;
        else
            return false;
    }

    private boolean getPlayer1Name() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
        String player1name = pref.getString("player_1_name","");
        if (!player1name.isEmpty())
            return true;
        else
            return false;
    }

    private void getNewLadder(){

        for(int i=0;i<10;i++){
            Random random=new Random();
            int ladder_place = random.nextInt(97-11) + 11;
            int ladder_value = random.nextInt(5) + 1;
            a[ladder_place][4]=1;
            a[ladder_place][5]=ladder_value;
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
            a[snake_place][6]=1;
            a[snake_place][7]=snake_value;
            SnakeValue.add(snake_value);
            SnakePlace.add(snake_place);
            recyclerView.getAdapter().notifyDataSetChanged();
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
                    a[f][4]=0;
                    a[f][5]=0;

                    a[f][6]=0;
                    a[f][7]=0;
                }
            }else{
                for (int j=10;j>=1;j--,f++){
                    a[f][4]=0;
                    a[f][5]=0;

                    a[f][6]=0;
                    a[f][7]=0;
                }
            }
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void getSnakeLadder(String key) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(TriPlayerActivity.this, R.style.LadderSnake);
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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        startActivity(new Intent(TriPlayerActivity.this, SelectPlayersActivity.class));
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
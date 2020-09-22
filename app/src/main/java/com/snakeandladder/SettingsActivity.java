package com.snakeandladder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import petrov.kristiyan.colorpicker.ColorPicker;

import static android.content.ContentValues.TAG;

public class SettingsActivity extends AppCompatActivity {

    private ImageView Player_2_Name_Edit, Player_3_Name_Edit;
    private ImageView Player_1_Color_Edit, Player_2_Color_Edit, Player_3_Color_Edit;
    private ImageView Player_1_Dice_Color_Edit, Back;
    private TextView Player_2_Name, Player_3_Name;

    private ArrayList<String> ColorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Player_1_Color_Edit = findViewById(R.id.player_1_color_picker);
        Player_1_Dice_Color_Edit = findViewById(R.id.player_1_dice_color_picker);

        Player_2_Color_Edit = findViewById(R.id.player_2_color_picker);
        Player_2_Name_Edit = findViewById(R.id.player_2_name_edit);

        Player_3_Color_Edit = findViewById(R.id.player_3_color_picker);
        Player_3_Name_Edit = findViewById(R.id.player_3_name_edit);

        Player_2_Name = findViewById(R.id.player_2_name);
        Player_3_Name = findViewById(R.id.player_3_name);

        Back = findViewById(R.id.back);

        ColorList = new ArrayList<>();
        SetColor();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getPlayer1Color()){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
            int player1color = pref.getInt("player_1_color",0);
            Player_1_Color_Edit.setBackgroundColor(player1color);
        }

        if (getPlayer2Color()){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
            int player2color = pref.getInt("player_2_color",0);
            Player_2_Color_Edit.setBackgroundColor(player2color);
        }

        if (getPlayer2Name()){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
            String player2name = pref.getString("player_2_name","Player 2");
            Player_2_Name.setText(player2name);
        }

        if (getPlayer3Color()){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
            int player3color = pref.getInt("player_3_color",0);
            Player_3_Color_Edit.setBackgroundColor(player3color);
        }

        if (getPlayer3Name()){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
            String player3name = pref.getString("player_3_name","Player 3");
            Player_3_Name.setText(player3name);
        }

        if (getPlayer1diceColor()){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
            int player1dicecolor = pref.getInt("player_1_dice_color",0);
            Player_1_Dice_Color_Edit.setBackgroundColor(player1dicecolor);
        }

        Player_2_Name_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditPlayer("player2name");
            }
        });

        Player_3_Name_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditPlayer("player3name");
            }
        });

        Player_1_Color_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPicker("player1color");
            }
        });

        Player_2_Color_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPicker("player2color");
            }
        });

        Player_3_Color_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPicker("player3color");
            }
        });

        Player_1_Dice_Color_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPicker("player1dicecolor");
            }
        });


    }

    private void SetColor() {

        ColorList.add("#F73D1F");
        ColorList.add("#F7811F");
        ColorList.add("#F5B041");
        ColorList.add("#22E652");
        ColorList.add("#22D7E6");
        ColorList.add("#2260E6");
        ColorList.add("#BF22E6");
        ColorList.add("#F71F74");
    }

    private void GetPicker(final String key){
        final ColorPicker colorPicker = new ColorPicker(SettingsActivity.this);
        colorPicker.setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
            @Override
            public void setOnFastChooseColorListener(int position, int color) {
                savePrefsColorData(color, key);
            }

            @Override
            public void onCancel() {

            }
        }).setColors(ColorList).setTitle("Select Color").setColumns(5).show();
    }

    private void savePrefsColorData(int color, String key) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (key.equals("player1color"))
            editor.putInt("player_1_color", color);
        else if (key.equals("player2color"))
            editor.putInt("player_2_color", color);
        else if (key.equals("player3color"))
            editor.putInt("player_3_color", color);
        else if (key.equals("player1dicecolor"))
            editor.putInt("player_1_dice_color", color);
        editor.commit();
        recreate();

    }

    private void savePrefsNameData(String name, String key) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (key.equals("player2name"))
            editor.putString("player_2_name", name);
        else if (key.equals("player3name"))
            editor.putString("player_3_name", name);
        editor.commit();
        recreate();

    }

    private boolean getPlayer1Color() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
        int player1color = pref.getInt("player_1_color",0);
        if (player1color!=0)
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

    private boolean getPlayer2Name() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
        String player2name = pref.getString("player_2_name","");
        if (!player2name.isEmpty())
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

    private boolean getPlayer3Color() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
        int player3color = pref.getInt("player_3_color",0);
        if (player3color!=0)
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

    private void getEditPlayer(final String key){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.edit_profile_name_layout, null);
        final EditText mPlayerName = mView.findViewById(R.id.player_name);
        Button mSubmit = mView.findViewById(R.id.submit);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!mPlayerName.getText().toString().isEmpty()){
                    savePrefsNameData(mPlayerName.getText().toString(), key);

                }else{
                    Toast.makeText(SettingsActivity.this, "Enter Player Name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
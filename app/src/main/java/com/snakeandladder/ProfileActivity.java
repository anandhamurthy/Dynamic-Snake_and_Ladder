package com.snakeandladder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView Profile_Image;
    private TextView User_Name;
    private ImageView Edit_Button, Back;

    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Profile_Image = findViewById(R.id.profile_image);
        User_Name = findViewById(R.id.profile_user_name);
        Edit_Button = findViewById(R.id.edit_button);
        Back = findViewById(R.id.back);

        File profile = new File(Details.profile_image);
        if (profile.exists()){
            Profile_Image.setImageDrawable(Drawable.createFromPath(profile.getPath()));
        }

        if (getPlayer1Name()){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
            String player1name = pref.getString("player_1_name","Player Name");
            User_Name.setText(player1name);
        }

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Profile_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1, 1)
                        .start(ProfileActivity.this);
            }
        });


        Edit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditPlayer();

            }
        });
    }


    private void savePrefsData(String player_name) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("player_1_name", player_name);
        editor.commit();
        recreate();

    }

    private void make_profile_image(String path, Bitmap bm){
        File dir = new File(path);
        if(!dir.exists())
        {
            dir.mkdir();

        }else{
            File f = new File(dir+"/profile_image.png");
            try {
                FileOutputStream outStream = new FileOutputStream(f);
                bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                outStream.flush();
                outStream.close();
            } catch (Exception e) { throw new RuntimeException(e); }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {



            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            mImageUri = result.getUri();
            File file = new File(mImageUri.getPath());
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            make_profile_image(Details.app_folder,bitmap);
            recreate();

        } else {
            Toast.makeText(ProfileActivity.this, "Something gone wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getEditPlayer(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfileActivity.this);
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
                    savePrefsData(mPlayerName.getText().toString());

                }else{
                    Toast.makeText(ProfileActivity.this, "Enter Your Name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean getPlayer1Name() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("snakeandladder",MODE_PRIVATE);
        String player1name = pref.getString("player_1_name","");
        if (!player1name.isEmpty())
            return true;
        else
            return false;
    }
}
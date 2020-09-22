package com.snakeandladder;

import android.os.Environment;

public class Details {

    public static final String root = Environment.getExternalStorageDirectory().toString();
    public static final String app_folder = root+"/snakeandladder/";
    public static String profile_image = app_folder + "profile_image.png";
}

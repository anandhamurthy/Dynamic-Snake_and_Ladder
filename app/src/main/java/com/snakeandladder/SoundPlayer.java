package com.snakeandladder;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundPlayer {

    private static SoundPool soundPool;
    private static int Hit_Sound;
    private static int Over_Sound;
    private static MediaPlayer mediaPlayer;

    public SoundPlayer(Context context){

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = MediaPlayer.create(context, R.raw.background);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.7f, 0.7f);

    }

    public void Play_Hit_Sound() {

        soundPool.play(Hit_Sound, 1.0f, 1.0f, 1, 0,1.0f);

    }

    public void Play_Over_Sound() {

        soundPool.play(Over_Sound, 1.0f, 1.0f, 1, 0,1.0f);

    }

    public void playBGM() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void pauseBGM() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void seekToTop() {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(0);
        }
    }
    public void stopBGM() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

}

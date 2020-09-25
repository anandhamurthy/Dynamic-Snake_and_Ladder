package com.snakeandladder;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundPlayer {

    private static SoundPool soundPool;
    private static int Ladder_Sound, Snake_Sound, Dice_Sound, Victory_Sound, Victory_Bg, Move_Sound;
    private static MediaPlayer mediaPlayer;

    public SoundPlayer(Context context){

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        Ladder_Sound = soundPool.load(context, R.raw.ladder_sound, 1);
        Snake_Sound = soundPool.load(context, R.raw.snake_sound, 1);
        Dice_Sound = soundPool.load(context, R.raw.dice, 1);
        Move_Sound = soundPool.load(context, R.raw.move, 1);
        Victory_Sound = soundPool.load(context, R.raw.victory, 1);
        Victory_Bg = soundPool.load(context, R.raw.victory_bg, 1);

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = MediaPlayer.create(context, R.raw.background);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.7f, 0.7f);

    }

    public void Play_Ladder_Sound() {

        soundPool.play(Ladder_Sound, 0.5f, 0.5f, 1, 0,1.0f);

    }

    public void Play_Snake_Sound() {

        soundPool.play(Snake_Sound, 0.5f, 0.5f, 1, 0,1.0f);

    }

    public void Play_Dice_Sound() {

        soundPool.play(Dice_Sound, 0, 0.5f, 1, 0,1.0f);

    }

    public void Play_Victory() {

        soundPool.play(Victory_Sound, 1.0f, 1.0f, 1, 0,1.0f);

    }

    public void Play_Victory_Bg() {

        soundPool.play(Victory_Bg, 1.0f, 1.0f, 1, 0,1.0f);

    }

    public void Play_Move_Sound() {

        soundPool.play(Move_Sound, 0.8f, 0, 1, 0,1.0f);

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

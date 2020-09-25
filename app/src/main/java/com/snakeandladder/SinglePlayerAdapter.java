package com.snakeandladder;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class SinglePlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context contextActivity;
    int array[][];
    Drawable drawable;
    int player1color;
    
    public SinglePlayerAdapter(SinglePlayerActivity singlePlayerActivity, int[][] a, Drawable drawable) {
        contextActivity=singlePlayerActivity;
        array=a;
        this.drawable=drawable;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout one,two,three,four,five, six, seven,eight,nine,ten;
        private TextView one_n,two_n,three_n,four_n,five_n,six_n,seven_n,eight_n,nine_n,ten_n;
        private ImageView one_o,two_o,three_o,four_o,five_o,six_o,seven_o,eight_o,nine_o,ten_o;
        public ViewHolder(View itemView) {
            super(itemView);
            one=itemView.findViewById(R.id.one);
            two=itemView.findViewById(R.id.two);
            three=itemView.findViewById(R.id.three);
            four=itemView.findViewById(R.id.four);
            five=itemView.findViewById(R.id.five);
            six=itemView.findViewById(R.id.six);
            seven=itemView.findViewById(R.id.seven);
            eight=itemView.findViewById(R.id.eight);
            nine=itemView.findViewById(R.id.nine);
            ten=itemView.findViewById(R.id.ten);

            one_n=itemView.findViewById(R.id.block_one);
            two_n=itemView.findViewById(R.id.block_two);
            three_n=itemView.findViewById(R.id.block_three);
            four_n=itemView.findViewById(R.id.block_four);
            five_n=itemView.findViewById(R.id.block_five);
            six_n=itemView.findViewById(R.id.block_six);
            seven_n=itemView.findViewById(R.id.block_seven);
            eight_n=itemView.findViewById(R.id.block_eight);
            nine_n=itemView.findViewById(R.id.block_nine);
            ten_n=itemView.findViewById(R.id.block_ten);

            one_o=itemView.findViewById(R.id.one_1);
            two_o=itemView.findViewById(R.id.one_2);
            three_o=itemView.findViewById(R.id.one_3);
            four_o=itemView.findViewById(R.id.one_4);
            five_o=itemView.findViewById(R.id.one_5);
            six_o=itemView.findViewById(R.id.one_6);
            seven_o=itemView.findViewById(R.id.one_7);
            eight_o=itemView.findViewById(R.id.one_8);
            nine_o=itemView.findViewById(R.id.one_9);
            ten_o=itemView.findViewById(R.id.one_10);
        }

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.single_single_player,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder= (ViewHolder) holder;

        if (getPlayer1Color()){
            SharedPreferences pref = contextActivity.getSharedPreferences("snakeandladder",MODE_PRIVATE);
            player1color = pref.getInt("player_1_color",0);
        }
        
        int numToDisplay=10*position+1;

        ((ViewHolder) holder).one_o.setBackgroundColor(player1color);
        ((ViewHolder) holder).two_o.setBackgroundColor(player1color);
        ((ViewHolder) holder).three_o.setBackgroundColor(player1color);
        ((ViewHolder) holder).four_o.setBackgroundColor(player1color);
        ((ViewHolder) holder).five_o.setBackgroundColor(player1color);
        ((ViewHolder) holder).six_o.setBackgroundColor(player1color);
        ((ViewHolder) holder).seven_o.setBackgroundColor(player1color);
        ((ViewHolder) holder).eight_o.setBackgroundColor(player1color);
        ((ViewHolder) holder).nine_o.setBackgroundColor(player1color);
        ((ViewHolder) holder).ten_o.setBackgroundColor(player1color);

        if(array[numToDisplay-1][1]==1){
            viewHolder.one.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorYellow));
            viewHolder.one_o.setVisibility(View.VISIBLE);
        }else if(array[numToDisplay-1][2]==1){
            viewHolder.one.setBackgroundResource(R.drawable.ladder_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.one.getBackground();
            frameAnimation.start();
            //viewHolder.one.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorBlue));
            viewHolder.one_o.setVisibility(View.GONE);
        }else if(array[numToDisplay-1][4]==1){
            viewHolder.one.setBackgroundResource(R.drawable.snake_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.one.getBackground();
            frameAnimation.start();
            viewHolder.one_o.setVisibility(View.GONE);
        }else {
            viewHolder.one.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorGrey));
            viewHolder.one_o.setVisibility(View.GONE);
        }

        if (numToDisplay==1){
            //viewHolder.one.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorTransparent));
            viewHolder.one.setBackground(contextActivity.getDrawable(R.drawable.start));
            viewHolder.one_n.setTextSize(1);
            viewHolder.one_n.setText("");
        }else{
            viewHolder.one_n.setText(""+numToDisplay);
            viewHolder.one_n.setTextSize(20);
        }

        numToDisplay++;

        if(array[numToDisplay-1][1]==1){
            viewHolder.two.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorYellow));
            viewHolder.two_o.setVisibility(View.VISIBLE);
        }else if(array[numToDisplay-1][2]==1){
            viewHolder.two.setBackgroundResource(R.drawable.ladder_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.two.getBackground();
            frameAnimation.start();
            viewHolder.two_o.setVisibility(View.GONE);
        }else if(array[numToDisplay-1][4]==1){
            viewHolder.two.setBackgroundResource(R.drawable.snake_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.two.getBackground();
            frameAnimation.start();
            viewHolder.two_o.setVisibility(View.GONE);
        } else {
            viewHolder.two.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorGrey));
            viewHolder.two_o.setVisibility(View.GONE);
        }

        viewHolder.two_n.setText(""+numToDisplay);

        numToDisplay++;

        if(array[numToDisplay-1][1]==1){
            viewHolder.three.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorYellow));
            viewHolder.three_o.setVisibility(View.VISIBLE);
        }else if(array[numToDisplay-1][2]==1){
            viewHolder.three.setBackgroundResource(R.drawable.ladder_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.three.getBackground();
            frameAnimation.start();
            viewHolder.three_o.setVisibility(View.GONE);
        }else if(array[numToDisplay-1][4]==1){
            viewHolder.three.setBackgroundResource(R.drawable.snake_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.three.getBackground();
            frameAnimation.start();
            viewHolder.three_o.setVisibility(View.GONE);
        } else {
            viewHolder.three.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorGrey));
            viewHolder.three_o.setVisibility(View.GONE);
        }
        viewHolder.three_n.setText(""+numToDisplay);

        numToDisplay++;

        if(array[numToDisplay-1][1]==1){
            viewHolder.four.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorYellow));
            viewHolder.four_o.setVisibility(View.VISIBLE);
        }else if(array[numToDisplay-1][2]==1){
            viewHolder.four.setBackgroundResource(R.drawable.ladder_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.four.getBackground();
            frameAnimation.start();
            viewHolder.four_o.setVisibility(View.GONE);
        }else if(array[numToDisplay-1][4]==1){
            viewHolder.four.setBackgroundResource(R.drawable.snake_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.four.getBackground();
            frameAnimation.start();
            viewHolder.four_o.setVisibility(View.GONE);
        }else {
            viewHolder.four.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorGrey));
            viewHolder.four_o.setVisibility(View.GONE);
        }

        viewHolder.four_n.setText(""+numToDisplay);

        numToDisplay++;

        if(array[numToDisplay-1][1]==1){
            viewHolder.five.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorYellow));
            viewHolder.five_o.setVisibility(View.VISIBLE);
        }else if(array[numToDisplay-1][2]==1){
            viewHolder.five.setBackgroundResource(R.drawable.ladder_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.five.getBackground();
            frameAnimation.start();
            viewHolder.five_o.setVisibility(View.GONE);
        }else if(array[numToDisplay-1][4]==1){
            viewHolder.five.setBackgroundResource(R.drawable.snake_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.five.getBackground();
            frameAnimation.start();
            viewHolder.five_o.setVisibility(View.GONE);
        }else {
            viewHolder.five.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorGrey));
            viewHolder.five_o.setVisibility(View.GONE);
        }

        viewHolder.five_n.setText(""+numToDisplay);

        numToDisplay++;

        if(array[numToDisplay-1][1]==1){
            viewHolder.six.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorYellow));
            viewHolder.six_o.setVisibility(View.VISIBLE);
        }else if(array[numToDisplay-1][2]==1){
            viewHolder.six.setBackgroundResource(R.drawable.ladder_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.six.getBackground();
            frameAnimation.start();
            viewHolder.six_o.setVisibility(View.GONE);
        }else if(array[numToDisplay-1][4]==1){
            viewHolder.six.setBackgroundResource(R.drawable.snake_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.six.getBackground();
            frameAnimation.start();
            viewHolder.six_o.setVisibility(View.GONE);
        }else {
            viewHolder.six.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorGrey));
            viewHolder.six_o.setVisibility(View.GONE);
        }

        viewHolder.six_n.setText(""+numToDisplay);

        numToDisplay++;

        if(array[numToDisplay-1][1]==1){
            viewHolder.seven.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorYellow));
            viewHolder.seven_o.setVisibility(View.VISIBLE);
        }else if(array[numToDisplay-1][2]==1){
            viewHolder.seven.setBackgroundResource(R.drawable.ladder_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.seven.getBackground();
            frameAnimation.start();
            viewHolder.seven_o.setVisibility(View.GONE);
        }else if(array[numToDisplay-1][4]==1){
            viewHolder.seven.setBackgroundResource(R.drawable.snake_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.seven.getBackground();
            frameAnimation.start();
            viewHolder.seven_o.setVisibility(View.GONE);
        }else {
            viewHolder.seven.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorGrey));
            viewHolder.seven_o.setVisibility(View.GONE);
        }

        viewHolder.seven_n.setText(""+numToDisplay);

        numToDisplay++;

        if(array[numToDisplay-1][1]==1){
            viewHolder.eight.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorYellow));
            viewHolder.eight_o.setVisibility(View.VISIBLE);
        }else if(array[numToDisplay-1][2]==1){
            viewHolder.eight.setBackgroundResource(R.drawable.ladder_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.eight.getBackground();
            frameAnimation.start();
            viewHolder.eight_o.setVisibility(View.GONE);
        }else if(array[numToDisplay-1][4]==1){
            viewHolder.eight.setBackgroundResource(R.drawable.snake_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.eight.getBackground();
            frameAnimation.start();
            viewHolder.eight_o.setVisibility(View.GONE);
        }else {
            viewHolder.eight.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorGrey));
            viewHolder.eight_o.setVisibility(View.GONE);
        }

        viewHolder.eight_n.setText(""+numToDisplay);

        numToDisplay++;

        if(array[numToDisplay-1][1]==1){
            viewHolder.nine.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorYellow));
            viewHolder.nine_o.setVisibility(View.VISIBLE);
        }else if(array[numToDisplay-1][2]==1){
            viewHolder.nine.setBackgroundResource(R.drawable.ladder_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.nine.getBackground();
            frameAnimation.start();
            viewHolder.nine_o.setVisibility(View.GONE);
        }else if(array[numToDisplay-1][4]==1){
            viewHolder.nine.setBackgroundResource(R.drawable.snake_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.nine.getBackground();
            frameAnimation.start();
            viewHolder.nine_o.setVisibility(View.GONE);
        }else {
            viewHolder.nine.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorGrey));
            viewHolder.nine_o.setVisibility(View.GONE);
        }

        viewHolder.nine_n.setText(""+numToDisplay);

        numToDisplay++;

        if(array[numToDisplay-1][1]==1){
            viewHolder.ten.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorYellow));
            viewHolder.ten_o.setVisibility(View.VISIBLE);
        }else if(array[numToDisplay-1][2]==1){
            viewHolder.ten.setBackgroundResource(R.drawable.ladder_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.ten.getBackground();
            frameAnimation.start();
            viewHolder.ten_o.setVisibility(View.GONE);
        }else if(array[numToDisplay-1][4]==1){
            viewHolder.ten.setBackgroundResource(R.drawable.snake_ani);
            AnimationDrawable frameAnimation = (AnimationDrawable) viewHolder.ten.getBackground();
            frameAnimation.start();
            viewHolder.ten_o.setVisibility(View.GONE);
        }else {
            viewHolder.ten.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorGrey));
            viewHolder.ten_o.setVisibility(View.GONE);
        }
        if (numToDisplay==100){
            viewHolder.ten.setBackground(contextActivity.getDrawable(R.drawable.finish));
            //viewHolder.ten.setBackgroundColor(contextActivity.getResources().getColor(R.color.colorYellow));
            viewHolder.ten_n.setTextSize(1);
            viewHolder.ten_n.setText("");
        }else{
            viewHolder.ten_n.setTextSize(20);
            viewHolder.ten_n.setText(""+numToDisplay);
        }

    }
    @Override
    public int getItemCount() {
        return 10;
    }

    private boolean getPlayer1Color() {
        SharedPreferences pref = contextActivity.getSharedPreferences("snakeandladder",MODE_PRIVATE);
        int player1color = pref.getInt("player_1_color",0);
        if (player1color!=0)
            return true;
        else
            return false;
    }
}
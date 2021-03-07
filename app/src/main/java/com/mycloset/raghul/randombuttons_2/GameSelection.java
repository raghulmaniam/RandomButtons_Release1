package com.mycloset.raghul.randombuttons_2;

import android.app.Activity;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameSelection extends Activity implements View.OnClickListener {

    public TextView game1;
    public TextView game2;



    private Handler mHandler = new Handler();
    Random rnd = new Random();

    public FrameLayout mainFrameLayout;

    int width;
    int height;

    int leftMargin;
    int topMargin;
    int dummyButtonCounter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //--- To set Full Screen mode ---

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //--- To set Full Screen mode ---

        setContentView(R.layout.activity_game_selection);

        game1 = findViewById(R.id.game1); //Random Buttons
        game2 = findViewById(R.id.game2); //Memory Buttons


        mainFrameLayout = findViewById(R.id.dummyButtonLayout2);

        game1.setOnClickListener(this);
        game2.setOnClickListener(this);


        createButtonRunnable.run();

        //zoom_in(mainFrameLayout, 40000);

        rotate_right(mainFrameLayout, 40000);


    }

    private Runnable createButtonRunnable = new Runnable() {
        @Override
        public void run() {
            newButton();
            dummyButtonCounter++;

            if(dummyButtonCounter<500)
                mHandler.postDelayed(createButtonRunnable, 800);
        }
    };

    public void rotate_right(FrameLayout layout, int duration) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.rotate_right);
        anim.setDuration(duration);
        anim.setRepeatCount(Animation.INFINITE);
        layout.startAnimation(anim);
    }

    public void zoom_in(FrameLayout layout, int duration) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.zoomin);
        anim.setDuration(duration);
        anim.setRepeatCount(Animation.INFINITE);
        layout.startAnimation(anim);
    }

    public void newButton() {
        Button button = new Button(this);
        button.setBackgroundResource(R.drawable.button_selector);
        //button.setOnClickListener(this);

        animate(button);

        Random randomParam = new Random();

        height = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(25) + 50) * 0.5) + 0.5f);
        width = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(50) + 50) * 0.5) + 0.5f);

        leftMargin = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(260) + 10) * 0.8) + 0.5f);
        topMargin = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(380) + 10) * 0.8) + 0.5f);


        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius( 8 );
        shape.setStroke(5,Color.BLACK);

        shape.setColor(color);

        //button.setBackgroundColor(color);
        button.setBackground(shape);

        //button.setBackgroundColor(color);



        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(width, height);
        layoutparams.setMargins(leftMargin, topMargin, 0, 0);

        mainFrameLayout.addView(button, layoutparams);
    }

    public void animate(Button button) {
        //Animation anim = new CircularRotateAnimation(button, 60);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.lefttoright);



        //duration of animation
        anim.setDuration(4000);
        anim.setRepeatCount(1);

        //start the animation
        button.startAnimation(anim);
    }


    @Override
    public void onClick(View view){

        switch (view.getId()) {
            case R.id.game1: {
                Intent intent = new Intent(getApplicationContext(), MainGameActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.zoomin_activity);

                break;
            }
            case R.id.game2: {
               // Intent intent = new Intent(getApplicationContext(), MemoryButtons.class);
                //startActivity(intent);

                Toast.makeText(getApplicationContext(), "Memory Buttons under Development.. Wait for the next patch.. "  , Toast.LENGTH_SHORT).show();

                break;
            }
        }

    }
}


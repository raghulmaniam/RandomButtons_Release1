package com.mycloset.raghul.randombuttons_2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import java.util.Random;

/*

Developer: Raghul Subramaniam
Email: raghulmaniam@gmail.com

 */

public class GameoverActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView score_gameover;
    public TextView counterValue_gameover;
    public TextView counterValueMain_gameover;
    public TextView buttonSpeedView_gameover;
    public TextView highScore;
    public TextView counterTime;
    private ImageView retryButton;
    private ImageView exitButton;
    public ImageView turtle;

    public Integer blinkDelay = 100;
    int width;
    int height;
    int leftMargin;
    int topMargin;
    int dummyButtonCounter;

    private Handler mHandler = new Handler();
    public FrameLayout mainFrameLayout;

    Random rnd = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //--- To set Full Screen mode ---

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //--- To set Full Screen mode ---

        setContentView(R.layout.activity_gameover);

        turtle = findViewById(R.id.turtle_gameover);
        turtle.setTag("open");

        score_gameover = findViewById(R.id.scoreTextView_gameover);
        counterValue_gameover = findViewById(R.id.CounterTextView_gameover);
        counterValueMain_gameover = findViewById(R.id.CounterTextViewMain_gameover);
        buttonSpeedView_gameover = findViewById(R.id.buttonSpeedView_gameover);
        highScore = findViewById(R.id.highscore_textView);
        counterTime = findViewById(R.id.timeValueTextView_gameover);
        mainFrameLayout = findViewById(R.id.dummyButtonLayoutGameOver);

        createButtonRunnable.run();

        //zoom_in(mainFrameLayout, 40000);
        rotate(mainFrameLayout, 40000);

        turleBlink.run();

        Bundle bundle = getIntent().getExtras();

        score_gameover.setText(bundle.getString("score"));
        counterValue_gameover.setText(bundle.getString("counter"));

        buttonSpeedView_gameover.setText(bundle.getString("buttonSpeed"));
        counterTime.setText(bundle.getString("timeCounter"));

        counterValueMain_gameover.setText(bundle.getString("counterMain"));

        retryButton = findViewById(R.id.retry_button_gameover);
        exitButton = findViewById(R.id.exit_button_gameover);

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);

        int score;
        String difficulty;

        if (bundle.getBoolean("isDifficult")) {
            difficulty = "hard";
            //score = prefs.getInt("hard", 0); //0 is the default value
        } else {
            difficulty = "easy";
            //score = prefs.getInt("hard", 0); //0 is the default value
        }

        score = prefs.getInt(difficulty, 0); //0 is the default value

        Integer finalScore = Integer.parseInt(bundle.getString("counterMain"));

        if (finalScore > score) {
            Editor editor = prefs.edit();

            editor.putInt(difficulty, finalScore);
            editor.commit();
            Toast.makeText(getApplicationContext(), "Meet the new Champion! High Score! ", Toast.LENGTH_LONG).show();

            highScore.setText("High Score: " + finalScore);
        } else {
            Toast.makeText(getApplicationContext(), "I was so close from becoming the world champion..! So close..!", Toast.LENGTH_LONG).show();
            highScore.setText("High Score: " + score);
        }

        retryButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        //Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(getApplicationContext(), MainGameActivity.class);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        //setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.retry_button_gameover:
            {
                Intent intent = new Intent(getApplicationContext(), MainGameActivity.class);
                startActivity(intent);

                break;
            }

            case R.id.exit_button_gameover: {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                break;
            }
        }
    }

    private Runnable turleBlink = new Runnable() {
        @Override
        public void run() {
            if (turtle.getTag().equals("open")) {
                turtle.setImageResource(R.mipmap.turtle_highscore_both);
                turtle.setTag("closed");
            } else {
                turtle.setImageResource(R.mipmap.turtle_highscore_partial);
                turtle.setTag("open");
            }

            //two blinks.. pause.. one blink.. pause..

            if (blinkDelay == 100) {
                blinkDelay = 110;
                //secondTurtle.setImageResource(R.mipmap.turtle_ingame_2_1);
                //secondTurtle.setTag("closed");
            } else if (blinkDelay == 110)
                blinkDelay = 112;
            else if (blinkDelay == 112 || blinkDelay == 810) {
                turtle.setImageResource(R.mipmap.turtle_highscore_partial);
                turtle.setTag("open");
                blinkDelay = 1800;
            } else if (blinkDelay == 1800)
                blinkDelay = 101;
            else if (blinkDelay == 101)
                blinkDelay = 1801;
            else if (blinkDelay == 1801)
                blinkDelay = 100;

            mHandler.postDelayed(turleBlink, blinkDelay);

        }
    };

    public void zoom_in(FrameLayout layout, int duration) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.zoomin);
        anim.setDuration(duration);
        anim.setRepeatCount(Animation.INFINITE);
        layout.startAnimation(anim);
    }
    public void rotate(FrameLayout layout, int duration) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.rotate_left);
        anim.setDuration(duration);
        anim.setRepeatCount(Animation.INFINITE);
        layout.startAnimation(anim);
    }

    private Runnable createButtonRunnable = new Runnable() {
        @Override
        public void run() {
            newButton();
            dummyButtonCounter++;

            if(dummyButtonCounter<1000)
                mHandler.postDelayed(createButtonRunnable, 200);
        }
    };

    public void newButton() {
        Button button = new Button(this);
        // button.setText("Click");
        button.setBackgroundResource(R.drawable.button_selector);
        button.setOnClickListener(this);

        fadinAnim(button);

        Random randomParam = new Random();

        /*Width
        Minimum: 50 Maximum: 450/250/150
        Height
        Minimum: 50 Maximum: 550/350/250
        Left Margin
        Minimum: 1  Maximum: 300
        Top Margin
        Minimum: 1  Minimum: 400/500
        */

        //getDensity*(unit in dp) --> converts dp to pixel, *0.5(-> half of the maximum allowed)

        //height = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(570) + 50) * 0.5) + 0.5f);
        //width = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(360) + 50) * 0.5) + 0.5f);

        /*height = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(250) + 50) * 0.5) + 0.5f);
        width = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(350) + 50) * 0.5) + 0.5f);*/

        height = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(50) + 50) * 0.5) + 0.5f);
        width = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(100) + 50) * 0.5) + 0.5f);


        //leftMargin = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(310) + 10) * 0.8) + 0.5f);
        //topMargin = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(570) + 10) * 0.8) + 0.5f);

        leftMargin = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(300) + 10) * 0.8) + 0.5f);
        topMargin = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(380) + 10) * 0.8) + 0.5f);

        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        button.setBackgroundColor(color);

        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(width, height);
        layoutparams.setMargins(leftMargin, topMargin, 0, 0);

        //mainConstraintLayout.addView(button, layoutparams);
        mainFrameLayout.addView(button, layoutparams);
    }

    public void fadinAnim(Button button) {
        //Animation anim = new CircularRotateAnimation(button, 60);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fadein);

        //duration of animation
        anim.setDuration(400);
        anim.setRepeatCount(1);

        //start the animation
        button.startAnimation(anim);
    }

}

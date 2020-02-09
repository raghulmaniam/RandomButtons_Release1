package com.mycloset.raghul.randombuttons_2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.graphics.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView enterImage;
    private ImageView aboutImage;
    private ImageView highScoreImage;
    private ImageView exitImage;
    private ImageView titleImage;

    public EditText playerName;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enterImage = findViewById(R.id.enter_image);
        aboutImage = findViewById(R.id.about_image);
        highScoreImage = findViewById(R.id.highscore_image);
        exitImage = findViewById(R.id.exit_image);
        titleImage = findViewById(R.id.titleImage);

        mainFrameLayout = findViewById(R.id.dummyButtonLayout);

        enterImage.setOnClickListener(this);
        exitImage.setOnClickListener(this);
        highScoreImage.setOnClickListener(this);
        aboutImage.setOnClickListener(this);
        titleImage.setOnClickListener(this);

        playerName = findViewById(R.id.playerName);

        createButtonRunnable.run();

        zoom_in(mainFrameLayout, 40000);
        //fade_in(mainFrameLayout, 2000);

    }


    public void fade_in(FrameLayout layout, int duration) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fadein);
        anim.setDuration(duration);
        anim.setRepeatCount(Animation.INFINITE);
        layout.startAnimation(anim);
    }


    public void newButton() {
        Button button = new Button(this);
        button.setBackgroundResource(R.drawable.button_selector);
        button.setOnClickListener(this);

        circularRotate(button);

        Random randomParam = new Random();

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

        mainFrameLayout.addView(button, layoutparams);
    }


    public void circularRotate(Button button) {
        //Animation anim = new CircularRotateAnimation(button, 60);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fadein);

        //duration of animation
        anim.setDuration(400);
        anim.setRepeatCount(1);

        //start the animation
        button.startAnimation(anim);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.enter_image: {
                Intent intent = new Intent(getApplicationContext(), MainGameActivity.class);
                startActivity(intent);

                break;
            }
            case R.id.exit_image: {
                finish();
                System.exit(0);
                break;
            }
            case R.id.highscore_image: {
                SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
                int scoreEasy = prefs.getInt("easy", 0); //0 is the default value
                //int scoreHard = prefs.getInt("hard", 0); //0 is the default value

                //Toast.makeText(getApplicationContext(), "HighScore: \n Easy: " + scoreEasy + "\n Hard: " + scoreHard, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "HighScore: " + scoreEasy , Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.titleImage:
            case R.id.about_image: {
                Intent intent = new Intent(getApplicationContext(), Infoctivity.class);
                startActivity(intent);

                break;
            }
        }

    }

    public void zoom_in(FrameLayout layout, int duration) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.zoomin);
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

}

package com.mycloset.raghul.randombuttons_2;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.support.constraint.Guideline;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import android.widget.LinearLayout.LayoutParams;
import android.os.Handler;
import android.os.CountDownTimer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import android.content.Context;
import android.os.Vibrator;
import android.view.animation.AnimationUtils;
import android.graphics.*;
import android.view.animation.Animation;

import android.app.Activity;
import android.view.View.OnTouchListener;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.SharedPreferences.Editor;

public class MainGameActivity extends Activity implements View.OnClickListener {


    public TextView score;
    public TextView timeCounter;
    public TextView counterValue;
    public TextView counterValueMain;
    public TextView buttonSpeedView;
    public FrameLayout mainFrameLayout;
    public RelativeLayout mainConstraintLayout;

    //public Button start;
    public Boolean stopRandomButtons;
    public Integer counterUpTimer = 0;
    int counter;
    int totalButtons;
    int buttonsClicked;
    int buttonsClickedForLevelChange;
    int delayInMS;
    int width;
    int height;
    int leftMargin;
    int topMargin;
    long speed;
    Integer blinkDelay = 100;
    Integer firstNegate;
    Integer secondNegate;
    Integer thirdNegate;
    Integer counterBeforeGameValue = 3;
    Bundle bundle;
    int curLevel = 0;
    Random rnd = new Random();
    boolean isSecondTurtleClicked = false;
    private ImageView secondTurtle;

    private ImageView start;

    private Handler mHandler = new Handler();
    public Runnable counterUpAfterGame = new Runnable() {
        @Override
        public void run() {
            timeCounter.setText(Integer.toString(++counterUpTimer));
            mHandler.postDelayed(counterUpAfterGame, 1000);
        }
    };
    Runnable counterBeforeGame = new Runnable() {
        @Override
        public void run() {

            counterValueMain.setText(counterBeforeGameValue);
            counterBeforeGameValue--;

            if (counterBeforeGameValue > 0)
                mHandler.postDelayed(counterBeforeGame, 1000);
        }
    };
    private ViewGroup mainLayout;
    private int xDelta;
    private int yDelta;

    Dialog rulesDialog;
    Dialog gameoverDialog;

    ImageView turtle; //to refactor the variable name

    public ProgressBar progressBar;
    public BigDecimal progressInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //--- To set Full Screen mode ---
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //--- To set Full Screen mode ---


        // ----- Initial ------
        setContentView(R.layout.activity_main_game);
        // ----- /Initial------

        bundle = getIntent().getExtras();

        firstNegate = 100;
        secondNegate = 42;
        thirdNegate = 15;

        mainLayout = (RelativeLayout) findViewById(R.id.mainConstraint);


        score = findViewById(R.id.scoreTextView);
        timeCounter = findViewById(R.id.timeValueTextView);
        counterValue = findViewById(R.id.CounterTextView);
        counterValueMain = findViewById(R.id.CounterTextViewMain);
        buttonSpeedView = findViewById(R.id.buttonSpeedView);

        secondTurtle = findViewById(R.id.secondTurtleImage);
        secondTurtle.setOnClickListener(this);
        secondTurtle.setOnTouchListener(onTouchListener());
        secondTurtle.setTag("closed");
        secondTurtleBlink.run();


        mainFrameLayout = findViewById(R.id.mainGameLayout);
        mainConstraintLayout = findViewById(R.id.mainConstraint);

        progressBar = findViewById(R.id.progressbar);

        //initial values
        height = 50;
        width = 50;
        leftMargin = 1;
        topMargin = 1;
        counter = 0;
        totalButtons = 0;
        buttonsClicked = 0;

        stopRandomButtons = false;
        timeCounter.setTextColor(Color.RED);

        delayInMS = RandomButtonsConstants.INITIALDELAY;

        score.setText(Integer.toString(buttonsClicked));

        buttonSpeedView.setText(Long.toString(0));

        //counterValue.setText(Integer.toString(counter));
        counterValue.setText(Integer.toString(buttonsClicked));

        start = findViewById(R.id.start);
        start.setOnClickListener(this);

        start.setImageResource(R.mipmap.start);
        //start.setVisibility(View.VISIBLE);


        //rotateLeft(mainFrameLayout, 12000);
        //customAnimation(mainFrameLayout, R.anim.lefttoright , 8000);

        /*Width
        Minimum: 50 Maximum: 450
        Height
        Minimum: 50 Maximum: 550
        Left Margin
        Minimum: 1  Maximum: 300
        Right Margin
        Minimum: 1  Minimum: 400

        */
    }

    private Runnable secondTurtleBlink = new Runnable() {
        @Override
        public void run() {
            if (!isSecondTurtleClicked) {
                if (secondTurtle.getTag().equals("open")) {
                    secondTurtle.setImageResource(R.mipmap.turtle_ingame_2_closed);
                    secondTurtle.setTag("closed");
                } else {
                    secondTurtle.setImageResource(R.mipmap.turtle_ingame_2);
                    secondTurtle.setTag("open");
                }

                //two blinks.. pause.. one blink.. pause..

                if (blinkDelay == 100) {
                    blinkDelay = 110;
                    //secondTurtle.setImageResource(R.mipmap.turtle_ingame_2_1);
                    //secondTurtle.setTag("closed");
                } else if (blinkDelay == 110)
                    blinkDelay = 112;
                else if (blinkDelay == 112 || blinkDelay == 810) {
                    secondTurtle.setImageResource(R.mipmap.turtle_ingame_2);
                    secondTurtle.setTag("open");
                    blinkDelay = 1800;
                } else if (blinkDelay == 1800)
                    blinkDelay = 101;
                else if (blinkDelay == 101)
                    blinkDelay = 1801;
                else if (blinkDelay == 1801)
                    blinkDelay = 100;

                mHandler.postDelayed(secondTurtleBlink, blinkDelay);

            }

        }

    };
    private Runnable wokeUpMessage = new Runnable() {
        @Override
        public void run() {

            if (!isSecondTurtleClicked) {

                isSecondTurtleClicked = true;
                secondTurtle.setImageResource(R.mipmap.turtle_ingame);
                //How can I wake my sleeping friend!
                //secondTurtleText.setText("Hey.. Not me..!");
                mHandler.postDelayed(wokeUpMessage, 2000);
            } else {
                secondTurtle.setImageResource(R.mipmap.turtle_ingame_2);
                //secondTurtleText.setText("How can I wake my sleeping friend.. Hmmm..!");

                isSecondTurtleClicked = false;
                blinkDelay = 810;
                secondTurtleBlink.run();
            }
        }
    };
    private Runnable createButtonRunnable = new Runnable() {
        @Override
        public void run() {

            if (!stopRandomButtons) {
                counterValue.setText(Integer.toString(counter));
                newButton();

                totalButtons++;
                counter++;

                counterValue.setText(Integer.toString(counter));

                progressInt = new BigDecimal(counter).divide(new BigDecimal(15), 2 , RoundingMode.UP).multiply(new BigDecimal(100));

                progressBar.setProgress(progressInt.intValue());


                if (buttonsClickedForLevelChange > 160) {
                    if (curLevel == 7)
                        customToast("Speed++", Toast.LENGTH_LONG);

                    curLevel = 8;
                } else if (buttonsClickedForLevelChange > 150 && curLevel <= 6) {
                    if (curLevel == 6)
                        callLevelUpText();
                    //rotateLeft(mainFrameLayout, 6000);
                    mainFrameLayout.clearAnimation();
                    customAnimation(mainFrameLayout, R.anim.bounce , 10000);
                    curLevel = 7;
                } else if (buttonsClickedForLevelChange > 130 && curLevel <= 5) {
                    if (curLevel == 5)
                        callLevelUpText();
                    mainFrameLayout.clearAnimation();
                    customAnimation(mainFrameLayout, R.anim.rotate_left,8000);
                    curLevel = 6;
                } else if (buttonsClickedForLevelChange > 110 && curLevel <= 4) {
                    if (curLevel == 4)
                        callLevelUpText();
                    //rotateLeft(mainFrameLayout, 6000);
                    mainFrameLayout.clearAnimation();
                    //bounce(mainFrameLayout, 10000);
                    customAnimation(mainFrameLayout, R.anim.zoomout, 10000);
                    curLevel = 5;
                } else if (buttonsClickedForLevelChange > 70 && curLevel <= 3) {
                    if (curLevel == 3)
                        callLevelUpText();
                    //rotateLeft(mainFrameLayout, 6000);
                    mainFrameLayout.clearAnimation();
                    customAnimation(mainFrameLayout, R.anim.bounce ,  10000);
                    curLevel = 4;
                } else if (buttonsClickedForLevelChange > 50 && curLevel <= 2) {
                    if (curLevel == 2)
                        callLevelUpText();
                    mainFrameLayout.clearAnimation();
                    //tex_anim(mainFrameLayout, 10000);
                    customAnimation(mainFrameLayout, R.anim.zoomin, 8000);
                    //mainFrameLayout.clearAnimation();
                    curLevel = 3;
                } else if (buttonsClickedForLevelChange > 30 && curLevel <= 1) {
                    if (curLevel == 1)
                        callLevelUpText();
                    mainFrameLayout.clearAnimation();
                    //tex_anim(mainFrameLayout, 10000);
                    customAnimation(mainFrameLayout, R.anim.blink_anim, 2000);
                    //mainFrameLayout.clearAnimation();
                    curLevel = 2;
                } else if (buttonsClickedForLevelChange > 10 && curLevel == 0) {
                    if (curLevel == 0)
                        callLevelUpText();
                    mainFrameLayout.clearAnimation();
                    //circularRotate(mainFrameLayout);
                    //fade_in(mainFrameLayout, 8000);
                    customAnimation(mainFrameLayout, R.anim.fadein, 10000);
                    //mainFrameLayout.clearAnimation();
                    curLevel = 1;
                }


                if (counter < RandomButtonsConstants.GAMECOUNTERLIMIT) {

                    //customAnimation(mainFrameLayout, R.anim.zoomin, 8000);
                    //mainFrameLayout.clearAnimation();
                   // customAnimation(mainFrameLayout, R.anim.zoomin, 8000);
                /*

                Setting Delay from Counter Value
                if(delayInMS> 700)
                delayInMS = delayInMS-100;
                else if (delayInMS> 500)
                    delayInMS = delayInMS-50;
                else if(delayInMS > 100)
                    delayInMS = delayInMS-5;
                else if (delayInMS > 50)
                    delayInMS = delayInMS-1;
                */

                    //Setting Delay from Total Button Value
                    if (totalButtons < 5) /*900 ,800, 700, 600*/
                        delayInMS = delayInMS - 68;
                    else if (totalButtons < 10) /*550, 500, 450, 400, 350*/
                        delayInMS = delayInMS - 28;
                    else if (totalButtons < 20) /* ~200 (Approx 5 Buttons per second)  */
                        delayInMS = delayInMS - 18;
                    else
                        delayInMS--;

                    //Number of buttons per second
                    speed = (1000) / (delayInMS);

                    buttonSpeedView.setText(Long.toString(speed));
                    mHandler.postDelayed(createButtonRunnable, delayInMS);
                } else {
                    gameOver();
                }

            } else {
                if (counterValue.getText().toString().equals("0") || counter < 0)
                    gameOver();
                else
                    mHandler.postDelayed(createButtonRunnable, 20);
            }
        }
    };


    public void showRulesDialog()
    {

        Button dialogOkay;
        rulesDialog = new Dialog(this);
        rulesDialog.setContentView(R.layout.rules_dialog);
        rulesDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogOkay = rulesDialog.findViewById(R.id.dialogOkayButton);
        rulesDialog.setCancelable(false);

        Window window = rulesDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations=R.style.DialogAnimation;
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        rulesDialog.show();

        dialogOkay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                rulesDialog.dismiss();
                counterBeforeGame();
                //startGame();
            }
        }
        );
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.start: {
                removeButton(view);
                counterValueMain.setVisibility(View.VISIBLE);
                //showgameoverDialog();
                showRulesDialog();
                break;
            }
            case R.id.mainGameLayout: {
                //Negative Score
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(50);

                //wokeUpMessage.run();

                buttonsClicked--;
                score.setText(Integer.toString(buttonsClicked));
                counterValueMain.setText(Integer.toString(buttonsClicked));
                break;
            }
            case R.id.secondTurtleImage: {
                wokeUpMessage.run();
                break;
            }
            default: {
                //Positive Score
                removeButton(view);
                counter--;
                counterValue.setText(Integer.toString(counter));
                buttonsClicked++;
                buttonsClickedForLevelChange++;
                score.setText(Integer.toString(buttonsClicked));
                counterValueMain.setText(Integer.toString(buttonsClicked));
                break;
            }
        }
    }

    public void removeButton(View view) {
        view.clearAnimation();
        view.setVisibility(View.GONE);
        ViewGroup parentView = (ViewGroup) view.getParent();
        parentView.removeView(view);

    }

    public void counterAfterGame() {
        new CountDownTimer(29000, 1000) {

            public void onTick(long val) {
                Long timeVal = val / 1000;
                timeCounter.setText(Integer.toString(timeVal.intValue()));
            }

            public void onFinish() {
                timeCounter.setTextColor(Color.BLACK);
            }
        }.start();
    }

    public void callLevelUpText() {
        customToast("Level Up", Toast.LENGTH_SHORT);
        wokeUpMessage.run();


    }

    public void counterBeforeGame() {
        new CountDownTimer(4000, 1000) {

            public void onTick(long millisUntilFinished) {
                Long val = millisUntilFinished / 1000;
                counterValueMain.setText(Integer.toString(val.intValue()));
            }

            public void onFinish() {


                counterValueMain.setText(Integer.toString(BigDecimal.ZERO.intValue()));
                customToast("Start" , Toast.LENGTH_SHORT);
                counterValueMain.setText(Integer.toString(0));
                startGame();
                counterUpAfterGame.run();
            }
        }.start();
    }

    public void gameOver() {

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(200);

        showgameoverDialog();

        //Toast.makeText(getApplicationContext(), "Game Over!!", Toast.LENGTH_SHORT).show();

       /* Intent intent = new Intent(MainGameActivity.this, GameoverActivity.class);
        intent.putExtra("score", score.getText().toString());
        intent.putExtra("counter", counterValue.getText().toString());
        intent.putExtra("counterMain", counterValueMain.getText().toString());
        intent.putExtra("buttonSpeed", buttonSpeedView.getText().toString());
        intent.putExtra("timeCounter", counterUpTimer.toString());

        startActivity(intent);
        */


    }

    public void showgameoverDialog()
    {
        gameoverDialog = new Dialog(this);
        gameoverDialog.setContentView(R.layout.rules_dialog_2);
        gameoverDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        turtle = gameoverDialog.findViewById(R.id.turtle);
        turtle.setTag("open");
        turleBlink.run();

        TextView dialogText;

        dialogText = gameoverDialog.findViewById(R.id.rulesText);
        dialogText.setText(" Score: ");


        Button retryButton = gameoverDialog.findViewById(R.id.dialogRetryButton);
        Button exitButton = gameoverDialog.findViewById(R.id.dialogExitButton);


        gameoverDialog.setCancelable(false);

        Window window = gameoverDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations=R.style.DialogAnimation;
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        gameoverDialog.show();

        retryButton.setOnClickListener(new View.OnClickListener(){
                                          @Override
                                          public void onClick(View view)
                                          {
                                              gameoverDialog.dismiss();
                                              Intent intent = new Intent(getApplicationContext(), MainGameActivity.class);
                                              startActivity(intent);
                                          }
                                      }
        );

        exitButton.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View view)
                                           {
                                               Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                               startActivity(intent);
                                               gameoverDialog.dismiss();
                                           }
                                       }
        );

        ScoreDelegator(dialogText);

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }

    public void ScoreDelegator(TextView dialogText){

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        int highScoreEasy = prefs.getInt("easy", 0); //0 is the default value

        Integer finalScore = Integer.parseInt(score.getText().toString());

        if(finalScore>highScoreEasy)
        {
            Editor editor = prefs.edit();

            editor.putInt("easy", finalScore);
            editor.commit();
            customToast("Meet the new Champion! High Score! ", Toast.LENGTH_LONG);
        }
        else {
            customToast( "I was so close to becoming the world champion.. So close..!" ,Toast.LENGTH_LONG);
        }

        dialogText.setText("Score: " + finalScore +"\n" +"High Score: " +highScoreEasy);
    }

    private Runnable turleBlink = new Runnable() {
        @Override
        public void run() {
            if (turtle.getTag().equals("open")) {
                turtle.setImageResource(R.drawable.turtle_highscore_fullblink);
                turtle.setTag("closed");
            } else {
                turtle.setImageResource(R.drawable.turtle_highscore_halfblink);
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
                turtle.setImageResource(R.drawable.turtle_highscore_halfblink);
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

    public void customToast(String message, int length ) {
        Toast.makeText(getApplicationContext(), message, length).show();
    }

    public void startGame() {

        mainFrameLayout.setOnClickListener(this);
        createButtonRunnable.run();
    }

    public void newButton() {
        Button button = new Button(this);
        button.setBackgroundResource(R.drawable.button_selector);
        button.setOnClickListener(this);

        //circularRotate(button);

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

        height = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(320) + 50) * 0.5) + 0.5f);
        width = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(150) + 50) * 0.5) + 0.5f);

        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        button.setBackgroundColor(color);

        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius( 8 );
        shape.setStroke(5,Color.BLACK);

        shape.setColor(color);

        //button.setBackgroundColor(color);
        button.setBackground(shape);


        //leftMargin = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(310) + 10) * 0.8) + 0.5f);
        //topMargin = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(570) + 10) * 0.8) + 0.5f);

        leftMargin = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(200) + 10) * 0.8) + 0.5f);
        topMargin = (int) (((getResources().getDisplayMetrics().density) * (randomParam.nextInt(340) + 10) * 0.8) + 0.5f);

        LayoutParams layoutparams = new LinearLayout.LayoutParams(width, height);
        layoutparams.setMargins(leftMargin, topMargin, 0, 0);

        //mainConstraintLayout.addView(button, layoutparams);
        mainFrameLayout.addView(button, layoutparams);
    }

    public void circularRotate(Button button) {
        //Animation anim = new CircularRotateAnimation(button, 60);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fadein);

        //duration of animation
        anim.setDuration(1000);
        anim.setRepeatCount(1);

        //start the animation
        button.startAnimation(anim);
    }

    public void circularRotate(FrameLayout layout) {
        Animation anim = new CircularRotateAnimation(layout, 60);
        //Animation anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        anim.setDuration(800);
        anim.setRepeatCount(Animation.INFINITE);

        layout.startAnimation(anim);
    }

    public  void customAnimation (FrameLayout layout , int animType , int duration ){
        Animation anim = AnimationUtils.loadAnimation(this, animType);
        anim.setDuration(duration);
        anim.setRepeatCount(Animation.INFINITE);
        layout.startAnimation(anim);
    }



    public void circularRotate(RelativeLayout layout) {
        Animation anim = new CircularRotateAnimation(layout, 60);
        //duration of animation
        anim.setDuration(1000);
        anim.setRepeatCount(Animation.INFINITE);

        //start the animation
        layout.startAnimation(anim);
    }

    public void circularRotate(Guideline layout) {
        Animation anim = new CircularRotateAnimation(layout, 60);
        //duration of animation
        anim.setDuration(5000);
        anim.setRepeatCount(100);

        //start the animation
        layout.startAnimation(anim);
    }

    private OnTouchListener onTouchListener() {
        return new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        /*RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();*/

                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:
                        /*Toast.makeText(MainActivity.this,
                                "I'm here!", Toast.LENGTH_SHORT)
                                .show();*/
                        break;

                    case MotionEvent.ACTION_MOVE:
                        /*RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();*/
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }

                mainLayout.invalidate();
                return true;
            }
        };
    }
}




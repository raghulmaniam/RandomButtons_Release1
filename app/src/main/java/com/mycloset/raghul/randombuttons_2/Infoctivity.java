/*
package com.mycloset.raghul.randombuttons_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Infoctivity extends AppCompatActivity implements View.OnClickListener{

    private Button infoBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //--- To set Full Screen mode ---

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //--- To set Full Screen mode ---


        setContentView(R.layout.activity_infoctivity);

        infoBackButton = findViewById(R.id.infoBackBackbutton);
        infoBackButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }



}
*/

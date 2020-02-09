package com.mycloset.raghul.randombuttons_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Infoctivity extends AppCompatActivity implements View.OnClickListener{

    private Button infoBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

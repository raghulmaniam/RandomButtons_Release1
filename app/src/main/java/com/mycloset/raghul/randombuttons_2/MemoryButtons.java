package com.mycloset.raghul.randombuttons_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MemoryButtons extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //--- To set Full Screen mode ---

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //--- To set Full Screen mode ---

        setContentView(R.layout.activity_memory_buttons);
    }
}

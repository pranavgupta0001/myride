package com.circle.prana.myride.navmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.circle.prana.myride.R;

public class Aboutus extends AppCompatActivity {

    TextView peragraph1, peragraph2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        peragraph1 =findViewById(R.id.pera1);
        peragraph2=findViewById(R.id.pera2);

        peragraph1.setText("In My Ride app, you will feel like its your ride.");
        peragraph2.setText("We are always trying to improve the your ride and destroy problems, " +
                "if you found " +
                "any problem please feel free to share with us.");

    }
}

package com.example.mike.wsnmonitor;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    public ImageButton imgButton_1, imgButton_n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgButton1 = (ImageButton) findViewById(R.id.imageButton1);
        imgButton1.setOnClickListener(new ButtonClickEvent(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),NetworkOne.class);
                startActivity(intent);

            }
        });

        imgButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imgButton3 = (ImageButton) findViewById(R.id.imageButton3);
        imgButton4 = (ImageButton) findViewById(R.id.imageButton4);
        imgButton2.setOnClickListener(new ButtonClickEvent());
        imgButton3.setOnClickListener(new ButtonClickEvent());
        imgButton4.setOnClickListener(new ButtonClickEvent());




    }


public class ButtonClickEvent implements View.OnClickListener {

    @Override
    public void onClick(View i) {
        if (i == imgButton2) {
            Toast.makeText(getApplicationContext(), "Button 2", Toast.LENGTH_SHORT).show();
        }
        if (i == imgButton3) {
            Toast.makeText(getApplicationContext(), "Button 3", Toast.LENGTH_SHORT).show();
        }
        if (i == imgButton4) {
            Toast.makeText(getApplicationContext(), "Button 4", Toast.LENGTH_SHORT).show();
        }
    }
}
}

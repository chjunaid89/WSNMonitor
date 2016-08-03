package com.example.mike.wsnmonitor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WSNControl extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wsncontrol);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final AsciiGenerator asciiGenerator = new AsciiGenerator(this);
        Button btnCode = (Button) findViewById(R.id.btnCode);
        btnCode.setOnClickListener(new View.OnClickListener() {

            //@Override
            public void onClick(View arg0) {


                new Thread(new Runnable() {
                    public void run() {


                        String word = ((EditText) findViewById(R.id.Line)).getText().toString();
                        int IniFreq = 1;
                        int FinFreq = 2;
                        int bitLength = 1;
                        int bitGap = 201;

                        Integer digits[] = new Integer[64];
                        int NumChar = word.length();

                        //This split the string in chars
                        for (int i = 0; i < NumChar; i++) {

                            digits[i] = (int) word.charAt(i);

                        }


                        double impulseDuration = 250; //not negotiable
                        int Delay = (int) (bitGap - ((int) impulseDuration * 0.8));
                        for (int IndexChar = 0; IndexChar < NumChar; IndexChar++) {
                            asciiGenerator.playDOWN(IniFreq * 1000, FinFreq * 1000, impulseDuration, digits[IndexChar], bitLength);
                            try {
                                Thread.sleep(Delay);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        Thread.currentThread().interrupt();

                    }

                }).start();

            }
        });

    }

    //Enable/disable button
    private void enableButton(boolean isEnable)
    {
        ((Button)findViewById(R.id.btnCode)).setEnabled(isEnable);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wsncontrol, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

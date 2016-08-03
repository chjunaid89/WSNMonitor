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

    ImageButton network1, network2, network3, network4,network5;
    Toast toast;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        network1 = (ImageButton) findViewById(R.id.imageButton1);
        network1.setOnClickListener(new ButtonClickEvent(){
            Intent intent;
            @Override
            public void onClick(View v) {
                intent=new Intent(v.getContext(),NetworkOne.class);
                startActivity(intent);

            }
        });

        network2 = (ImageButton) findViewById(R.id.imageButton2);
        network3 = (ImageButton) findViewById(R.id.imageButton3);
        network4 = (ImageButton) findViewById(R.id.imageButton4);
        network5 = (ImageButton) findViewById(R.id.imageButton5);
        network2.setOnClickListener(new ButtonClickEvent());
        network3.setOnClickListener(new ButtonClickEvent());
        network4.setOnClickListener(new ButtonClickEvent());
        network5.setOnClickListener(new ButtonClickEvent());

    }


    public class ButtonClickEvent implements View.OnClickListener {

        Toast toast;

        @Override
        public void onClick(View i) {
            if (i == network2) {
                toast.makeText(getApplicationContext(), "network 2", Toast.LENGTH_SHORT).show();
            }
            if (i == network3) {
                toast.makeText(getApplicationContext(), "network 3", Toast.LENGTH_SHORT).show();
            }
            if (i == network4) {
                toast.makeText(getApplicationContext(), "network 4", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.bluetooth){
            toast.makeText(getApplicationContext(), "Bluetooth", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getApplicationContext(), Bluetooth.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}

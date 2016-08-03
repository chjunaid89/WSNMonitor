package com.example.mike.wsnmonitor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class LightIntensityInternet extends AppCompatActivity {

    TextView textMsg, textMsg1, date, time, sensorId;
    final String textSource = "http://www.medan-sites.com/ACL/remotedata.txt";
    StringBuilder StringData = new StringBuilder();
    StringBuilder StringData1 = new StringBuilder();
    StringBuilder StringData_date = new StringBuilder();
    StringBuilder StringData_time = new StringBuilder();
    StringBuilder StringData_sensor_id = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_intensity_internet);
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

        textMsg = (TextView) this.findViewById(R.id.infrared_light_data);
        textMsg1 = (TextView) this.findViewById(R.id.visible_light_data);
        date = (TextView) this.findViewById(R.id.date);
        time = (TextView) this.findViewById(R.id.time);
        sensorId = (TextView) this.findViewById(R.id.sensor_id);

        new MyTask().execute();
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {


        String textResult,textResult1, dateResult, timeResult, sensorIdResult;

        @Override
        protected Void doInBackground(Void... params) {

            URL textUrl;

            try {
                textUrl = new URL(textSource);

                BufferedReader bufferReader
                        = new BufferedReader(new InputStreamReader(textUrl.openStream()));

                StringBuilder StringBuffer = new StringBuilder();

                String string;
                String stringText = "";

                /*for(int i=0; i<=0; i++) {
                    string = bufferReader.readLine();
                    stringText += string;
                    StringBuffer.append(stringText);
                }*/
                for(int i=0; i<=75; i++){
                    int c;
                    c = bufferReader.read();
                    char character = (char)c;
                    StringBuffer.append(character);
                }

                bufferReader.close();

                StringData.append(StringBuffer.substring(36, 40));
                StringData1.append(StringBuffer.substring(40, 44));
                StringData_date.append(StringBuffer.substring(57, 65));
                StringData_time.append(StringBuffer.substring(65, 71));
                StringData_sensor_id.append(StringBuffer.substring(71, 75));
                StringBuffer.setLength(0);
                //char comma = ',';
                //StringData.insert(2,comma);
                char dot = '.';
                char colon = ':';
                StringData_date.insert(2, dot);
                StringData_date.insert(5, dot);
                StringData_time.insert(2, colon);
                StringData_time.insert(5,colon);

                textResult = StringData.toString();
                textResult1 = StringData1.toString();
                dateResult = StringData_date.toString();
                timeResult = StringData_time.toString();
                sensorIdResult = StringData_sensor_id.toString();

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                textResult = e.toString();
                textResult1 = e.toString();
                dateResult = e.toString();
                timeResult = e.toString();
                sensorIdResult = e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                textResult = e.toString();
                textResult1 = e.toString();
                dateResult = e.toString();
                timeResult = e.toString();
                sensorIdResult = e.toString();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            textMsg.setText(textResult);
            textMsg1.setText(textResult1);
            date.setText(dateResult);
            time.setText(timeResult);
            sensorId.setText(sensorIdResult);

            super.onPostExecute(result);

            StringData.setLength(0);


        }

    }

}

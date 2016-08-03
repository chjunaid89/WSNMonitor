package com.example.mike.wsnmonitor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;


public class SensorNode0 extends ActionBarActivity {

    GridView gridView;
    private ImageAdapter adapter;
    ArrayList<String> Sensor_Type;
    String sensorString;
    String addedSensor;
    String[] sensorsArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_node_0);


        SharedPreferences sl = getSharedPreferences("AppSharedPref", 1);
        addedSensor = sl.getString("key", "");

        Sensor_Type = convertToArray(addedSensor);

        adapter = new ImageAdapter(this, Sensor_Type);

        gridView = (GridView) findViewById(R.id.gridView0);

        gridView.setAdapter(adapter);
        registerForContextMenu(gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                CharSequence itemText = ((TextView) v.findViewById(R.id.grid_item_label)).getText();
                Toast.makeText(getApplicationContext(), ((TextView) v.findViewById(R.id.grid_item_label)).getText(), Toast.LENGTH_SHORT).show();
                if (itemText.equals("Temperature")) {
                    Intent intent = new Intent(v.getContext(), Temperature.class);
                    startActivity(intent);
                }

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sensor_node_0, menu);
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
        if (id == R.id.action_add_sensor) {
            sensorsArray = new String[]{"Temperature", "Air Pressure", "Accelerometer", "Humidity", "Vibration", "Light Intensity", "Acoustic"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.select_sensor)
                    .setItems(sensorsArray, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            if (position == 0){
                                temperature(position);
                            }
                            if (position == 1){
                                airPressure(position);
                            }
                            if (position == 2){
                                accelerometer(position);
                            }
                            if (position == 3){
                                humidity(position);
                            }
                            if (position == 4){
                                vibration(position);
                            }
                            if (position == 5){
                                lightIntensity(position);
                            }
                            if (position == 6){
                                acoustic(position);
                            }
                        }
                    });
            AlertDialog dialog =  builder.create();
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // To create the context menu and adding items in the menu.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v == gridView){
            menu.setHeaderTitle("Select");
            AdapterView.AdapterContextMenuInfo cmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.add(1, cmi.position, 0, "Delete");
            menu.add(2, cmi.position, 0, "Action 2");
        }
    }
    //**********************************************************************************************


    // To set the actions if the item is selected from the context menu.
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        gridView = (GridView) findViewById(R.id.gridView0);
        String s = (String) gridView.getItemAtPosition(item.getItemId());
        switch (item.getGroupId()) {
            case 1:
                Toast.makeText(this, "Deleted, Item "+s, Toast.LENGTH_SHORT).show();
                Sensor_Type.remove(item.getItemId());
                sensorString = convertToString(Sensor_Type);

                SharedPreferences sl = getSharedPreferences("AppSharedPref", 1);
                SharedPreferences.Editor editor = sl.edit();
                editor.putString("key", sensorString);
                editor.commit();
                adapter.notifyDataSetChanged();
                gridView.setAdapter(adapter);
                break;
            case 2:
                Toast.makeText(this, "Action 2, Item "+s, Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
    //**********************************************************************************************

    // The actions to perform with respect to the selection of the items from the context menu.
    public void temperature(int id){
        Sensor_Type.add("Temperature");
        Toast.makeText(this, "Temperature sensor added", Toast.LENGTH_SHORT).show();
        sensorString = convertToString(Sensor_Type);

        SharedPreferences sl = getSharedPreferences("AppSharedPref", 1);
        SharedPreferences.Editor editor = sl.edit();
        editor.putString("key", sensorString);
        editor.commit();
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);
    }

    public void airPressure(int id){
        Sensor_Type.add("Air Pressure");
        Toast.makeText(this, "Air Pressure sensor added", Toast.LENGTH_SHORT).show();
        sensorString = convertToString(Sensor_Type);

        SharedPreferences sl = getSharedPreferences("AppSharedPref", 1);
        SharedPreferences.Editor editor = sl.edit();
        editor.putString("key", sensorString);
        editor.commit();
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);
    }

    public void accelerometer(int id){
        Sensor_Type.add("Accelerometer");
        Toast.makeText(this, "Accelerometer sensor added", Toast.LENGTH_SHORT).show();
        sensorString = convertToString(Sensor_Type);

        SharedPreferences sl = getSharedPreferences("AppSharedPref", 1);
        SharedPreferences.Editor editor = sl.edit();
        editor.putString("key", sensorString);
        editor.commit();
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);
    }

    public void humidity(int id){
        Sensor_Type.add("Humidity");
        Toast.makeText(this, "Humidity sensor added", Toast.LENGTH_SHORT).show();
        sensorString = convertToString(Sensor_Type);

        SharedPreferences sl = getSharedPreferences("AppSharedPref", 1);
        SharedPreferences.Editor editor = sl.edit();
        editor.putString("key", sensorString);
        editor.commit();
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);
    }

    public void vibration(int id){
        Sensor_Type.add("Vibration");
        Toast.makeText(this, "Vibration sensor added", Toast.LENGTH_SHORT).show();
        sensorString = convertToString(Sensor_Type);

        SharedPreferences sl = getSharedPreferences("AppSharedPref", 1);
        SharedPreferences.Editor editor = sl.edit();
        editor.putString("key", sensorString);
        editor.commit();
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);
    }

    public void lightIntensity(int id){
        Sensor_Type.add("Light Intensity");
        Toast.makeText(this, "Light Intensity sensor added", Toast.LENGTH_SHORT).show();
        sensorString = convertToString(Sensor_Type);

        SharedPreferences sl = getSharedPreferences("AppSharedPref", 1);
        SharedPreferences.Editor editor = sl.edit();
        editor.putString("key", sensorString);
        editor.commit();
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);
    }

    public void acoustic(int id){
        Sensor_Type.add("Acoustic");
        Toast.makeText(this, "Acoustic sensor added", Toast.LENGTH_SHORT).show();
        sensorString = convertToString(Sensor_Type);

        SharedPreferences sl = getSharedPreferences("AppSharedPref", 1);
        SharedPreferences.Editor editor = sl.edit();
        editor.putString("key", sensorString);
        editor.commit();
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);
    }
    //**********************************************************************************************

    public String convertToString(ArrayList<String> list) {

        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (String s : list)
        {
            sb.append(delim);
            sb.append(s);;
            delim = ",";
        }
        return sb.toString();
    }

    public ArrayList<String> convertToArray(String string) {

        ArrayList<String> list = new ArrayList<String>(Arrays.asList(string.split(",")));
        return list;
    }

}

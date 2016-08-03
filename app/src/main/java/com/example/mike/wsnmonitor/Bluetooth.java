package com.example.mike.wsnmonitor;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class Bluetooth extends AppCompatActivity {

    ListView listViewPaired;
    ListView listViewDetected;
    ArrayList<String> arrayListpaired;
    Button buttonSearch,buttonOn,buttonDesc,buttonOff;
    ArrayAdapter<String> adapter,detectedAdapter;
    BluetoothDevice bdDevice;
    ArrayList<BluetoothDevice> arrayListPairedBluetoothDevices;
    private ButtonClicked clicked;
    ListItemClickedonPaired listItemClickedonPaired;
    BluetoothAdapter bluetoothAdapter = null;
    ArrayList<BluetoothDevice> arrayListBluetoothDevices = null;
    ListItemClicked listItemClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
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

        listViewDetected = (ListView) findViewById(R.id.listViewDetected);
        listViewPaired = (ListView) findViewById(R.id.listViewPaired);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonOn = (Button) findViewById(R.id.buttonOn);
        buttonDesc = (Button) findViewById(R.id.buttonDesc);
        buttonOff = (Button) findViewById(R.id.buttonOff);
        arrayListpaired = new ArrayList<String>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        clicked = new ButtonClicked();
        arrayListPairedBluetoothDevices = new ArrayList<BluetoothDevice>();

        listItemClickedonPaired = new ListItemClickedonPaired();
        arrayListBluetoothDevices = new ArrayList<BluetoothDevice>();
        adapter= new ArrayAdapter<String>(Bluetooth.this, android.R.layout.simple_list_item_1, arrayListpaired);
        detectedAdapter = new ArrayAdapter<String>(Bluetooth.this, android.R.layout.simple_list_item_single_choice);
        listViewDetected.setAdapter(detectedAdapter);
        listItemClicked = new ListItemClicked();
        detectedAdapter.notifyDataSetChanged();
        listViewPaired.setAdapter(adapter);
    }
    @Override
    protected void onStart() {

        super.onStart();
        getPairedDevices();                         // The call for the method to get the paired devices and show them in the listview
        buttonOn.setOnClickListener(clicked);
        buttonSearch.setOnClickListener(clicked);
        buttonDesc.setOnClickListener(clicked);
        buttonOff.setOnClickListener(clicked);
        listViewDetected.setOnItemClickListener(listItemClicked);
        listViewPaired.setOnItemClickListener(listItemClickedonPaired);
    }
    private void getPairedDevices() {       // The method to get the paired devices with the device name and device address

        Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();
        if(pairedDevice.size()>0)
        {
            for(BluetoothDevice device : pairedDevice)
            {
                arrayListpaired.add(device.getName()+"\n"+device.getAddress());
                arrayListPairedBluetoothDevices.add(device);
            }
        }
        adapter.notifyDataSetChanged();
    }
    class ListItemClicked implements OnItemClickListener        // The class to pair the devices together on the click of the particular device from the listview
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // TODO Auto-generated method stub
            bdDevice = arrayListBluetoothDevices.get(position);
            Log.i("Log", "The device : "+bdDevice.toString());
            Boolean isBonded = false;
            try {
                isBonded = createBond(bdDevice);
                if(isBonded)
                {
                    getPairedDevices();
                    adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("Log", "The bond is created: "+isBonded);
            Toast.makeText(getApplicationContext(), "Paired", Toast.LENGTH_SHORT).show();
        }
    }
    class ListItemClickedonPaired implements OnItemClickListener        // // The class to remove the bond between devices on the click of the particular device from the listview
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            bdDevice = arrayListPairedBluetoothDevices.get(position);
            try {
                Boolean removeBonding = removeBond(bdDevice);
                if(removeBonding)
                {
                    arrayListpaired.remove(position);
                    adapter.notifyDataSetChanged();
                }

                Log.i("Log", "Removed"+removeBonding);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public boolean removeBond(BluetoothDevice btDevice)     // The method to call the BluetoothDevice Class with its method to remove the bond between devices
            throws Exception
    {
        Class btClass = Class.forName("android.bluetooth.BluetoothDevice");
        Method removeBondMethod = btClass.getMethod("removeBond");
        Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }


    public boolean createBond(BluetoothDevice btDevice)     // The method to call the BluetoothDevice Class with its method to create the bond between devices
            throws Exception
    {
        Class class1 = Class.forName("android.bluetooth.BluetoothDevice");
        Method createBondMethod = class1.getMethod("createBond");
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }


    class ButtonClicked implements OnClickListener      // The class to perform actions when the particular button is clicked
    {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonOn:
                    onBluetooth();
                    break;
                case R.id.buttonSearch:
                    arrayListBluetoothDevices.clear();
                    startSearching();
                    break;
                case R.id.buttonDesc:
                    makeDiscoverable();
                    break;
                case R.id.buttonOff:
                    offBluetooth();
                    break;
                default:
                    break;
            }
        }
    }
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {        // The class to receive the broadcasted messages
        @Override
        public void onReceive(Context context, Intent intent) {             // Receives the intent that broadcasted if the new device found during the search process

            Message msg = Message.obtain();
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                Toast.makeText(context, "Device Found", Toast.LENGTH_SHORT).show();

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                try
                {
                }
                catch (Exception e) {
                    Log.i("Log", "Inside the exception: ");
                    e.printStackTrace();
                }

                if(arrayListBluetoothDevices.size()<1)
                {
                    detectedAdapter.add(device.getName()+"\n"+device.getAddress());
                    arrayListBluetoothDevices.add(device);
                    detectedAdapter.notifyDataSetChanged();
                }
                else
                {
                    boolean flag = true;
                    for(int i = 0; i<arrayListBluetoothDevices.size();i++)
                    {
                        if(device.getAddress().equals(arrayListBluetoothDevices.get(i).getAddress()))
                        {
                            flag = false;
                        }
                    }
                    if(flag == true)
                    {
                        detectedAdapter.add(device.getName()+"\n"+device.getAddress());
                        arrayListBluetoothDevices.add(device);
                        detectedAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    };

    private void startSearching() {             // The method to search for a new device

        Log.i("Log", "in the start searching method");
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        Bluetooth.this.registerReceiver(myReceiver, intentFilter);
        bluetoothAdapter.startDiscovery();
        Toast.makeText(getApplicationContext(), "Searching", Toast.LENGTH_LONG).show();
    }

    private void onBluetooth() {            // The method to enable the bluetooth

        if(!bluetoothAdapter.isEnabled())
        {
            bluetoothAdapter.enable();
            Log.i("Log", "Bluetooth is Enabled");
            Toast.makeText(getApplicationContext(), "Bluetooth is ON", Toast.LENGTH_LONG).show();

        }
    }

    private void offBluetooth() {        // The method to disable the bluetooth

        if(bluetoothAdapter.isEnabled())
        {
            bluetoothAdapter.disable();
            Toast.makeText(getApplicationContext(), "Bluetooth is OFF", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeDiscoverable() {       // The method to make the device discoverable for any other device

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
        Log.i("Log", "Discoverable ");
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bluetooth, menu);
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

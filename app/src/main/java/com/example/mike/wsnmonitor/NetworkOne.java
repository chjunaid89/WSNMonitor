package com.example.mike.wsnmonitor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;


public class NetworkOne extends ActionBarActivity {

    String selectedImagePath;
    ImageButton sensorNode;
    FrameLayout.LayoutParams params;
    GestureDetector gestureDetector;
    View view;
    int totalSensorNodes;
    float mDownX;
    float mDownY;
    final float dragThreshold = 10;
    boolean isOnClick;
    int del_view_id;
    int check;
    int left, left1, left2, left3, left4, left5, left6, left7, left8, left9;
    int top, top1, top2, top3, top4, top5, top6, top7, top8, top9;
    int right, right1, right2, right3, right4, right5, right6, right7, right8, right9;
    int bottom, bottom1, bottom2, bottom3, bottom4, bottom5, bottom6, bottom7, bottom8, bottom9;
    int j = 100+1;
    int k = 100+1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_one);

        loadSensorNodesPositions();// To get the actual position of the Image Button from Shared Preferences.

        loadSensorNode();// To add the new Image button by clicking on 'Add Sensor Node'.

        gestureDetector = new GestureDetector(this, new SingleTapConfirm());  // To detect the single click on the Image button.

        loadNetworkImage();// To get the saved path of the background image and to set that image as a background.

    }

    public void loadNetworkImage() {

        SharedPreferences sp = getSharedPreferences("AppSharedPref", 1);
        selectedImagePath = sp.getString("ImagePath", "");
        if (selectedImagePath != null) {
            FrameLayout bg = (FrameLayout) findViewById(R.id.networkOneRL);
            Drawable drawable = new BitmapDrawable(getResources(), selectedImagePath);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                bg.setBackgroundDrawable(drawable);
            } else {
                bg.setBackground(drawable);
            }
        }
    }

    public void loadSensorNode() {

        SharedPreferences nbp1 = getSharedPreferences("AppSharedPref", 1);
        totalSensorNodes = nbp1.getInt("numButtons",101);
        del_view_id = nbp1.getInt("deleted",-1);
        check = nbp1.getInt("Check",-1);

        for (j = 0; j<=totalSensorNodes-100; j++) {
            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.networkOneRL);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            sensorNode = new ImageButton(this);
            sensorNode.setImageResource(R.drawable.node);
            sensorNode.setLayoutParams(lp);
            sensorNode.setTag(j);
            sensorNode.setId(j);
            frameLayout.addView(sensorNode);
            sensorNode.setOnTouchListener(getOnTouchDoSomething(sensorNode));
            if (j == 0) {
                if (left != 0 || top != 0 || right != 0 || bottom != 0) {
                    params = new FrameLayout.LayoutParams(100, 100);
                    params.setMargins(left, top, right, bottom);
                    sensorNode.setLayoutParams(params);
                }
            }
            if (j==1) {
                if (left1 != 0 || top1 != 0 || right1 != 0 || bottom1 != 0) {
                    params = new FrameLayout.LayoutParams(100, 100);
                    params.setMargins(left1, top1, right1, bottom1);
                    sensorNode.setLayoutParams(params);
                }
            }
            if (j==2) {
                if (left2 != 0 || top2 != 0 || right2 != 0 || bottom2 != 0) {
                    params = new FrameLayout.LayoutParams(100, 100);
                    params.setMargins(left2, top2, right2, bottom2);
                    sensorNode.setLayoutParams(params);
                }

            }
            if (j==3) {
                if (left3 != 0 || top3 != 0 || right3 != 0 || bottom3 != 0) {
                    params = new FrameLayout.LayoutParams(100, 100);
                    params.setMargins(left3, top3, right3, bottom3);
                    sensorNode.setLayoutParams(params);
                }
            }
            if (j==4) {
                if (left4 != 0 || top4 != 0 || right4 != 0 || bottom4 != 0) {
                    params = new FrameLayout.LayoutParams(100, 100);
                    params.setMargins(left4, top4, right4, bottom4);
                    sensorNode.setLayoutParams(params);
                }
            }
            if (j==5) {
                if (left5 != 0 || top5 != 0 || right5 != 0 || bottom5 != 0) {
                    params = new FrameLayout.LayoutParams(100, 100);
                    params.setMargins(left5, top5, right5, bottom5);
                    sensorNode.setLayoutParams(params);
                }
            }
            if (j==6) {
                if (left6 != 0 || top6 != 0 || right6 != 0 || bottom6 != 0) {
                    params = new FrameLayout.LayoutParams(100, 100);
                    params.setMargins(left6, top6, right6, bottom6);
                    sensorNode.setLayoutParams(params);
                }
            }
            if (j==7) {
                if (left7 != 0 || top7 != 0 || right7 != 0 || bottom7 != 0) {
                    params = new FrameLayout.LayoutParams(100, 100);
                    params.setMargins(left7, top7, right7, bottom7);
                    sensorNode.setLayoutParams(params);
                }
            }
            if (j==8) {
                if (left8 != 0 || top8 != 0 || right8 != 0 || bottom8 != 0) {
                    params = new FrameLayout.LayoutParams(100, 100);
                    params.setMargins(left8, top8, right8, bottom8);
                    sensorNode.setLayoutParams(params);
                }
            }
            if (j==9) {
                if (left9 != 0 || top9 != 0 || right9 != 0 || bottom9 != 0) {
                    params = new FrameLayout.LayoutParams(100, 100);
                    params.setMargins(left9, top9, right9, bottom9);
                    sensorNode.setLayoutParams(params);
                }
            }


        }
    }

    public void loadSensorNodesPositions() {

        SharedPreferences bp = getSharedPreferences("AppSharedPref", 1);
        left = bp.getInt("Left", 0);
        top = bp.getInt("Top", 0);
        right = bp.getInt("Right", 0);
        bottom = bp.getInt("Bottom", 0);

        left1 = bp.getInt("Left1", 0);
        top1 = bp.getInt("Top1", 0);
        right1 = bp.getInt("Right1", 0);
        bottom1 = bp.getInt("Bottom1", 0);

        left2 = bp.getInt("Left2", 0);
        top2 = bp.getInt("Top2", 0);
        right2 = bp.getInt("Right2", 0);
        bottom2 = bp.getInt("Bottom2", 0);

        left3 = bp.getInt("Left3", 0);
        top3 = bp.getInt("Top3", 0);
        right3 = bp.getInt("Right3", 0);
        bottom3 = bp.getInt("Bottom3", 0);

        left4 = bp.getInt("Left4", 0);
        top4 = bp.getInt("Top4", 0);
        right4 = bp.getInt("Right4", 0);
        bottom4 = bp.getInt("Bottom4", 0);

        left5 = bp.getInt("Left5", 0);
        top5 = bp.getInt("Top5", 0);
        right5 = bp.getInt("Right5", 0);
        bottom5 = bp.getInt("Bottom5", 0);

        left6 = bp.getInt("Left6", 0);
        top6 = bp.getInt("Top6", 0);
        right6 = bp.getInt("Right6", 0);
        bottom6 = bp.getInt("Bottom6", 0);

        left7 = bp.getInt("Left7", 0);
        top7 = bp.getInt("Top7", 0);
        right7 = bp.getInt("Right7", 0);
        bottom7 = bp.getInt("Bottom7", 0);

        left8 = bp.getInt("Left8", 0);
        top8 = bp.getInt("Top8", 0);
        right8 = bp.getInt("Right8", 0);
        bottom8 = bp.getInt("Bottom8", 0);

        left9 = bp.getInt("Left9", 0);
        top9 = bp.getInt("Top9", 0);
        right9 = bp.getInt("Right9", 0);
        bottom9 = bp.getInt("Bottom9", 0);

    }

    // The handler to perform actions in the response to long press on the Image button.
    final Handler handler = new Handler();
    Runnable mLongPressed = new Runnable() {
        public void run() {
            openContextMenu(view);
            Toast.makeText(getApplicationContext(), "Long Press", Toast.LENGTH_SHORT).show();

        }
    };
    //**********************************************************************************************


    // To set the Touch listener on the Image buttons (all in the loop).
    public View.OnTouchListener getOnTouchDoSomething(final ImageButton imageButton)  {
        return new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent me) {

                // The action if there is long press on the Image button.
                if(me.getAction() == MotionEvent.ACTION_DOWN) {
                    mLongPress(v,me);
                }
                // The action if there is a single click on the Image button.
                if (gestureDetector.onTouchEvent(me)) {
                    mSingleClick(v, me);
                    return true;
                }
                // The action for the drag and drop of the Image button.
                if (me.getAction() == MotionEvent.ACTION_MOVE && isOnClick && (Math.abs(mDownX - me.getX()) > dragThreshold || Math.abs(mDownY - me.getY()) > dragThreshold)) {
                    int view_Id = mDragAndDrop(v, me);
                    setSensorNodesPositions(view_Id,v,me);
                }
                return true;
            }

            public int mDragAndDrop(View v, MotionEvent me) {
                handler.removeCallbacks(mLongPressed);
                params = new FrameLayout.LayoutParams(v.getWidth(), v.getHeight());
                params.setMargins((int) me.getRawX() - v.getWidth() / 2, (int) (me.getRawY() - v.getHeight() * 1.5), (int) me.getRawX() - v.getWidth() / 2, (int) (me.getRawY() - v.getHeight() * 1.5));
                v.setLayoutParams(params);
                return v.getId();
            }

            public void setSensorNodesPositions(int view_id, View v, MotionEvent me) {
                if (view_id == 0){
                    left = (int) me.getRawX() - v.getWidth() / 2;
                    top = (int) (me.getRawY() - v.getHeight() * 1.5);
                    right = (int) me.getRawX() - v.getWidth() / 2;
                    bottom = (int) (me.getRawY() - v.getHeight() * 1.5);
                    SharedPreferences bp = getSharedPreferences("AppSharedPref", 1);
                    SharedPreferences.Editor editor = bp.edit();
                    editor.putInt("Left", left);
                    editor.putInt("Top", top);
                    editor.putInt("Right", right);
                    editor.putInt("Bottom", bottom);
                    editor.commit();
                }
                if (view_id == 1){
                    left1 = (int) me.getRawX() - v.getWidth() / 2;
                    top1 = (int) (me.getRawY() - v.getHeight() * 1.5);
                    right1 = (int) me.getRawX() - v.getWidth() / 2;
                    bottom1 = (int) (me.getRawY() - v.getHeight() * 1.5);
                    SharedPreferences bp = getSharedPreferences("AppSharedPref", 1);
                    SharedPreferences.Editor editor = bp.edit();
                    editor.putInt("Left1", left1);
                    editor.putInt("Top1", top1);
                    editor.putInt("Right1", right1);
                    editor.putInt("Bottom1", bottom1);
                    editor.commit();
                }
                if (view_id == 2){
                    left2 = (int) me.getRawX() - v.getWidth() / 2;
                    top2 = (int) (me.getRawY() - v.getHeight() * 1.5);
                    right2 = (int) me.getRawX() - v.getWidth() / 2;
                    bottom2 = (int) (me.getRawY() - v.getHeight() * 1.5);
                    SharedPreferences bp = getSharedPreferences("AppSharedPref", 1);
                    SharedPreferences.Editor editor = bp.edit();
                    editor.putInt("Left2", left2);
                    editor.putInt("Top2", top2);
                    editor.putInt("Right2", right2);
                    editor.putInt("Bottom2", bottom2);
                    editor.commit();
                }
                if (view_id == 3){
                    left3 = (int) me.getRawX() - v.getWidth() / 2;
                    top3 = (int) (me.getRawY() - v.getHeight() * 1.5);
                    right3 = (int) me.getRawX() - v.getWidth() / 2;
                    bottom3 = (int) (me.getRawY() - v.getHeight() * 1.5);
                    SharedPreferences bp = getSharedPreferences("AppSharedPref", 1);
                    SharedPreferences.Editor editor = bp.edit();
                    editor.putInt("Left3", left3);
                    editor.putInt("Top3", top3);
                    editor.putInt("Right3", right3);
                    editor.putInt("Bottom3", bottom3);
                    editor.commit();
                }
                if (view_id == 4){
                    left4 = (int) me.getRawX() - v.getWidth() / 2;
                    top4 = (int) (me.getRawY() - v.getHeight() * 1.5);
                    right4 = (int) me.getRawX() - v.getWidth() / 2;
                    bottom4 = (int) (me.getRawY() - v.getHeight() * 1.5);
                    SharedPreferences bp = getSharedPreferences("AppSharedPref", 1);
                    SharedPreferences.Editor editor = bp.edit();
                    editor.putInt("Left4", left4);
                    editor.putInt("Top4", top4);
                    editor.putInt("Right4", right4);
                    editor.putInt("Bottom4", bottom4);
                    editor.commit();
                }
                if (view_id == 5){
                    left5 = (int) me.getRawX() - v.getWidth() / 2;
                    top5 = (int) (me.getRawY() - v.getHeight() * 1.5);
                    right5 = (int) me.getRawX() - v.getWidth() / 2;
                    bottom5 = (int) (me.getRawY() - v.getHeight() * 1.5);
                    SharedPreferences bp = getSharedPreferences("AppSharedPref", 1);
                    SharedPreferences.Editor editor = bp.edit();
                    editor.putInt("Left5", left5);
                    editor.putInt("Top5", top5);
                    editor.putInt("Right5", right5);
                    editor.putInt("Bottom5", bottom5);
                    editor.commit();
                }
                if (view_id == 6){
                    left6 = (int) me.getRawX() - v.getWidth() / 2;
                    top6 = (int) (me.getRawY() - v.getHeight() * 1.5);
                    right6 = (int) me.getRawX() - v.getWidth() / 2;
                    bottom6 = (int) (me.getRawY() - v.getHeight() * 1.5);
                    SharedPreferences bp = getSharedPreferences("AppSharedPref", 1);
                    SharedPreferences.Editor editor = bp.edit();
                    editor.putInt("Left6", left6);
                    editor.putInt("Top6", top6);
                    editor.putInt("Right6", right6);
                    editor.putInt("Bottom6", bottom6);
                    editor.commit();
                }
                if (view_id == 7){
                    left7 = (int) me.getRawX() - v.getWidth() / 2;
                    top7 = (int) (me.getRawY() - v.getHeight() * 1.5);
                    right7 = (int) me.getRawX() - v.getWidth() / 2;
                    bottom7 = (int) (me.getRawY() - v.getHeight() * 1.5);
                    SharedPreferences bp = getSharedPreferences("AppSharedPref", 1);
                    SharedPreferences.Editor editor = bp.edit();
                    editor.putInt("Left7", left7);
                    editor.putInt("Top7", top7);
                    editor.putInt("Right7", right7);
                    editor.putInt("Bottom7", bottom7);
                    editor.commit();
                }
                if (view_id == 8){
                    left8 = (int) me.getRawX() - v.getWidth() / 2;
                    top8 = (int) (me.getRawY() - v.getHeight() * 1.5);
                    right8 = (int) me.getRawX() - v.getWidth() / 2;
                    bottom8 = (int) (me.getRawY() - v.getHeight() * 1.5);
                    SharedPreferences bp = getSharedPreferences("AppSharedPref", 1);
                    SharedPreferences.Editor editor = bp.edit();
                    editor.putInt("Left8", left8);
                    editor.putInt("Top8", top8);
                    editor.putInt("Right8", right8);
                    editor.putInt("Bottom8", bottom8);
                    editor.commit();
                }
                if (view_id == 9){
                    left9 = (int) me.getRawX() - v.getWidth() / 2;
                    top9 = (int) (me.getRawY() - v.getHeight() * 1.5);
                    right9 = (int) me.getRawX() - v.getWidth() / 2;
                    bottom9 = (int) (me.getRawY() - v.getHeight() * 1.5);
                    SharedPreferences bp = getSharedPreferences("AppSharedPref", 1);
                    SharedPreferences.Editor editor = bp.edit();
                    editor.putInt("Left9", left9);
                    editor.putInt("Top9", top9);
                    editor.putInt("Right9", right9);
                    editor.putInt("Bottom9", bottom9);
                    editor.commit();
                }
            }

            public void mSingleClick(View v, MotionEvent me) {
                handler.removeCallbacks(mLongPressed);
                int view_Id = v.getId();
                Toast.makeText(getApplicationContext(), "Id is" +view_Id, Toast.LENGTH_SHORT).show();
                if (view_Id == 0) {
                    Intent intent = new Intent(v.getContext(), SensorNode0.class);
                    startActivity(intent);
                }
            }

            public void mLongPress(View v, MotionEvent me) {
                registerForContextMenu(v);
                view = v;
                handler.postDelayed(mLongPressed, 1000);
                mDownX = me.getX();
                mDownY = me.getY();
                isOnClick = true;
            }
        };
    }
    //**********************************************************************************************


    // The activity to get the results in response to the intent started for the background image.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri uri = data.getData();
                        selectedImagePath = getImagePath(uri);       // To get the Image path.
                        SharedPreferences sp = getSharedPreferences("AppSharedPref", 1);    //open shared preferences with name AppSharedPref.
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("ImagePath", selectedImagePath);   //Store selectedImagePath with key "ImagePath". This key will be then used to retrieve data.
                        editor.commit();

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                                getContentResolver(), data.getData());
                        FrameLayout bg = (FrameLayout) findViewById(R.id.networkOneRL);
                        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            bg.setBackgroundDrawable(drawable);
                        } else {
                            bg.setBackground(drawable);
                        }
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                }
        }
    }
    //**********************************************************************************************



    // The method to get the path of the background image from the device.
    public String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(column_index);
        if (cursor != null) {
            cursor.close();
        }
        return imagePath;
    }
    //**********************************************************************************************



    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_network_one, menu);
        return super.onCreateOptionsMenu(menu);

    }
    //**********************************************************************************************


    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_device){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
            return true;
        }
        if(id == R.id.action_camera){
            return true;
        }
        if(id == R.id.action_add_sensor_node){
            SharedPreferences nbp1 = getSharedPreferences("AppSharedPref", 1);
            k = nbp1.getInt("numButtons",101);

            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.networkOneRL);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            sensorNode = new ImageButton(this);
            sensorNode.setImageResource(R.drawable.node);
            sensorNode.setLayoutParams(lp);
            sensorNode.setTag(k);
            sensorNode.setId(k);
            frameLayout.addView(sensorNode);
            k = k+1;

            SharedPreferences.Editor editor = nbp1.edit();
            editor.putInt("numButtons", k);
            editor.commit();
            Log.d("k is", Integer.toString(k));
            Toast.makeText(getApplicationContext(), "Button no. "+k, Toast.LENGTH_SHORT).show();

            finish();
            startActivity(getIntent());
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
    //**********************************************************************************************



    // To create the context menu and adding items in the menu.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select");
        menu.add(0, v.getId(), 0, "Delete");
        menu.add(0, v.getId(), 0, "WSN Control");
        menu.add(0, v.getId(), 0, "Remote Monitoring");
    }
    //**********************************************************************************************


    // To set the actions if the item is selected from the context menu.
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals("Delete")){
            deleteSensorNode(item.getItemId());
        }
        else if(item.getTitle().equals("WSN Control")){
            wsnControl(item.getItemId());
        }
        else if(item.getTitle().equals("Remote Monitoring")){
            remoteMonitoring(item.getItemId());
        }
        else {
            return false;
        }
        return true;
    }
    //**********************************************************************************************


    // The actions to perform with respect to the selection of the items from the context menu.
    public void deleteSensorNode(int id){
        SharedPreferences nbp1 = getSharedPreferences("AppSharedPref", 1);
        k = nbp1.getInt("numButtons",101);

        Toast.makeText(this, "Sensor Node Deleted", Toast.LENGTH_SHORT).show();
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.networkOneRL);
        frameLayout.removeView(view);
        del_view_id = view.getId();
        Toast.makeText(this, "Deleted Id is"+del_view_id, Toast.LENGTH_SHORT).show();
        k = k-1;

        SharedPreferences.Editor editor = nbp1.edit();
        editor.putInt("deleted", del_view_id);
        editor.putInt("numButtons", k);
        editor.commit();
        Log.d("k is", Integer.toString(k));
        Toast.makeText(getApplicationContext(), "Button no. "+k, Toast.LENGTH_SHORT).show();
    }
    public void wsnControl(int id){
        Toast.makeText(this, "WSN Control", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this.getApplicationContext(), WSNControl.class);
        startActivity(intent);
    }
    public void remoteMonitoring(int id){
        Toast.makeText(this, "Remote Monitoring", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this.getApplicationContext(), SensorNode0Internet.class);
        startActivity(intent);
    }

    //**********************************************************************************************


    // The class to detect if there is a single click on the Image button.
    public class SingleTapConfirm extends SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }
    //**********************************************************************************************
}


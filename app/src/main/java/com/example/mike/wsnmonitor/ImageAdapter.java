package com.example.mike.wsnmonitor;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    public final ArrayList<String> Sensor_Type;
    View gridView;
    TextView textView;
    ImageView imageView;
    String sensor;

    public ImageAdapter(Context context, ArrayList Sensor_Type) {
        this.context = context;
        this.Sensor_Type = Sensor_Type;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            gridView = new View(context);

            // get layout from sensor.xml
            gridView = inflater.inflate(R.layout.sensor, null);

            // set value into textview
            textView = (TextView) gridView.findViewById(R.id.grid_item_label);
            textView.setText(Sensor_Type.get(position));

            // set image based on selected text
            imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);

            sensor = Sensor_Type.get(position);

            if (sensor.equals("Temperature")) {
                imageView.setImageResource(R.drawable.temperature);
            } else if (sensor.equals("Air Pressure")) {
                imageView.setImageResource(R.drawable.pressure);
            } else if (sensor.equals("Accelerometer")) {
                imageView.setImageResource(R.drawable.accelerometer);
            } else if (sensor.equals("Humidity")) {
                imageView.setImageResource(R.drawable.humidity);
            } else if (sensor.equals("Vibration")) {
                imageView.setImageResource(R.drawable.vibration);
            } else if (sensor.equals("Light Intensity")) {
                imageView.setImageResource(R.drawable.light);
            } else if (sensor.equals("Acoustic")) {
                imageView.setImageResource(R.drawable.acoustic);
            }
            else {
                imageView.setImageResource(R.drawable.network_icon);
            }

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return Sensor_Type.size();
    }

    @Override
    public Object getItem(int position) {
        return Sensor_Type.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}

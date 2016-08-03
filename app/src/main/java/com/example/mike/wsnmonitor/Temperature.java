package com.example.mike.wsnmonitor;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Temperature extends ActionBarActivity {

    /** Called when the activity is first created. */
    public boolean isRecording = false;


    static final int frequency = 48000;//
    static final int channelConfiguration = AudioFormat.CHANNEL_IN_MONO;
    static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    static final int xMax = 4;//X,X?

    AudioManager am	= null;
    AudioRecord audioRecord;
    TextView buf_data;

    int recBufSize;//buffer
    ArrayList dataList = new ArrayList();
    int time = 0;
    int d = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        //
        recBufSize = bufferInitialization();
        audioRecord = audiorecordInitialization();
        //
        buf_data = (TextView)this.findViewById(R.id.temperature);
        startRecordAndDecode(audioRecord, recBufSize);
    }

    public int bufferInitialization() {
        recBufSize = AudioRecord.getMinBufferSize(frequency,
                channelConfiguration, audioEncoding);
        return recBufSize;
    }

    public AudioRecord audiorecordInitialization() {
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency,
                channelConfiguration, audioEncoding, recBufSize);

        am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        am.setMode(AudioManager.MODE_IN_COMMUNICATION);
        am.startBluetoothSco();
        am.setBluetoothScoOn(true);
        return audioRecord;
    }

    public class WebService extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            postDataToServer(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Double result) {
            Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
        }

    }

    public void postDataToServer(String sensorData1){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://www.medan-sites.com/ACL/ACL_data.php");

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("messageData", sensorData1));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpClient.execute(httpPost);
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }
    }


    public void startRecordAndDecode(AudioRecord audioRecord, int recBufSize) {
        short[] tmpBuf, data, dataBuf, dataBuf_a, dataBuf_b;
        int counter_1 = 0;
        int counter_0 = 0;
        int ones_count = 0;
        int a = 0;
        int count_00 = 0;
        int count_11 = 0;
        int b = 0;
        int even_00_11 = 0;
        int zeros_count = 0;
        int start_counter = 0;
        int listRow_count = 0;
        short[] dataArray = new short[1000];
        int col = 0;
        int maxSize = 0;
        int msgNum = 0;
        int msgSize = 0;
        int charNum = 0;
        char character;
        StringBuilder message = new StringBuilder();
        int messageSize;

        isRecording = true;

        short[] buffer = new short[recBufSize];
        audioRecord.startRecording();//
        while (isRecording) {
            // MIC
            int bufferReadResult = audioRecord.read(buffer, 0,
                    recBufSize);
            tmpBuf = new short[bufferReadResult / xMax];
            for (int i = 0, ii = 0; i < tmpBuf.length; i++, ii = i
                    * xMax) {
                tmpBuf[i] = buffer[ii];
            }

            data = new short[tmpBuf.length];
            dataBuf = new short[tmpBuf.length];
            dataBuf_a = new short[tmpBuf.length];
            dataBuf_b = new short[51];
            for (int j = 0; j<tmpBuf.length; j++)
            {
                data[j] = tmpBuf[j];

                if (data[j] > 0) {
                    dataBuf[j] = 1;

                    counter_0 = 0;
                    counter_1 = counter_1 + 1;
                }

                else if (data[j] < 0) {
                    dataBuf[j] = 0;

                    counter_1 = 0;
                    counter_0 = counter_0 + 1;
                }

                if(counter_1 == 2 || counter_1 == 8){

                    ones_count = ones_count + 1;
                    dataBuf_a[a] = dataBuf[j];
                    if(ones_count == 3){
                        a = a-1;
                    }

                    if (a == 1){
                        if (dataBuf_a[1] == 0 && dataBuf_a[0] == 0){
                            count_00 = 2;
                            dataBuf_b[b] = 0;
                            b = b + 1;
                        }
                        else if(dataBuf_a[1] == 1 && dataBuf_a[0] == 1){
                            count_11 = 2;
                            dataBuf_b[b] = 1;
                            b = b + 1;
                        }
                    }

                    if(count_00 == 2 || count_11 == 2){
                        if((a % 2) == 0)
                            if (dataBuf_a[a - 1] == 0 && dataBuf_a[a] == 1){
                                dataBuf_b[b] = 1;
                                b = b + 1;
                            }
                            else if (dataBuf_a[a - 1] == 1 && dataBuf_a[a] == 0){
                                dataBuf_b[b] = 0;
                                b = b + 1;
                            }
                    }


                    if(count_00 < 2 && count_11 < 2){
                        if ((a % 2) != 0) {

                            if(dataBuf_a[a - 1] == 0 && dataBuf_a[a] == 0){
                                count_00 = 2;
                                count_11 = 2;
                                even_00_11 = dataBuf_b.length-1;
                                for(int e = even_00_11; e >= 0; e--){
                                    if(dataBuf_b[e] == 0){
                                        dataBuf_b[e] = 1;
                                    }
                                    else if(dataBuf_b[e] == 1){
                                        dataBuf_b[e] = 0;
                                    }
                                }
                            }

                            if (dataBuf_a[a - 1] == 0 && dataBuf_a[a] == 1){
                                dataBuf_b[b] = 1;
                                b = b + 1;
                            }
                            else if (dataBuf_a[a - 1] == 1 && dataBuf_a[a] == 0){
                                dataBuf_b[b] = 0;
                                b = b + 1;
                            }
                        }
                    }
                    a = a + 1;
                }

                else if (counter_0 == 2 || counter_0 == 8){

                    ones_count = 0;
                    dataBuf_a[a] = dataBuf[j];
                    if(zeros_count == 3){
                        a = a-1;
                    }


                    if (a == 1){
                        if (dataBuf_a[1] == 0 && dataBuf_a[0] == 0){
                            count_00 = 2;
                            dataBuf_b[b] = 0;
                            b = b + 1;
                        }
                        else if(dataBuf_a[1] == 1 && dataBuf_a[0] == 1){
                            count_11 = 2;
                            dataBuf_b[b] = 1;
                            b = b + 1;
                        }
                    }

                    if(count_00 == 2 || count_11 == 2){
                        if((a % 2) == 0){
                            if (dataBuf_a[a - 1] == 0 && dataBuf_a[a] == 1){
                                dataBuf_b[b] = 1;
                                b = b + 1;
                            }
                            else if (dataBuf_a[a - 1] == 1 && dataBuf_a[a] == 0){
                                dataBuf_b[b] = 0;
                                b = b + 1;
                            }
                        }
                    }

                    if(count_00 < 2 && count_11 < 2){
                        if ((a % 2) != 0) {

                            if(dataBuf_a[a - 1] == 0 && dataBuf_a[a] == 0){
                                count_00 = 2;
                                count_11 = 2;
                                even_00_11 = dataBuf_b.length-1;
                                for(int e = even_00_11; e >= 0; e--){
                                    if(dataBuf_b[e] == 0){
                                        dataBuf_b[e] = 1;
                                    }
                                    else if(dataBuf_b[e] == 1){
                                        dataBuf_b[e] = 0;
                                    }
                                }
                            }

                            if (dataBuf_a[a - 1] == 0 && dataBuf_a[a] == 1) {
                                dataBuf_b[b] = 1;
                                b = b + 1;

                            }
                            else if (dataBuf_a[a - 1] == 1 && dataBuf_a[a] == 0){
                                dataBuf_b[b] = 0;
                                b = b + 1;
                            }
                        }
                    }
                    a = a + 1;
                }
            }
            Log.d("Decoded Buffer: ", Arrays.toString(dataBuf_b));
            Log.d("DecodedBuf Length: ", Integer.toString(b));
            a = 0;
            count_00 = 0;
            count_11 = 0;

            for(int k = 0; k<=b-1; k++) {
                dataList.add(dataBuf_b[k]);

                if (dataBuf_b[k] == 0) {
                    start_counter = start_counter + 1;
                } else if (dataBuf_b[k] == 1) {
                    start_counter = 0;
                }

                if (start_counter == 10) {
                    start_counter = 0;
                    listRow_count = listRow_count + 1;
                    k = k + 1;
                }
                if(listRow_count == 1){
                    dataArray[col] = dataBuf_b[k];
                    col = col + 1;
                    maxSize = col;
                }
                if(listRow_count > 1){
                    System.out.println();
                    listRow_count = 1;
                    k = k - 1;
                    col = col - 9;
                    msgNum = msgNum + 1;
                    if(msgNum == 1){
                        msgSize = col;
                    }
                }
            }
            Log.d("Data Array: ", Arrays.toString(dataArray));
            Log.d("Msg Length: ", Integer.toString(msgSize));
            Log.d("dataList: ", dataList.toString());

            StringBuilder binaryBuf = new StringBuilder();
            if (msgSize > 0) {
                for (int q = 0; q <= msgSize - 1; q++) {
                    binaryBuf.append(dataArray[q]);
                    charNum = charNum + 1;
                    if (charNum == 7) {
                        charNum = 0;
                        binaryBuf.reverse();
                        String binary = binaryBuf.toString();
                        int ascii = Integer.parseInt(binary, 2);
                        character = (char) ascii;
                        message.append(character);
                        Log.d("Character Binary: ", binaryBuf.toString());
                        Log.d("Msg Characters: ", Character.toString(character));
                        Log.d("Loop Length: ", Integer.toString(q));
                        binaryBuf.setLength(0);
                    }
                }
                Log.d("Message: ", message.toString());
                if (message.charAt(0) == 'U' && message.charAt(message.length() - 1) == 'U'){
                    buf_data = (TextView) this.findViewById(R.id.temperature);
                    buf_data.setText(message.toString());
                    audioRecord.stop();
                    isRecording = false;
                    new WebService().execute(message.toString());
                    d = d + 1;

                }
                if(d == 0) {
                    audioRecord.stop();
                    isRecording = false;
                }

                messageSize = message.length();
                charNum = 0;
                message.setLength(0);
                if (time < 10){
                    handler.postDelayed(run, 300);
                }
                else if (time == 10){
                    audioRecord.stop();
                    isRecording = false;
                }
            }
            b = 0;
            d = 0;

        }
    }

    final Handler handler = new Handler();
    Runnable run = new Runnable() {
        @Override
        public void run() {
            startRecordAndDecode(audioRecord, recBufSize);
            time = time + 1;
        }
    };

    public void stopRecord(){
        isRecording = false;
        audioRecord.stop();
    }
}


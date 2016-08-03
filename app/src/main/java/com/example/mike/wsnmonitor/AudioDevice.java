package com.example.mike.wsnmonitor;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class AudioDevice {

    public static final int SAMPLING_RATE = 44100;
    AudioManager am;
    AudioTrack track;
    short[] buffer = new short[1024];
    Context context;

    public AudioDevice(Context context)
    {
        this.context = context;
        int minSize =AudioTrack.getMinBufferSize( SAMPLING_RATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT );
        track = new AudioTrack( AudioManager.MODE_IN_COMMUNICATION, SAMPLING_RATE,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
                minSize, AudioTrack.MODE_STREAM);

        am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        am.setMode(AudioManager.MODE_IN_COMMUNICATION);
        am.startBluetoothSco();
        am.setBluetoothScoOn(true);

        track.play();
    }

    public void writeSamples(float[] samples)
    {
        am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        am.setMode(AudioManager.MODE_IN_COMMUNICATION);
        am.startBluetoothSco();
        am.setBluetoothScoOn(true);
        fillBuffer( samples );
        track.write( buffer, 0, samples.length );
        track.release();
    }

    private void fillBuffer( float[] samples )
    {
        if( buffer.length < samples.length )
            buffer = new short[samples.length];

        for( int i = 0; i < samples.length; i++ )
            buffer[i] = (short)Math.round(samples[i] * (float)Short.MAX_VALUE);
    }

}

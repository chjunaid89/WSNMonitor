package com.example.mike.wsnmonitor;

import android.content.Context;

public class AsciiGenerator {

    private AudioDevice device;
    Context context;


    public AsciiGenerator(Context context) {
        this.context = context;
        device = new AudioDevice(this.context);
    }


    void playDOWN(int initialFreq, int finalFreq, double impulseDuration, int Code, int LengthBit) {

        AudioDevice device = new AudioDevice(this.context);
        int LengthBitInSamples = LengthBit*44;
        int NumberOfBits= 10;
        int LimitOfInterestingSamples=NumberOfBits*LengthBitInSamples;

        double k = (double)(finalFreq - initialFreq) / (LengthBit/1000.0);
        float samples[] = new float[1024];
        double currentFreq = finalFreq;
        double phase;
        double t;
        int j = 0;
        int CountBits = 0;
        int IndexBit=1;
        Integer digits[]= new Integer[64];

        digits[1] = 1; // Start bit
        digits[10] = 1;// Stop bit

        for(int n=NumberOfBits-1, m=2; n>1; m++, n--)
        {

            digits[m]=Code%2;
            Code/=2;
            System.out.println("Bit value"+digits[m]);

        }



        for (int i = 0; i < (int)(impulseDuration/1000.0 * AudioDevice.SAMPLING_RATE); i++, CountBits++) {

            if(i>LimitOfInterestingSamples)
            {
                samples[j++] = 0;

            }
            else{

                if(CountBits>LengthBitInSamples)
                {
                    CountBits=0;
                    if(IndexBit<NumberOfBits)
                        IndexBit++;


                }

                if(digits[IndexBit]==1) //This means 1
                {

                    t = (double)CountBits / (double)AudioDevice.SAMPLING_RATE;
                    currentFreq = initialFreq + 0.5 * k * t;
                    phase = 2.0 * Math.PI * (currentFreq) * t;

                }
                else{ //This means 0

                    phase = 0;
                }

                samples[j++] = (float) Math.sin(phase);

            }

            if (j == 1024) {
                device.writeSamples( samples );
                j = 0;
            }

        }

    }

}

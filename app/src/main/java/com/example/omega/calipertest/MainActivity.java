package com.example.omega.calipertest;


import android.content.Intent;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {


    public static android.os.Handler handler;

    Double lastValue = 0.000;
    Double referenceToZero = 0.000;
    TextView leftReading;
    TextView rightReading;
    TextView leftAbsolute;
    Button LeftResetZero;
     Button leftZero;
    DecimalFormat df = new  DecimalFormat("0.000");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        leftReading = (TextView) findViewById(R.id.leftReading);
        rightReading = (TextView) findViewById(R.id.rightReading);
        leftAbsolute = (TextView) findViewById(R.id.leftAbsolute);

        Intent intent = new Intent(this, NetCode.class);
        startService(intent);








        handler = new android.os.Handler() {


            @Override

            public void handleMessage(Message msg) {
               //Get Bundle data from Esp8266 left
                Boolean noConLeft = msg.getData().getBoolean("leftNoData");
                String leftData = msg.getData().getString("leftData");

                Boolean noConRight = true;
                //Right Esp8266 data
               if(noConRight){
                   rightReading.setTextColor(Color.RED);
                   rightReading.setText("N/A");
               }

                //Left Esp8266 data
                if (noConLeft) {

                    leftReading.setTextColor(Color.RED);
                    leftReading.setText("N/A");
                    referenceToZero = 0.000;
                    leftAbsolute.setText("Absolute Reading: 0.000");




                } else {




                    lastValue = Double.valueOf(leftData);
                    leftReading.setTextColor(Color.WHITE);
                    leftReading.setText(String.valueOf(df.format(lastValue - referenceToZero)));


                    leftAbsolute.setText(String.format("Absolute Reading: %s", df.format(lastValue)));

                }


            }
        };

    }


    public void leftButtonZero(View view) {
        referenceToZero = lastValue;

        LeftResetZero = (Button) findViewById(R.id.LeftResetZero);
        LeftResetZero.setTextColor(Color.WHITE);
        System.out.println("Short");

    }

    public void leftReset(View view) {
        referenceToZero = 0.000;
        LeftResetZero = (Button) findViewById(R.id.LeftResetZero);
        LeftResetZero.setTextColor(Color.argb(255,0,0,0));

    }

    public void zeroBoth(View view) {
        referenceToZero = lastValue;
    }


//    private class NetworkingClass extends AsyncTask<Void, Double, Void> {
//
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            while (true) {
//
//                Socket soc = new Socket();
//
//
//                try {
//                    //SocketAddress esp = new InetSocketAddress("192.168.43.22", 3378);
//
//                    InetSocketAddress esps = new InetSocketAddress("192.168.1.253", 3378);
//
//                    soc.setSoTimeout(3000);
//                    soc.connect(esps, 3000);
//
//                        Scanner scan = new Scanner(soc.getInputStream());
//
//
//
//                        rawin = scan.nextLine();
//
//
//                        publishProgress(Double.valueOf(rawin));
//                        lastValue = Double.valueOf(rawin);
//                        noData = false;
//                        System.out.println(lastValue);
//                        System.out.println(referenceToZero);
//                        scan.close();
//
//
//                    soc.close();
//                } catch (NoSuchElementException e) {
//                    e.printStackTrace();
//                    System.out.println("Past Iterator");
//
//                    publishProgress(lastValue);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    System.out.println("Timed out");
//                    noData = true;
//                    publishProgress(lastValue);
//                }
//
//
//            }
//
//
//        }
//
//        @Override
//        protected void onProgressUpdate(Double... dat) {
//
//            TextView leftReading = (TextView) findViewById(R.id.leftReading);
//            TextView rightReading = (TextView) findViewById(R.id.textView2);
//            TextView leftDebug = (TextView) findViewById(R.id.leftAbsolute);
//            TextView leftDebug2 = (TextView) findViewById(R.id.textView6);
//            rightReading.setTextColor(Color.RED);
//            if (noData) {
//
//                leftReading.setTextColor(Color.RED);
//            } else {
//
//                leftReading.setTextColor(Color.WHITE);
//            }
//
//
//            leftDebug.setText("Absolute Reading: " + df.format(dat[0]));
//
//            leftReading.setText(String.valueOf(df.format(dat[0] - referenceToZero)));
//            leftDebug2.setText("Freeze Value: " + df.format(referenceToZero));
//
//        }
//    }


}










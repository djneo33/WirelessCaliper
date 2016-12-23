package com.example.omega.calipertest;

import android.app.IntentService;
import android.content.Intent;

import android.os.Bundle;
import android.os.Message;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import java.util.Scanner;


public class NetCode extends IntentService {


    public NetCode() {
        super("NetCode");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this) {

            while (true) {
                try {


                    InetSocketAddress addr = new InetSocketAddress("192.168.1.253", 3378);
                    Socket soc = new Socket();

                    soc.connect(addr, 3000);
                    Bundle bundle = new Bundle();
                    Message message = Message.obtain();
                    Scanner scan = new Scanner(soc.getInputStream());



                    String data = scan.nextLine();
                    System.out.println(data);

                    bundle.putString("leftData", data);
                    message.setData(bundle);
                    MainActivity.handler.sendMessage(message);


                    Thread.sleep(50);
                    scan.close();
                    soc.close();




                } catch (SocketTimeoutException e) {

                    Bundle bundle = new Bundle();
                    Message message = Message.obtain();
                    bundle.putBoolean("leftNoData", true);
                    message.setData(bundle);
                    MainActivity.handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}

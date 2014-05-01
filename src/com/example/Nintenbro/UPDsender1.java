package com.example.Nintenbro;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


/**
 * Created by student on 4/15/14.
 */
public class UPDsender1 {
    private static int port=0;
    private static String ipAddress;
    public static boolean isRunning = false;


    public static boolean forward = false;
    public static boolean reverse = false;
    public static boolean left = false;
    public static boolean right = false;
    public static boolean stop = false;
    public static boolean realign = false;
    public static boolean authenticate = false;



    public static void logandSendPacket(String packetContents, String ipAddress, int port) {
        try {
            DatagramSocket socket = new DatagramSocket(port);

            InetAddress address = InetAddress.getByName(ipAddress);

            int messageLength = packetContents.length();
            byte[] byteMessage = packetContents.getBytes();

            DatagramPacket packet = new DatagramPacket(byteMessage, messageLength, port);

            socket.send(packet);

            Log.d("UDP", String.format("Send packet %d to address %s", packet.toString(),port,ipAddress));
        }
        catch(IOException e) {
            Log.e("UDP", "IOException occured during packet send",e);
        }
    }

    public static String getIpAddress() {
        return ipAddress;
    }

    public static void setIpAddress(String ipAddress) {
        UPDsender1.ipAddress = ipAddress;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        UPDsender1.port = port;
    }
    public static boolean isRunning() {
        return isRunning;
    }

    public static void setRunningState(boolean isRunning) {
        UPDsender1.isRunning = isRunning;
    }





    public static void beginUdpLoop() {

        final String FORWARD = "d";
        final String REVERSE = "b";
        final String LEFT = "l";
        final String RIGHT = "r";
        final String STOP = "p";
        final String REALIGN = "s";
        final String AUTH = "hello";

        // Spawn a new thread to send UDP messages
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                while (isRunning) {
                    if (forward) {
                        logandSendPacket(FORWARD, ipAddress, port);

                    }
                    else if (reverse) {
                        logandSendPacket(REVERSE, ipAddress, port);
                    }

                    if (left) {
                        logandSendPacket(LEFT, ipAddress, port);
                    }

                    else if (right) {
                        logandSendPacket(RIGHT, ipAddress, port);
                    }

                    else if (stop) {
                        logandSendPacket(STOP, ipAddress, port);
                        stop = false;
                    }

                    else if (realign) {
                        logandSendPacket(REALIGN, ipAddress, port);
                    }
                    else if (authenticate) {
                        logandSendPacket(AUTH, ipAddress, port);
                        authenticate = false;
                    }
                }

                return null;
            }
        }.execute();
    }
}



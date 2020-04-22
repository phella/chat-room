package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Subscriber extends Thread {
    private ArrayList<String> messages;
    private PrintWriter out;
    private int id;

    Subscriber(int id){
        messages = new ArrayList<String>();
        this.id = id;
    }

    public void addMessage(String message){
        messages.add(message);
    }

    public void run(){
        while(true) {
            if (!messages.isEmpty()) {
                byte[] sendData = new byte[1024];
                sendData = (messages.get(0)).getBytes();
                messages.remove(0);
                try {
                    InetAddress IPAddress = InetAddress.getByName("localhost");
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, id);
                    DatagramSocket clientSocket = new DatagramSocket();
                    clientSocket.send(sendPacket);
                } catch (Exception e) {

                }
            } else {
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    // e.printStackTrace();
                }
            }
        }
    }
}

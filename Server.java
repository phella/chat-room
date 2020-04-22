package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static final int SUBSRIBE_PORT = 3000;
    public static final int BROADCAST_PORT = 3001;
    public static  final int MAX_SUBS = 2;
    private static int  ID= 7000;
    private static ArrayList<Subscriber> subscribers1;
    private static ArrayList<Subscriber> subscribers2;

    public static void main(String[] args) {
        System.out.println("The server started .. ");
        subscribers1 = new ArrayList<Subscriber>();
        subscribers2 = new ArrayList<Subscriber>();
        new Thread() {
            public void run() {
                try {
                    ServerSocket ss = new ServerSocket(SUBSRIBE_PORT);
                    while (true) {
                        Socket socket = ss.accept();
                        Subscriber sub = new Subscriber(ID);
                        sub.start();
                        System.out.println("New client connected ");
                        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        String choice = br.readLine();
                        if( choice.equals("1") && subscribers1.size() < MAX_SUBS) {
                            subscribers1.add(sub);
                        }else if(choice.equals("2") && subscribers2.size() < MAX_SUBS) {
                            subscribers2.add(sub);
                        }   else {
                            out.println("Wrong choice choose another group");
                            out.close();
                            socket.close();
                            continue;
                        }
                        out.println("You are successfully added to the group Section-" + choice +"with ID = "+Integer.toString(ID++));
                        out.close();
                        br.close();
                        socket.close();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread() {
            public void run() {
                try {
                    ServerSocket ss = new ServerSocket(BROADCAST_PORT);
                    while (true) {
                        Socket socket = ss.accept();
                        System.out.println("New admin connected");
                        Publisher pub = new Publisher(socket);
                        pub.start();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void broadcast(String message, int groupNo){
        ArrayList<Subscriber> subs;
        if( groupNo == 1){
            subs  = new ArrayList<Subscriber>( subscribers1);
        } else {
            subs  = new ArrayList<Subscriber>( subscribers2);
        }
        for(int i = 0 ; i < subs.size() ; i++){
            subs.get(i).addMessage(message);
            subs.get(i).interrupt();
        }
    }
}

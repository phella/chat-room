package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Publisher extends Thread{
    private Socket socket;
    private int groupNo;

    Publisher(Socket socket){
        this.socket = socket;
    }

    public void run(){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String choice = br.readLine();
            if (choice.equals("1") || choice.equals("2")) {
                groupNo = Integer.parseInt(choice);
            } else {
                // Wrong Choice
            }
            System.out.println("Admin is assgined to group "+ choice);
            out.println("What is the message to be broadcast?");
            String message;
            while ((message = br.readLine()) != null) {
                System.out.println("Recieved message from admin: " + message);
                Server.broadcast(message , groupNo);
            }
            br.close();
            out.close();
            socket.close();
        } catch (Exception e){

        }
    }
}

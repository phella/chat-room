package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    private static int id;

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome, Which group do you want to join?");
        System.out.println("    -Press 1 to join group1");
        System.out.println("    -Press 2 to join group2");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();

        Socket s = new Socket("localhost", 3000);
        BufferedReader socketReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        out.println(choice);
        String response = socketReader.readLine();
        System.out.println("Server responded: " + response);
        out.close();
        socketReader.close();
        s.close();
        try {
            id = Integer.parseInt(response.substring(response.length() - 4));
        } catch (Exception e){
            System.out.println(response);
            return;
        }
        DatagramSocket serverSocket = new DatagramSocket(id);
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        try {
            while (true) {
                Arrays.fill(receiveData , (byte)0);
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());
                System.out.println("RECEIVED: " + sentence);
            }
        }  catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            serverSocket.close();
        }

    }
}

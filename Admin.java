package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Admin {

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome, Which group do you want to join?");
        System.out.println("    -Press 1 to join group1");
        System.out.println("    -Press 2 to join group2");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();

        Socket s = new Socket("localhost", 3001);
        BufferedReader socketReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        out.println(choice);
        String response = socketReader.readLine();
        System.out.println("Server responded: "+ response);
        String message;

        while((message = scanner.nextLine()) != null){
            out.println(message);
        }
        scanner.close();
        socketReader.close();
        out.close();
        s.close();
    }
}

package com.codecool;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Scanner;

public class PeerCreator {



    public Peer get() {
        ServerSocket serverSocket = setUpServer();
        Integer myPort = serverSocket.getLocalPort();
        String path = askForDirectory();

        return new Peer(new HashSet<>(myPort), serverSocket, path);
    }

    private ServerSocket setUpServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(0);
            System.out.println("You are server on port: " + serverSocket.getLocalPort());
            return serverSocket;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String askForDirectory() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Set path for directory to exchange");
        return scanner.nextLine();
    }


}

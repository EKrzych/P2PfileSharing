package com.codecool;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Scanner;

public class App {


    public static void main( String[] args ) {

        new App().start();

    }

    private void start() {
        ServerSocket serverSocket = setUpServer();
        Peer peer = createPeer(serverSocket);
        Finder finder = new Finder(peer);

        new Thread(new ClientPart(new Downloader(finder))).start();
        new Thread(new ServerPart(finder, serverSocket)).start();
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

    public Peer createPeer(ServerSocket serverSocket) {

        String path = askForDirectory();

        Peer peer = new Peer(new HashSet<>(), path, serverSocket.getLocalPort(), serverSocket.getInetAddress().getHostAddress());
        peer.getFriends().add(peer);
        return  peer;
    }

    private String askForDirectory() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Set path for directory to exchange");
        return scanner.nextLine();
    }


}

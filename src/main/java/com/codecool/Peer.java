package com.codecool;

import java.io.*;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Peer {
    private String directory;
    private int SOCKET_PORT;
    private int PEER_PORT = 3333;
    private int myPort;


    public Peer(String directory, int SOCKET_PORT) {
        this.directory = directory;
        this.SOCKET_PORT = SOCKET_PORT;
    }

    public boolean getFile(String fileName, Peer fromPeer) {
        return true;
    }

//    public boolean sendFile(String fileName, Peer toPeer) {
//        return true;
//    }


    public List<String> getFileNamesFromFolder() {
        return getFileNamesFromFolder(directory);
    }

    private List<String> getFileNamesFromFolder(String folder) {
        List<String> fileNames = new ArrayList<>();

        File folderFile = new File(folder);
        File[] listOfFiles = folderFile.listFiles();

        if (listOfFiles == null) {
            return fileNames;
        }

        for (File file : listOfFiles) {
            if (file.isFile()) {
                fileNames.add(file.getName());
            } else if (file.isDirectory()) {
                fileNames.addAll(getFileNamesFromFolder(file.getName()));
            }
        }
        return fileNames;
    }

    public void start() {

        try {
            Socket socket = new Socket(InetAddress.getLocalHost().getHostAddress(), SOCKET_PORT);
            this.myPort = socket.getInputStream().read() + 1024;
            new Thread(() ->  {
                downloadFile(socket);
            }).start();

            new Thread(() ->  {
                sendFile();
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void sendFile() {
        ServerSocket serverSocket = null;
        try {
            System.out.println("My port" + myPort);
            serverSocket = new ServerSocket(myPort);
            System.out.println("waiting for connection");
            Socket socket = serverSocket.accept();//jeden watek ktory skceptuje peery i drugi ktory przesyla wiadomosci
            System.out.println("get host name" + socket.getInetAddress().getHostName());
            System.out.println("asked for file: " + socket.getInputStream().read());
            socket.getOutputStream().write(10);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void downloadFile(Socket socket) {
        System.out.println("in download in client");
        Scanner sc = new Scanner(System.in);
        System.out.println("Download files? yes?");
        if(sc.nextLine().equals("yes")) {
            try {

                System.out.println("socket get adres: " + socket.getInetAddress().getHostAddress() );
                DataOutputStream dOut =  new DataOutputStream(socket.getOutputStream());
                System.out.println("before writting 2");
                dOut.write(2);
                System.out.println("Have sent!!");
              //  dOut.flush();
                DataInputStream dIn = new DataInputStream(socket.getInputStream());
//                while(dIn.available() == -1) {
//                    wait(200);
//                }
                System.out.println("___________________________");
                int peerPort = dIn.read();
                peerPort += 1024;
                System.out.println("Read peer address: " + peerPort);
                System.out.println("Our End!!!!!!");

                Socket socketToGetFile = new Socket(InetAddress.getLocalHost().getHostAddress(), peerPort);
                socketToGetFile.getOutputStream().write(2);
                System.out.println("Got file: " + socketToGetFile.getInputStream().read());



            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
}

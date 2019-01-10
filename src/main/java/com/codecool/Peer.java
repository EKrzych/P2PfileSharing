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
    private Integer myPort;
    private Socket server;

    public Peer(int portToConnect) {
       connectIfPossible(portToConnect);
       setUpServer();
    }

    private void setUpServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(0);
            myPort = serverSocket.getLocalPort();
            System.out.println("You are server on port" + myPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectIfPossible(int portToConnect) {
        try {
            this.server = new Socket(InetAddress.getLocalHost().getHostAddress(), portToConnect);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
           ObjectOutputStream os = new ObjectOutputStream(server.getOutputStream());
           os.writeObject("Let's connect!");
            System.out.println("You have connected!!");
        } catch (IOException e) {
            System.out.println("There is no such port - you are the first one.");
            e.printStackTrace();
        }


    }

    public boolean getFile(String fileName, Peer fromPeer) {
        return true;
    }

//    public boolean sendFile(String fileName, Peer toPeer) {
//        return true;
//    }


//    public List<String> getFileNamesFromFolder() {
//        return getFileNamesFromFolder(directory);
//    }

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

//    public void start() {
//        try {
//            new Thread(() ->  {
//               // downloadFile();
//            }).start();
//
//            new Thread(() ->  {
//                sendFile();
//            }).start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


  //  }

    private void sendFile() {
        try {
            System.out.println("My port" + myPort);
            ServerSocket serverSocket = new ServerSocket(myPort);
            while (true) {
                System.out.println("waiting for connection");
                Socket socket = serverSocket.accept();//jeden watek ktory skceptuje peery i drugi ktory przesyla wiadomosci
                System.out.println("get host name" + socket.getInetAddress().getHostName());
                System.out.println("asked for file: " + socket.getInputStream().read());
                socket.getOutputStream().write(10);
            }
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
                ObjectInputStream oIn = new ObjectInputStream(socket.getInputStream());

                System.out.println("___________________________");
                int peerPort = (Integer) oIn.readObject();
                peerPort += 1024;
                System.out.println("Read peer address: " + peerPort);
                System.out.println("Our End!!!!!!");

                Socket socketToGetFile = new Socket(InetAddress.getLocalHost().getHostAddress(), peerPort);
                socketToGetFile.getOutputStream().write(2);
                System.out.println("Got file: " + socketToGetFile.getInputStream().read());



            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }



    }
}

package com.codecool;

import java.io.*;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Peer {

    private Set<Integer> serverPorts;///TODO change into hashMap!!!
    private ServerSocket serverSocket;
    private String pathToFolder;

    public Peer(Set<Integer> serverPorts, ServerSocket serverSocket, String pathToFolder) {
        this.serverPorts = serverPorts;

        this.serverSocket = serverSocket;
        this.pathToFolder = pathToFolder;
    }

    public Set<Integer> getServerPorts() {
        return serverPorts;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public String getPathToFolder() {
        return pathToFolder;
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





//    private void sendFile() {
//        try {
//            System.out.println("My port" + myPort);
//            ServerSocket serverSocket = new ServerSocket(myPort);
//            while (true) {
//                System.out.println("waiting for connection");
//                Socket socket = serverSocket.accept();//jeden watek ktory skceptuje peery i drugi ktory przesyla wiadomosci
//                System.out.println("get host name" + socket.getInetAddress().getHostName());
//                System.out.println("asked for file: " + socket.getInputStream().read());
//                socket.getOutputStream().write(10);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

//    public void downloadFile(Socket socket) {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Download files? yes?");
//        if(sc.nextLine().equals("yes")) {
//            try {
//                DataOutputStream dOut =  new DataOutputStream(socket.getOutputStream());
//                System.out.println("before writting 2");
//                dOut.write(2);
//                System.out.println("Have sent!!");
//              //  dOut.flush();
//                ObjectInputStream oIn = new ObjectInputStream(socket.getInputStream());
//
//                System.out.println("___________________________");
//                int peerPort = (Integer) oIn.readObject();
//                peerPort += 1024;
//                System.out.println("Read peer address: " + peerPort);
//                System.out.println("Our End!!!!!!");
//
//                Socket socketToGetFile = new Socket(InetAddress.getLocalHost().getHostAddress(), peerPort);
//                socketToGetFile.getOutputStream().write(2);
//                System.out.println("Got file: " + socketToGetFile.getInputStream().read());

//
//
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//
//    }
}

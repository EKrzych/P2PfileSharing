package com.codecool;

import java.io.*;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Peer {

    private Set<Integer> serverPorts = new HashSet<>();
    private String hostIP = "localhost";
    private ServerSocket serverSocket;

    public Peer(int portToConnect) {
        setUpServer();
        connectIfPossible(portToConnect);
        downloadFiles();
    }

    private void downloadFiles() {
        String fileName;
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("What file would you download?");
            fileName = sc.nextLine();
            int portToConnect = findPortToConnect(fileName);
        }
    }

    private int findPortToConnect(String fileName) {
        Set<Integer> checkedPorts = new HashSet<>();

        for(Integer i : serverPorts) {
            try {
                Socket socket = new Socket(hostIP, i);
                ObjectOutputStream oOs = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream oIs = new ObjectInputStream(socket.getInputStream());
                oOs.writeObject("looking for file");
                oOs.writeObject(fileName);
                oOs.writeObject("set with already checked ports");
                oOs.writeObject(checkedPorts);
                String message = (String) oIs.readObject();
                if(message.equals("found port")) {
                    return (Integer) oIs.readObject();
                } else if(message.equals("list with already checked ports")){
                    checkedPorts = (Set<Integer>) oIs.readObject();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private void setUpServer() {
        try {
            this.serverSocket = new ServerSocket(0);
            int myPort = serverSocket.getLocalPort();
            System.out.println("You are server on port" + myPort);
            serverPorts.add(myPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectIfPossible(int portToConnect) {
        Socket server;
        try {
            server = new Socket(hostIP, portToConnect);
            ObjectOutputStream oOs = new ObjectOutputStream(server.getOutputStream());
            ObjectInputStream oIs = new ObjectInputStream(server.getInputStream());
            oOs.writeObject("newPort");
            oOs.writeObject(serverSocket.getLocalPort());
            serverPorts.add(portToConnect);
            serverPorts.addAll((Set<Integer>)oIs.readObject());
            System.out.println("our port List: ");

            serverPorts.forEach(System.out::println);

            System.out.println("You have connected!!");

        } catch (IOException e) {
            System.out.println("There is no such port - you are the first one.");
        } catch (ClassNotFoundException e) {
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

//    private List<String> getFileNamesFromFolder(String folder) {
//        List<String> fileNames = new ArrayList<>();
//
//        File folderFile = new File(folder);
//        File[] listOfFiles = folderFile.listFiles();
//
//        if (listOfFiles == null) {
//            return fileNames;
//        }
//
//        for (File file : listOfFiles) {
//            if (file.isFile()) {
//                fileNames.add(file.getName());
//            } else if (file.isDirectory()) {
//                fileNames.addAll(getFileNamesFromFolder(file.getName()));
//            }
//        }
//        return fileNames;
//    }

    public void start() {
        new Thread(() ->  {
           welcomeNewPeers();
        }).start();
//
//        new Thread(() ->  {
//            sendFile();
//        }).start();


    }

    private void welcomeNewPeers() {
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                ObjectInputStream oIs = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oOs = new ObjectOutputStream(socket.getOutputStream());

                String message = (String) oIs.readObject();
                if(message.equals("newPort")) {
                    serverPorts.add((Integer) oIs.readObject());
                    oOs.writeObject(serverPorts);
                }
                socket.close();
                oIs.close();
                oOs.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

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

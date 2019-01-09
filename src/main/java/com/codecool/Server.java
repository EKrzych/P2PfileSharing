package com.codecool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class Server {
    private Map<String,Peer> activePeers;
    private Map<Socket, Integer> activeSockets = new Hashtable<>();
    private int SOCKET_PORT;


    public Server(int SOCKET_PORT) {
        this.SOCKET_PORT = SOCKET_PORT;
    }

    private void checkActivePeers() {
        //TODO
    }
    public List<String> getFileNameProposition(String fileNamePart) {
        //TODO
        return null;
    }

    public List<Peer> getPeerListForFile(String fileName) {
        //TODO
        return null;
    }



    public void run() {

        new Thread(() ->  {
            handleMassage();
        }).start();

        new Thread(() ->  {
            exchangeMessage();
        }).start();
    }

    private void exchangeMessage() {
        try {
            while(true) {
                List<Socket> myTempList = this.activeSockets.keySet().stream().collect(Collectors.toList());

                for(Socket s : myTempList) {
                    DataInputStream dIn = new DataInputStream(s.getInputStream());

                    if(dIn.available() > -1) {
                        int message = dIn.read();
                        System.out.println("active socket list" + this.activeSockets.size());
                        if (message == 112) {
                            //this.activeSockets.removeIf(n->n.equals(s));
                        } else {
                            System.out.println("read in server: " + message);
                            ObjectOutputStream oOut = new ObjectOutputStream(s.getOutputStream());
                            System.out.println("Port that should be sent: " + activeSockets.get(myTempList.get(0)));
                            oOut.writeObject(activeSockets.get(myTempList.get(0)));

                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void removeUnactiveSocket() {
//        System.out.println("in remove");
//        this.activeSockets = activeSockets.stream()
//                                            .filter(n -> !n.isClosed())
//                                            .collect(Collectors.toList());
//    }

    private void handleMassage() {
        Integer starter = 10;
        try {
            ServerSocket serverSocket = new ServerSocket(SOCKET_PORT);
            while(true) {
                System.out.println("waiting for connection");
                Socket socket = serverSocket.accept();//jeden watek ktory skceptuje peery i drugi ktory przesyla wiadomosci
                System.out.println("get host name" + socket.getInetAddress().getHostName());
                activeSockets.put(socket, starter++);
                socket.getOutputStream().write(starter);

                System.out.println("Socket added");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private void addNewActivePeer() {

    }


}

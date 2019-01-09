package com.codecool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Server {
    private Map<String,Peer> activePeers;
    private List<Socket> activeSockets = new ArrayList<>();
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
                List<Socket> myTempList = new ArrayList<>(this.activeSockets);

                for(Socket s : myTempList) {
                    DataInputStream dIn = new DataInputStream(s.getInputStream());
                    if(dIn.available() > 0) {
                        int message = dIn.read();
                        System.out.println("active socket list" + this.activeSockets.size());
                        if (message == 112) {
                            this.activeSockets.removeIf(n->n.equals(s));
                        } else {
                            System.out.println("read in server: " + message);

                            s.getOutputStream().write(7);



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
        try {
            ServerSocket serverSocket = new ServerSocket(SOCKET_PORT);
            while(true) {
                System.out.println("waiting for connection");
                Socket socket = serverSocket.accept();//jeden watek ktory skceptuje peery i drugi ktory przesyla wiadomosci
                activeSockets.add(socket);

                System.out.println("Socket added");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private void addNewActivePeer() {

    }


}

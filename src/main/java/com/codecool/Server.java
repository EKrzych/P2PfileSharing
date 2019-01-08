package com.codecool;

import java.util.List;

public class Server {
    private List<Peer> activePeers;
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

    public List<Peer> getActivePeers() {
        return activePeers;
    }

    public void run() {



    }


}

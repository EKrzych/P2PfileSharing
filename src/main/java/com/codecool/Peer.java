package com.codecool;

import java.util.List;

public class Peer {
    private String directory;
    private int SOCKET_PORT;

    public Peer(String directory, int SOCKET_PORT) {
        this.directory = directory;
        this.SOCKET_PORT = SOCKET_PORT;
    }

    public boolean getFile(String fileName, Peer fromPeer) {
        return true;
    }

    public boolean sendFile(String fileName, Peer toPeer) {
        return true;
    }

    public List<String> getFileNamesFromFolder() {
        return null;
    }

    public void start() {

    }
}

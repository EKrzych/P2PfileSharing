package com.codecool;

import java.util.List;

public class Peer {
    private String directory;

    public Peer(String directory) {
        this.directory = directory;
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
}

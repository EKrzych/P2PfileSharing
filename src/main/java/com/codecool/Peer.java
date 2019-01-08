package com.codecool;

import java.util.List;

public class Peer {
    private String folder;

    public Peer(String folder) {
        this.folder = folder;
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

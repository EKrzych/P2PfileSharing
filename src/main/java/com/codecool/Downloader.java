package com.codecool;

import java.io.File;

public class Downloader {
    private Finder finder;

    public Downloader(Finder finder) {
        this.finder = finder;
    }

    public void downloadFile(String fileName) {
        Peer peerWithFile = finder.findPeerToConnect(fileName);
        if(peerWithFile != null) {
            System.out.println("I found file in peer with port: " + peerWithFile.getPort());
        }
        File file = new Communicator(peerWithFile).getFile(fileName);

        unpackFile(file);
    }

    private void unpackFile(File file) {
        //TODO how pack/unpack files in java!
    }

    public Finder getFinder() {
        return finder;
    }
}

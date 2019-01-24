package com.codecool;

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
        //TODO download file
    }

    public Finder getFinder() {
        return finder;
    }
}

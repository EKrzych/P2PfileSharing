package com.codecool;

public class Downloader {
    private Finder finder;

    public Downloader(Finder finder) {
        this.finder = finder;
    }

    public void downloadFile(String fileName) {
        int portWithFile = finder.findPortToConnect(fileName);
        //TODO download file
    }

    public Finder getFinder() {
        return finder;
    }
}

package com.codecool;

public class App {


    public static void main( String[] args ) {
        start(new PeerCreator().get());

    }

    private static void start(Peer peer) {
        Finder finder = new Finder(peer);

        new Thread(new ClientPart(new Downloader(finder))).start();
        new Thread(new ServerPart(finder)).start();
    }

}

package com.codecool;

import java.util.Scanner;
import java.util.Set;

public class ClientPart implements Runnable {
    private Scanner scanner = new Scanner(System.in);
    private Downloader downloader;

    public ClientPart(Downloader downloader) {
        this.downloader = downloader;
    }

    @Override
    public void run() {
        updatePortList();
        System.out.println("You have connected!!");
        while (true) {
            System.out.println("What file would you download?");
            String fileName = scanner.nextLine();
            downloader.downloadFile(fileName);
        }
    }

    private void updatePortList() {
        String hostIP = askForHostIp();
        Integer portToConnect = askForPort();
        Peer peer = downloader.getFinder().getPeer();
        peer.getFriends().addAll(connectIfPossible(hostIP, portToConnect, peer));
    }

    private Set<Peer> connectIfPossible(String hostIP, Integer portToConnect, Peer peer) {
        Communicator communicator = new Communicator(hostIP, portToConnect);
        communicator.sayHello(peer);
        return communicator.readPeers();
    }

    private Integer askForPort() {
        System.out.println("Set port for connection");
        return Integer.valueOf(scanner.nextLine());
    }

    private String askForHostIp() {
        return "localhost";//TODO if working on multiple device
    }
}




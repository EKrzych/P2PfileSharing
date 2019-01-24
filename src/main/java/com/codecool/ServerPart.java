package com.codecool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;

public class ServerPart implements Runnable {
    private Finder finder;
    private ServerSocket server;


    public ServerPart(Finder finder, ServerSocket server) {
        this.finder = finder;
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            Communicator communicator = waitToBeginCommunication();
            String message = communicator.readAction();

            if (message.equals("hello")) {
                welcomeNewPeer(communicator);
            } else if (message.equals("looking for file")) {
                findFile(communicator);
            }
            communicator.close();
        }
    }

    private void findFile(Communicator communicator) {
        finder.lookForPeerWithFile(communicator);
    }

    private Communicator waitToBeginCommunication() {
        Socket socket = null;
        try {
            socket = server.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Communicator(socket);
    }

    private void welcomeNewPeer(Communicator communicator) {
        Set<Peer> friends = finder.getPeer().getFriends();
        friends.add(communicator.readPeer());
        communicator.sendPeers(friends);
    }

}

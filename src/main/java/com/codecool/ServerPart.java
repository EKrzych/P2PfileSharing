package com.codecool;

import java.io.IOException;
import java.net.Socket;
import java.util.Set;

public class ServerPart implements Runnable {
    private Finder finder;


    public ServerPart(Finder finder) {
        this.finder = finder;
    }

    @Override
    public void run() {
        while (true) {
            Communicator communicator = waitToBeginCommunication();
            String message = communicator.readAction();

            if (message.equals("newPort")) {
                welcomeNewPeer(communicator);
            } else if (message.equals("looking for file")) {
                findFile(communicator);
            }
            communicator.close();
        }
    }

    private void findFile(Communicator communicator) {
        finder.lookForPortWithFile(communicator);
    }

    private Communicator waitToBeginCommunication() {
        Socket socket = null;
        try {
            socket = finder.getPeer().getServerSocket().accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Communicator(socket);
    }

    private void welcomeNewPeer(Communicator communicator) {
        Set<Integer> ports = finder.getPeer().getServerPorts();
        Integer newPort = communicator.readPort();
        ports.add(newPort);
        communicator.sendPorts(ports);
    }

}

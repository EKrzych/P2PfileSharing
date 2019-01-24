package com.codecool;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Communicator {
    private Socket socket;
    private ObjectOutputStream oOs;
    private ObjectInputStream oIs;
    private boolean isCommunicationPossible;

    public Communicator(String hostIP, Integer port) {
        isCommunicationPossible = createStreams(hostIP, port);
    }

    public Communicator(Socket socket) {
        this.socket = socket;
        isCommunicationPossible = createStreams(socket);

    }

    public Communicator(Peer peer) {
        this(peer.getHostIP(), peer.getPort());
    }

    private boolean createStreams(String hostIP, Integer port) {
        try {
            this.socket = new Socket(hostIP, port);
            return createStreams(socket);
        } catch (IOException e) {
            System.out.println("There is no such port");
        }
        return false;
    }

    private boolean createStreams(Socket socket) {
        try {
            this.oOs = new ObjectOutputStream(socket.getOutputStream());
            this.oIs = new ObjectInputStream(socket.getInputStream());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void close() {
        try {
            if(socket != null) socket.close();
            if(oOs != null) oOs.close();
            if(oIs != null) oIs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String askForFile(String fileName, Set<Peer> checkedPeers) {
        if(isCommunicationPossible) {
            try {
                oOs.writeObject("looking for file");
                oOs.writeObject(fileName);
                oOs.writeObject("list with already checked peers");
                oOs.writeObject(checkedPeers);
                return (String) oIs.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "couldn't get message"; // TODO handle this message
    }

    public Peer readPeer() {
        if(isCommunicationPossible) {
            try {
                return (Peer) oIs.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Socket getSocket() {
        return socket;
    }

    public Set<Peer> readPeers() {
        if(isCommunicationPossible) {
            try {
                return (Set<Peer>) oIs.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new HashSet<>();
    }

    public String readAction() {
        if(isCommunicationPossible) {
            try {
                return (String) oIs.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "couldn't get message";
    }

    public void sendPeers(Set<Peer> friends) {
        if(isCommunicationPossible) {
            try {
                oOs.writeObject(friends);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getFileName() {
        if(isCommunicationPossible) {
            try {
                return (String) oIs.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "couldn't get message";
    }

    public void sendPeer(Peer peer) {
        if(isCommunicationPossible) {
            try {
                oOs.writeObject("found peer");
                oOs.writeObject(peer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendAlreadyCheckedPeers(Set<Peer> checkedPeersFromAnotherPeer) {
        if(isCommunicationPossible) {
            try {
                oOs.writeObject("list with already checked peers");
                oOs.writeObject(checkedPeersFromAnotherPeer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void sayHello(Peer peer) {
        if(isCommunicationPossible) {
            try {
                oOs.writeObject("hello");
                oOs.writeObject(peer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

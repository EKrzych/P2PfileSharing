package com.codecool;

import java.io.File;
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

    public Message askForFile(String fileName, Set<Peer> checkedPeers) {
        if(isCommunicationPossible) {
            try {
                oOs.writeObject(Message.FILE);
                oOs.writeObject(fileName);
                oOs.writeObject(Message.PEERS);
                oOs.writeObject(checkedPeers);
                return (Message) oIs.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return Message.PROBLEM;//todo
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

    public Message readAction() {
        if(isCommunicationPossible) {
            try {
                return (Message) oIs.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return Message.PROBLEM;
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
        return Message.PROBLEM.getMessage();
    }

    public void sendPeer(Peer peer) {
        if(isCommunicationPossible) {
            try {
                oOs.writeObject(Message.PEER);
                oOs.writeObject(peer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendAlreadyCheckedPeers(Set<Peer> checkedPeersFromAnotherPeer) {
        if(isCommunicationPossible) {
            try {
                oOs.writeObject(Message.PEERS);
                oOs.writeObject(checkedPeersFromAnotherPeer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void sayHello(Peer peer) {
        if(isCommunicationPossible) {
            try {
                oOs.writeObject(Message.HELLO);
                oOs.writeObject(peer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File getFile(String fileName) {
        if(isCommunicationPossible) {
            try {
                oOs.writeObject(Message.SEND);
                oOs.writeObject(fileName);
                return (File) oIs.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void sendFile(File file) {
        if(isCommunicationPossible) {
            try {
                oOs.writeObject(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

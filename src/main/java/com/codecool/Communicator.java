package com.codecool;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Set;

public class Communicator {
    private Socket socket;
    private ObjectOutputStream oOs;
    private ObjectInputStream oIs;

    public Communicator(String hostIP, Integer port) {
        createStreams(hostIP, port);
    }

    public Communicator(Socket socket) {
        this.socket = socket;
        createStreams(socket);
    }

    private void createStreams(String hostIP, Integer port) {
        try {
            this.socket = new Socket(hostIP, port);
            createStreams(socket);
        } catch (IOException e) {
            System.out.println("There is no such port");
        }
    }

    private void createStreams(Socket socket) {
        try {
            this.oOs = new ObjectOutputStream(socket.getOutputStream());
            this.oIs = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            socket.close();
            oOs.close();
            oIs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String askForFile(String fileName, Set<Integer> checkedPorts) {
        try {
            oOs.writeObject("looking for file");
            oOs.writeObject(fileName);
            oOs.writeObject("list with already checked ports");
            oOs.writeObject(checkedPorts);
            return (String) oIs.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "couldn't get message"; // TODO handle this message
    }

    public Integer readPort() {
        try {
            return (Integer) oIs.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Socket getSocket() {
        return socket;
    }

    public Set<Integer> readPorts() {
        try {
            return (Set<Integer>) oIs.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String readAction() {
        try {
            return (String) oIs.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "couldn't get message";
    }

    public void sendPorts(Set<Integer> ports) {
        try {
            oOs.writeObject(ports);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        try {
            return (String) oIs.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "couldn't get message";
    }

    public void sendPort(Integer port) {
        try {
            oOs.writeObject("found port");
            oOs.writeObject(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAlreadyCheckedPorts(Set<Integer> checkedPortsFromAnotherPeer) {
        try {
            oOs.writeObject("list with already checked ports");
            oOs.writeObject(checkedPortsFromAnotherPeer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sayHello(Integer myPort) {
        try {
            oOs.writeObject("newPort");
            oOs.writeObject(myPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

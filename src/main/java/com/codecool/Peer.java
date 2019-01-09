package com.codecool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Peer {
    private String directory;
    private int SOCKET_PORT;

    public Peer(String directory, int SOCKET_PORT) {
        this.directory = directory;
        this.SOCKET_PORT = SOCKET_PORT;
    }

    public boolean getFile(String fileName, Peer fromPeer) {
        return true;
    }

    public boolean sendFile(String fileName, Peer toPeer) {
        return true;
    }


    public List<String> getFileNamesFromFolder() {
        return getFileNamesFromFolder(directory);
    }

    private List<String> getFileNamesFromFolder(String folder) {
        List<String> fileNames = new ArrayList<>();

        File folderFile = new File(folder);
        File[] listOfFiles = folderFile.listFiles();

        if (listOfFiles == null) {
            return fileNames;
        }

        for (File file : listOfFiles) {
            if (file.isFile()) {
                fileNames.add(file.getName());
            } else if (file.isDirectory()) {
                fileNames.addAll(getFileNamesFromFolder(file.getName()));
            }
        }
        return fileNames;
    }

    public void start() throws IOException, InterruptedException {

           Socket socket = new Socket("localhost", SOCKET_PORT);
           DataInputStream dIn = new DataInputStream(socket.getInputStream());
           DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());

           dOut.write(2);
           System.out.println("Have sent!! And got: ");
            dOut.flush();
           System.out.println(dIn.read());
        dOut.write(111);
        dOut.flush();
           socket.close();
           dIn.close();
           dOut.close();
            System.out.println("socket closed>" + socket.isClosed());


    }
}

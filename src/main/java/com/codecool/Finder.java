package com.codecool;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Finder {
    private Peer peer;

    public Finder(Peer peer) {
        this.peer = peer;
    }



    int findPortToConnect(String fileName) {
        return findPortToConnect(fileName, new LinkedHashSet<>());
    }


    private int findPortToConnect(String fileName, Set<Integer> checkedPorts ) {
        Iterator<Integer> it = getIteratorWithPortsToCheck(checkedPorts);

        if(it.hasNext()) {
            Communicator communicator = new Communicator(peer.getHostIP(, it.next());
            String message = communicator.askForFile(fileName, checkedPorts);

            if(message.equals("found port")) {
                Integer foundPort = communicator.readPort();
                System.out.println("Found port" + foundPort + "I've found it: " + communicator.getSocket().getPort());
                communicator.close();
                return foundPort;
            } else if(message.equals("list with already checked ports")){
                Set<Integer> actualizedPorts = communicator.readPorts();
                communicator.close();
                return findPortToConnect(fileName, actualizedPorts);
            }
            communicator.close();
        }
        return 0;
    }

    private Iterator<Integer> getIteratorWithPortsToCheck(Set<Integer> checkedPorts){
        Set<Integer> portsToCheck = peer.getServerPorts().stream()
                .filter(n->!checkedPorts.contains(n))
                .collect(Collectors.toSet());
        return  portsToCheck.iterator();
    }

    public void lookForPortWithFile(Communicator communicator) {
        String fileName = communicator.getFileName();
        String message = communicator.readAction();
        if (message.equals("list with already checked ports")) {
            Set<Integer> checkedPortsFromAnotherPeer = communicator.readPorts();
            if (checkIfHaveFile(fileName)) {
                communicator.sendPort(peer.getServerSocket().getLocalPort());
            } else {
                checkedPortsFromAnotherPeer.add(peer.getServerSocket().getLocalPort());
                int portToConnect = findPortToConnect(fileName, checkedPortsFromAnotherPeer);

                if (portToConnect > 0) {
                    System.out.println("Found port - before writing to outputstream: " + portToConnect);
                    communicator.sendPort(portToConnect);

                } else {
                    System.out.println("list with already checked ports - before writting into outputstream: ");
                    checkedPortsFromAnotherPeer.forEach(n -> System.out.println(n));
                    communicator.sendAlreadyCheckedPorts(checkedPortsFromAnotherPeer);
                }
            }
        }
    }

    private boolean checkIfHaveFile(String fileName) {
        return getFileNamesFromFolder(peer.getPathToFolder()).contains(fileName);
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
                fileNames.addAll(getFileNamesFromFolder(file.getAbsolutePath()));
            }
        }
        return fileNames;
    }

    public Peer getPeer() {
        return peer;
    }
}

package com.codecool;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Finder {
    private Peer peer;

    public Finder(Peer peer) {
        this.peer = peer;
    }

    Peer findPeerToConnect(String fileName) {
        return findPeerToConnect(fileName, new LinkedHashSet<>());
    }


    private Peer findPeerToConnect(String fileName, Set<Peer> checkedPeers ) {
        Iterator<Peer> it = getIteratorWithPortsToCheck(checkedPeers);

        if(it.hasNext()) {
            Communicator communicator = new Communicator(peer);
            String message = communicator.askForFile(fileName, checkedPeers);

            if(message.equals("found peer")) {
                Peer foundPeer = communicator.readPeer();
                System.out.println("Found port" + foundPeer + "I've found it: " + communicator.getSocket().getPort());
                communicator.close();
                return foundPeer;
            } else if(message.equals("list with already checked peers")){
                Set<Peer> actualizedPeers = communicator.readPeers();
                communicator.close();
                return findPeerToConnect(fileName, actualizedPeers);
            }
            communicator.close();
        }
        return null;
    }

    private Iterator<Peer> getIteratorWithPortsToCheck(Set<Peer> checkedPeers){
        Set<Peer> peersToCheck = peer.getFriends().stream()
                .filter(n->!checkedPeers.contains(n))
                .collect(Collectors.toSet());
        return  peersToCheck.iterator();
    }

    public void lookForPeerWithFile(Communicator communicator) {
        String fileName = communicator.getFileName();
        String message = communicator.readAction();
        if (message.equals("list with already checked peers")) {
            Set<Peer> checkedPeersFromAnotherPeer = communicator.readPeers();
            if (checkIfHaveFile(fileName)) {
                communicator.sendPeer(peer);
            } else {
                checkedPeersFromAnotherPeer.add(peer);
                Peer peerToConnect = findPeerToConnect(fileName, checkedPeersFromAnotherPeer);

                if (peerToConnect != null) {
                    System.out.println("Found peer - before writing to outputstream: " + peerToConnect);
                    communicator.sendPeer(peerToConnect);

                } else {
                    System.out.println("list with already checked ports - before writting into outputstream: ");
                    checkedPeersFromAnotherPeer.forEach(n -> System.out.println(n));
                    communicator.sendAlreadyCheckedPeers(checkedPeersFromAnotherPeer);
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

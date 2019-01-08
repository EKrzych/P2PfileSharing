package com.codecool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Peer {
    private String directory;

    public Peer(String directory) {
        this.directory = directory;
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
}

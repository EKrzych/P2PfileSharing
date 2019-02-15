package com.codecool;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class Peer implements Serializable {

    private Set<Peer> friends;
    private String pathToFolder;
    private Integer port;
    private String hostIP;

    public Peer(Set<Peer> friends, String pathToFolder, Integer port, String hostIP) {
        this.friends = friends;
        this.pathToFolder = pathToFolder;
        this.port = port;
        this.hostIP = hostIP;
    }

    public Integer getPort() {
        return port;
    }

    public String getHostIP() {
        return hostIP;
    }

    public Set<Peer> getFriends() {
        return friends;
    }


    public String getPathToFolder() {
        return pathToFolder;
    }

    public boolean getFile(String fileName, Peer fromPeer) {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Peer peer = (Peer) o;
        return Objects.equals(port, peer.port);
    }

    @Override
    public int hashCode() {

        return Objects.hash(port);
    }
}

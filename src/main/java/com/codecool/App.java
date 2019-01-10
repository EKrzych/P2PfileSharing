package com.codecool;

import java.io.IOException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {


    public static void main( String[] args ) {

        handlePeer();

    }

    private static void handlePeer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Set port for connection");
        Integer portToConnect = scanner.nextInt();
        new Peer(portToConnect).start();
    }
}

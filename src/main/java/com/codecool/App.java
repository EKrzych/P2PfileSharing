package com.codecool;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public final static int SOCKET_PORT = 9000;

    public static void main( String[] args ) {
        if(args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "client":
                    handlePeer();
                    break;

                case "server":
                    handleServer();
                    break;

                default:
                    System.err.println("Incompatible mode, use: server or client");
                    System.exit(1);

            }
        } else {
            System.err.println("Try again - Command signature should look in a following way: java P2PfileSharing mode");
            System.exit(1);
        }
    }

    private static void handleServer() {
        new Server(SOCKET_PORT).run();
    }

    private static void handlePeer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Set directory for transfer");
        String directory = scanner.nextLine();
        new Peer(directory, SOCKET_PORT).start();
    }
}

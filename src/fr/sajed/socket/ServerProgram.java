package fr.sajed.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerProgram {
    public static void main(String args[]) throws IOException {

        //Create a server socket
        ServerSocket listener = null;

        System.out.printf("Server %s is waiting to accept user...",GetIpHostName.now());
        int clientNumber = 0;

        // Try to open a server socket on port 7777
        // Note that we can't choose a port less than 1023 if we are not
        // privileged users (root)

        try {
            /**
             * Un port est identifié par un entier de 1 à 65535.
             * Par convention les 1024 premiers sont réservés pour
             * des services standard (80 : HTTP, 21 : FTP, 25: SMTP, ...)
             * bind a server socket to a specific port number
              */

            listener = new ServerSocket(7777);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1); //exit(0) : Generally used to indicate successful termination.
                                  // exit(1) or exit(-1) or any other non-zero value – Generally indicates unsuccessful termination.
        }

        try {
            while (true) {
                // Accept client connection request
                // Get new Socket at Server.

                Socket socketOfServer = listener.accept();
                new ServiceThread(socketOfServer, clientNumber++).start();
            }
        } finally {
            listener.close();
        }

    }

    private static void log(String message) {
        System.out.println(message);
    }

    private static class ServiceThread extends Thread {

        private int clientNumber;
        private Socket socketOfServer;

        public ServiceThread(Socket socketOfServer, int clientNumber) {
            this.clientNumber = clientNumber;
            this.socketOfServer = socketOfServer;

            // Log
            log("New connection with client# " + this.clientNumber + " at " + socketOfServer);
        }

        @Override
        public void run() {

            try {

                // Open input and output streams
                BufferedReader is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
                BufferedWriter os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));

                while (true) {
                    // Read data to the server (sent from client).
                    String line = is.readLine();

                    // Write to socket of Server
                    // (Send to client)
                    os.write(">> " + line+"copy");
                    // End of line.
                    os.newLine();
                    // Flush data.//vider les caractères du flux d'écriture mis en mémoire tampon
                    os.flush();


                    // If users send QUIT (To end conversation).
                    if (line.equals("QUIT")) {
                        os.write(">> OK");
                        os.newLine();
                        os.flush();
                        break;
                    }
                }

            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }
}


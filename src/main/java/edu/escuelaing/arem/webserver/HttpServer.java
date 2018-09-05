/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arem.webserver;

/**
 *
 * @author jonnh
 */
import java.net.*;
import java.io.*;

public class HttpServer {

    public static void main(String[] args) throws IOException {
        while (true) {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(getPort());
            } catch (IOException e) {
                System.err.println("Could not listen on port: 35000.");
                System.exit(1);
            }
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()
                    ));
            String inputLine, outputLine;
//            while ((inputLine = in.readLine()) != null) {
//                System.out.println("Recibí: " + inputLine);
//                if (!in.ready()) {
//                    break;
//                }
//            }
            inputLine = in.readLine();
            if (Reader.existWord("png", inputLine)) {
                out.println(new String(Reader.imageReader(Reader.searchResource(inputLine))));
            } else {
                try {
                    out.println(Reader.htmlReader(Reader.searchResource(inputLine)));
                } catch (Exception e) {
                    out.println("HTTP/1.1 404 OK\r\n"
                            + "Content-Type: text/html\r\n"
                            + "\r\n");
                }
            }
            //outputLine = "";
            //out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();
        }
    }
    
    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e.on localhost)
    }

}

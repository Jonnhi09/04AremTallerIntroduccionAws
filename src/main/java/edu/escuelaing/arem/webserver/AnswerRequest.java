/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arem.webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase AnswerRequest implementa Runnable para resolver las peticiones
 * hechas por un cliente de manera concurrene.
 *
 * @author Jonathan Prieto
 */
public class AnswerRequest implements Runnable {

    private Socket clientSocket;

    public AnswerRequest(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        PrintWriter out = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            //Leer la peticion que hace el cliente.
            inputLine = in.readLine();
            //Se busca en el encabezado de la peticion para saber si el recurso solicitado es png o html.
            if (inputLine != null && inputLine.contains("GET")) {
                String[] encabezado = inputLine.split(" ");
                String recusro = encabezado[1];
                if (recusro.contains("png")) {
                    byte[] image = Reader.imageReader(Search.searchResource(inputLine));
                    DataOutputStream binaryOut;
                    binaryOut = new DataOutputStream(clientSocket.getOutputStream());
                    binaryOut.writeBytes("HTTP/1.1 200 OK \r\n");
                    binaryOut.writeBytes("Content-Type: image/png\r\n");
                    binaryOut.writeBytes("Content-Length: " + image.length);
                    binaryOut.writeBytes("\r\n\r\n");
                    binaryOut.write(image);
                    binaryOut.close();
                } else {
                    try {
                        out.println(Reader.htmlReader(Search.searchResource(inputLine)));
                    } catch (IOException e) {
                        out.println("HTTP/1.1 404 NOT FOUND\r\n"
                                + "Content-Type: text/html\r\n"
                                + "\r\n");
                    }
                }
            }
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(AnswerRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

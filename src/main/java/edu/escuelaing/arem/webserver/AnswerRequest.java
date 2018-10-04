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

    /**
     * El siguiente metodo genera un encabezado http junto con los parametros
     * status y resource.
     *
     * @param status
     * @param resource
     * @return String con el encabezado http.
     */
    public String header(String status, String resource) {
        return "HTTP/1.1 " + status + " \r\n"
                + "Content-Type: " + resource
                + "\r\n\r\n";
    }

    @Override
    public void run() {
        PrintWriter out = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            //Leer la peticion que hace el cliente.
            inputLine = in.readLine();
            //Se busca en el encabezado de la peticion para saber si el recurso solicitado es png o html.
            if (inputLine != null && inputLine.contains("GET")) {
                String[] encabezado = inputLine.split(" ");
                String recusro = encabezado[1];
                try {
                    if (recusro.contains("png")) {
                        byte[] image = Reader.imageReader(Search.searchResource(recusro, 1));
                        DataOutputStream binaryOut;
                        binaryOut = new DataOutputStream(clientSocket.getOutputStream());
                        binaryOut.writeBytes(header("200 OK", "image/png\r\n"
                                + "Content-Length: " + image.length));
                        binaryOut.write(image);
                        binaryOut.close();
                    } else if (recusro.contains("comp")) {
                        out.println(header("200 OK", "text/html\r\n") + Reader.componentReader(Search.searchResource(recusro, 1), Search.searchResource(recusro, 2)));
                    } else {
                        out.println(header("200 OK", "text/html\r\n") + Reader.htmlReader(Search.searchResource(recusro, 1)));
                    }
                } catch (IOException e) {
                    out.println(header("404 NOT FOUND", "text/html\r\n") + "<h1> 404 PAGE NOT FOUND </h1> <h3> This is not the web page you are looking for. </h3>");
                } catch (Exception ex) {
                    Logger.getLogger(AnswerRequest.class.getName()).log(Level.SEVERE, null, ex);
                    out.println(header("404 NOT FOUND", "text/html\r\n") + "<h1> 404 PAGE NOT FOUND </h1> <h3> This is not the web page you are looking for. </h3>");
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arem.webserver;

import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase HttpServer se comporta como un servidor web, el cual recibe
 * peticiones de manera concurrente usando el protocolo http, y responde con
 * recursos html y png usando este mismo protocolo.
 *
 * @author Jonathan Prieto
 */
public class HttpServer {

    public static void main(String[] args) {
        int i = 0;
        ExecutorService es = Executors.newFixedThreadPool(10);
        while (true) {
            i++;
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(getPort());
            } catch (IOException e) {
                System.err.println("Could not listen on port: " + getPort());
                System.exit(1);
            }
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ... " + i);
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            AnswerRequest ar = new AnswerRequest(clientSocket);
            es.execute(ar);
            try {
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * La siguiente funcion retorna un numero entero, que correspondera al
     * puerto por el cual se establecera la comunicacion entre el cliente y el
     * servidor.
     *
     * @return int
     */
    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e.on localhost)
    }

}

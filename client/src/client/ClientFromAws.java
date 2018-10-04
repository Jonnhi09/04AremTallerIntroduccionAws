/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase ClientFromAws se comporta como un explorador, leyendo el contenido
 * de una URL e imprimiendo su contenido en consola.
 *
 * @author Jonathan Prieto
 */
public class ClientFromAws {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(10);
        int i=0;
        for (String s : args) {
            i++;
            try {
                UrlReader urlReader = new UrlReader(new URL(s),i);
                es.execute(urlReader);
            } catch (MalformedURLException ex) {
                Logger.getLogger(ClientFromAws.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientFromAws.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

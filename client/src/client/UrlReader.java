/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase UrlReader se encarga de leer urls y de imprimir su contenido en
 * pantalla. Ademas esta implementa la interfaz Runnable para poder leer urls de
 * manera concurrente. Para entender todo el contenido que se genera al leer la
 * url, se crean archivos llamados resultado i-esimo con extension html
 * (Resultadoi.html) para visualizar el contenido de las urls analizadas.
 *
 * @author Jonathan Prieto
 */
public class UrlReader implements Runnable {

    private URL url;
    private int i;

    public UrlReader(URL url, int i) {
        this.url = url;
        this.i = i;
    }

    @Override
    public void run() {
        try {
            File archivo = new File("./resultado" + i + ".html");
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()))) {
                String inputLine = null;
                while ((inputLine = reader.readLine()) != null) {
                    System.out.println(inputLine);
                    bw.write(inputLine);
                }
                bw.close();
            } catch (IOException x) {
                Logger.getLogger(ClientFromAws.class.getName()).log(Level.SEVERE, null, x);
            }
        } catch (IOException ex) {
            Logger.getLogger(UrlReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

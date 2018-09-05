/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arem.webserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 * @author jonnh
 */
public class Reader {

    /**
     * Obtener una pagina html
     *
     * @param ruta
     * @return String html con el contenido de la pagina html.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String htmlReader(String ruta) throws FileNotFoundException, IOException {
        String html = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n";
        FileReader fr = new FileReader("./resources/" + ruta + ".html");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        while (line != null) {
            html = html + line;
            line = br.readLine();
        }
        br.close();
        return html;
    }

    /**
     * Obtener una imagen.
     *
     * @param ruta
     * @return byte[] codigo byte con el contenido de la pagina.
     * @throws IOException
     */
    public static byte[] imageReader(String ruta) throws IOException {
        byte[] bytesPageToShow = Files.readAllBytes(new File("./resources/" + ruta + ".png").toPath());
        String contentLength = "" + bytesPageToShow.length;
        byte[] bytesEncabezado = ("HTTP/1.1 200 OK\r\n"
                + "Content-Type: image/png\r\n"
                + "\r\n").getBytes();
        byte[] bytesPage = new byte[bytesPageToShow.length + bytesEncabezado.length];
        for (int i = 0; i < bytesEncabezado.length; i++) {
            bytesPage[i] = bytesEncabezado[i];
        }
        for (int i = bytesEncabezado.length; i < bytesEncabezado.length + bytesPageToShow.length; i++) {
            bytesPage[i] = bytesPageToShow[i - bytesEncabezado.length];
        }
        return bytesPage;
    }

    /**
     * Saber si existe una palabra dentro de una cadena
     *
     * @param buscar
     * @param frase
     * @return boolean true si la palabra esta contenida dentro de la frase,
     * false en otro caso.
     */
    public static boolean existWord(String buscar, String frase) {
        boolean respuesta = false;
        String[] palabras = frase.split("\\W+");
        for (String palabra : palabras) {
            if (buscar.contains(palabra)) {
                respuesta = true;
            }
        }
        return respuesta;
    }

    /**
     * Obtener el recurso que se solicita por medio de una peticion http.
     *
     * @param frase
     * @return string palabra
     */
    public static String searchResource(String frase) {
        String[] palabras = frase.split("\\W+");
        return palabras[1];
    }
}

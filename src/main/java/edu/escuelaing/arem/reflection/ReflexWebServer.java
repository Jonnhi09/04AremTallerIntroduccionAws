package edu.escuelaing.arem.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * La clase ReflexWebServer permite crear y usar componentes software en tiempo
 * de ejecución.
 *
 * @author Jonathan Prieto
 */
public class ReflexWebServer {

    /**
     * Crear una instancia de un componente (app) e invoar un metodo dicho
     * componente.
     *
     * @param app String, nombre del componente.
     * @param metodo String, nombre del metodo.
     * @return String, respuesta que retorna el metodo invocado.
     * @throws Exception
     */
    public static String component(String app, String metodo) throws Exception {
        Class c = Class.forName(app);
        String respuesta = "<h2> App " + c.getSimpleName() + " Respond: </h2>";
        //Class c = Class.forName("edu.escuelaing.arem.components.IrrationalNumber");
        for (Method m : c.getMethods()) {
            Constructor cons = c.getConstructor();
            if (m.isAnnotationPresent(Mapping.class) && m.getAnnotation(Mapping.class).name().equals(metodo)) {
                try {
                    //Mapping anot = m.getAnnotation(Mapping.class);
                    //System.out.println(anot.name());
                    Object o = cons.newInstance();
                    Object a = m.invoke(o);
                    respuesta += " R/: " + a.toString();
                } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException ex) {
                    System.out.printf("error on %s: %s %n", m, ex.getCause());
                }
            }
        }
        return respuesta;
    }
}

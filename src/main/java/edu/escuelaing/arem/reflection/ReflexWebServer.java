package edu.escuelaing.arem.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ReflexWebServer {

    public static String component(String[] args) throws Exception {
        Class c = Class.forName(args[0]);
        String respuesta = "App: " + c.getSimpleName() + " Respond: ";
        //Class c = Class.forName("edu.escuelaing.arem.components.IrrationalNumber");
        for (Method m : c.getMethods()) {
            Constructor cons = c.getConstructor();
            if (m.isAnnotationPresent(Mapping.class) && m.getAnnotation(Mapping.class).name().equals(args[1])) {
                try {
                    //Mapping anot = m.getAnnotation(Mapping.class);
                    //System.out.println(anot.name());
                    Object o = cons.newInstance();
                    Object a = m.invoke(o);
                    respuesta += a.toString();
                } catch (Throwable ex) {
                    System.out.printf("error on %s: %s %n", m, ex.getCause());
                }
            }
        }
        return respuesta;
    }
}

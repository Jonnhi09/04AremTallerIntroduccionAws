/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arem.components;

import edu.escuelaing.arem.reflection.Component;
import edu.escuelaing.arem.reflection.Mapping;
import java.util.Calendar;
import java.util.Date;

/**
 * La clase SystemDate permite conocer informacion asociada al día, hora, region
 * y fecha del sistema.
 *
 * @author Jonathan Prieto
 */
@Component
public class SystemDate {

    /**
     * Fecha actual del sistema.
     *
     * @return Date
     */
    @Mapping(name = "date")
    public Date date() {
        return Calendar.getInstance().getTime();
    }
}

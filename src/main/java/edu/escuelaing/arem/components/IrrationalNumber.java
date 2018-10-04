/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arem.components;

import edu.escuelaing.arem.reflection.Component;
import edu.escuelaing.arem.reflection.Mapping;

/**
 * La clase IrrationalNumber permite obtener el valor numerico de numeros
 * irracionales en formato decimal.
 *
 * @author Jonathan Prieto
 */
@Component
public class IrrationalNumber {

    /**
     * Numero pi.
     *
     * @return Double, valor numerico del numero pi en formato decimal.
     */
    @Mapping(name = "pi")
    public Double pi() {
        return Math.PI;
    }

    /**
     * Numero e, euler o constante de Napier.
     *
     * @return Double, valor numerico del numero e en formato decimal.
     */
    @Mapping(name = "e")
    public Double e() {
        return Math.E;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arem.components;

import edu.escuelaing.arem.reflection.Mapping;

/**
 *
 * @author jonnhi
 */
public class IrrationalNumber {

    @Mapping(name = "pi")
    public Double pi() {
        return Math.PI;
    }

    @Mapping(name = "e")
    public Double e() {
        return Math.E;
    }
}

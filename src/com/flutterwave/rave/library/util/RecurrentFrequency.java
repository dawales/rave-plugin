/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flutterwave.rave.library.util;

/**
 *
 * @author emmanueladeyemi
 */
public enum RecurrentFrequency {
    
    Daily("recurring-daily"),
    Weekly("recurring-weekly"),
    Monthly("recurring-monthly"),
    Quarterly("recurring-quarterly"),
    Bi_Annually("recurring-bianually"), 
    Annually("recurring-anually");

    private final String value;

    private RecurrentFrequency(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
}

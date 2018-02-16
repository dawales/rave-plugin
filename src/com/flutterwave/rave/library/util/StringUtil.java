/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flutterwave.rave.library.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author emmanueladeyemi
 */
public class StringUtil {
    
    public static String nullToEmpty(String data){
        
        if(data == null)
            return "";
        
        return data;
    }
    
    public static String emptyToNull(String data){
        
        if(data == null || "".equals(data))
            return null;
        
        return data;
    }
    
    public static String format(double value){
        
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String data = decimalFormat.format(value);
        
        return data;
    }
    
    public static String formateDate(Date date, String pattern) {

        if (date == null) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        return dateFormat.format(date);
    }

}

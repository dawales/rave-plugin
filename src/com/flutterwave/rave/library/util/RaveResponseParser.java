/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flutterwave.rave.library.util;

import org.json.JSONObject;

/**
 *
 * @author emmanueladeyemi
 */
public class RaveResponseParser {
    
    public static JSONObject parseResponse(String data) throws Exception{
           
        if(data == null)
            throw new Exception("No data response provided");
        
        JSONObject response = new JSONObject(data);
        String status = response.optString("status");
        String message = response.optString("message");
        
        if("error".equalsIgnoreCase(status)){
            throw new Exception(message);
        }
        
        JSONObject responseData = new JSONObject()
                .put("data", response.optJSONObject("data"))
                .put("message_type", message);
        
        return responseData;
    }
}

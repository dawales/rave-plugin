/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flutterwave.rave.library;

import com.flutterwave.rave.library.model.RaveAccount;
import com.flutterwave.rave.library.model.RaveCard;
import com.flutterwave.rave.library.util.HttpUtil;
import com.flutterwave.rave.library.util.RaveMode;
import com.flutterwave.rave.library.util.RavePaths;
import com.flutterwave.rave.library.util.RaveResponseParser;
import com.flutterwave.rave.library.util.StringUtil;
import com.flutterwave.rave.library.util.TripleDES;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author emmanueladeyemi
 */
public final class RavePaymentHelper {

    private final static String SANDBOX = "http://flw-pms-dev.eu-west-1.elasticbeanstalk.com";
    private final static String LIVE = "https://api.ravepay.co";
    
    /**
     * @param mode the mode to set
     */
    public void setMode(RaveMode mode) {
        this.mode = mode;
    }

    /**
     * @param publicKey the publicKey to set
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * @param privateKey the privateKey to set
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * @return the mode
     */
    public RaveMode getMode() {
        return mode;
    }

    /**
     * @return the publicKey
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * @return the privateKey
     */
    public String getPrivateKey() {
        return privateKey;
    }
    
    private RaveMode mode;
    private String publicKey;
    private String privateKey;
    
    public RavePaymentHelper(String privateKey, String publicKey, RaveMode mode) throws IllegalArgumentException{
        
        if(StringUtil.emptyToNull(privateKey) == null || StringUtil.emptyToNull(publicKey) == null)
            throw new IllegalArgumentException("Private or public key must not be null");
        
        setPrivateKey(privateKey);
        setPublicKey(publicKey);
        
        setMode(mode == null ? RaveMode.SANDBOX : mode);
    }
   
    public JSONObject chargeCard(RaveCard raveCard) throws Exception{
        
        if(raveCard == null){
            throw new IllegalArgumentException("Request can not be null");
        }
        
        if(raveCard.getAmount() <= 0.0)
            throw new Exception("Amount must be greater than 0.0");
        
        if(StringUtil.emptyToNull(raveCard.getEmail()) == null)
            throw new Exception("Email must be provided");
        
        if(StringUtil.emptyToNull(raveCard.getCardNo()) == null)
            throw new Exception("Card number must be provided");
        
        if(StringUtil.emptyToNull(raveCard.getCvv()) == null)
            throw new Exception("Card cvv must be provided");
        
        if(StringUtil.emptyToNull(raveCard.getExpiryMonth()) == null)
            throw new Exception("Card month must be provided");
        
        if(StringUtil.emptyToNull(raveCard.getExpiryYear()) == null)
            throw new Exception("Card year must be provided");
        
//        if(StringUtil.emptyToNull(raveCard.getPin()) == null)
//            throw new Exception("Card pin must be provided");
        
//        if(StringUtil.emptyToNull(raveCard.getPublicKey()) == null)
//            throw new Exception("Public key must be provided");
        
//        if(StringUtil.emptyToNull(raveCard.getPhone()) == null)
//            throw new Exception("Phone must be provided");
        raveCard.setPublicKey(publicKey);
        JSONObject payloadData = raveCard.toJson();
        
        String generatedKey = TripleDES.getKey(getPrivateKey());
        
        String encryptedRequest = TripleDES.encryptData(payloadData.toString(), generatedKey);
        
        JSONObject requestData = new JSONObject()
                .put("PBFPubKey", getPublicKey())
                .put("client", encryptedRequest)
                .put("alg", "3DES-24");
        
        String responseString;
        
        String baseUrl; 
        
        if(mode == RaveMode.SANDBOX)
            baseUrl = SANDBOX;
        else
            baseUrl = LIVE;
        
        Logger.getGlobal().log(Level.INFO, "REQUEST: {0}", requestData.toString());
        
        responseString = HttpUtil.doPost( baseUrl + RavePaths.CHARGE_CARD, requestData.toString(), getHeader());
        
        Logger.getGlobal().log(Level.INFO, "RESPONSE: {0}", responseString);
        
        if(responseString == null)
            throw new Exception("No response received from server");
        
        
        return RaveResponseParser.parseResponse(responseString);
    }
    
    public JSONObject chargeAccount(RaveAccount raveAccount) throws Exception{
        
        if(raveAccount == null){
            throw new IllegalArgumentException("Request can not be null");
        }
        
        if(raveAccount.getAmount() <= 0.0)
            throw new Exception("Amount must be greater than 0.0");
        
        if(StringUtil.emptyToNull(raveAccount.getEmail()) == null)
            throw new Exception("Email must be provided");
        
        if(StringUtil.emptyToNull(raveAccount.getAccountNo()) == null)
            throw new Exception("Account number must be provided");
        
        if(StringUtil.emptyToNull(raveAccount.getAccountBank()) == null)
            throw new Exception("Account bank must be provided");
        
//        if(StringUtil.emptyToNull(raveAccount.getExpiryMonth()) == null)
//            throw new Exception("Card month must be provided");
        
//        if(StringUtil.emptyToNull(raveCard.getExpiryYear()) == null)
//            throw new Exception("Card year must be provided");
        
////        if(StringUtil.emptyToNull(raveAccount.getPin()) == null)
//            throw new Exception("Card pin must be provided");
        
        if(StringUtil.emptyToNull(raveAccount.getPaymentType()) == null)
            throw new Exception("Payment type must be provided");
        
//        if(StringUtil.emptyToNull(raveAccount.getPhone()) == null)
//            throw new Exception("Phone must be provided");
        raveAccount.setPublicKey(publicKey);
        JSONObject payloadData = raveAccount.toJson();
        
        System.out.println(payloadData);
        
        String generatedKey = TripleDES.getKey(getPrivateKey());
        
        String encryptedRequest = TripleDES.encryptData(payloadData.toString(), generatedKey);
        
        JSONObject requestData = new JSONObject()
                .put("PBFPubKey", getPublicKey())
                .put("client", encryptedRequest)
                .put("alg", "3DES-24");
        
        String responseString;
        
        String baseUrl; 
        if(mode == RaveMode.SANDBOX)
            baseUrl = SANDBOX;
        else
            baseUrl = LIVE;
        
        responseString = HttpUtil.doPost( baseUrl + RavePaths.CHARGE_ACCOUNT, requestData.toString(), getHeader());
        
        if(responseString == null)
            throw new Exception("No response received from server");
        
        return RaveResponseParser.parseResponse(responseString);
    }
    
    public JSONObject getStatus(String reference, boolean isGateway) throws Exception{
        
        if(reference == null)
            throw new IllegalAccessException("Reference must be provided");
        
        JSONObject request = new JSONObject();
        request.put("SECKEY", getPrivateKey());
        request.put("normalize", 1);
        
        if(isGateway == true){
            request.put("flw_ref", reference);
        }
        else
            request.put("tx_ref", reference);
        
        String baseUrl; 
        if(mode == RaveMode.SANDBOX)
            baseUrl = SANDBOX;
        else
            baseUrl = LIVE;
        
        String responseString = HttpUtil.doPost( baseUrl + RavePaths.QUERY_TRANSACTION, request.toString(), getHeader());
        
        if(responseString == null)
            throw new Exception("No response from server");
        
        return RaveResponseParser.parseResponse(responseString);
    }
    
    public JSONObject validateCardCharge(String transactionReference, String otp) throws Exception{
        
        
        if(StringUtil.emptyToNull(transactionReference) == null || StringUtil.emptyToNull(otp) == null)
            throw new Exception("OTP and transaction reference must be provided");
        
        JSONObject request = new JSONObject()
                .put("PBFPubKey", getPublicKey())
                .put("transaction_reference", transactionReference)
                .put("otp", otp);
        
//        String response;
        
        String baseUrl; 
        if(mode == RaveMode.SANDBOX)
            baseUrl = SANDBOX;
        else
            baseUrl = LIVE;
        
        String responseString = HttpUtil.doPost( baseUrl + ""+RavePaths.VALIDATE_CARD_TRANSACTION, request.toString(), getHeader());
        
        if(responseString == null)
            throw new Exception("No response from server");
        
        return RaveResponseParser.parseResponse(responseString);
    }
    
    public JSONObject validateAccountCharge(String transactionReference, String otp) throws Exception{
        
        
        if(StringUtil.emptyToNull(transactionReference) == null || StringUtil.emptyToNull(otp) == null)
            throw new Exception("OTP and transaction reference must be provided");
        
        JSONObject request = new JSONObject()
                .put("PBFPubKey", getPublicKey())
                .put("transactionreference", transactionReference)
                .put("otp", otp);
        
//        String response;
        
        String baseUrl; 
        if(mode == RaveMode.SANDBOX)
            baseUrl = SANDBOX;
        else
            baseUrl = LIVE;
        
        String responseString = HttpUtil.doPost( baseUrl +RavePaths.VALIDATE_ACCOUNT_TRANSACTION, request.toString(), getHeader());
        
        if(responseString == null)
            throw new Exception("No response from server");
        
        return RaveResponseParser.parseResponse(responseString);
    }
    
    private Map getHeader(){
        
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        
        return header;
    }
    
}

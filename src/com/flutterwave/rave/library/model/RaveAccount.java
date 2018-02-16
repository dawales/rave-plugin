/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flutterwave.rave.library.model;

import com.flutterwave.rave.library.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author emmanueladeyemi
 */
public class RaveAccount extends RavePaymentModel {

    /**
     * @return the accountNo
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * @param accountNo the accountNo to set
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     * @return the accountBank
     */
    public String getAccountBank() {
        return accountBank;
    }

    /**
     * @param accountBank the accountBank to set
     */
    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    /**
     * @return the deviceFingerprint
     */
    public String getDeviceFingerprint() {
        return deviceFingerprint;
    }

    /**
     * @param deviceFingerprint the deviceFingerprint to set
     */
    public void setDeviceFingerprint(String deviceFingerprint) {
        this.deviceFingerprint = deviceFingerprint;
    }

    /**
     * @return the paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * @return the passCode
     */
    public String getPassCode() {
        return passCode;
    }

    /**
     * @param passCode the passCode to set
     */
    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    private String accountNo;
    private String accountBank;
//    private boolean internetBanking;
    private String deviceFingerprint;
    private String paymentType = "account";
    private String passCode;
    
    public JSONObject toJson() throws JSONException{
        
        JSONObject jSONObject = new JSONObject()
                .put("PBFPubKey", getPublicKey())
                .put("phonenumber", getPhone())
                .put("currency", getCurrency())
                .put("country", getCountry())
                .put("accountnumber", getAccountNo())
                .put("amount", getAmount())
                .put("accountbank", getAccountBank())
                .put("payment_type", getPaymentType())
                .put("suggested_auth", getAuthModel())
                .put("passcode", getPassCode() == null ? "" : getPassCode())
                .put("IP", getIp())
                .put("redirect_url", getRedirectUrl())
                .put("email", getEmail())
                .put("txRef", getTransactionReference());
        
        return jSONObject;
    }
    
    
}

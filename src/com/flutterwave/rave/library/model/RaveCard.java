/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flutterwave.rave.library.model;

import com.flutterwave.rave.library.util.RecurrentFrequency;
import com.flutterwave.rave.library.util.StringUtil;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author emmanueladeyemi
 */
public class RaveCard extends RavePaymentModel {

    /**
     * @return the recurrentFrequency
     */
    public RecurrentFrequency getRecurrentFrequency() {
        return recurrentFrequency;
    }

    /**
     * @param recurrentFrequency the recurrentFrequency to set
     */
    public void setRecurrentFrequency(RecurrentFrequency recurrentFrequency) {
        this.recurrentFrequency = recurrentFrequency;
    }

    /**
     * @return the recurrencyStopDate
     */
    public Date getRecurrencyStopDate() {
        return recurrencyStopDate;
    }

    /**
     * @param recurrencyStopDate the recurrencyStopDate to set
     */
    public void setRecurrencyStopDate(Date recurrencyStopDate) {
        this.recurrencyStopDate = recurrencyStopDate;
    }

    /**
     * @return the cardNo
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * @param cardNo the cardNo to set
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * @return the cvv
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * @param cvv the cvv to set
     */
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    /**
     * @return the pin
     */
    public String getPin() {
        return pin;
    }

    /**
     * @param pin the pin to set
     */
    public void setPin(String pin) {
        this.pin = pin;
    }

    /**
     * @return the expiryYear
     */
    public String getExpiryYear() {
        return expiryYear;
    }

    /**
     * @param expiryYear the expiryYear to set
     */
    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
    }

    /**
     * @return the expiryMonth
     */
    public String getExpiryMonth() {
        return expiryMonth;
    }

    /**
     * @param expiryMonth the expiryMonth to set
     */
    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    private String cardNo;
    private String cvv;
    private String pin;
    private String expiryYear;
    private String expiryMonth;
    private RecurrentFrequency recurrentFrequency;
    private Date recurrencyStopDate;
    
    public JSONObject toJson() throws JSONException{
        
        JSONObject jSONObject = new JSONObject()
                .put("PBFPubKey", getPublicKey())
                .put("cardno", getCardNo())
                .put("currency", getCurrency())
                .put("country", getCountry())
                .put("cvv", getCvv())
                .put("phonenumber", getPhone())
                .put("firstname", getFirstName())
                .put("lastname", getLastName())
                .put("amount", getAmount())
                .put("expiryyear", getExpiryYear())
                .put("expirymonth", getExpiryMonth())
                .put("suggested_auth", getAuthModel())
                .put("pin", getPin())
                .put("redirect_url", getRedirectUrl())
                .put("email", getEmail())
                .put("txRef", getTransactionReference());
        
        if(recurrentFrequency != null){
            jSONObject.put("charge_type", recurrentFrequency.getValue());
            
            if(recurrencyStopDate != null){
                jSONObject.put("recurring_stop", StringUtil.formateDate(recurrencyStopDate, "YYMMDD"));
            }
        }
        
        
        return jSONObject;
    }
}

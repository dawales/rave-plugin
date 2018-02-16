/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flutterwave.rave.library.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

/**
 *
 * @author emmanueladeyemi
 */
public class HttpUtil {
    
    /**
     * 
     * This is called to perform a POST request
     * 
     * @param url
     * @param body
     * @param header
     * @return 
     */
    public static String doPost(String url, String body, Map<String,String> header){
        
        try {
            
            HttpPost httpPost = new HttpPost(""+url);
            
            header.entrySet().forEach((key) -> {
                httpPost.addHeader(key.getKey(), key.getValue());
            });
            
            StringEntity params =new StringEntity(body);

//            params.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(params);
            
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build();
            
            HttpClient client = new DefaultHttpClient();
            
//            CloseableHttpClient client =   //HttpClients.custom().setSSLContext(sslContext).setSSLHostnameVerifier(new NoopHostnameVerifier()).build();

            httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
//            httpGet.addHeader("Content-Type", "application/json");

            HttpResponse response = client.execute(httpPost);
            
            int resultCode = response.getStatusLine().getStatusCode();

            InputStream stream = response.getEntity().getContent();
            
//            if (resultCode != 200 && resultCode != 201 && resultCode != 204) {
//                
//                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
//                String result = br.lines().collect(Collectors.joining());
//                
//                throw new Exception(result);
//            }

//            String result = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            
            String result = br.lines().collect(Collectors.joining(""));
            
            return result;
        } catch (Exception ex) {
            Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, null, ex.getCause());
            
        }
        
        return null; 
    }
    
    /**
     * 
     * This is called to perform a GET request
     * 
     * @param url
     * @param header
     * @return 
     */
    public static String doGet(String url, Map<String,String> header){
        
        try {
            
            HttpGet httpGet = new HttpGet(""+url);
            
            header.entrySet().forEach((key) -> {
                httpGet.addHeader(key.getKey(), key.getValue());
            });
            
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build();

            CloseableHttpClient client = HttpClients.custom().setSSLContext(sslContext).setSSLHostnameVerifier(new NoopHostnameVerifier()).build();

            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
//            httpGet.addHeader("Content-Type", "application/json");

            HttpResponse response = client.execute(httpGet);
            
            int resultCode = response.getStatusLine().getStatusCode();

            InputStream stream = response.getEntity().getContent();
            
            if (resultCode != 200 && resultCode != 201 && resultCode != 204) {
                
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                String result = br.lines().collect(Collectors.joining());
                
                throw new Exception(result);
            }

//            String result = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            
            String result = br.lines().collect(Collectors.joining(""));
            
            return result;
        } catch (Exception ex) {
            Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, null, ex.getCause());
            
        }
        
        return null; 
    }
}

package com.rbsg.ob.aspspbank.authorisation.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


public class OBHttpRequestHelper {


    private String paymentBaseUrl;


    public OBHttpRequestHelper(String paymentBaseUrl){
       this.paymentBaseUrl=paymentBaseUrl;

    }


    public String getPaymentBaseUrl() {
        return paymentBaseUrl;
    }

    public HttpHeaders   setupOBRequestHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set("x-idempotency-key","12345");
        headers.set("x-fapi-financial-id","1");
        headers.set("Authorization","");


        return headers;
    }




}

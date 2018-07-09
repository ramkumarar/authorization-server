package com.rbsg.ob.aspspbank.authorisation.service;

import com.rbsg.ob.aspspbank.authorisation.common.OBHttpRequestHelper;
import com.rbsg.ob.aspspbank.authorisation.common.PispResources;
import com.rbsg.ob.aspspbank.authorisation.model.Payment;
import com.rbsg.ob.aspspbank.authorisation.model.PaymentSetupPOSTResponse;
import com.rbsg.ob.aspspbank.authorisation.model.TrustedBeneficiaryDTO;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PaymentService {

 @Autowired
 RestTemplate restTemplate;

 @Autowired
 OBHttpRequestHelper obHttpRequestHelper;

 public Payment fetchPayment(String paymentId) {

  String resourceUrl= new StringBuilder().append(obHttpRequestHelper.getPaymentBaseUrl())
          .append("/")
          .append(PispResources.PAYMENT_INITIATION.toString())
          .append("/")
          .append(paymentId)
          .toString();
  HttpEntity entity = new HttpEntity(obHttpRequestHelper.setupOBRequestHeaders());

  ResponseEntity<PaymentSetupPOSTResponse> response = restTemplate.exchange(
          resourceUrl, HttpMethod.GET, entity, PaymentSetupPOSTResponse.class);

  String trustedBeneficiariesUrl= new StringBuilder().append(obHttpRequestHelper.getPaymentBaseUrl())
          .append("/")
          .append(PispResources.TRUSTED_BENEFICIARIES).toString();

   List<TrustedBeneficiaryDTO> trustedBeneficiary = exchangeAsList(trustedBeneficiariesUrl,new ParameterizedTypeReference<List<TrustedBeneficiaryDTO>>() {});


  PaymentSetupPOSTResponse responseBody= response.getBody();


  DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

  boolean isTrustedBeneficiary=isTrustedBeneficiary(trustedBeneficiary,new TrustedBeneficiaryDTO(responseBody.getData().getInitiation().getCreditorAccount().getName(),
          responseBody.getData().getInitiation().getCreditorAgent().getIdentification(),responseBody.getData().getInitiation().getCreditorAccount().getIdentification()));


  Payment paymentSetup= new Payment();
  paymentSetup.setPaymentId(responseBody.getData().getPaymentId());
  paymentSetup.setDebitAccount(responseBody.getData().getInitiation().getDebtorAccount().getIdentification());
  paymentSetup.setCreditAccount(responseBody.getData().getInitiation().getCreditorAccount().getIdentification());
  paymentSetup.setCreditorName(responseBody.getData().getInitiation().getCreditorAccount().getName());
  paymentSetup.setAmount(responseBody.getData().getInitiation().getInstructedAmount().getAmount());
  paymentSetup.setReference(responseBody.getData().getInitiation().getRemittanceInformation().getReference());
  paymentSetup.setDateTime(fmt.print(responseBody.getData().getCreationDateTime()));
  paymentSetup.setTrustedBeneficiery(isTrustedBeneficiary);

  return paymentSetup;
 }

 private boolean isTrustedBeneficiary(List<TrustedBeneficiaryDTO> trustedBeneficiaries,TrustedBeneficiaryDTO currentBenificiery){
   for(TrustedBeneficiaryDTO trustedBeneficiary : trustedBeneficiaries) {
      if (trustedBeneficiary.toString().equals(currentBenificiery.toString()) ){
        return true;
    }
   }
    return false;
 }

 private <T> List<T> exchangeAsList(String uri, ParameterizedTypeReference<List<T>> responseType) {
  return restTemplate.exchange(uri, HttpMethod.GET, null, responseType).getBody();
 }

}

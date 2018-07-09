package com.rbsg.ob.aspspbank.authorisation.model;


import java.io.Serializable;

public class Payment implements Serializable {
 private String paymentId;
 private String debitAccount;
 private String creditAccount;
 private String creditorName;
 private String amount;
 private String reference;
 private String dateTime;
 private boolean isTrustedBeneficiery;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(String debitAccount) {
        this.debitAccount = debitAccount;
    }

    public String getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isTrustedBeneficiery() {
        return isTrustedBeneficiery;
    }

    public void setTrustedBeneficiery(boolean trustedBeneficiery) {
        isTrustedBeneficiery = trustedBeneficiery;
    }
}

package com.rbsg.ob.aspspbank.authorisation.model;

public class TrustedBeneficiaryDTO {
    private String creditorAgentIdentification;
    private String creditorAccountIdentification;
    private String creditorName;

    public TrustedBeneficiaryDTO(){

    }

    public TrustedBeneficiaryDTO(String creditorName, String creditorAgentIdentification, String creditorAccountIdentification){
        this.creditorName=creditorName;
        this.creditorAccountIdentification=creditorAccountIdentification;
        this.creditorAgentIdentification=creditorAgentIdentification;
    }

    public String getCreditorAgentIdentification() {
        return creditorAgentIdentification;
    }

    public void setCreditorAgentIdentification(String creditorAgentIdentification) {
        this.creditorAgentIdentification = creditorAgentIdentification;
    }

    public String getCreditorAccountIdentification() {
        return creditorAccountIdentification;
    }

    public void setCreditorAccountIdentification(String creditorAccountIdentification) {
        this.creditorAccountIdentification = creditorAccountIdentification;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    @Override
    public String toString() {
        return String.format(
                "TrustedBeneficiary[creditorAgentIdentification='%s', creditorAccountIdentification='%s', creditorName='%s']",
                creditorAgentIdentification, creditorAccountIdentification,creditorName);
    }
}

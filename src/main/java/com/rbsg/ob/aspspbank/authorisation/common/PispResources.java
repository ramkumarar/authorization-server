package com.rbsg.ob.aspspbank.authorisation.common;

public enum PispResources {
    PAYMENT_INITIATION("payments"),
    PAYMENT_SUBMISSION("payment-submissions"),
    TRUSTED_BENEFICIARIES("trustedBeneficiaries");

    PispResources(String resourceName) {
        this.resourceName=resourceName;
    }

    @Override
    public String toString() {
        return String.valueOf(resourceName);
    }

    private String resourceName;
}

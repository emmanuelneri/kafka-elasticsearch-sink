package br.com.emmanuelneri.producer;

import java.math.BigDecimal;

public class OrderRequest {

    private String identifier;
    private String customer;
    private BigDecimal value;

    public OrderRequest() {

    }

    public OrderRequest(String identifier, String customer, BigDecimal value) {
        this.identifier = identifier;
        this.customer = customer;
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCustomer() {
        return customer;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}

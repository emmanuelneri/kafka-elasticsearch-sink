package br.com.emmanuelneri.producer;

import java.math.BigDecimal;

public class OrderRequest {

    private String identifier;
    private String customer;
    private Type type;
    private BigDecimal value;

    public OrderRequest() {

    }

    public OrderRequest(String identifier, Type type, String customer, BigDecimal value) {
        this.identifier = identifier;
        this.type = type;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

enum Type {
    PURCHASE,
    SALE
}

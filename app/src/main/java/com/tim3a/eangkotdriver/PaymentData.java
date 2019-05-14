package com.tim3a.eangkotdriver;

public class PaymentData {

    private int id, id_customer, distance;
    private boolean alreadyPay;

    public PaymentData(int id, int id_customer, int distance, boolean alreadyPay) {
        this.id = id;
        this.id_customer = id_customer;
        this.distance = distance;
        this.alreadyPay = alreadyPay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public boolean isAlreadyPay() {
        return alreadyPay;
    }

    public void setAlreadyPay(boolean alreadyPay) {
        this.alreadyPay = alreadyPay;
    }
}

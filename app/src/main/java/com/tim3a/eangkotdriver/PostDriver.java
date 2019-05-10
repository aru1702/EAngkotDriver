package com.tim3a.eangkotdriver;

public class PostDriver {
    public String address, email, id_angkot, last_update, name, password, phonenumber, qrcode, since, username;

    public PostDriver(String address, String email, String id_angkot, String last_update, String name, String password, String phonenumber, String qrcode, String since, String username) {
        this.address = address;
        this.email = email;
        this.id_angkot = id_angkot;
        this.last_update = last_update;
        this.name = name;
        this.password = password;
        this.phonenumber = phonenumber;
        this.qrcode = qrcode;
        this.since = since;
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId_angkot() {
        return id_angkot;
    }

    public void setId_angkot(String id_angkot) {
        this.id_angkot = id_angkot;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getSince() {
        return since;
    }

    public void setSince(String since) {
        this.since = since;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

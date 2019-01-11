package com.imejadevs.employee.Model;

public class Expenses {
    String flag_name, total_cash,date_claimed,  claim_summary,flag_claimed,date_incurred,  date_paid,  date_added,  vat_component,payment_status;
    byte[] byteArray;

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public Expenses() {
    }

    public String getDate_claimed() {
        return date_claimed;
    }

    public void setDate_claimed(String date_claimed) {
        this.date_claimed = date_claimed;
    }

    public String getFlag_name() {
        return flag_name;
    }

    public void setFlag_name(String flag_name) {
        this.flag_name = flag_name;
    }

    public String getTotal_cash() {
        return total_cash;
    }

    public void setTotal_cash(String total_cash) {
        this.total_cash = total_cash;
    }

    public String getClaim_summary() {
        return claim_summary;
    }

    public void setClaim_summary(String claim_summary) {
        this.claim_summary = claim_summary;
    }

    public String getFlag_claimed() {
        return flag_claimed;
    }

    public void setFlag_claimed(String flag_claimed) {
        this.flag_claimed = flag_claimed;
    }

    public String getDate_incurred() {
        return date_incurred;
    }

    public void setDate_incurred(String date_incurred) {
        this.date_incurred = date_incurred;
    }

    public String getDate_paid() {
        return date_paid;
    }

    public void setDate_paid(String date_paid) {
        this.date_paid = date_paid;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getVat_component() {
        return vat_component;
    }

    public void setVat_component(String vat_component) {
        this.vat_component = vat_component;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }
}

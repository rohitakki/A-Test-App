package com.mixedapp.iamro45;

public class MixedLogs {
    private String number;
    private String date;

    public MixedLogs(){
    }

    public MixedLogs(String number, String date){
        this.number = number;
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

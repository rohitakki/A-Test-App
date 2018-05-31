package com.mixedapp.iamro45;

public class MixedMessage {
    private String text;
    private String number;
    private String date;

    public MixedMessage() {
    }

    public MixedMessage(String text, String number, String date) {
        this.text = text;
        this.number = number;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

package com.mteam.chat_professional;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

/**
 * Created by Stealer Of Souls on 4/28/2018.
 */
@IgnoreExtraProperties
public class Message {
   private String phone;
    private String message;
    private String  date;

    public Message(String phone, String message, String date) {
        this.phone = phone;
        this.message = message;
        this.date = date;
    }

    public Message() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

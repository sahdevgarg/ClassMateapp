
package com.htlconline.sm.classmate.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContactListPojo {

    @SerializedName("tmessage")
    @Expose
    private List<Tmessage> tmessage = null;
    @SerializedName("sender")
    @Expose
    private String sender;
    @SerializedName("senderId")
    @Expose
    private Integer senderId;
    @SerializedName("tuserId")
    @Expose
    private String tuserId;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("action")
    @Expose
    private String action;

    public List<Tmessage> getTmessage() {
        return tmessage;
    }

    public void setTmessage(List<Tmessage> tmessage) {
        this.tmessage = tmessage;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public String getTuserId() {
        return tuserId;
    }

    public void setTuserId(String tuserId) {
        this.tuserId = tuserId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}

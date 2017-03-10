package com.htlconline.sm.classmate.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Message {







	@SerializedName("tmessage")
	@Expose
	private String tmessage;
	@SerializedName("sender")
	@Expose
	private String sender;
	@SerializedName("senderId")
	@Expose
	private String senderId;
	@SerializedName("tuserId")
	@Expose
	private String tuserId;
	@SerializedName("time")
	@Expose
	private String time;
	@SerializedName("action")
	@Expose
	private String action;



	private String fromName;
	private boolean isSelf;




	public String getTmessage() {
		return tmessage;
	}

	public void setTmessage(String tmessage) {
		this.tmessage = tmessage;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
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

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public boolean isSelf() {
		return isSelf;
	}

	public void setIsSelf(boolean isSelf) {
		this.isSelf = isSelf;
	}
}

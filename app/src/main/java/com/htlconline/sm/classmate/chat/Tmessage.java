
package com.htlconline.sm.classmate.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tmessage {

    @SerializedName("last_message")
    @Expose
    private String lastMessage;
    @SerializedName("gname")
    @Expose
    private String gname;
    @SerializedName("is_group")
    @Expose
    private String isGroup;
    @SerializedName("umess")
    @Expose
    private String umess;
    @SerializedName("gid")
    @Expose
    private String gid;
    @SerializedName("time")
    @Expose
    private String time;

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup;
    }

    public String getUmess() {
        return umess;
    }

    public void setUmess(String umess) {
        this.umess = umess;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}

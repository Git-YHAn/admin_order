package com.yang.server.vo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TokenPayLoad {
    private long userid = 0L;
    private String username = "";
    private String password = "";
    private String ip = "";
    private long createDate = System.currentTimeMillis();
    private long expDate = System.currentTimeMillis() + 1800 * 1000;

    public TokenPayLoad(long userid, String username, String password, String ip) {
        super();
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.ip = ip;
    }

    public TokenPayLoad() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getExpDate() {
        return expDate;
    }

    public void setExpDate(long expDate) {
        this.expDate = expDate;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

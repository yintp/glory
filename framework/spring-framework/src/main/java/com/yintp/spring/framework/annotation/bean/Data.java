package com.yintp.spring.framework.annotation.bean;

public class Data {

    private String name;
    private String nick;
    private String pwd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "Data{" +
                "name='" + name + '\'' +
                ", nick='" + nick + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}

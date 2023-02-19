package com.yintp.rmi.api;

import java.io.Serializable;

/**
 * rmi远程调用对象，需实现Serializable
 *
 * @author yintp
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    public User() {
        this.username = "default";
    }

    public User(String username) {
        this.username = username;
    }

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
            "username='" + username + '\'' +
            '}';
    }
}

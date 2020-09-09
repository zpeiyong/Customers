package com.dataint.topic.model;


//从请求流中提取的登录对象
public class LoginUser {
    private String username;
    private String password;
    private Integer rememberMe;

    public LoginUser() {
    }

    public LoginUser(String username, String password, Integer rememberMe) {
        this.username = username;
        this.password = password;
        this.rememberMe = rememberMe;
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

    public Integer getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Integer rememberMe) {
        this.rememberMe = rememberMe;
    }
}

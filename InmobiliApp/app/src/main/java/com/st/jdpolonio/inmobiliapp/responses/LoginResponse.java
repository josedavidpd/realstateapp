package com.st.jdpolonio.inmobiliapp.responses;

import com.st.jdpolonio.inmobiliapp.models.User;

public class LoginResponse {
    private String token;
    private User user;

    public LoginResponse(){}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

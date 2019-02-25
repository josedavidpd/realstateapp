package com.st.jdpolonio.inmobiliapp.responses;

import com.st.jdpolonio.inmobiliapp.models.User;

public class UserResponse {

    private User user;

    public UserResponse(){}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "user=" + user +
                '}';
    }
}

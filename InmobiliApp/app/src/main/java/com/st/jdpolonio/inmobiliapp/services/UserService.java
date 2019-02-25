package com.st.jdpolonio.inmobiliapp.services;

import com.st.jdpolonio.inmobiliapp.models.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {

    @GET("/users/me")
    Call<User> getMe();
}

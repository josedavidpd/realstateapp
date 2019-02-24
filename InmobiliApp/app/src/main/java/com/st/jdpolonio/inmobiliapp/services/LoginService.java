package com.st.jdpolonio.inmobiliapp.services;

import com.st.jdpolonio.inmobiliapp.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginService {

   @POST("/auth")
   Call<LoginResponse> login(@Header("Authorization") String authorization);


}

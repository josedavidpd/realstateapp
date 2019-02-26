package com.st.jdpolonio.inmobiliapp.services;

import com.st.jdpolonio.inmobiliapp.models.ResponseContainer;
import com.st.jdpolonio.inmobiliapp.models.User;
import com.st.jdpolonio.inmobiliapp.responses.PropertyAuthResponse;
import com.st.jdpolonio.inmobiliapp.responses.PropertyFavResponse;
import com.st.jdpolonio.inmobiliapp.responses.PropertyResponse;
import com.st.jdpolonio.inmobiliapp.responses.SingleResponseContainer;
import com.st.jdpolonio.inmobiliapp.responses.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PropertyService {

    @GET("/properties")
    Call<ResponseContainer<PropertyResponse>> getProperties();

    @POST("/properties/fav/{id_property}")
    Call<UserResponse> addToFav(@Path("id_property")String id_property);

    @DELETE("/properties/fav/{id_property}")
   // @HTTP(method = "DELETE", path = "/properties/fav/{id_property}", hasBody = true)
    Call<UserResponse> deleteFav(@Path("id_property")String id_property);

    @GET("/properties/{id_property}")
    Call<SingleResponseContainer> findOne(@Path("id_property")String id_property);

    @GET("/properties/fav")
    Call<ResponseContainer<PropertyFavResponse>> getPropsFavs();

    @GET("/properties/auth")
    Call<ResponseContainer<PropertyAuthResponse>> getPropAuth();

}

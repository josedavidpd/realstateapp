package com.st.jdpolonio.inmobiliapp.services;

import com.st.jdpolonio.inmobiliapp.models.ResponseContainer;
import com.st.jdpolonio.inmobiliapp.models.User;
import com.st.jdpolonio.inmobiliapp.responses.MinePropertyResponse;
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
import retrofit2.http.Query;

public interface PropertyService {

    public final String BASE_URL = "/properties";

    @GET(BASE_URL)
    Call<ResponseContainer<PropertyResponse>> getProperties();

    @POST(BASE_URL + "/fav/{id_property}")
    Call<UserResponse> addToFav(@Path("id_property") String id_property);

    @DELETE(BASE_URL + "/fav/{id_property}")
        // @HTTP(method = "DELETE", path = "/properties/fav/{id_property}", hasBody = true)
    Call<UserResponse> deleteFav(@Path("id_property") String id_property);

    @GET(BASE_URL + "/{id_property}")
    Call<SingleResponseContainer> findOne(@Path("id_property") String id_property);

    @GET(BASE_URL + "/fav")
    Call<ResponseContainer<PropertyFavResponse>> getPropsFavs();

    @GET(BASE_URL + "/auth")
    Call<ResponseContainer<PropertyAuthResponse>> getPropAuth();

    @GET(BASE_URL)
    Call<ResponseContainer<PropertyResponse>> query(@Query("q") String query);

    @GET(BASE_URL + "/mine")
    Call<ResponseContainer<MinePropertyResponse>> getMyProps();

}

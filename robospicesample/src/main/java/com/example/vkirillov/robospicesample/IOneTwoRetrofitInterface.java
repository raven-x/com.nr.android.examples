package com.example.vkirillov.robospicesample;

import java.util.List;

import retrofit.http.GET;

/**
 * Created by vkirillov on 16.12.2015.
 */
public interface IOneTwoRetrofitInterface {
    @GET("/key/value/one/two")
    OneTwoRetrofitJson getOneTwo();
}

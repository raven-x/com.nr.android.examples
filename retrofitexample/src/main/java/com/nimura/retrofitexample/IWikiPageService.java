package com.nimura.retrofitexample;

import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Limi on 31.08.2015.
 */
public interface IWikiPageService {
    @GET("/wiki/{id}")
    public Call<String> getPage(@Path("id") String pageId);
}

package com.example.vkirillov.robospicesample;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by vkirillov on 16.12.2015.
 */
public class SampleRetrofitSpiceRequest extends RetrofitSpiceRequest<OneTwoRetrofitJson, IOneTwoRetrofitInterface> {

    public SampleRetrofitSpiceRequest(){
        super(OneTwoRetrofitJson.class, IOneTwoRetrofitInterface.class);
    }

    @Override
    public OneTwoRetrofitJson loadDataFromNetwork() throws Exception {
        return getService().getOneTwo();
    }
}

package com.example.sayarsamanta.cashrichtest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetData {
    @GET("/testCashRich")

//Wrap the response in a Call object with the type of the expected result//

    Call<List<Model>> getAllData();
}

package com.example.samitiapplication.modal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {


    @GET("data")
    Call<List<Person>> getPerson();

}

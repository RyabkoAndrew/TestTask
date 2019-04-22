package com.example.testtask.webservice;

import com.example.testtask.model.Example;
import com.example.testtask.model.JSONResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIYoutube {

    @GET("v3/playlistItems?playlistId=RDEMORiNTZh5MD9bcOhfcgo6iQ&maxResults=50&part=snippet&key=AIzaSyCXhdS5_mA1E-Bf4oeLHNoOkufCnwbOl-c")
    Call<JSONResponse> getData();


    @GET("v3/playlistItems?playlistId=PLISN9BJSOPGIxoq0wCvYwPDyC41mu8IFd&maxResults=50&part=snippet&key=AIzaSyCXhdS5_mA1E-Bf4oeLHNoOkufCnwbOl-c")
    Call<JSONResponse> getDataSecond();


    @GET("v3/playlistItems?playlistId=PLMC9KNkIncKtPzgY-5rmhvj7fax8fdxoj&maxResults=50&part=snippet&key=AIzaSyCXhdS5_mA1E-Bf4oeLHNoOkufCnwbOl-c")
    Call<JSONResponse> getDataThird();

}

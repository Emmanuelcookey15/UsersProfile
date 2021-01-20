package com.LineCard.userprofileprojectquestionone.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    /**
     * Business
     * */
    @GET("users")
    fun getUserData(): Call<JsonObject>



}
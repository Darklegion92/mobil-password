package com.example.generatepassword

import retrofit2.Call
import retrofit2.http.GET

interface APIService {

    @GET("generate")
    fun getPassword(): Call<ResponseApi>
}
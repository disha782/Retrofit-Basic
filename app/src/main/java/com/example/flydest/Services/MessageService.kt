package com.example.flydest.Services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface MessageService {

    @GET
    fun getMessages(@Url alternateurl : String) : Call<String>
}
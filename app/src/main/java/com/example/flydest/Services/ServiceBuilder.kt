package com.example.flydest.Services

import android.os.Build
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale
import java.util.concurrent.TimeUnit

object ServiceBuilder {

    private const val URL = "http://192.168.29.251:500/"
    const val ALTERNATE_URL = "http://192.168.29.251:100/"

    //adding log
    //add dependency for this
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val headerInterceptor = object : Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            request = request.newBuilder().addHeader("x-device-type", Build.DEVICE)
                .addHeader("Accept-Language", Locale.getDefault().language).build()
            val response = chain.proceed(request)
            return response
        }

    }

    //Create OkHttp Client
    //addInterceptor can be used again and again.
    //the sequence should be header and then logger
    private val okHttp = OkHttpClient.Builder().addInterceptor(headerInterceptor)
        .addInterceptor(logger)

    //Create Retrofit Builder
    private val builder = Builder().baseUrl(URL)
        .client(OkHttpClient.Builder().connectTimeout(50, TimeUnit.SECONDS).build())
        .addConverterFactory(GsonConverterFactory.create()).client(okHttp.build())

    //Create Retrofit Instance
    private val retrofit = builder.build()

    fun <T> buildService(serviceType : Class<T>): T {
        return retrofit.create(serviceType)
    }
}
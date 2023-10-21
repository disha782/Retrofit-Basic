package com.example.flydest.Services

import com.example.flydest.model.Destination
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface DestinationService {

//    using query parameter
//    @GET("destination")
//    fun getDestinationList(@Query("country") country : String?) : Call<List<Destination>>

    //creating header which is not standard
    //there should be no space between :
 //   @Headers("x-disha:poojari", "x-abcds-integer-type:1234")

    //using querymap. Also adding header to request
//    @GET("destination")
//    fun getDestinationList(@QueryMap qmap: HashMap<String, String>?,
//                           @Header("Accept-Language") header: String) : Call<List<Destination>>

    @GET("desitnation")
    fun getDestinationList(@QueryMap qmap : HashMap<String, String>) : Call<List<Destination>>

    @GET("destination/{id}")
    fun getDestination(@Path("id") id : Int) : Call<Destination>

    @POST("destination")
    fun addDestination(@Body newDestination: Destination) : Call<Destination>

    @FormUrlEncoded
    @PUT("destination/{id}")
    fun updateDestination(@Path("id") id : Int,
    @Field("city") city : String,
    @Field("country") country : String,
    @Field("description") desc : String) : Call<Destination>

    //Unit is null in java
    @DELETE("destination/{id}")
    fun deleteDestination(@Path("id") id : Int) : Call<Unit>
}
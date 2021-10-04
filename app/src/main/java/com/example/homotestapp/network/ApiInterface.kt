package com.example.homotestapp.network

import com.example.homotestapp.hamster.data.HamsterModel
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("test3")
    fun fetchAllPosts(): Call<MutableList<HamsterModel>>

}
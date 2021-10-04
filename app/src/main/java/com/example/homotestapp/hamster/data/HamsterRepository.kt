package com.example.homotestapp.hamster.data

import androidx.lifecycle.MutableLiveData
import com.example.homotestapp.network.ApiClient
import com.example.homotestapp.network.ApiInterface

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HamsterRepository {
    private var apiInterface: ApiInterface?=null

    init {
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    }

    fun fetchAllPosts(): MutableLiveData<MutableList<HamsterModel>?> {
        val data = MutableLiveData<MutableList<HamsterModel>?>()

        apiInterface?.fetchAllPosts()?.enqueue(object : Callback<MutableList<HamsterModel>>{

            override fun onFailure(call: Call<MutableList<HamsterModel>>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<MutableList<HamsterModel>>,
                response: Response<MutableList<HamsterModel>>
            ) {
                val res = response.body()
                if (response.code() == 200 &&  res!=null){
                    data.value = res
                }else{
                    data.value = null
                }
            }
        })
        return data
    }
}
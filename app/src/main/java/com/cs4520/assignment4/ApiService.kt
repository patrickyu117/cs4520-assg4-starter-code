package com.cs4520.assignment4

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(Api.ENDPOINT)
    suspend fun getProducts(@Query("page") page: Int? = null): List<Product>
}
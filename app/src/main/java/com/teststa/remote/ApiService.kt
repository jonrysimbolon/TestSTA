package com.teststa.remote

import retrofit2.Call
import retrofit2.http.GET


interface ApiService {
    @GET("users/google/repos")
    fun fetchRepo(): Call<List<Repo>>

}

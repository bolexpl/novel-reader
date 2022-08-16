package com.example.novelreader

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("/2020/06/27/forbidden-master-part-3-chapter-64/")
    suspend fun getContent(): Call<String?>?

    companion object {
        private var instance: ApiService? = null

        fun getInstance(): ApiService {

            if (instance != null) return instance!!

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor).build()

            instance = Retrofit.Builder()
                .baseUrl("https://sads07.wordpress.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build()
                .create(ApiService::class.java)

            return instance!!
        }
    }
}
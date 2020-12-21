package com.iml1s.interview.test.datasource

import com.chenxyu.retrofit.adapter.FlowCallAdapterFactory
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.iml1s.interview.test.BuildConfig
import com.iml1s.interview.test.model.SearchResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("/api")
    fun search(
        @Query("key") key: String = KEY,
        @Query("q") keyword: String
    ): Flow<SearchResponse>
}

// region [const]
const val KEY = "19606302-e12e86be65f3144e4ac1d6f08"
const val BASE_URL = "https://pixabay.com"
// endregion


// region [network]
private val client: OkHttpClient
    get() = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
                )
        )
        .build()

private val gsonConverterFactory = GsonConverterFactory.create(
    GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
)

val defaultApiService: ApiService
    get() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(FlowCallAdapterFactory())
        .addConverterFactory(gsonConverterFactory)
        .client(client)//show basic log
        .build()
        .create(ApiService::class.java)

// endregion

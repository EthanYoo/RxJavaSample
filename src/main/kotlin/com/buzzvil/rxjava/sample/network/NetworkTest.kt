package com.buzzvil.rxjava.sample.network

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

fun main(args: Array<String>) {
    val httpService = retrofit.create(HttpService::class.java)
    httpService.requestPosts()
            .flatMapIterable { it }
            .filter { it.id == 99 }
            .map { "Filtered Target : ${it.title}" }
            .observeOn(Schedulers.newThread())
            .subscribe { println(it) }
}

interface HttpService {
    @GET("posts")
    fun requestPosts(): Observable<List<Contents>>
}

const val BASE_HOST = "https://jsonplaceholder.typicode.com/"
val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_HOST)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

data class Contents(val userId: Int, val id: Int, val title: String, val body: String)

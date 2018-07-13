package com.buzzvil.rxjava.sample.concurrent

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

fun main(args: Array<String>) {
    println("Start")

    val async = true

    if (async)
        startAsync()
    else
        start()

    Thread.sleep(5000)
}

fun start(): Disposable = getToken().subscribeOn(Schedulers.io())
        .flatMap {
            Observable.zip(
                    getBigData(it).subscribeOn(Schedulers.io()),
                    getSmallData(it).subscribeOn(Schedulers.io()),
                    BiFunction<BigData, SmallData, String> { a, b -> "$a | $b" }
            )
        }
        .observeOn(Schedulers.single())
        .subscribe { println(it) }

fun startAsync(): Disposable = getTokenAsync()
        .flatMap {
            Observable.zip(
                    getBigDataAsync(it),
                    getSmallDataAsync(it),
                    BiFunction<BigData, SmallData, String> { a, b -> "$a | $b" }
            )
        }
        .observeOn(Schedulers.single())
        .subscribe { println(it) }

fun getToken(): Observable<Token> = Observable.create {
    println("<top>.getToken")
    Thread.sleep(1000)
    it.onNext(Token("1234"))
    it.onComplete()
}

fun getTokenAsync(): Observable<Token> = Observable.defer { getToken().subscribeOn(Schedulers.io()) }

fun getBigData(token: Token): Observable<BigData> = Observable.create {
    println("<top>.getBigData")
    Thread.sleep(2000)
    it.onNext(BigData("BigData.a.$token", 6))
    it.onComplete()
}

fun getBigDataAsync(token: Token): Observable<BigData> = Observable.defer { getBigData(token).subscribeOn(Schedulers.io()) }

fun getSmallData(token: Token): Observable<SmallData> = Observable.create {
    println("<top>.getSmallData")
    Thread.sleep(1000)
    it.onNext(SmallData("SmallData.a.$token", 1))
    it.onComplete()
}

fun getSmallDataAsync(token: Token): Observable<SmallData> = Observable.defer { getSmallData(token).subscribeOn(Schedulers.io()) }

data class Token(val key: String)
data class BigData(val name: String, val data: Int)
data class SmallData(val name: String, val data: Int)
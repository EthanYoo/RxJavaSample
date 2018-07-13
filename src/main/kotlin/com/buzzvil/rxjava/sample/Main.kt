package com.buzzvil.rxjava.sample

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun main(args: Array<String>) {
    println("Start")

    getUser()
            .filter { it.age > 1 }
            .map { it.name }
            .subscribe { println(it) }

    println("End")
}

private fun getUser(): Observable<User> {
    return Observable.just(
            User("a", 1),
            User("b", 2),
            User("c", 3)
    )
}

data class User(val name: String, val age: Int)
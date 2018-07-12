import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun main(args: Array<String>) {
    println("kotlin main start")

    userlist().subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .doOnComplete { println("doOnComplete") }
            .doOnError { println("doOnError") }
            .subscribe { println("user : $it") }
}

private fun userlist(): Observable<User> {
    return Observable.create<User> {
        it.onNext(User(1, "young"))
        it.onNext(User(2, "jim"))
        it.onNext(User(3, "jane"))
        it.onNext(User(4, "tim"))
        it.onNext(User(5, "ethan"))
        it.onNext(User(6, "mike"))
        it.onComplete()
    }
}

data class User(val id: Long, val name: String)
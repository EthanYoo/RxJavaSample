package com.buzzvil.rxjava.sample.errorhandle;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ErrorTest {
    public static void main(String[] args) {
        Observable.just(1, 3, 5, 7)
                .filter(i -> i > 3)
                .map(i -> i / 0)
                .doOnError(t -> System.out.println("doOnError : " + t.getMessage()))
                .retry(1)
                .onErrorReturn(throwable -> 2)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("ErrorTest.onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("ErrorTest.onNext : " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("ErrorTest.onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("ErrorTest.onComplete");
                    }
                });
    }

}

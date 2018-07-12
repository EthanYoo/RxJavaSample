package com.buzzvil.rxjava.sample;

import io.reactivex.Observable;
import lombok.AllArgsConstructor;
import lombok.Data;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start");

        getUser()
                .filter(user -> user.getAge() > 1)
                .map(user -> user.getAge())
                .subscribe(System.out::println);

        System.out.println("End");
    }

    private static Observable<User> getUser() {
        return Observable.just(
                new User("a", 1),
                new User("b", 2),
                new User("c", 3)
        );
    }

    @Data
    @AllArgsConstructor
    public static class User {
        private String name;
        private int age;
    }
}
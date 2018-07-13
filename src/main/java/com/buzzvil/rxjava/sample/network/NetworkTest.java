package com.buzzvil.rxjava.sample.network;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import java.util.List;

public class NetworkTest {

    public static final String BASE_HOST = "https://jsonplaceholder.typicode.com/";

    public static void main(String[] args) {
        HttpService httpService = HttpService.retrofit.create(HttpService.class);
        Observable<List<Contents>> contentsObservable = httpService.requestPosts();
        contentsObservable
                .flatMapIterable(x -> x)
                .filter(contents -> contents.getId() == 99)
                .map(contents -> "Filtered Target : " + contents.getTitle())
                .observeOn(Schedulers.newThread())
                .subscribe(System.out::println);
    }

    public interface HttpService {
        @GET("posts")
        Observable<List<Contents>> requestPosts();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Contents {
        private int userId;
        private int id;
        private String title;
        private String body;
    }
}

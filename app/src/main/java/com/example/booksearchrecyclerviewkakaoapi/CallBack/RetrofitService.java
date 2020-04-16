package com.example.booksearchrecyclerviewkakaoapi.CallBack;

import com.example.booksearchrecyclerviewkakaoapi.model.Document;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RetrofitService {
    String url = "https://dapi.kakao.com/v3/search/book?target=title";
    @Headers("Authorization : KakaoAK a85301089026f3d76b61ac72f59b1d91")
    @GET("/v3/search/book?target=title")


    Call<List<Document>> getData2(@Query("target") String title);


}

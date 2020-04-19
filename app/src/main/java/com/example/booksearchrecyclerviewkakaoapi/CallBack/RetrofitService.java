package com.example.booksearchrecyclerviewkakaoapi.CallBack;

import com.example.booksearchrecyclerviewkakaoapi.model.Document;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *  KaokaoAPI OookSearch URL = "https://dapi.kakao.com/v3/search/book?target=title"
 */
public interface RetrofitService {
    @GET("/v3/search/book")
    Call<Document> deocument(@Query("target") String keyword);
}

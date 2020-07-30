package com.example.booksearchrecyclerviewkakaoapi.callBack;

import com.example.booksearchrecyclerviewkakaoapi.model.Document;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface KakaoRetrofit {
    String kakaoAK = "a85301089026f3d76b61ac72f59b1d91";

    @GET("v3/search/book?target=title")
    Call<List<Document>>
    getData(@Header("Authorization:KakaoAK") String kakaoAK
            ,@Query("query") String keyword);

    @GET("v3/search/book?target=isbn")
    Call<Document>
    getISBN(@Header("Authorization:KakaoAK") String kakaoAK
            ,@Query("query") String keyword);

//    @GET("/v3/search/book?target=isbn")
//    Call<List<Document>> getData(
//            @Header("Authorization:KaKaoAK a85301089026f3d76b61ac72f59b1d91") String KaKaoAK
//            ,@Query("query")String query);

}

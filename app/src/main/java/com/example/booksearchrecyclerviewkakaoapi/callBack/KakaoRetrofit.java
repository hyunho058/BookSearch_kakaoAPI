package com.example.booksearchrecyclerviewkakaoapi.callBack;

import com.example.booksearchrecyclerviewkakaoapi.model.DocumentList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface KakaoRetrofit {

//    @Headers("Authorization: KakaoAK a85301089026f3d76b61ac72f59b1d91")
//    @GET("v3/search/book?target=title")
//    Call<List<SearchData>> getData(
//            @Header("Authorization") String kakaoAK,
//            @Query("query") String keyword);

    @GET("v3/search/book?target=title")
    Call<DocumentList> getDocument(
            @Header("Authorization") String kakaoAK,
            @Query("query") String keyword);

//    @GET("v3/search/book?target=isbn")
//    Call<Document> getISBN(
//            @Header("Authorization:KakaoAK") String kakaoAK
//            ,@Query("query") String keyword);

//    @GET("/v3/search/book?target=isbn")
//    Call<List<Document>> getData(
//            @Header("Authorization:KaKaoAK a85301089026f3d76b61ac72f59b1d91") String KaKaoAK
//            ,@Query("query")String query);

//    @GET("v3/search/book?target=title")
//    Call<List<Document>> deocument(
//            @Header("Authorization:KakaoAK") String token
//            ,@Query("title") String title);

//    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//    Call<CategoryResult> call = apiInterface.getSearchCategory(getString(R.string.restapi_key),
//    "MT1", x + "", y + "", 1000);


//    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//    Call<CategoryResult> call = apiInterface.getSearchLocation(getString(R.string.restapi_key),
//    charSequence.toString(), 15);

}

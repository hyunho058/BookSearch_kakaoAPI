package com.example.booksearchrecyclerviewkakaoapi.CallBack;

import com.example.booksearchrecyclerviewkakaoapi.model.Document;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 *  KaokaoAPI OookSearch URL = "https://dapi.kakao.com/v3/search/book?target=title"
 */
public interface RetrofitService {
    @GET("/v3/search/book?target=")
    Call<List<Document>> deocument(
            @Header("Authorization") String token,
            @Query("title") String keyword);
}


//    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//    Call<CategoryResult> call = apiInterface.getSearchCategory(getString(R.string.restapi_key),
//    "MT1", x + "", y + "", 1000);


//    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//    Call<CategoryResult> call = apiInterface.getSearchLocation(getString(R.string.restapi_key),
//    charSequence.toString(), 15);

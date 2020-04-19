package com.example.booksearchrecyclerviewkakaoapi.CallBack;

import android.os.AsyncTask;
import android.util.Log;

import com.example.booksearchrecyclerviewkakaoapi.model.Document;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * AsyncTask<시작파라미터,진행상태,서버로받은데이터를 리턴할때사용하는타입>
 */
public class JsonObjectTest extends AsyncTask<String, Void, ArrayList<Document>> {
    String TAG = "JsonObjectTest";
    String keyword = "";
    ArrayList<Document> documentList;
    ArrayList docList;

    public JsonObjectTest(String keyword) {
        this.keyword = keyword;
    }

    /**
     * background 에서 동작한다는메소드
     * ...은 파라미터가 배열처럼 넘어온다는 뜻 한개도될수있고 여러개될수도있음(가변적)
     * @param params
     * @return
     */
    @Override
    protected ArrayList<Document> doInBackground(String... params) {
        Log.v(TAG,"doInBackground()_Start");
        Log.v(TAG,"doInBackground()_keyword"+keyword);
        String url = "https://dapi.kakao.com/v3/search/book?target=title";
        url += "&query=" + keyword;
        try {
            /*** REQUEST */
            //URL 객체 생성
            URL objUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) objUrl.openConnection();
            // 요청방식 설정(API 문서 참조)
            con.setRequestMethod("GET");
            // 인증 설정
            con.setRequestProperty("Authorization", "KakaoAK a85301089026f3d76b61ac72f59b1d91");

            /*** RESPONSE */
            //JSON 형테의 OPEN API 데이터를 bufferedReader 를 이용해 가져온다.
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            bufferedReader.close();

            Log.v(TAG, "DEBUG:stringBuffer==" + stringBuffer);
            String data = stringBuffer.toString();
            JSONObject jsonData = new JSONObject(data);
            JSONArray documents = jsonData.getJSONArray("documents");
            docList = new ArrayList();
            documentList = new ArrayList<Document>();
            for(int i = 0; i < documents.length(); i++) {
                JSONObject document = documents.getJSONObject(i);
                Document doc = new Document(document);
                docList.add(doc);
                documentList.add(doc);
                Log.v(TAG, "DEBUG:document[" + i + "]=" + document);
            }
            Log.v(TAG, "DEBUG:jsonData==" + jsonData);
            Log.v(TAG,"docList==="+documentList);

        } catch (Exception e) {
            Log.v(TAG, "run()_Exception==" + e.toString());
        }


//        Retrofit client = new Retrofit.Builder().baseUrl(
//                "https://dapi.kakao.com").addCallAdapterFactory(GsonConverterFactory.create()).build();

        /**
         *  https://falinrush.tistory.com/5
         *  https://kang6264.tistory.com/entry/Retrofit-%EA%B8%B0%EB%B3%B8-%EA%B8%B0%EB%8A%A5%EC%97%90-%EB%8C%80%ED%95%B4%EC%84%9C-%EC%95%8C%EC%95%84%EB%B3%B4%EC%9E%90%EB%82%A0%EC%94%A8%EB%A5%BC-%EC%A1%B0%ED%9A%8C%ED%95%98%EB%8A%94-RestAPI
         *   https://altongmon.tistory.com/745
         *   https://calyfactory.github.io/view-binding/
         *   https://square.github.io/retrofit/
         */



        return documentList;
    }
}

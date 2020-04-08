package com.example.booksearchrecyclerviewkakaoapi.CallBack;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.example.booksearchrecyclerviewkakaoapi.model.BookVO;
import com.example.booksearchrecyclerviewkakaoapi.model.Document;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class BookSearchTask extends AsyncTask<String, Void, ArrayList<BookVO>> {
    String TAG = "BookSearchTask";
    String keyword = "";
    ArrayList<BookVO> bookList;

    public BookSearchTask(String keyword) {
        this.keyword = keyword;
    }

    @Override
    protected ArrayList<BookVO> doInBackground(String... params) {
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

            ///////////////////////////////////////////////////////////////
            Log.v(TAG, "DEBUG:stringBuffer==" + stringBuffer);
            String data = stringBuffer.toString();
            JSONObject jsonData = new JSONObject(data);
            JSONArray documents = jsonData.getJSONArray("documents");
            ArrayList docList = new ArrayList();
            for(int i = 0; i < documents.length(); i++) {
                JSONObject document = documents.getJSONObject(i);
                Document doc = new Document(document);
                docList.add(doc);
                Log.v(TAG, "DEBUG:document[" + i + "]=" + document);
            }
            Log.v(TAG, "DEBUG:jsonData==" + jsonData);
            ///////////////////////////////////////////////////////////

            //Jackson Library
            ObjectMapper mapper = new ObjectMapper();
            //Json을 읽어서 documents 를 key로 설정하고 최상위 객체인 Object type 으로 책 정보들을 객체화
            Map<String, Object> map = mapper.readValue(
                    stringBuffer.toString(), new TypeReference<Map<String, Object>>(){});
            Object jsonObject = map.get("documents");
            //객체화된 json data를 String 문자열로 변환
            String jsonString = mapper.writeValueAsString(jsonObject);
            Log.v(TAG, "jsonString==" + jsonString);

            bookList = mapper.readValue(jsonString, new TypeReference<ArrayList<BookVO>>() {
            });
            ArrayList<String> resultData = new ArrayList<>();
            BookVO[] jsonBookStringDataList = mapper.readValue(
                    jsonString.toString(), BookVO[].class);

            for (BookVO vo : bookList) {
                resultData.add(vo.getTitle());
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("list", jsonBookStringDataList);
            bundle.putSerializable("bookList", bookList);
            bundle.putSerializable("documentList",docList);

        } catch (Exception e) {
            Log.v(TAG, "run()_Exception==" + e.toString());
        }
        return bookList;
    }

}

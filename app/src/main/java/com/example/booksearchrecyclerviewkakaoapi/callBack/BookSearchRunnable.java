package com.example.booksearchrecyclerviewkakaoapi.callBack;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.example.booksearchrecyclerviewkakaoapi.model.BookVO;
import com.example.booksearchrecyclerviewkakaoapi.model.Document;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONObject;

public class BookSearchRunnable implements Runnable {
    String TAG = "BookSearchRunnable";
    String keyword = "";
    Handler handler;
    Context context;

    public BookSearchRunnable() {
    }

    public BookSearchRunnable(String keyword) {
        this.keyword = keyword;
    }

    public BookSearchRunnable(String keyword, Handler handler) {
        this.keyword = keyword;
        this.handler = handler;
    }

    @Override
    public void run() {
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
                    stringBuffer.toString(), new TypeReference<Map<String, Object>>() {});
            Object jsonObject = map.get("documents");
            //객체화된 json data를 String 문자열로 변환
            String jsonString = mapper.writeValueAsString(jsonObject);
            Log.v(TAG, "jsonString==" + jsonString);

            ArrayList<BookVO> bookList = mapper.readValue(
                    jsonString, new TypeReference<ArrayList<BookVO>>() {});
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
            Message message = new Message();
            message.setData(bundle);
            handler.sendMessage(message);

        } catch (Exception e) {
            Log.v(TAG, "run()_Exception==" + e.toString());
        }
    }
}

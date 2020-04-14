package com.example.booksearchrecyclerviewkakaoapi.model;

import android.content.Context;

import java.util.ArrayList;

public class AdapterVO {
    Context context;
    String bookTitle ="";
    ArrayList<BookVO> bookVO;
    ArrayList<Document> documentList;
    int viewType =0;

    public AdapterVO( String bookTitle, int viewType) {
        this.bookTitle = bookTitle;
        this.viewType = viewType;
    }
    /**
    Constructor 에 변수 데이터를 초기화(재정의) 해서 사용
     */
//    public AdapterVO(Context context, ArrayList<BookVO> bookVO, int viewType) {
//        this.context = context;
//        this.bookVO = bookVO;
//        this.viewType = viewType;
//    }

    /**
    JSONObject 를 이용한 데이터 VO 처리
     */
    public AdapterVO(Context context, ArrayList<Document> documentList, int viewType) {
        this.context = context;
        this.documentList = documentList;
        this.viewType = viewType;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public ArrayList<BookVO> getBookVO() {
        return bookVO;
    }

    public void setBookVO(ArrayList<BookVO> bookVO) {
        this.bookVO = bookVO;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public ArrayList<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(ArrayList<Document> documentList) {
        this.documentList = documentList;
    }
}


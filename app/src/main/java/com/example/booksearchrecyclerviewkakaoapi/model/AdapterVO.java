package com.example.booksearchrecyclerviewkakaoapi.model;

import android.content.Context;

import java.util.ArrayList;

public class AdapterVO {
    Context context;
    String bookTitle ="";
    ArrayList<BookVO> bookVO;
    int viewType =0;

    public AdapterVO( String bookTitle, int viewType) {
        this.bookTitle = bookTitle;
        this.viewType = viewType;
    }

    public AdapterVO(Context context, ArrayList<BookVO> bookVO, int viewType) {
        this.context = context;
        this.bookVO = bookVO;
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
}


package com.example.booksearchrecyclerviewkakaoapi.CallBack;

import android.os.AsyncTask;

public class BookSearchTask extends AsyncTask<Integer, Void, Void> {
    String TAG = "BookSearchTask";
    String keyword = "";

    public BookSearchTask(String keyword) {
        this.keyword = keyword;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        return null;
    }
}

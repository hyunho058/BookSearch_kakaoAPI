package com.example.booksearchrecyclerviewkakaoapi.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DocumentList {
    @SerializedName("documents")
    public ArrayList<Document> documents = new ArrayList<>();
}

package com.example.booksearchrecyclerviewkakaoapi.fragmentView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksearchrecyclerviewkakaoapi.adapter.VerticalAdapter;
import com.example.booksearchrecyclerviewkakaoapi.R;
import com.example.booksearchrecyclerviewkakaoapi.model.AdapterVO;
import com.example.booksearchrecyclerviewkakaoapi.model.Document;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    String TAG="FragmentA";
    View view;
    Context context;
    VerticalAdapter verticalAdapter;
    BookInfoFragment bookInfoFragment;
    ArrayList<Document> documentList;
    ArrayList<AdapterVO> adapterVO;

    public HomeFragment(ArrayList<AdapterVO> adapterVO, BookInfoFragment bookInfoFragment) {
        this.adapterVO = adapterVO;
        this.bookInfoFragment=bookInfoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmen_home,container,false);
        context=container.getContext();

        Log.v(TAG,"bundle=="+(ArrayList<AdapterVO>)getArguments().get("list"));
        documentList=(ArrayList<Document>)getArguments().get("list");

//        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewVertical);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
//                context, LinearLayoutManager.VERTICAL, false);
//        verticalAdapter = new VerticalAdapter(context,adapterVO, bookInfoFragment);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(verticalAdapter);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewVertical);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false);
        verticalAdapter = new VerticalAdapter(context, adapterVO, bookInfoFragment);
        //context, listItems, bookDetailFragment
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(verticalAdapter);

        return  view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v(TAG,"onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG,"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG,"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(TAG,"onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v(TAG,"onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG,"onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG,"onDetach");
    }
}

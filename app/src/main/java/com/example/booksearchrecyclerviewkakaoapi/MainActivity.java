package com.example.booksearchrecyclerviewkakaoapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.booksearchrecyclerviewkakaoapi.Adapter.VerticalAdapter;
import com.example.booksearchrecyclerviewkakaoapi.Adapter.ViewType;
import com.example.booksearchrecyclerviewkakaoapi.CallBack.BookSearchRunnable;
import com.example.booksearchrecyclerviewkakaoapi.FragmentView.BookInfoFragment;
import com.example.booksearchrecyclerviewkakaoapi.FragmentView.SearchFragment;
import com.example.booksearchrecyclerviewkakaoapi.model.AdapterVO;
import com.example.booksearchrecyclerviewkakaoapi.model.BookVO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    Button btnHome;
    Button btnSearch;
    Bundle bundle;
    Handler handler;
    public static FragmentManager fragmentManager;
    public static FragmentTransaction fragmentTransaction;
    SearchFragment searchFragment;
    BookInfoFragment bookInfoFragment;
    VerticalAdapter verticalAdapter;

    ArrayList<AdapterVO> adapterVO = new ArrayList<>();
    ArrayList<BookVO> bookList;

    public static boolean isInfoOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(mClick);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(mClick);

        fragmentManager = getSupportFragmentManager();
        //데이터 생성
        threadData("여행");
        threadData("java");
        threadData("c언어");
        threadData("python");
        threadData("Linux");
        threadData("경제");

        RecyclerView recyclerView = findViewById(R.id.recyclerViewVertical);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        verticalAdapter = new VerticalAdapter(this, adapterVO, bookInfoFragment);
        //context, listItems, bookDetailFragment
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(verticalAdapter);
    }

    View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fragmentTransaction = fragmentManager.beginTransaction();
            bundle = new Bundle();
            switch (v.getId()) {
                case R.id.btnHome:
                    Log.v(TAG, "btnHome_Fragment_ShutDown" + btnHome.getId());
                    if (searchFragment != null) {
                        fragmentTransaction.remove(searchFragment);
                        fragmentTransaction.commit();
                        searchFragment = null;
                    }
                    break;
                case R.id.btnSearch:
                    Log.v(TAG, "btnSearch" + btnSearch.getId());
                    if (searchFragment == null) {
                        searchFragment = new SearchFragment();
                    }
                    fragmentTransaction.replace(R.id.frame, searchFragment).commitAllowingStateLoss();
                    searchFragment.setArguments(bundle);
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (searchFragment != null && fragmentTransaction != null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(searchFragment);
            fragmentTransaction.commit();
            searchFragment = null;
        } else if (isInfoOpen) {
            Toast.makeText(getApplicationContext(), "여기서!", Toast.LENGTH_SHORT).show();
          
            isInfoOpen = false;
        } else {
            super.onBackPressed();
        }
    }

    //BookSearchRunnable 를 호출해 API로 부터 데이터를 받아옴
    public void threadData(final String keyword) {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.v(TAG, "-----------handleMessage() Start---------");
                Bundle bundle = msg.getData();
                bookList = (ArrayList<BookVO>) bundle.getSerializable("bookList");
                Log.v(TAG, "threadData()_bookList============" + bookList);

                initData(keyword);
            }
        };
        Thread thread = new Thread(new BookSearchRunnable(keyword, handler));
        thread.start();
        Log.v(TAG, "-------------Thread Start-------------");
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.v(TAG, "Thread_InterruptedException" + e.toString());
        }
    }

    //adapterVO 에 데이터 저장
    public void initData(String keyword) {
        Log.v(TAG, "-----------initData() Start------------");
        adapterVO.add(new AdapterVO(keyword, ViewType.ItemBookTitle));
        adapterVO.add(new AdapterVO(this, bookList, ViewType.ItemHorizontal));
    }
}

package com.example.booksearchrecyclerviewkakaoapi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.booksearchrecyclerviewkakaoapi.Adapter.VerticalAdapter;
import com.example.booksearchrecyclerviewkakaoapi.Adapter.ViewType;
import com.example.booksearchrecyclerviewkakaoapi.CallBack.BookSearchTask;
import com.example.booksearchrecyclerviewkakaoapi.CallBack.JsonObjectTest;
import com.example.booksearchrecyclerviewkakaoapi.FragmentView.BookInfoFragment;
import com.example.booksearchrecyclerviewkakaoapi.FragmentView.SearchFragment;
import com.example.booksearchrecyclerviewkakaoapi.model.AdapterVO;
import com.example.booksearchrecyclerviewkakaoapi.model.BookVO;
import com.example.booksearchrecyclerviewkakaoapi.model.Document;
import com.google.android.material.tabs.TabLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    Button btnHome;
    Button btnSearch;
    Bundle bundle;
    Handler handler;
    TabLayout tabLayout;
    public static FragmentManager fragmentManager;
    public static FragmentTransaction fragmentTransaction;
    SearchFragment searchFragment;
    BookInfoFragment bookInfoFragment;
    VerticalAdapter verticalAdapter;

    IntentIntegrator intentIntegrator;

    ArrayList<AdapterVO> adapterVO = new ArrayList<>();
    ArrayList<BookVO> bookList;
    ArrayList<Document> documentList;

    ArrayList<Document> documentListR;

    public static boolean isInfoOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        btnHome = findViewById(R.id.btnHome);
//        btnHome.setOnClickListener(mClick);
//        btnSearch = findViewById(R.id.btnSearch);
//        btnSearch.setOnClickListener(mClick);
        tabLayout=(TabLayout) findViewById(R.id.tabLayout);

        //QR code - Zxing Library
        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(false);//바코드 인식시 소리

        fragmentManager = getSupportFragmentManager();
        //  AsyncTask 이용한 데이터 생성
        AsyncTaskData("java");
        AsyncTaskData("c언어");
        AsyncTaskData("python");
        AsyncTaskData("Linux");
        AsyncTaskData("경제");
        AsyncTaskData("여행");

        // Thread 이용한 데이터 생성
//        threadData("여행");
//        threadData("java");
//        threadData("c언어");
//        threadData("python");
//        threadData("Linux");
//        threadData("경제");

        RecyclerView recyclerView = findViewById(R.id.recyclerViewVertical);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);
        verticalAdapter = new VerticalAdapter(this, adapterVO, bookInfoFragment);
        //context, listItems, bookDetailFragment
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(verticalAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Search"));
        tabLayout.addTab(tabLayout.newTab().setText("QR"));
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Tap 이 선택 되었을떄 호출
                Log.v(TAG,"onTabSelected()_getPosition())="+tab.getPosition());
                fragmentTransaction = fragmentManager.beginTransaction();
                bundle = new Bundle();
                switch (tab.getPosition()){
                    case  0:
                        if (searchFragment != null) {
                            fragmentTransaction.remove(searchFragment);
                            fragmentTransaction.commit();
                            searchFragment = null;
                        }
                        break;
                    case 1:
                        if (searchFragment == null) {
                            searchFragment = new SearchFragment();
                        }
                        fragmentTransaction.replace(
                                R.id.frame, searchFragment).commitAllowingStateLoss();
                        searchFragment.setArguments(bundle);
                        break;
                    case 2:
                        new IntentIntegrator(MainActivity.this).initiateScan();
//                        intentIntegrator.initiateScan();
                        break;
                    case 3:
                        startQRCode();
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Tap 이 선택되지 않았을때 호출
                Log.v(TAG,"onTabUnselected()=="+tab.getPosition());
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Tap 이 다시 선택 되었을떄 호출
                Log.v(TAG,"onTabReselected()=="+tab.getPosition());
            }
        });

    }

    /*
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
                    fragmentTransaction.replace(
                            R.id.frame, searchFragment).commitAllowingStateLoss();
                    searchFragment.setArguments(bundle);
                    break;
            }
        }
    };
    */
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

    /**
     * CustomScannerActivity에서 정상적으로 Scan이 완료되면 다시 본 activity로 돌아온다.
     * 이 때, onActivityResult로 결과를 받는다.
     * 출처: https://devvkkid.tistory.com/98 [개발자입니까?]
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode == Activity.RESULT_OK) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            String re = scanResult.getContents();
            Log.v(TAG, "result.getContents() == "+scanResult.getContents());
            Intent intent = new Intent();
            ComponentName cname = new ComponentName("com.example.booksearchrecyclerviewkakaoapi", "\""+re+"\"");
            intent.setComponent(cname);
            startActivity(intent);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * BookSearchRunnable 를 호출해 API로 부터 데이터를 받아옴
     * @param keyword
     */
//    public void threadData(final String keyword) {
//        Log.v(TAG,"threadData_keyword=="+keyword);
//        handler = new Handler() {
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                super.handleMessage(msg);
//                Log.v(TAG, "-----------handleMessage() Start---------");
//                Bundle bundle = msg.getData();
//                bookList = (ArrayList<BookVO>) bundle.getSerializable("bookList");
//                Log.v(TAG, "threadData()_bookList============" + bookList);
//
//                initData(keyword);
//            }
//        };
//        Thread thread = new Thread(new BookSearchRunnable(keyword, handler));
//        thread.start();
//        Log.v(TAG, "-------------Thread Start-------------");
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            Log.v(TAG, "Thread_InterruptedException" + e.toString());
//        }
//    }

    /**
     * AsyncTask 이용한 REST API 호출
     * @param keyword
     */
    public void AsyncTaskData(String keyword){
        Log.v(TAG,"AsyncTaskData()_keyword=="+keyword);
        try {
            bookList = new BookSearchTask(keyword).execute().get();
            documentList = new JsonObjectTest(keyword).execute().get();
        } catch (ExecutionException e) {
            Log.v(TAG,"e.printStackTrace() ="+e.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.v(TAG,"e.printStackTrace() ="+e.toString());
        }
        Log.v(TAG,"documentList=="+documentList);
        initData(keyword);
        Log.v(TAG,"AsyncTask == "+bookList);
    }

    /**
     * adapterVO 에 데이터 저장
     * @param keyword
     */
    public void initData(String keyword) {
        Log.v(TAG, "-----------initData() Start------------");
        adapterVO.add(new AdapterVO(keyword, ViewType.ItemBookTitle));
//        adapterVO.add(new AdapterVO(this, bookList, ViewType.ItemHorizontal));
        adapterVO.add(new AdapterVO(this, documentList, ViewType.ItemHorizontal));
    }
    public void startQRCode() {
        new IntentIntegrator(this).initiateScan();
    }

    public void generateQRCode(String contents){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            Bitmap bitmap = toBitmap(qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 100, 100));
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    public static Bitmap toBitmap(BitMatrix matrix) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }
}
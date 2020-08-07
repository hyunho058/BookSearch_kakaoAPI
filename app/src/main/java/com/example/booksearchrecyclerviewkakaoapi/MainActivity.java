package com.example.booksearchrecyclerviewkakaoapi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booksearchrecyclerviewkakaoapi.adapter.VerticalAdapter;
import com.example.booksearchrecyclerviewkakaoapi.adapter.ViewType;
import com.example.booksearchrecyclerviewkakaoapi.callBack.APIClient;
import com.example.booksearchrecyclerviewkakaoapi.callBack.BookSearchTask;
import com.example.booksearchrecyclerviewkakaoapi.callBack.JsonObjectTest;
import com.example.booksearchrecyclerviewkakaoapi.callBack.KakaoRetrofit;
import com.example.booksearchrecyclerviewkakaoapi.fragmentView.BookInfoFragment;
import com.example.booksearchrecyclerviewkakaoapi.fragmentView.HomeFragment;
import com.example.booksearchrecyclerviewkakaoapi.fragmentView.SearchFragment;
import com.example.booksearchrecyclerviewkakaoapi.model.AdapterVO;
import com.example.booksearchrecyclerviewkakaoapi.model.BookVO;
import com.example.booksearchrecyclerviewkakaoapi.model.Document;
import com.example.booksearchrecyclerviewkakaoapi.model.DocumentList;
import com.google.android.material.tabs.TabLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    Bundle bundle;
    Handler handler;
    TabLayout tabLayout;
    public static FragmentManager fragmentManager;
    public static FragmentTransaction fragmentTransaction;
    SearchFragment searchFragment;
    BookInfoFragment bookInfoFragment;
    HomeFragment homeFragment;
    VerticalAdapter verticalAdapter;
    Context context = this;

    IntentIntegrator intentIntegrator;

    ArrayList<AdapterVO> adapterVO = new ArrayList<>();
    ArrayList<BookVO> bookList;
    ArrayList<Document> documentList;
    ArrayList<Document> documents;
    KakaoRetrofit kakaoRetrofit;
    String kakaoAK = "KakaoAK xxxxxx";

    public static boolean isInfoOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout=(TabLayout) findViewById(R.id.tabLayout);

        //QR code - Zxing Library
        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(false);//바코드 인식시 소리

        //  AsyncTask 이용한  REST API 데이터 생성
        AsyncTaskData("java");
        AsyncTaskData("c언어");
        AsyncTaskData("python");
        AsyncTaskData("Linux");
        AsyncTaskData("경제");
        AsyncTaskData("여행");

        // RETROFIT2 이요한  REST API
        callRetrofit("JAVA");

        fragmentManager = getSupportFragmentManager();
        if (homeFragment == null) {
            fragmentTransaction=fragmentManager.beginTransaction();
            bundle = new Bundle();
            homeFragment = new HomeFragment(adapterVO, bookInfoFragment);
            bundle.putSerializable("list", documentList);
            homeFragment.setArguments(bundle);
            fragmentTransaction.replace(
                    R.id.frame, homeFragment).commitAllowingStateLoss();
        }

        // tabLayout 생성
        tabLayout.addTab(tabLayout.newTab()
                .setCustomView(createTabView(
                        "HOME",R.drawable.house_black_18dp)));
        tabLayout.addTab(tabLayout.newTab()
                .setCustomView(createTabView(
                        "Search",R.drawable.baseline_search_black_18dp)));
        tabLayout.addTab(tabLayout.newTab()
                .setCustomView(createTabView(
                        "QR",R.drawable.baseline_camera_alt_black_18dp)));
        tabLayout.addOnTabSelectedListener(tabSelect);

    }

    TabLayout.OnTabSelectedListener tabSelect = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            //Tap 이 선택 되었을떄 호출
            Log.v(TAG,"onTabSelected()_getPosition())="+tab.getPosition());
            fragmentTransaction = fragmentManager.beginTransaction();
            bundle = new Bundle();
            switch (tab.getPosition()){
                case 0:
                    if (homeFragment == null) {
                        homeFragment = new HomeFragment(adapterVO, bookInfoFragment);
                        Log.v(TAG,"fragmentHome==");
                    }
                    fragmentTransaction.replace(
                            R.id.frame, homeFragment).commitAllowingStateLoss();
                    bundle.putSerializable("list", documentList);
                    homeFragment.setArguments(bundle);
                    tab.getIcon().setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                    break;
                case 1:
                    if (searchFragment == null) {
                        searchFragment = new SearchFragment();
                    }
                    fragmentTransaction.replace(
                            R.id.frame, searchFragment).commitAllowingStateLoss();
                    searchFragment.setArguments(bundle);
                    tab.getIcon().setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                    break;
                case 2:
                    new IntentIntegrator(MainActivity.this).initiateScan();
                    intentIntegrator.initiateScan();
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
    };

    @Override
    public void onBackPressed() {
        for (Fragment currentFragment : getSupportFragmentManager().getFragments()) {
            if (currentFragment.isVisible()) {
                Log.v(TAG,"onBackPressed__currentFragment=="+currentFragment.toString());
                if (currentFragment instanceof HomeFragment) {
                    Log.v(TAG, "onBackPressed__HomeFragment");
                    super.onBackPressed();
                }else{
                    // 가장 최상위 View 가  HomeFragment 가 아닐시 0번 index tab을 호출한다
                    TabLayout.Tab tabSelect = tabLayout.getTabAt(0);
                    tabSelect.select();
                }
            }
        }
    }

    /**
     * CustomScannerActivity에서 정상적으로 Scan이 완료되면 다시 본 activity로 돌아온다.
     * 이 때, onActivityResult로 결과를 받는다.
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
     * RETROFIT2 이요한 REST API Response and Request
     */
    public void callRetrofit(String keyword){
        kakaoRetrofit = APIClient.getClient().create(KakaoRetrofit.class);
        Call<DocumentList> callDocumentList = kakaoRetrofit.getDocument(kakaoAK, keyword);
        callDocumentList.enqueue(new Callback<DocumentList>() {
            @Override
            public void onResponse(Call<DocumentList> call, Response<DocumentList> response) {
                Log.v(TAG,"retrofit_onResponse=="+response.code());
                Log.v(TAG,"retrofit_onResponse=="+call.request().toString());
                Log.v(TAG,"retrofit_response.body().size()=="+response.body().documents.size());
                Log.v(TAG,"retrofit_response.body()_documents.get(0).getAuthors()=="+response.body().documents.get(0).getAuthors());
                documents = response.body().documents;
                Log.v(TAG,"retrofit_documents=="+documents.get(0).getAuthors());
//                initData(keyword);
//                documentListR = (List<SearchData>) response.body();
//                Log.v(TAG,"retrofit_response=="+documentListR.get(0).toString());
            }
            @Override
            public void onFailure(Call<DocumentList> call, Throwable t) {
                Log.v(TAG,"retrofit_onFailure=="+t.toString());
                Log.v(TAG,"retrofit_onFailure=="+call.request().toString());
            }
        });
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
     * 한번 호출 할때마다 Document List 객체를 저장한다
     * @param keyword
     */
    public void initData(String keyword) {
        Log.v(TAG, "-----------initData() Start------------");
        adapterVO.add(new AdapterVO(keyword, ViewType.ItemBookTitle));
//        adapterVO.add(new AdapterVO(this, bookList, ViewType.ItemHorizontal));
        adapterVO.add(new AdapterVO(this, documentList, ViewType.ItemHorizontal));
    }

    /**
     * QR Code - Zxing Library
     */
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

    /**
     * Custom TabLayout
     */
    private View createTabView(String tabName, int iconImage){
        View tabView = getLayoutInflater().inflate(R.layout.custom_tab, null);
        TextView tvTab = (TextView) tabView.findViewById(R.id.tvTab);
        tvTab.setText(tabName);
        ImageView ivTab = (ImageView) tabView.findViewById(R.id.ivTab);
        ivTab.setImageResource(iconImage);
        return tabView;
    }
}
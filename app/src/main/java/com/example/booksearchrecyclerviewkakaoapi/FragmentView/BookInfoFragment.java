package com.example.booksearchrecyclerviewkakaoapi.FragmentView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.booksearchrecyclerviewkakaoapi.R;

import java.io.InputStream;
import java.net.URL;

public class BookInfoFragment extends Fragment {
    String TAG = "BookInfoFragment";
    View view;
    Context context;
    Bundle bundle;
    Bitmap bitmap;
    Handler handler = new Handler();

    TextView tvTitle;
    TextView tvAuthor;
    TextView tvPrice;
    TextView tvISBN;
    ImageView ivBookImage;

    String getUrl = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bookinfo_fragment, container, false);
        context=container.getContext();
        Log.v(TAG, "BookInfoFragment Start");
        bundle = getArguments();
        tvTitle = view.findViewById(R.id.tvTitle);
        tvAuthor = view.findViewById(R.id.tvAuthor);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvISBN = view.findViewById(R.id.tvISBN);
        ivBookImage = view.findViewById(R.id.ivBookImage);


        Log.v(TAG, "Title = " + bundle.getString("Title"));
        getUrl = bundle.getString("Url");
        Log.v(TAG,"Image_URL=="+getUrl);
        //Glide Library 이용한 url imageView set??
        Glide.with(context).load(getUrl).into(ivBookImage);
//        thread.start();

        tvTitle.setText(bundle.getString("Title"));
        tvAuthor.setText(bundle.getSerializable("Authors").toString().replaceAll("]", "").replaceAll("\\[", ""));
        tvPrice.setText(bundle.getSerializable("Price").toString());
        tvISBN.setText(bundle.getString("ISBN"));
        return view;
    }

//    Thread thread = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            try{
//                URL url = new URL(getUrl);
//                Log.v(TAG,"url==="+url);
//                InputStream inputStream = url.openStream();
//                Log.v(TAG,"InputStream=========="+inputStream);
//                bitmap = BitmapFactory.decodeStream(inputStream); //InputStream 으로부터 Bitmap를 만든다
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {  // 화면에 그려줄 작업
//                        Log.v(TAG,"handler.post_run()----------------"+bitmap);
//                        ivBookImage.setImageBitmap(bitmap);
//                    }
//                });
//            } catch(Exception e){
//                Log.v(TAG,"thread_run()_Exception ="+e.toString());
//            }
//        }
//    });
}

package com.example.booksearchrecyclerviewkakaoapi.Adapter;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.booksearchrecyclerviewkakaoapi.FragmentView.BookInfoFragment;
import com.example.booksearchrecyclerviewkakaoapi.model.BookVO;
import com.example.booksearchrecyclerviewkakaoapi.MainActivity;
import com.example.booksearchrecyclerviewkakaoapi.R;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.CardItem> {
    String TAG = "HorizontalAdapter";
    ArrayList<BookVO> bookVOSList = new ArrayList<>();
    Context context;
    Handler handler = new Handler();
    Bitmap bitmap;
    String getImageUrl = "";

    Fragment bookInfoFragment;

    public HorizontalAdapter(Context context, ArrayList<BookVO> bookVOSList, Fragment bookInfoFragment) {
        this.bookVOSList = bookVOSList;
        this.context = context;
        this.bookInfoFragment = bookInfoFragment;
    }

    @NonNull
    @Override
    public CardItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.v(TAG, "onCreateViewHolder==" + viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, null);
        return new CardItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardItem holder, final int position) {
        holder.tv_title.setText(bookVOSList.get(position).getTitle());
        holder.tv_title.setSelected(true);

        getImageUrl = bookVOSList.get(position).getThumbnail();
        Log.v(TAG, "onBindViewHolder==" + bookVOSList.get(position).getTitle());

        //https://jizard.tistory.com/179   => Glide
        Glide.with(context).load(getImageUrl).into(holder.iv_poster);
        //Thread 를 이용한 URL Image 호출
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    URL url = new URL(getImageUrl);
//                    InputStream inputStream = url.openStream();
//                    bitmap = BitmapFactory.decodeStream(inputStream); //InputStream 으로부터 Bitmap를 만든다
//                    handler.post(new Runnable() { //Runnable 객체 전달(핸들러에 연결된 메시지큐에 추가)
//                        @Override
//                        public void run() {  // 화면에 그려줄 작업
//                            holder.iv_poster.setImageBitmap(bitmap);
//                        }
//                    });
//                } catch (Exception e) {
//                    Log.v(TAG, "thread_run()_Exception =" + e.toString());
//                }
//            }
//        });
//        thread.start();

        //card_item XML 의 ImageVIew의 외각을 라운드
        GradientDrawable drawable = (GradientDrawable) context.getDrawable(R.drawable.frame);
        holder.iv_poster.setBackground(drawable);
        holder.iv_poster.setClipToOutline(true);

        MainActivity.fragmentManager = ((MainActivity) context).getSupportFragmentManager();
//        bookInfoFragment = new BookInfoFragment();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentTransaction = MainActivity.fragmentManager.beginTransaction();
                bookInfoFragment = new BookInfoFragment();
                MainActivity.fragmentTransaction.replace(R.id.frame, bookInfoFragment).commitAllowingStateLoss();

                MainActivity.isInfoOpen = true;

                Log.v(TAG, "onBindViewHolder_onClick()_position==" + position);
                Toast.makeText(context, "Thitle : " + bookVOSList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Log.v(TAG, "getTitle==" + bookVOSList.get(position).getTitle());
                Log.v(TAG, "getAuthors==" + bookVOSList.get(position).getAuthors());
                Log.v(TAG, "getPrice==" + bookVOSList.get(position).getPrice());
                Log.v(TAG, "getIsbn==" + bookVOSList.get(position).getIsbn());
                Log.v(TAG, "getThumbnail==" + bookVOSList.get(position).getThumbnail());
                Bundle bundle = new Bundle();
                bundle.putSerializable("Title", bookVOSList.get(position).getTitle());
                bundle.putSerializable("ISBN", bookVOSList.get(position).getIsbn());
                bundle.putSerializable("Price", bookVOSList.get(position).getPrice());
                bundle.putSerializable("Url", bookVOSList.get(position).getThumbnail());
                bundle.putSerializable("Authors", bookVOSList.get(position).getAuthors());
                bookInfoFragment.setArguments(bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.v(TAG, "getItemCount()__getItemCount()==" + bookVOSList.size());
        return bookVOSList.size();
    }

    public class CardItem extends RecyclerView.ViewHolder {
        public ImageView iv_poster;
        public TextView tv_title;

        public CardItem(@NonNull View itemView) {
            super(itemView);
            iv_poster = itemView.findViewById(R.id.iv_poster);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }
}

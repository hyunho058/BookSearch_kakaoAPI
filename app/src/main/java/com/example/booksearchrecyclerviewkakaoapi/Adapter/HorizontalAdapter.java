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
import com.example.booksearchrecyclerviewkakaoapi.model.Document;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.CardItem> {
    String TAG = "HorizontalAdapter";
    ArrayList<BookVO> bookVOSList = new ArrayList<>();
    ArrayList<Document> documentList= new ArrayList<>();
    Context context;
    Handler handler = new Handler();
    Bitmap bitmap;
    String getImageUrl = "";

    Fragment bookInfoFragment;

    /**
     * BookVO  데이터 객체를 이용
     * @param context
     * @param documentList
     * @param bookInfoFragment
     */
//    public HorizontalAdapter(Context context, ArrayList<BookVO> bookVOSList, Fragment bookInfoFragment) {
//        this.bookVOSList = bookVOSList;
//        this.context = context;
//        this.bookInfoFragment = bookInfoFragment;
//    }

    /**
     *  Constructor
     * JSONObject 를 받는 데이터 객체(Document)를 이용
     * @param context
     * @param documentList
     * @param bookInfoFragment
     */
    public HorizontalAdapter(Context context, ArrayList<Document> documentList, Fragment bookInfoFragment) {
        this.documentList = documentList;
        this.context = context;
        this.bookInfoFragment = bookInfoFragment;
    }
    /**
     * Create new views (invoked by the layout manager)
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public CardItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.v(TAG, "onCreateViewHolder==" + viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, null);
        return new CardItem(view);
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final CardItem holder, final int position) {
        holder.tv_title.setText(documentList.get(position).getTitle());
        holder.tv_title.setSelected(true);

        getImageUrl = documentList.get(position).getThumbnail();
        Log.v(TAG, "onBindViewHolder==" + documentList.get(position).getTitle());

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

        //card_item XML 의 ImageVIew 의 외각을 라운드
        GradientDrawable drawable = (GradientDrawable) context.getDrawable(R.drawable.frame);
        holder.iv_poster.setBackground(drawable);
        holder.iv_poster.setClipToOutline(true);

        MainActivity.fragmentManager = ((MainActivity) context).getSupportFragmentManager();
        bookInfoFragment = new BookInfoFragment();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentTransaction = MainActivity.fragmentManager.beginTransaction();
                bookInfoFragment = new BookInfoFragment();
                MainActivity.fragmentTransaction.replace(R.id.frame, bookInfoFragment).commitAllowingStateLoss();

                MainActivity.isInfoOpen = true;

                Log.v(TAG, "onBindViewHolder_onClick()_position==" + position);
                Toast.makeText(context, "Thitle : " + documentList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Log.v(TAG, "getTitle==" + documentList.get(position).getTitle());
                Log.v(TAG, "getAuthors==" + documentList.get(position).getAuthors());
                Log.v(TAG, "getPrice==" + documentList.get(position).getPrice());
                Log.v(TAG, "getIsbn==" + documentList.get(position).getIsbn());
                Log.v(TAG, "getThumbnail==" + documentList.get(position).getThumbnail());
                Bundle bundle = new Bundle();
                bundle.putSerializable("Title", documentList.get(position).getTitle());
                bundle.putSerializable("ISBN", documentList.get(position).getIsbn());
                bundle.putSerializable("Price", documentList.get(position).getPrice());
                bundle.putSerializable("Url", documentList.get(position).getThumbnail());
                bundle.putSerializable("Authors", documentList.get(position).getAuthors());
                bookInfoFragment.setArguments(bundle);
            }
        });
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     * @return
     */
    @Override
    public int getItemCount() {
        Log.v(TAG, "getItemCount()__getItemCount()==" + documentList.size());
        return documentList.size();
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

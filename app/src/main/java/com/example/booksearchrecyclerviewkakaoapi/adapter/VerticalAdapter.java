package com.example.booksearchrecyclerviewkakaoapi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksearchrecyclerviewkakaoapi.fragmentView.BookInfoFragment;
import com.example.booksearchrecyclerviewkakaoapi.model.AdapterVO;
import com.example.booksearchrecyclerviewkakaoapi.R;

import java.util.ArrayList;

public class VerticalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    String TAG = "VerticalAdapter";
    View view;
    ArrayList<AdapterVO> adapterList = new ArrayList<AdapterVO>();
    Context context;
    HorizontalAdapter horizontalAdapter;
    BookInfoFragment bookInfoFragment;

    public VerticalAdapter(Context context,ArrayList<AdapterVO> adapterList, BookInfoFragment bookInfoFragment) {
        this.context = context;
        this.adapterList = adapterList;
        this.bookInfoFragment = bookInfoFragment;
    }
    /**
     * Layout을 만들어서 Holde r에 저장 (View Holder 객체 를 생성하고 View 를 붙여줌
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater
                =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.v(TAG,"onCreateViewHolder__viewType=="+viewType);
        if(viewType == ViewType.ItemBookTitle){
            view=inflater.inflate(R.layout.title_item,parent,false);
            return new BookTitleItem(view);
        }else {
            view=inflater.inflate(R.layout.recycler_horizontal,parent,false);
            return new HorizontalItem(view);
        }
    }
    /**
     * 넘겨받은 데이터를 화면에 출력하는 역할
     * 제활용되는 View가 호출하여 실행되는 Method
     * View Holder 를 전달하고 Adapter는 position 의 데이터를 결합
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Log.v(TAG,"onBindViewHolder=="+holder+"/position=="+position);
        if (holder instanceof BookTitleItem){
            ((BookTitleItem)holder).tvBookTitle.setText(adapterList.get(position).getBookTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"Title="+adapterList.get(position).getBookTitle(),Toast.LENGTH_LONG).show();
                }
            });
        }else if(holder instanceof HorizontalItem){
            ((HorizontalItem)holder).recycler_horizontal.setLayoutManager
                    (new LinearLayoutManager
                            (context,LinearLayoutManager.HORIZONTAL,false));
            horizontalAdapter = new HorizontalAdapter
                    (context, adapterList.get(position).getDocumentList(), bookInfoFragment);
            ((HorizontalItem)holder).recycler_horizontal.setAdapter(horizontalAdapter);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Log.v(TAG,"getItemViewType_position"+position);
        return adapterList.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        Log.v(TAG,"getItemCount_ddapterList.size()"+adapterList.size());
        return adapterList.size();
    }

    public class BookTitleItem extends RecyclerView.ViewHolder{
        TextView tvBookTitle;

        public BookTitleItem(View itemView) {
            super(itemView);
            tvBookTitle=itemView.findViewById(R.id.tvBookTitle);
        }
    }

    public class HorizontalItem extends RecyclerView.ViewHolder{
        RecyclerView recycler_horizontal;

        public HorizontalItem(View itemView) {
            super(itemView);
            this.recycler_horizontal=(RecyclerView)itemView.findViewById(R.id.recyclerViewHorizontal);
        }
    }

}

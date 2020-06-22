package com.example.booksearchrecyclerviewkakaoapi.fragmentView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.booksearchrecyclerviewkakaoapi.callBack.JsonObjectTest;
import com.example.booksearchrecyclerviewkakaoapi.model.BookVO;
import com.example.booksearchrecyclerviewkakaoapi.R;
import com.example.booksearchrecyclerviewkakaoapi.model.Document;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SearchFragment extends Fragment {
    String TAG = "SearchFragment";
    View view;
    Context context;

    EditText etSearch;
    Button btnSearch;
    ListView lvBookList;

    Handler handler;
    String[] titleList;
    BookVO[] bookVOList;
    ArrayList<Document> documentList;
    ArrayList<String> titleData;

    ArrayAdapter adapter;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    BookInfoFragment bookInfoFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment, container, false);
        context = container.getContext();
        lvBookList = view.findViewById(R.id.lvBookList);
        etSearch = view.findViewById(R.id.etSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        fragmentManager = getFragmentManager();

//        handler = new Handler() {
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                super.handleMessage(msg);
//                Bundle bundle = msg.getData();
////                Bundle bundle1 = getArguments();
//                bookVOList = (BookVO[]) bundle.getSerializable("list");
//                titleList = new String[bookVOList.length];
//                int i = 0;
//                for (BookVO vo : bookVOList) {
//                    titleList[i++] = vo.getTitle();
//                }
//                adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, titleList);
//                lvBookList.setAdapter(adapter);
//            }
//        };

        //EditText 에 검색어를 입력하고 버튼을 클릭하면 BookSearchRunnable에 keyword값이 전다되어 api 호출
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "SearchFragment_btnSearch==" + btnSearch.getId());
//                Thread thread = new Thread(new BookSearchRunnable(etSearch.getText().toString(), handler));
//                Log.v(TAG, "SearchFragment_btnSearch==" + etSearch.getText().toString());
//                thread.start();
                AsyncTaskData(etSearch.getText().toString());
            }
        });
        /**
         * ListVIew Item Click Event
         */
        lvBookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v(TAG, "onItemClick_position==" + position);
                bookInfoFragment = new BookInfoFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, bookInfoFragment).commitAllowingStateLoss();
                Bundle bundle = new Bundle();
                bundle.putSerializable("ISBN", documentList.get(position).getIsbn());
                bundle.putSerializable("Price", documentList.get(position).getPrice());
                bundle.putSerializable("Url", documentList.get(position).getThumbnail());
                bundle.putSerializable("Authors", documentList.get(position).getAuthors());
                bundle.putString("Title", documentList.get(position).getTitle());
                bookInfoFragment.setArguments(bundle);
                Log.v(TAG, "getIsbn==" +documentList.get(position).getIsbn());
            }
        });
        return view;
    }

    /**
     * AsyncTask 이용한 REST API 호출
     * Book Data 가져와 title 정보를 ListView 에 출력
     * @param keyword
     */
    public void  AsyncTaskData(String keyword){
        Log.v(TAG,"AsyncTaskData()_keyword"+keyword);
        try {
            documentList = new JsonObjectTest(keyword).execute().get();
        }catch (ExecutionException e) {
            Log.v(TAG,"e.printStackTrace() ="+e.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.v(TAG,"e.printStackTrace() ="+e.toString());
        }
        titleData=new ArrayList<>();
        for(Document vo : documentList){
            titleData.add(vo.getTitle());
        }
        Log.v(TAG,"titleData==="+titleData);
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,titleData);
        lvBookList.setAdapter(adapter);
    }
}

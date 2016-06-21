package com.example.dictionary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.dictionary.Adapter.FileType_Adapter;
import com.example.dictionary.R;
import com.example.dictionary.model.DictionaryDB;
import com.example.dictionary.model.Word_file;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doudou on 2016/6/20.
 */
public class WordBook_Fragment extends Fragment implements AdapterView.OnItemClickListener{
    private TextView tv_wordsum;
    private ListView lv_filetype;
    private FileType_Adapter adapter;
    private List<Word_file> list;
    private int history_Word;
    private int  unfamiliar_word;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.wordbook_main,container,false);
        tv_wordsum= (TextView) view.findViewById(R.id.tv_wordsum);
        lv_filetype= (ListView) view.findViewById(R.id.lv_filetype);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageButton imageButton_search = (ImageButton) getActivity().findViewById(R.id.button_search);
        EditText editText_search = (EditText) getActivity().findViewById(R.id.edit_search);
        lv_filetype.setOnItemClickListener(this);
        imageButton_search.setVisibility(View.GONE);
        editText_search.setVisibility(View.GONE);
        toolbar.setTitle("生词本");
        //初始化2个item（历史记录与生词表）文件夹
        InitData();
        adapter=new FileType_Adapter(getActivity(),list);
        lv_filetype.setAdapter(adapter);



        return view;
    }

    private void InitData() {
        unfamiliar_word=DictionaryDB.getSum();

        list=new ArrayList<Word_file>();
        Word_file word_file1=new Word_file();
        word_file1.setFiletype("历史记录");
        word_file1.setFile_word_sum("");

        Word_file word_file2=new Word_file();
        word_file2.setFiletype("生词本");
        word_file2.setFile_word_sum(""+unfamiliar_word);

        list.add(word_file1);
        list.add(word_file2);

        tv_wordsum.setText(unfamiliar_word+"");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position){
           case 1:
                Unfamiliar_Word_Detail_Fragment unfamiliar_word_detail_fragment=new Unfamiliar_Word_Detail_Fragment();
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction init_transaction=fragmentManager.beginTransaction();
                init_transaction.replace(R.id.framement_alternative,unfamiliar_word_detail_fragment);
                init_transaction.commit();
                break;
        }


    }
}

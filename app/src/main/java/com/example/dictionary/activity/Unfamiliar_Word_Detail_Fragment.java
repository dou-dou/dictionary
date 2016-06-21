package com.example.dictionary.activity;

import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


import com.example.dictionary.Adapter.Word_Detail_Adapter;
import com.example.dictionary.R;
import com.example.dictionary.model.DictionaryDB;
import com.example.dictionary.model.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doudou on 2016/6/21.
 */
public class Unfamiliar_Word_Detail_Fragment extends Fragment{

    private ListView lv_unfamiliar_word;
    private List<Word> list;
    private Word_Detail_Adapter word_detail_adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.unfamiliar_word_main,container,false);
        lv_unfamiliar_word= (ListView) view.findViewById(R.id.lv_unfamiliar_word);
        list=new ArrayList<>();
        list= DictionaryDB.load_unfamiliar_word();
        word_detail_adapter=new Word_Detail_Adapter(getActivity(),list);
        lv_unfamiliar_word.setAdapter(word_detail_adapter);

        return view;
    }
}

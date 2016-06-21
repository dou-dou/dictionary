package com.example.dictionary.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dictionary.R;
import com.example.dictionary.activity.MainActivity;
import com.example.dictionary.model.Word;

import java.io.IOException;
import java.util.List;

/**
 * Created by doudou on 2016/6/21.
 */
public class Word_Detail_Adapter extends BaseAdapter{

    List<Word> list;
    Context context;

    public Word_Detail_Adapter(Context context, List<Word> list) {
        super();
        this.list=list;
        this.context=context;
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    viewHolder viewHolder;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            viewHolder=new viewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.word_detail_item,null);
            viewHolder.tv_word= (TextView) convertView.findViewById(R.id.tv_word);
            viewHolder.tv_meaning= (TextView) convertView.findViewById(R.id.tv_wordmean);
            viewHolder.ib_voice= (ImageButton) convertView.findViewById(R.id.ib_voice);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (viewHolder) convertView.getTag();
        }
        final Word word=list.get(position);
        viewHolder.tv_word.setText(word.getWord());
        viewHolder.tv_meaning.setText(word.getParts());
        viewHolder.ib_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(word.getPh_en_map3_src());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    Log.v("src", e.getMessage());
                }
            }
        });
        return convertView;

    }

    class viewHolder{
        private TextView tv_word;
        private TextView tv_meaning;
        private ImageButton ib_voice;
    }
}

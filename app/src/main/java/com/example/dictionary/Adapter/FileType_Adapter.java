package com.example.dictionary.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.dictionary.R;
import com.example.dictionary.activity.MainActivity;
import com.example.dictionary.activity.WordBook_Fragment;
import com.example.dictionary.model.Word_file;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by doudou on 2016/6/21.
 */
public class FileType_Adapter extends BaseAdapter{
    List<Word_file> list;
    Context context;

    public FileType_Adapter(Context context, List<Word_file> list) {
        super();
        this.context=context;
        this.list=list;
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
             convertView= LayoutInflater.from(context).inflate(R.layout.wordbook_item,null);
            viewHolder.fileWordSum= (TextView) convertView.findViewById(R.id.tv_fileWordSum);
            viewHolder.fileType= (TextView) convertView.findViewById(R.id.tv_fileType);
            convertView.setTag(viewHolder);
            }else {
            viewHolder= (viewHolder) convertView.getTag();
        }
        Word_file word_file=list.get(position);
        viewHolder.fileType.setText(word_file.getFiletype());
        viewHolder.fileWordSum.setText(word_file.getFile_word_sum());
        return convertView;
    }
    class viewHolder{
        public TextView fileType;
        public TextView fileWordSum;

    }
}

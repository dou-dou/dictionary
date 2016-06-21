package com.example.dictionary.activity;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dictionary.R;
import com.example.dictionary.db.DBOpenHelper;
import com.example.dictionary.model.DictionaryDB;
import com.example.dictionary.model.Word;
import com.example.dictionary.util.HttpCallbackListener;
import com.example.dictionary.util.HttpUtil;
import com.example.dictionary.util.JsonUtility;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by doudou on 2016/5/15.
 */
public class Search_show_Fragment extends Fragment implements View.OnClickListener{

    private TextView textView_ph_en;
    private TextView textView_ph_am;
    private TextView textView_parts;
    private TextView textView_exchange;
    private ImageButton bt_ph_en_map3;
    private ImageButton bt_ph_am_map3;
    private Button bt_addWordBook;
    private String ph_en_map3_src;
    private String ph_am_map3_src;
    private String exchange_string="";
    private String search_word;
    private String parts_string="";
    private static  final String  parts_type[]={"n.","adj.","adv.","vi.","vt.","v.","abbr.","prep.","conj.","vt.&vi.","int."};
    public static Search_show_Fragment newInstance(String search_word) {
                Search_show_Fragment f = new Search_show_Fragment();
                Bundle bundle=new Bundle();
                bundle.putString("search_word", search_word);
                f.setArguments(bundle);
                return f;
             }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.search_show_main,container,false);
        textView_ph_en= (TextView) view.findViewById(R.id.ph_en_text);
        textView_ph_am= (TextView) view.findViewById(R.id.ph_am_text);
        textView_parts= (TextView) view.findViewById(R.id.parts_text);
        textView_exchange= (TextView) view.findViewById(R.id.exchange_text);
        bt_ph_en_map3= (ImageButton) view.findViewById(R.id.ph_en_mp3_button);
        bt_ph_am_map3= (ImageButton) view.findViewById(R.id.ph_am_mp3_button);
        bt_addWordBook= (Button) view.findViewById(R.id.search_addbook);
        bt_ph_en_map3.setOnClickListener(this);
        bt_ph_am_map3.setOnClickListener(this);
       // Typeface mFace = Typeface.createFromAsset(getActivity().getAssets(), "font/Kingsoft_Phonetic_Plain.ttf");
       // textView_ph_en.setTypeface(mFace);
       // textView_ph_am.setTypeface(mFace);
          Bundle bundle= getArguments();
        bt_addWordBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DictionaryDB.querySameWord(search_word)){
                    Toast.makeText(getActivity(),"生词表中已经有这个单词了！",Toast.LENGTH_LONG).show();
                }else{
                    Word word=new Word();
                    word.setWord(search_word);
                    word.setParts(parts_string);
                    word.setPh_en_map3_src(ph_en_map3_src);
                    DictionaryDB.saveWord(word);
                    Toast.makeText(getActivity(),"已经存入生词表",Toast.LENGTH_LONG).show();
                }

            }
        });
        search_word=bundle.get("search_word").toString();
        queryFromServer(search_word);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ph_en_mp3_button:

                load_mp3(ph_en_map3_src);
                break;
            case R.id.ph_am_mp3_button:

                load_mp3(ph_am_map3_src);

                break;
        }
    }

    private void load_mp3(String src) {

                try {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(src);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    Log.v("src", e.getMessage());
                }



    }
    private void Gain_show_data(Map<String,Map> map_show)
    {

        Map<String,Object> map_part1=map_show.get("Map_part1");
        ph_en_map3_src=map_part1.get("ph_en_mp3").toString();
        ph_am_map3_src=map_part1.get("ph_am_mp3").toString();
        if(ph_en_map3_src.isEmpty()&&ph_am_map3_src.isEmpty()){
            ph_en_map3_src=ph_am_map3_src=map_part1.get("ph_tts_mp3").toString();
        }
        textView_ph_en.setText("英["+map_part1.get("ph_en").toString()+"]");
        textView_ph_am.setText("美["+map_part1.get("ph_am").toString()+"]");
        Log.i("json",map_part1.get("ph_am").toString());

        Map<String,List> map_part2=map_show.get("Map_part2");


        for(int k=0;k<parts_type.length;k++)
        {
            String type=parts_type[k];
            if(map_part2.containsKey(type))
            {
                parts_string=parts_string+type+"  ";
                List<String> list_parts=map_part2.get(type);
                for(int j=0;j<list_parts.size();j++)
                {
                    parts_string=parts_string+list_parts.get(j)+";";
                }
                parts_string=parts_string+"\n";
            }

        }
        textView_parts.setText(parts_string);


        if(map_part1.containsKey("word_pl")){
            exchange_string=exchange_string+"复数： "+map_part1.get("word_pl")+"    ";

        }
        if(map_part1.containsKey("word_third")){
            exchange_string=exchange_string+"第三人称单数： "+map_part1.get("word_third")+"    ";
        }
        if(map_part1.containsKey("word_past")){
            exchange_string=exchange_string+"过去式： "+map_part1.get("word_past")+"    ";
        }
        if(map_part1.containsKey("word_done")){
            exchange_string=exchange_string+"过去分词： "+map_part1.get("word_done")+"    ";
        }
        if(map_part1.containsKey("word_ing")){
            exchange_string=exchange_string+"现在分词： "+map_part1.get("word_ing")+"    ";
        }
        if(map_part1.containsKey("word_er")){
            exchange_string=exchange_string+"比较级： "+map_part1.get("word_er")+"    ";
        }
        if(map_part1.containsKey("word_est")){
            exchange_string=exchange_string+"最高级： "+map_part1.get("word_est")+"    ";
        }
        if(exchange_string.isEmpty()){
            textView_exchange.setVisibility(View.GONE);
        }
        textView_exchange.setText(exchange_string);

    }
    private void queryFromServer(String content)
    {
        if(!content.trim().isEmpty())
        {
            String address="http://dict-co.iciba.com/api/dictionary.php?w="+content.trim()+"&type=json&key=38EA03739FF724728F9217DA32F0DE8B";
            HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                @Override
                public void onFinish(final String response) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Log.i("json",response);
                                Map<String,Map> map_parse_text;
                                map_parse_text= JsonUtility.parseJSONWithJSONObject_translate(response);
                                Gain_show_data(map_parse_text);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                @Override
                public void onError(Exception e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"加载失败",Toast.LENGTH_LONG).show();
                            Log.i("tag","失败");
                        }
                    });
                }
            });
        }
    }

}

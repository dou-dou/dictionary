package com.example.dictionary.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dictionary.R;
import com.example.dictionary.util.HttpCallbackListener;
import com.example.dictionary.util.HttpUtil;
import com.example.dictionary.util.ImageUtility;
import com.example.dictionary.util.JsonUtility;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by doudou on 2016/6/18.
 */
public class Everyword_Fragment extends Fragment{
    private TextView tv_content;
    private TextView tv_note;
    private ImageView iv_picture;
    private TextView tv_translation;
    private Button bt_like;
    private Button bt_tts;
    Map<String,Object> every_word_map;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.everyword_main,container,false);
        tv_content= (TextView) view.findViewById(R.id.tv_content);
        tv_note= (TextView) view.findViewById(R.id.tv_note);
        iv_picture= (ImageView) view.findViewById(R.id.iv_picture);
        tv_translation= (TextView) view.findViewById(R.id.tv_translation);
        bt_like= (Button) view.findViewById(R.id.bt_like);
        bt_tts= (Button) view.findViewById(R.id.bt_tts);
        Init();

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageButton imageButton_search = (ImageButton) getActivity().findViewById(R.id.button_search);
        EditText editText_search = (EditText) getActivity().findViewById(R.id.edit_search);
        imageButton_search.setVisibility(View.GONE);
        editText_search.setVisibility(View.GONE);
        toolbar.setTitle("每日一句");

        return view;
    }

    private void Init() {
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH)+1;
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        String address="http://open.iciba.com/dsapi/?date="+year+"-"+month+"-"+day;//标准化输入是2016-01-01，但是2016-1-1正常，所以没有标准化
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            every_word_map = JsonUtility.parseJSONWithJSONObject_Everyword(response);
                            String imageaddr=every_word_map.get("picture2").toString();
                            DisplayImageOptions options= ImageUtility.imageLoader(getActivity());
                            ImageLoader.getInstance().displayImage(imageaddr,iv_picture, options);
                            tv_content.setText(every_word_map.get("content").toString());
                            tv_note.setText(every_word_map.get("note").toString());
                            tv_translation.setText(every_word_map.get("translation").toString());
                            bt_like.setText("喜欢人数"+every_word_map.get("love"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            @Override
            public void onError(Exception e) {

            }
        });
        bt_tts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(every_word_map.get("tts").toString());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    Log.v("src", e.getMessage());
                }
            }
        });
    }
}

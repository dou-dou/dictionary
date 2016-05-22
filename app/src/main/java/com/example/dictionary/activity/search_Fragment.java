package com.example.dictionary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dictionary.R;
import com.example.dictionary.util.HttpCallbackListener;
import com.example.dictionary.util.HttpUtil;
import com.example.dictionary.util.ImageUtility;
import com.example.dictionary.util.JsonUtility;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by doudou on 2016/5/15.
 */
public class search_Fragment extends Fragment{
    private ImageView iv_image;
    private TextView tv_data,tv_chanese;
    private Button bt_change;
    private TextView tv_word;
    private static final String array_month[]={"0","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    private static final String array_change[]={"deadline"," emotions","vast ",};
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.search_main,container,false);
        bt_change= (Button) view.findViewById(R.id.bt_change);
        tv_word= (TextView) view.findViewById(R.id.tv_word);
        iv_image= (ImageView) view.findViewById(R.id.iv_image);
        tv_chanese= (TextView) view.findViewById(R.id.tv_chinese);
        tv_data= (TextView) view.findViewById(R.id.tv_data);
        int j=(int)(Math.random() * 3);
        tv_word.setText(array_change[j]);
        UI_show_data();

        return view;    }

    private void UI_show_data() {
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH)+1;
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        String address="http://open.iciba.com/dsapi/?date="+year+"-"+month+"-"+day;//标准化输入是2016-01-01，但是2016-1-1正常，所以没有标准化
        Log.i("qqqq",address);
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Map<String,Object> every_word_map;
                            every_word_map = JsonUtility.parseJSONWithJSONObject_Everyword(response);
                            String imageaddr=every_word_map.get("picture2").toString();
                            DisplayImageOptions options=ImageUtility.imageLoader(getActivity());
                            ImageLoader.getInstance().displayImage(imageaddr,iv_image, options);
                            tv_chanese.setText(every_word_map.get("content").toString());
                            tv_data.setText(array_month[month]+" "+day);
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

        bt_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=(int)(Math.random() * 3);
                 tv_word.setText(array_change[i]);
    }
});
        tv_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager=getFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.framement_alternative,search_show_Fragment.newInstance(tv_word.getText().toString()));
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

    }



}

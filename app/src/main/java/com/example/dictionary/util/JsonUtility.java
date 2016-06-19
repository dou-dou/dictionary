package com.example.dictionary.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by doudou on 2016/5/15.
 */
public class JsonUtility {
        public static Map<String,Map> parseJSONWithJSONObject_translate(String jsonData) throws JSONException {
            Map<String,Map> tranlate_map= new HashMap<String,Map>();
            Map<String,Object> Map_part1= new HashMap<String,Object>();
            Map<String,List> Map_part2= new HashMap<String,List>();
            JSONObject jsonObject = new JSONObject(jsonData);

            String word_name=jsonObject.getString("word_name");
            Map_part1.put("word_name",word_name);

            JSONObject exchange=jsonObject.getJSONObject("exchange");

            String key[]= JsonUtility.analyzeJsonToArray(exchange,"key");
            for(int i=0;i<key.length;i++)
            {
                String string=key[i];
                if(!exchange.getString(string).equals(""))
                {
                    String  content="";
                    JSONArray word_pl=exchange.getJSONArray(string);
                    for(int j=0;j<word_pl.length();j++)
                    {
                        content = content + word_pl.get(j)+"  ";
                    }
                    Map_part1.put(string,content);
                }
            }


            JSONArray symbols=jsonObject.getJSONArray("symbols");
            JSONObject symbols_firstObject=(JSONObject)symbols.get(0);
            String ph_en=symbols_firstObject.getString("ph_en");
            String ph_am=symbols_firstObject.getString("ph_am");
            String ph_other=symbols_firstObject.getString("ph_other");
            String ph_en_mp3=symbols_firstObject.getString("ph_en_mp3");
            String ph_am_mp3=symbols_firstObject.getString("ph_am_mp3");
            String ph_tts_mp3=symbols_firstObject.getString("ph_tts_mp3");

            Map_part1.put("ph_en",ph_en);
            Map_part1.put("ph_am",ph_am);
            Map_part1.put("ph_other",ph_other);
            Map_part1.put("ph_en_mp3",ph_en_mp3);
            Log.i("json",ph_am);
            Map_part1.put("ph_am_mp3",ph_am_mp3);
            Map_part1.put("ph_tts_mp3",ph_tts_mp3);

            JSONArray parts=symbols_firstObject.getJSONArray("parts");

            for(int i=0;i<parts.length();i++)
            {
                List<String> List_part=new ArrayList<String>();
                JSONObject part=(JSONObject)parts.get(i);
                String part_key=part.getString("part");
                JSONArray means=part.getJSONArray("means");

                for(int j=0;j<means.length();j++)
                {
                    String item=means.get(j).toString();

                    List_part.add(item);
                }
                Map_part2.put(part_key+"",List_part);
            }
            tranlate_map.put("Map_part1",Map_part1);
            tranlate_map.put("Map_part2",Map_part2);
            return tranlate_map;
        }
    public static Map<String,Object> parseJSONWithJSONObject_Everyword(String jsonData) throws JSONException {
        Map<String,Object> everyword_map=new HashMap<>();

        JSONObject jsonObject=new JSONObject(jsonData);

        everyword_map.put("tts",jsonObject.getString("tts"));

        everyword_map.put("content",jsonObject.getString("content"));
        everyword_map.put("note",jsonObject.getString("note"));
        everyword_map.put("love",jsonObject.getString("love"));
        everyword_map.put("translation",jsonObject.getString("translation"));
        everyword_map.put("picture",jsonObject.getString("picture"));

        everyword_map.put("picture2",jsonObject.getString("picture2"));
        everyword_map.put("caption",jsonObject.getString("caption"));
        everyword_map.put("dateline",jsonObject.getString("dateline"));
        everyword_map.put("s_pv",jsonObject.getInt("s_pv"));
        JSONArray array = jsonObject.getJSONArray("tags");
        String tagstr="";
        for(int i=0;i<array.length();i++)
        {
            JSONObject tag = (JSONObject)array.get(i);
            tagstr += tag.getString("name")+"," ;
        }
        everyword_map.put("tags",tagstr);
        everyword_map.put("fenxiang_img",jsonObject.getString("fenxiang_img"));
        return everyword_map;
    }
    //用于返回一个json格式数据的key数组或者value数组
    private static String[] analyzeJsonToArray(JSONObject jsonject, String type) {

        String string = jsonject.toString();
        string = string.replace("}", "");
        string = string.replace("{", "");
        string = string.replace("\"", "");
        String[] strings = string.split(",");

        if (type.equals("key")) {
            String[] stringsNum = new String[strings.length];
            for (int i = 0; i < strings.length; i++) {
                stringsNum[i] = strings[i].split(":")[0];
            }
            return stringsNum;
        } else if (type.equals("value")) {
            String[] stringsName = new String[strings.length];
            for (int i = 0; i < strings.length; i++) {
                stringsName[i] = strings[i].split(":")[1];
            }
            return stringsName;
        } else {
            return null;
        }
    }
}

package com.example.dictionary.model;

/**
 * Created by doudou on 2016/6/21.
 */
public class Word {
    private String word;
    private String parts;
    private String ph_en_map3_src;

    public void setWord(String word){
        this.word=word;

    }
    public  String getWord(){
        return  word;
    }
    public void setParts(String parts){
        this.parts=parts;

    }
    public  String getParts(){
        return  parts;
    }
    public void setPh_en_map3_src(String ph_en_map3_src){
        this.ph_en_map3_src=ph_en_map3_src;

    }
    public  String getPh_en_map3_src(){
        return  ph_en_map3_src;
    }
}

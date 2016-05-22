package com.example.dictionary.util;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by doudou on 2016/5/17.
 */
public class Serializable_Map implements Serializable {
    private Map<String,Map> map;

    public Map<String, Map> getMap() {
        return map;
    }

    public void setMap(Map<String, Map> map) {
        this.map = map;
    }
}

package com.lzl_rjkx.doctor.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by lzl_os on 16/2/23.
 */
public class SerializableMap implements Serializable {

    private Map<String, Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

}

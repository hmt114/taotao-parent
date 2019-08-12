package com.taotao.rest.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author hmt
 * @date 2019/7/31 22:01
 */
public class ItemCat implements Serializable {
    private String u;
    private String n;
    private List<Object> i;

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public List<Object> getI() {
        return i;
    }

    public void setI(List<Object> i) {
        this.i = i;
    }
}

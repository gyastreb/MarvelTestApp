package com.gyastreb.testproject.models;

import java.io.Serializable;

/**
 * Created by gyastreb on 9/13/16.
 */
public class Url implements Serializable{
    String type;
    String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

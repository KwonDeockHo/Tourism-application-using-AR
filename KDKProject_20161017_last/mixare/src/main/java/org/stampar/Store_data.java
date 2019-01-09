package org.stampar;

/**
 * Created by leejongho on 2016-11-19.
 */

/**
 * Created by pratap.kesaboyina on 01-12-2015.
 */
public class Store_data {


    private String name;
    private int img;
    private String description;
    private String url;


    public Store_data() {
    }

    public Store_data(String name, int img , String url) {
        this.name = name;
        this.img = img;
        this.url= url;
    }


    public int getimg() {
        return img;
    }

    public void setimg(String url) {
        this.img = img;
    }

    public String getName() {
        return name;
    }
    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}


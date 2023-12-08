package com.example.kpizani.todolist;

import android.widget.Button;

import java.io.Serializable;

/**
 * Created by kpizani on 3/21/2017.
 */

public class Model implements Serializable {
    private String name;
    private String desc;
    private Button button;

    public Model (String name, String desc){
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public Button getButton(){
        return button;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String toString(){
        return new StringBuffer("")
                .append(this.name).append("_")
                .append(this.desc).toString();
    }
}

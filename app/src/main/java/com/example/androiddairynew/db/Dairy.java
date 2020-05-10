package com.example.androiddairynew.db;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class Dairy extends LitePalSupport {
    private String dairyId;

    private String content;

    private String dairyDay;

    private String dairyUpdateTime;

    public String getDairyId() {
        return dairyId;
    }

    public String getContent() {
        return content;
    }

    public String getDairyDay() {
        return dairyDay;
    }

    public String getDairyUpdateTime() {
        return dairyUpdateTime;
    }

    public void setDairyId(String dairyId) {
        this.dairyId = dairyId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDairyDay(String dairyDay) {
        this.dairyDay = dairyDay;
    }

    public void setDairyUpdateTime(String dairyUpdateTime) {
        this.dairyUpdateTime = dairyUpdateTime;
    }
}

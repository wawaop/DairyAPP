package com.example.androiddairynew.dao;

import android.database.Cursor;

import com.example.androiddairynew.db.Dairy;

import org.litepal.LitePal;

import java.util.List;

public class DairyDao {

    /**
     * 获取某日的日记信息
     *
     * @param dairyDate 想要获取的日期时间
     * @return 日志对象
     */
    public List<Dairy> getDairyByDate(String dairyDate){
        return LitePal.where("dairyDay = ?", dairyDate).find(Dairy.class);
    }
}

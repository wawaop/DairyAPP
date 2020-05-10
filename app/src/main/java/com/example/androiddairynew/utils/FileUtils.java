package com.example.androiddairynew.utils;

import android.util.Log;

import com.example.androiddairynew.constant.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    public static boolean fileCopy(String oldFilePath,String newFilePath){
        //如果原文件不存在
        if(fileExists(oldFilePath) == false){
            return false;
        }
        // 创建目的目录
        File outputFile = new File(Constant.diaryImageUrl);
        if(!outputFile.exists()){
            outputFile.mkdir();
        }
        //获得原文件流
        FileInputStream inputStream = null;
        byte[] data = new byte[1024];
        //输出流
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File(newFilePath));
            inputStream = new FileInputStream(new File(oldFilePath));
            //开始处理流
            while (inputStream.read(data) != -1) {
                outputStream.write(data);
            }
        } catch (FileNotFoundException e) {
            Log.e("FileError", "fileCopy: can not find the image file.");
            return false;
        } catch (IOException e) {
            Log.e("FileError", "there had an IOException.");
            return false;
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                Log.e("FileError","There had an error when want to close the file stream.");
                return false;
            }
        }
        return true;
    }

    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
}

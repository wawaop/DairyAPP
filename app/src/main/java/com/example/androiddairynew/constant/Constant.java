package com.example.androiddairynew.constant;

import android.os.Environment;

import java.io.File;

public class Constant {
    public static int READ_PERMISSION_REQUAST_CODE = 1;

    public static int MITISSA_REQUAST_CODE = 2;

    public static String diaryImageUrl = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dairy" + File.separator;
}

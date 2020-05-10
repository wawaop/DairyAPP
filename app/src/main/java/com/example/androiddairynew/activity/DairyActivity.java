package com.example.androiddairynew.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androiddairynew.R;
import com.example.androiddairynew.constant.Constant;
import com.example.androiddairynew.dao.DairyDao;
import com.example.androiddairynew.db.Dairy;
import com.example.androiddairynew.utils.DairyGlideEngine;
import com.example.androiddairynew.utils.FileUtils;
import com.example.androiddairynew.utils.SDCardUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.List;

import jp.wasabeef.richeditor.RichEditor;

public class DairyActivity extends AppCompatActivity {
    // 当前日志内容是否改变的标志
    private boolean contentHasChange = false;

    // 当前日志是否被保存的标志
    private boolean hasSave = false;

    // 判断当前日期在之前是否存在日志的标志
    private boolean hasOldContent = false;

    private String currentDayStr;

    private RichEditor mEditor;

    private ImageView selectImageView;

    private TextView saveTextView;

    private ImageView mfontSizeAddImageView;

    private ImageView mfontSizeSubImageView;

    private ImageView mboldImageView;

    private ImageView mlistImageView;

    private ImageView mbackImageView;

    private DairyDao dairyDao = new DairyDao();

    private int mEditorFontSize = 4;

    private boolean isBold = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy__rich_editor_layout);

        mEditor = findViewById(R.id.editor);
        selectImageView = findViewById(R.id.insertImageView);
        saveTextView = findViewById(R.id.saveContentImageView);

        mEditor.setFontSize(mEditorFontSize);
        mEditor.setPadding(15, 10, 15, 10);
        mEditor.setPlaceholder("点击此处，输入内容……");

        // 判断当前日期下是否已存在日记内容
        Intent intent = getIntent();
        currentDayStr = intent.getStringExtra("currentDayStr");
        List<Dairy> dairies = dairyDao.getDairyByDate(currentDayStr);
        if (dairies.size() != 0) {
            // 当前日期已经存在之前写过的日记内容
            mEditor.setHtml(dairies.get(0).getContent());
            hasOldContent = true;
        }

        // 打开相册
        selectImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(DairyActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DairyActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.READ_PERMISSION_REQUAST_CODE);
                } else {
                    // 打开相册
                    selectImage();
                }
            }
        });

        // 保存信息
        saveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDairy();
                hasSave = true;
                Toast.makeText(DairyActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            }
        });

        // 监听日志信息是否被修改
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                contentHasChange = true;
            }
        });

        // 字号变大
        mfontSizeAddImageView = findViewById(R.id.fontSizeAddImageView);
        mfontSizeAddImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEditorFontSize>=7){
                    Toast.makeText(DairyActivity.this, "字体已最大",Toast.LENGTH_SHORT).show();
                } else{
                    mEditorFontSize = mEditorFontSize + 1;
                    mEditor.setFontSize(mEditorFontSize);
                }
            }
        });

        // 字号减小
        mfontSizeSubImageView = findViewById(R.id.fontSizeSubImageView);
        mfontSizeSubImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEditorFontSize<=1){
                    Toast.makeText(DairyActivity.this, "字体已最小", Toast.LENGTH_SHORT).show();
                } else {
                    mEditorFontSize = mEditorFontSize - 1;
                    mEditor.setFontSize(mEditorFontSize);
                }
            }
        });

        // 字体加粗
        mboldImageView = findViewById(R.id.boldImageView);
        mboldImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mEditor.setBold();
            }
        });

        mlistImageView = findViewById(R.id.listImageView);
        mlistImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBullets();
            }
        });

        // 返回
        mbackImageView = findViewById(R.id.backImageView);
        mbackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contentHasChange == true && hasSave == false) {
                    returnActivity();
                } else{
                    DairyActivity.this.finish();
                }
            }
        });
    }

    /**
     * 打开相册选择图片
     */
    private void selectImage() {
        Matisse.from(DairyActivity.this)
                .choose(MimeType.ofAll())
                .countable(true)
                .maxSelectable(9)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.album_item_height))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new DairyGlideEngine())
                .forResult(Constant.MITISSA_REQUAST_CODE);
    }

    /**
     * 处理相册返回的结果：根据requestCode进行判断
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 2:
                    // 向Eitor中插入图片
                    insertImageToEditView(data);

            }
        } else {
            Toast.makeText(DairyActivity.this, "the Matisse's return has an error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                // 文件读取权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage();
                } else {
                    Toast.makeText(DairyActivity.this, "文件读取权限申请失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (contentHasChange == true && hasSave == false) {
                returnActivity();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 返回
     */
    private void returnActivity(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(DairyActivity.this);
        dialog.setTitle("内容已修改，是否保存？");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveDairy();
                hasSave = true;
                Toast.makeText(DairyActivity.this, "已保存", Toast.LENGTH_SHORT).show();
                // 结束当前Activity
                DairyActivity.this.finish();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DairyActivity.this.finish();
            }
        });
        dialog.show();
    }

    /**
     * 向Editor中插入图片
     *
     * @param imageData
     */
    private void insertImageToEditView(Intent imageData) {
        List<Uri> imageUri = Matisse.obtainResult(imageData);
        for (Uri uri : imageUri) {
            String imageFileUri = SDCardUtil.getFilePathByUri(DairyActivity.this, uri);
            // 获取文件后缀
            String[] images = imageFileUri.split("\\.");
            String newImageUrli = Constant.diaryImageUrl + String.valueOf(System.currentTimeMillis()) + "." + images[1];
            // 复制到指定路径
            if(FileUtils.fileCopy(imageFileUri, newImageUrli)){
                mEditor.insertImage(newImageUrli, "图片信息\" style=\"max-width:100%");
            } else{
                Toast.makeText(DairyActivity.this, "文件处理时出现错误",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 保存日志信息
     */
    private void saveDairy() {
        if (hasOldContent) {
            // 更新日志信息
            Dairy dairy = dairyDao.getDairyByDate(currentDayStr).get(0);
            dairy.setContent(mEditor.getHtml());
            dairy.save();
        } else {
            Dairy dairy = new Dairy();
            dairy.setContent(mEditor.getHtml());
            dairy.setDairyDay(currentDayStr);
            dairy.setDairyUpdateTime(String.valueOf(System.currentTimeMillis()));
            dairy.save();
        }
    }

}

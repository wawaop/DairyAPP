package com.example.androiddairynew.utils;

import com.sendtion.xrichtext.RichTextEditor;

import java.util.List;

public class RichEditorUtils {
    /**
     * 获取编辑框的内容
     *
     * @return 编辑框生成的HTML字符
     */
    public static String getEditData(RichTextEditor richTextEditor) {
        List<RichTextEditor.EditData> editList = richTextEditor.buildEditData();
        StringBuffer content = new StringBuffer();
        for (RichTextEditor.EditData itemData : editList) {
            if (itemData.inputStr != null) {
                content.append(itemData.inputStr);
            } else if (itemData.imagePath != null) {
                content.append("<img src=\"").append(itemData.imagePath).append("\"/>");
            }
        }
        return content.toString();
    }
}

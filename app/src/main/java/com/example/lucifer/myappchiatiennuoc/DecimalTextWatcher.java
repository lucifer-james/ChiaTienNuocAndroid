package com.example.lucifer.myappchiatiennuoc;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.text.DecimalFormat;

/**
 * Created by Lucifer on 7/18/2021.
 */

public class DecimalTextWatcher implements TextWatcher {

    EditText editText;

    String value;

    public DecimalTextWatcher(EditText editText) {
        this.editText=editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        //đầu tiên là ngắt hần theo dõi sự kiện TextChanged của textbox này
        editText.removeTextChangedListener(this);

        //lấy dữ liệu người dùng vừa nhập
        String originalString = editable.toString();

        //nếu trong phần nhập đã có dấu phân cách phần ngàn ","
        if (originalString.contains(",")){
            //xóa hết dấu phân cách phần ngàn đi
            originalString = originalString.replaceAll(",","");
        }

        //khai báo một DecimalFormat và áp mẫu định dạng
        DecimalFormat dmf = new DecimalFormat("#,###");
        String editedString;
        try{
            if (originalString.equalsIgnoreCase("")){
                editedString=originalString;
            }else{
                editedString =  dmf.format(Integer.parseInt(originalString)).toString();
            }
            //sau khi định dạng xong thì gán lại vào textbox
            editText.setText(editedString);

            //dời con trỏ xuống cuối
            editText.setSelection(editText.getText().length());

            //bật lại tính năng theo dõi dữ liệu người dùng nhập
            editText.addTextChangedListener(this);
        }catch (NumberFormatException e){
            Log.d("ERROR: ","Có lỗi tại class DecimalTextWatcher: NumberFormatException");
            e.printStackTrace();
        }

    }
}

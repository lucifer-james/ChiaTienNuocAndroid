package com.example.lucifer.myappchiatiennuoc;


import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class chitiettiennuoc extends Fragment {
    private String chisonuoc;
    TextView txtChiSoKhoiTong, txtChiSoNhaTri, txtChiSoNhaMinh, txtDonGiaDinhMuc;
    TextView txtThue, txtPhi, txtDonGiaSauThuePhi, txtTienNuocNhaMinh, txtTienNuocNhaTri;

    public chitiettiennuoc() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.chitiettiennuoc_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtChiSoKhoiTong = view.findViewById(R.id.textViewSoKhoiNuocThang);
        txtChiSoNhaTri = view.findViewById(R.id.textViewSoKhoiNuocNhaTri);
        txtChiSoNhaMinh=view.findViewById(R.id.textViewSoKhoiNhaMinh);
        txtDonGiaDinhMuc=view.findViewById(R.id.textViewDonGiaDinhMuc);
        txtThue=view.findViewById(R.id.textViewThueGTGT);
        txtPhi=view.findViewById(R.id.textViewPhiBVMT);
        txtDonGiaSauThuePhi=view.findViewById(R.id.textViewDonGiaDMSauThuePhi);
        txtTienNuocNhaMinh=view.findViewById(R.id.textViewTienNuocNhaMinh);
        txtTienNuocNhaTri=view.findViewById(R.id.textViewTienNuocNhaTri);

    }

    //Lưu trạng thái của dữ liệu đang có trong fragment
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast toast=Toast.makeText(getActivity(),"Lưu trạng thái của chi tiết tiền nước",Toast.LENGTH_SHORT);
        toast.show();
    }

    //lấy ra lại dữ liệu của fragment
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Toast toast=Toast.makeText(getActivity(),"Chi tiết tiền nước ViewStateRestored",Toast.LENGTH_SHORT);
        toast.show();
    }


    public void getData(String chisotong, String chisonhaTri, String sokhoinhaminh,
                        String dongiadinhmuc, String thue, String phi, String dongiasauthuephi,
                        String tiennuocnhaminh, String tiennuocnhaTri) {
        DecimalFormat df = new DecimalFormat("#,###");

        try{
            txtChiSoKhoiTong.setText(df.format(Integer.parseInt(chisotong)));
            txtChiSoNhaTri.setText(df.format(Integer.parseInt(chisonhaTri)));
            txtChiSoNhaMinh.setText(df.format(Integer.parseInt(sokhoinhaminh)));
            txtDonGiaDinhMuc.setText(df.format(Integer.parseInt(dongiadinhmuc)));
            txtThue.setText(df.format(Integer.parseInt(thue)));
            txtPhi.setText(df.format(Integer.parseInt(phi)));
            txtDonGiaSauThuePhi.setText(df.format(Integer.parseInt(dongiasauthuephi)));
            txtTienNuocNhaMinh.setText(df.format(Integer.parseInt(tiennuocnhaminh)));
            txtTienNuocNhaTri.setText(df.format(Integer.parseInt(tiennuocnhaTri)));

        }catch (NumberFormatException e){
            MessegeBox msgbox = new MessegeBox();
            msgbox.showAlert(getActivity(),"Caution","Lỗi đọc dữ liệu không phải là số");
            e.printStackTrace();
        }catch (NullPointerException e){
            MessegeBox msgbox = new MessegeBox();
            msgbox.showAlert(getActivity(),"Caution","Lỗi truy xuất dử liệu là Null");
            e.printStackTrace();
        }catch (RuntimeException e){
            MessegeBox msgbox = new MessegeBox();
            msgbox.showAlert(getActivity(),"Caution","Lỗi chưa xác định");
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        Toast toast = Toast.makeText(getActivity(),"Chi tiết tiền nước Resume",Toast.LENGTH_SHORT);
        toast.show();
        final String STING_PATH = Environment.getDataDirectory().getPath() + "/data/com.example.lucifer.myappchiatiennuoc/";
        final String STING_FILENAME="dulieutam.txt";
        File filetemp = new File(STING_PATH + STING_FILENAME);

        try {
            if (!filetemp.exists()){
                toast = Toast.makeText(getActivity(),"Không tồn tại file tạm",Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            toast = Toast.makeText(getActivity(),"Có tồn tại file tạm",Toast.LENGTH_SHORT);
            toast.show();

            FileInputStream input = new FileInputStream(filetemp);

            int code;

            byte[] tempByte=  new byte[1024];
            String dataString = "";

            String[] subString;

            while ((code=input.read(tempByte))>0){
                dataString += new String(tempByte,0,code);
            }

            toast = Toast.makeText(getActivity(),dataString,Toast.LENGTH_SHORT);
            toast.show();

            input.close();
            filetemp.delete();

            if (!dataString.equalsIgnoreCase("")){
                subString = dataString.split("-");
                txtChiSoKhoiTong.setText(subString[0]);
                txtChiSoNhaTri.setText(subString[1]);
                txtChiSoNhaMinh.setText(subString[2]);
                txtDonGiaDinhMuc.setText(subString[3]);
                txtThue.setText(subString[4]);
                txtPhi.setText(subString[5]);
                txtDonGiaSauThuePhi.setText(subString[6]);
                txtTienNuocNhaMinh.setText(subString[7]);
                txtTienNuocNhaTri.setText(subString[8]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStop() {
        //Toast toast = Toast.makeText(getActivity(),"Chi tiết tiền nước Stop",Toast.LENGTH_SHORT);
        //toast.show();

        final String STING_PATH = Environment.getDataDirectory().getPath() + "/data/com.example.lucifer.myappchiatiennuoc/";
        final String STING_FILENAME="dulieutam.txt";
        File filetemp = new File(STING_PATH + STING_FILENAME);

        try {
            String dataString = txtChiSoKhoiTong.getText().toString() + "-" +
                    txtChiSoNhaTri.getText().toString() + "-" +
                    txtChiSoNhaMinh.getText().toString() + "-" +
                    txtDonGiaDinhMuc.getText().toString() + "-" +
                    txtThue.getText().toString() + "-" +
                    txtPhi.getText().toString() + "-" +
                    txtDonGiaSauThuePhi.getText().toString() + "-" +
                    txtTienNuocNhaMinh.getText().toString() + "-" +
                    txtTienNuocNhaTri.getText().toString() + "-"
                    ;

            if (!dataString.replaceAll("-","").equalsIgnoreCase("")){

                if (filetemp.exists()){
                    filetemp.delete();
                    filetemp.createNewFile();
                }else{
                    filetemp.createNewFile();
                }

                FileOutputStream output = new FileOutputStream(filetemp);

                byte[] dataByte = dataString.getBytes();

                output.write(dataByte,0,dataByte.length);
                output.close();

                //Toast toast =Toast.makeText(getActivity(),"Đã ghi ra file tạm thành công",Toast.LENGTH_SHORT);
                //toast.show();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onStop();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onPause() {
        super.onPause();

    }


    @Override
    public void onDestroy() {
        final String STING_PATH = Environment.getDataDirectory().getPath() + "/data/com.example.lucifer.myappchiatiennuoc/";
        final String STING_FILENAME="dulieutam.txt";
        File filetemp = new File(STING_PATH + STING_FILENAME);

        if (filetemp.exists()){
            filetemp.delete();
        }

        super.onDestroy();

    }
}

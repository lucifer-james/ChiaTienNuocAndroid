package com.example.lucifer.myappchiatiennuoc;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class tinhtiennuoc extends Fragment implements View.OnClickListener {
    EditText txtChiSoNuocThangNay, txtChiSoNuocThangTruoc, txtChiSoTriThangNay,txtChiSoTriThangTruoc;
    EditText txtTongTienNuoc;
    TextView txtKetQua;

    DecimalTextWatcher[] dftxtWatcher= new DecimalTextWatcher[5];

    int don_gia, don_gia_ngoai_DM, thueGTGT, phiBVMT, so_nguoi_DM, so_khoi_DM;

    private DuLieuDauVaoNuoc objDuLieuDauVao;

    final String STING_PATH = Environment.getDataDirectory().getPath() + "/data/com.example.lucifer.myappchiatiennuoc/";
    final String STING_FILENAME="DonGiaNuoc.xml";
    File fileXML = new File(STING_PATH + STING_FILENAME);

    DocumentBuilderFactory docBuilderFactory;
    DocumentBuilder docBuilder;

    Document docXMLDonGia;

    public SendMessage SM;


    public tinhtiennuoc() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tinhtiennuoc_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        txtChiSoNuocThangNay = view.findViewById(R.id.editTextChiSoTongThangNay);
        dftxtWatcher[0] = new DecimalTextWatcher(txtChiSoNuocThangNay);
        txtChiSoNuocThangNay.addTextChangedListener(dftxtWatcher[0]);

        txtChiSoNuocThangTruoc = view.findViewById(R.id.editTextChiSoTongThangTruoc);
        dftxtWatcher[1] = new DecimalTextWatcher(txtChiSoNuocThangTruoc);
        txtChiSoNuocThangTruoc.addTextChangedListener(dftxtWatcher[1]);

        txtChiSoTriThangNay= view.findViewById(R.id.editTextChiSoNhaTriThangNay);
        dftxtWatcher[2] = new DecimalTextWatcher(txtChiSoTriThangNay);
        txtChiSoTriThangNay.addTextChangedListener(dftxtWatcher[2]);

        txtChiSoTriThangTruoc= view.findViewById(R.id.editTextChiSoTriThangTruoc);
        dftxtWatcher[3] = new DecimalTextWatcher(txtChiSoTriThangTruoc);
        txtChiSoTriThangTruoc.addTextChangedListener(dftxtWatcher[3]);

        //khai báo 1 EditText txtTongTienNuoc là tham chiếu đến textbox có ID là editTextTongTienNuoc
        txtTongTienNuoc = view.findViewById(R.id.editTextTongTienNuoc);
        dftxtWatcher[4] = new DecimalTextWatcher(txtTongTienNuoc);
        txtTongTienNuoc.addTextChangedListener(dftxtWatcher[4]);

        Button btnTinhTien = view.findViewById(R.id.buttonTinhTienNuoc);

        txtKetQua = view.findViewById(R.id.textViewKetQua);

        btnTinhTien.setOnClickListener(this);

        super.onViewCreated(view, savedInstanceState);
    }

    public interface SendMessage{
        void sendData(String chisotong, String chisonhaTri, String sokhoinhaminh,
                      String dongiadinhmuc, String thue, String phi,String dongiasauthuephi,
                      String tiennuocnhaminh, String tiennuocnhaTri);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            SM = (SendMessage)getActivity();
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        SM = null;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.buttonTinhTienNuoc:
                calTienNuoc();
                return;
            case R.id.buttonSaveData:
                saveData();
                return;
        }
    }

    private void saveData() {
        MessegeBox msgbox = new MessegeBox();
        msgbox.showAlertSaveOrNot(getActivity(),"Thông báo","Bạn có muốn lưu chỉ số nước mới ?",
                txtChiSoNuocThangNay.getText().toString(),txtChiSoTriThangNay.getText().toString());
    }

    private void calTienNuoc() {
        int chi_so_nha_minh;
        int chi_so_tong_thang_nay, chi_so_tong_thang_truoc;
        int chi_so_Tri_thang_nay,chi_so_Tri_thang_truoc;
        int so_tien_nha_minh, so_tien_nha_Tri;
        int tong_tien_thang;

        int donGiaNuocSauPhiThue;

        int donGiaNuocNgoaiDMSauPhiThue;

        DecimalFormat dmf = new DecimalFormat();
        dmf.applyPattern("#,###.#");

        MessegeBox msgbox = new MessegeBox();

        readDataInputOfNuoc();

        if (objDuLieuDauVao==null){
            //msgbox.showAlert(getActivity(),"Lưu ý","Chưa có file dữ liệu chỉ số nước" +
            //        "\nVui lòng cập nhật trong tab Cập nhật chỉ số nước");
            return;
        }

        try{

            don_gia=objDuLieuDauVao.getDonGiaDM();

            don_gia_ngoai_DM=objDuLieuDauVao.getDonGiaNgoaiDM();

            thueGTGT = objDuLieuDauVao.getThue();

            phiBVMT=objDuLieuDauVao.getPhi();

            so_khoi_DM=objDuLieuDauVao.getDinhMucCaNhan();

            so_nguoi_DM=objDuLieuDauVao.getTongSoNguoiDM();

            donGiaNuocSauPhiThue = don_gia + don_gia*thueGTGT/100 + don_gia*phiBVMT/100;

            donGiaNuocNgoaiDMSauPhiThue = don_gia_ngoai_DM + don_gia_ngoai_DM*thueGTGT/100 + don_gia_ngoai_DM*phiBVMT/100;

            // lấy giá trị của textbox Chỉ Số Nước Tháng Này và gán vào biến chi_so_tong_thang_nay
            // trường hợp có lổi trong việc chuyển đổi text sang chữ thì bẫy lỗi try catch
            chi_so_tong_thang_nay = Integer.parseInt(txtChiSoNuocThangNay.getText().toString().replaceAll(",",""));

            chi_so_tong_thang_truoc = Integer.parseInt(txtChiSoNuocThangTruoc.getText().toString().replaceAll(",",""));

            chi_so_Tri_thang_nay = Integer.parseInt(txtChiSoTriThangNay.getText().toString().replaceAll(",",""));

            chi_so_Tri_thang_truoc = Integer.parseInt(txtChiSoTriThangTruoc.getText().toString().replaceAll(",",""));

            tong_tien_thang=Integer.parseInt(txtTongTienNuoc.getText().toString().replaceAll(",",""));

        }catch (NumberFormatException nfe){
            //nếu có lỗi thì hiện thông báo và ngừng chương trình
            msgbox.showAlert(getActivity(),"Lưu ý","Một trong các ô dữ liệu bị trống" +
                    "\nVui lòng nhập đầy đủ số liệu");
            return;
        }

        // xét nếu chỉ số tháng trước lớn hơn chỉ số tháng này => dữ liệu đầu vào bất hợp lý
        if ((chi_so_tong_thang_nay - chi_so_tong_thang_truoc<=0)|| (chi_so_Tri_thang_nay-chi_so_Tri_thang_truoc<=0)){
            msgbox.showAlert(getActivity(),"Lưu ý","Chỉ số nước các tháng không hợp lý");
            return;
        }else{
            //tính chì số nước nhà mình
            chi_so_nha_minh = (chi_so_tong_thang_nay - chi_so_tong_thang_truoc) - (chi_so_Tri_thang_nay-chi_so_Tri_thang_truoc);

            //nếu chỉ số nước nhà mình âm => dữ liệu bất hợp lý => hiện thông báo và dừng lại
            if (chi_so_nha_minh<=0){
                msgbox.showAlert(getActivity(),"Lưu ý","Chỉ số nước nhà mình dùng bị âm");
                return;
            }else if(chi_so_nha_minh<=16){
                so_tien_nha_minh = chi_so_nha_minh*(donGiaNuocSauPhiThue);

            }else{
                so_tien_nha_minh = 16*donGiaNuocSauPhiThue + (chi_so_nha_minh-16)*donGiaNuocNgoaiDMSauPhiThue;
            }

            //nếu số tiền nước nhà mình lớn hơn tổng tiền tháng thì sai rồi
            if (so_tien_nha_minh>= tong_tien_thang){
                msgbox.showAlert(getActivity(),"Lưu ý","Số tiền nước nhà mình dùng lớn hơn tổng tiền nước của tháng");
                return;
            }

            so_tien_nha_Tri=tong_tien_thang-so_tien_nha_minh;

            txtKetQua.setText(dmf.format(so_tien_nha_Tri));


            String chisotong = String.valueOf(chi_so_tong_thang_nay-chi_so_tong_thang_truoc);
            String chisonhaTri = String.valueOf(chi_so_Tri_thang_nay-chi_so_Tri_thang_truoc);
            String chisonhaminh = String.valueOf(chi_so_nha_minh);
            String dongia = String.valueOf(don_gia);

            //String thue = String.valueOf(Math.round(donGiaNuoc*0.05));
            String thue = String.valueOf(Math.round(don_gia*thueGTGT/100));

            String phi =String.valueOf(Math.round(don_gia*phiBVMT/100));
            String dongiasauthuephi = String.valueOf(Math.round(don_gia+don_gia*thueGTGT/100+don_gia*phiBVMT/100));
            String tiennuocnhaminh = String.valueOf(so_tien_nha_minh);
            String tiennuocnhaTri = String.valueOf(so_tien_nha_Tri);

            Toast toast = Toast.makeText(getActivity(),"Đã tính xong",Toast.LENGTH_SHORT);
            toast.show();

            try{
                SM.sendData(chisotong,chisonhaTri,chisonhaminh,dongia,thue,phi,dongiasauthuephi,tiennuocnhaminh,tiennuocnhaTri);
            }catch (ClassCastException e){
                msgbox.showAlert(getActivity(),"Caution","Lỗi chuyển dữ liệu sang tab chi tiết nước");
                e.printStackTrace();
            }
        }
    }

    private void readDataInputOfNuoc() {
        String text = "";
        try{
            XmlPullParserFactory xmlPPF = XmlPullParserFactory.newInstance();

            xmlPPF.setNamespaceAware(true);

            XmlPullParser xmlPP = xmlPPF.newPullParser();

            FileInputStream inputFileXML = new FileInputStream(fileXML);

            xmlPP.setInput(inputFileXML,null);

            int eventType = xmlPP.getEventType();

            while (eventType!=XmlPullParser.END_DOCUMENT) {
                String tagname = xmlPP.getName();

                switch (eventType) {
                    case XmlPullParser.TEXT:
                        text = xmlPP.getText();
                        break;

                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("Data")){
                            objDuLieuDauVao=new DuLieuDauVaoNuoc();
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("DonGiaDinhMuc")) {
                            objDuLieuDauVao.setDonGiaDM(Integer.parseInt(text));
                            text="";
                        } else if (tagname.equalsIgnoreCase("DonGiaNgoaiDinhMuc")) {
                            objDuLieuDauVao.setDonGiaNgoaiDM(Integer.parseInt(text));
                            text="";
                        } else if (tagname.equalsIgnoreCase("Thue")) {
                            objDuLieuDauVao.setThue(Integer.parseInt(text));
                            text="";
                        } else if (tagname.equalsIgnoreCase("Phi")) {
                            objDuLieuDauVao.setPhi(Integer.parseInt(text));
                            text="";
                        } else if (tagname.equalsIgnoreCase("DMCaNhan")) {
                            objDuLieuDauVao.setDinhMucCaNhan(Integer.parseInt(text));
                            text="";
                        } else if (tagname.equalsIgnoreCase("SoNguoiGiaDinhDM")) {
                            objDuLieuDauVao.setTongSoNguoiDM(Integer.parseInt(text));
                            text="";
                        }
                        break;

                    default:break;
                }
                eventType=xmlPP.next();
            }

        } catch (XmlPullParserException e) {
            MessegeBox msgbox = new MessegeBox();
            msgbox.showAlert(getActivity(),"Caution","Lỗi khi phân tích file XML dữ liệu đầu vào");
            e.printStackTrace();
            return;
        } catch (FileNotFoundException e) {
            MessegeBox msgbox = new MessegeBox();
            msgbox.showAlert(getActivity(),"Caution","Không tìm thấy file chứa đơn giá nước");
            e.printStackTrace();
            return;
        } catch (IOException e) {
            MessegeBox msgbox = new MessegeBox();
            msgbox.showAlert(getActivity(),"Caution","Lỗi khi đọc file");
            e.printStackTrace();
            return;
        }catch( NullPointerException e){
            MessegeBox msgbox = new MessegeBox();
            msgbox.showAlert(getActivity(),"Caution","Lỗi khi truy xuất một dữ liệu là Null");
            e.printStackTrace();
            return;
        }

    }

}

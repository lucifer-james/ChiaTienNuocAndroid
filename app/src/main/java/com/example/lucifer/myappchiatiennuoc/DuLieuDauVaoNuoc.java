package com.example.lucifer.myappchiatiennuoc;

/**
 * Created by Lucifer on 7/14/2021.
 */

public class DuLieuDauVaoNuoc {
    int don_gia_DM, don_gia_ngoai_DM, thue, phi, dinh_muc_ca_nhan, so_nguoi;

    public void setDonGiaDM (int dongiadinhmuc){
        this.don_gia_DM=dongiadinhmuc;
    }

    public int getDonGiaDM (){
        return this.don_gia_DM;
    }

    public void setDonGiaNgoaiDM (int dongiangoaidinhmuc){
        this.don_gia_ngoai_DM=dongiangoaidinhmuc;
    }

    public int getDonGiaNgoaiDM (){
        return this.don_gia_ngoai_DM;
    }

    public void setThue (int thue){
        this.thue = thue;
    }

    public int getThue(){
        return this.thue;
    }

    public void setPhi (int phi){
        this.phi=phi;
    }

    public int getPhi (){
        return this.phi;
    }

    public void setDinhMucCaNhan (int dinhmuccanhan){
        this.dinh_muc_ca_nhan=dinhmuccanhan;
    }

    public int getDinhMucCaNhan  (){
        return this.dinh_muc_ca_nhan;
    }

    public void setTongSoNguoiDM (int songuoidm){
        this.so_nguoi=songuoidm;
    }

    public int getTongSoNguoiDM  (){
        return this.so_nguoi;
    }
}

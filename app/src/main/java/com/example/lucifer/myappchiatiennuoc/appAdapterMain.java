package com.example.lucifer.myappchiatiennuoc;

import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by Lucifer on 7/11/2021.
 */

public class appAdapterMain extends FragmentPagerAdapter {

    String listTab[] = {"Tính tiền nước", "Chi tiết tiền nước","Cập nhật đơn giá", "Giới thiệu"};

    public tinhtiennuoc frag_tinhtiennuoc;
    public chitiettiennuoc frag_chitiettiennuoc;
    public capnhatdongia frag_capnhatdongia;
    public gioithieu frag_gioithieu;

    public appAdapterMain(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                frag_tinhtiennuoc=new tinhtiennuoc();
                return frag_tinhtiennuoc;
            case 1:
                frag_chitiettiennuoc=new chitiettiennuoc();
                return frag_chitiettiennuoc;
            case 2:
                frag_capnhatdongia = new capnhatdongia();
                return frag_capnhatdongia;
            case 3:
                frag_gioithieu=new gioithieu();
                return frag_gioithieu;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return listTab[position];
    }
}

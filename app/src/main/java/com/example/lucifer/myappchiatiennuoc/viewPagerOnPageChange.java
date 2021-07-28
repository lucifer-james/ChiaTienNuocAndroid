package com.example.lucifer.myappchiatiennuoc;

import android.content.Context;
import android.support.annotation.Px;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

/**
 * Created by Lucifer on 7/18/2021.
 */

public class viewPagerOnPageChange implements ViewPager.OnPageChangeListener {

    ViewPager viewPager;
    Context context;

    public viewPagerOnPageChange(ViewPager viewpager, Context context) {
        this.viewPager = viewpager;
        this.context=context;
    }

    @Override
    public void onPageScrolled(int i, float v, @Px int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        //Toast toast =Toast.makeText(context,"Trang số " + viewPager.getCurrentItem(),Toast.LENGTH_SHORT);
        //toast.show();
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}

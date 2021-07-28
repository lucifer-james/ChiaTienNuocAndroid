package com.example.lucifer.myappchiatiennuoc;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Px;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.R.attr.dropDownWidth;
import static android.R.attr.fragment;
import static android.R.attr.width;

public class MainActivity extends AppCompatActivity implements tinhtiennuoc.SendMessage {
    TabLayout tbl_Function;
    ViewPager vpagerContent;
    viewPagerOnPageChange viewpagerOnPageChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tbl_Function = (TabLayout)findViewById(R.id.tbl_Main);
        vpagerContent = (ViewPager)findViewById(R.id.viewPagerMain);
        vpagerContent.setAdapter(new appAdapterMain(getSupportFragmentManager()));

        viewpagerOnPageChangeListener = new viewPagerOnPageChange(vpagerContent,getApplicationContext());
        vpagerContent.addOnPageChangeListener(viewpagerOnPageChangeListener);

        tbl_Function.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LinearLayout tabLayout = (LinearLayout) ((ViewGroup)tbl_Function.getChildAt(0)).getChildAt(tab.getPosition());
                TextView tabTextView = (TextView)tabLayout.getChildAt(1);
                tabTextView.setTypeface(Typeface.DEFAULT_BOLD);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout tabLayout = (LinearLayout) ((ViewGroup)tbl_Function.getChildAt(0)).getChildAt(tab.getPosition());
                TextView tabTextView = (TextView)tabLayout.getChildAt(1);
                tabTextView.setTypeface(Typeface.DEFAULT);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tbl_Function.setupWithViewPager(vpagerContent);

    }

    int counter=0;
    @Override
    public void onBackPressed(){
        counter++;
        Toast.makeText(getApplicationContext(), "Nhấn Back lần nữa để Exit", Toast.LENGTH_SHORT).show();
        if (counter==2){
            super.onBackPressed();
        }

    }

    @Override
    public void sendData(String chisotong, String chisonhaTri, String sokhoinhaminh,
                  String dongiadinhmuc, String thue, String phi,String dongiasauthuephi,
                  String tiennuocnhaminh, String tiennuocnhaTri) {

        List<Fragment> fragments;
        chitiettiennuoc frag_Chitietnuoc;

        try{
            fragments = (List<Fragment>) getSupportFragmentManager().getFragments();
            int i=0;

            for (i=0;i<fragments.size();i++){
                String nameOfFragment = fragments.get(i).toString();
                int charPosition = nameOfFragment.indexOf("{");

                nameOfFragment = nameOfFragment.substring(0,charPosition);

                if (nameOfFragment.equalsIgnoreCase("chitiettiennuoc")){
                    frag_Chitietnuoc = (chitiettiennuoc)fragments.get(i);
                    frag_Chitietnuoc.getData(chisotong,chisonhaTri,sokhoinhaminh,dongiadinhmuc,thue,phi,dongiasauthuephi,
                            tiennuocnhaminh,tiennuocnhaTri);
                }

            }
        }catch (NullPointerException e){
            MessegeBox msgbox = new MessegeBox();
            msgbox.showAlert(getApplicationContext(),"Caution","Lỗi không lấy được dữ liệu từ tab Tính tiền nước");
        }

    }

}

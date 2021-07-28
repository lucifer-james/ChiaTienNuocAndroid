package com.example.lucifer.myappchiatiennuoc;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lucifer on 7/11/2021.
 */

public class ChiSoNuoc {

    public void writeChiSoMoi(Context context, String chi_so_moi, String chi_so_nha_Tri_moi){
        MessegeBox msgbox = new MessegeBox();

        SharedPreferences sharedPrefChiSoNuoc = context.getSharedPreferences("ChiSoNuocCu", Context.MODE_PRIVATE);

        SharedPreferences.Editor sharedPrefEditor = sharedPrefChiSoNuoc.edit();

        sharedPrefEditor.putString("ChiSoTong",chi_so_moi);
        sharedPrefEditor.putString("ChiSoTri",chi_so_nha_Tri_moi);

        sharedPrefEditor.commit();

    }


    public static String[] readChiSoCu(Context context){
        final String[] ketqua={"",""};
        MessegeBox msgbox = new MessegeBox();

        SharedPreferences sharedPrefChiSoNuoc = context.getSharedPreferences("ChiSoNuocCu", Context.MODE_PRIVATE);

        ketqua[0]= sharedPrefChiSoNuoc.getString("ChiSoTong","");
        ketqua[1]= sharedPrefChiSoNuoc.getString("ChiSoTri","");

        return ketqua;
    }
}

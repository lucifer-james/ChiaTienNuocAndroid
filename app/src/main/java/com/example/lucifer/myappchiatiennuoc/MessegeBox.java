package com.example.lucifer.myappchiatiennuoc;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Lucifer on 7/11/2021.
 */

public class MessegeBox {

    public static void showAlert(Context context, String title, String messege){

        AlertDialog.Builder alertDlgBuilder = new AlertDialog.Builder(context);

        alertDlgBuilder.setMessage(messege);
        alertDlgBuilder.setTitle(title);

        alertDlgBuilder.setCancelable(false);

        alertDlgBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                return;
            }
        });

        AlertDialog alertDlg = alertDlgBuilder.create();
        alertDlg.show();

    }

    public static void showAlertSaveOrNot(final Context context, String title, String messege,
                                          final String chi_so_tong, final String chi_so_nha_Tri){

        AlertDialog.Builder alertDlgBuilder = new AlertDialog.Builder(context);

        alertDlgBuilder.setMessage(messege);
        alertDlgBuilder.setTitle(title);

        alertDlgBuilder.setCancelable(true);

        alertDlgBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                ChiSoNuoc objChiSoNuoc = new ChiSoNuoc();
                objChiSoNuoc.writeChiSoMoi(context,chi_so_tong,chi_so_nha_Tri );
                Toast toast = Toast.makeText(context,"Đã lưu chỉ số mới", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        alertDlgBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                //return;
                //dialog.dismiss();
                dialog.cancel();
            }
        });

        AlertDialog alertDlg = alertDlgBuilder.create();
        alertDlg.show();

    }

}

package com.dien.android_home_test.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

    private static ProgressDialog progressDialog;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo item : info) {
                    if (item.getState() == NetworkInfo.State.CONNECTED) ;
                    return true;
                }
            }
        }
        return false;
    }

    public static void showSimpleProgressDialog(Context context, String tittle, String msg, boolean isCancelAble) {
        try {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(context, tittle, msg);
                progressDialog.setCancelable(isCancelAble);
            }

            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showSimpleProgressDialog(Context context) {
        showSimpleProgressDialog(context, null, "Loading...........", false);
    }

    public static void removeSimpleProgressDialog() {
        try {
            if (progressDialog != null) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getLines(String input) {
        StringBuilder result = new StringBuilder();
        String[] arrLine = input.split(" ");
        int postion = arrLine.length / 2;
        for (int i = 0; i < arrLine.length; i++) {
            if (i == postion && i != 0) {
                result.append("\n").append(arrLine[i]);
            } else {
                result.append(" ").append(arrLine[i]);
            }

        }
        return result.toString();
    }
}

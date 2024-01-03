package com.unitbv.siipa.fragments;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class CreateDialog {

    public static void dialog(Context context, String message, String positiveButtonText,
                              String negativeButtonText, DialogInterface.OnClickListener actions) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setPositiveButton(positiveButtonText, actions)
                .setNegativeButton(negativeButtonText, actions).show();
    }

}
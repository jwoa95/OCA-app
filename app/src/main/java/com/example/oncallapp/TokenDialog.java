package com.example.oncallapp;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;


/**
 * The type Token dialog.
 */
public class TokenDialog extends AppCompatDialogFragment {

    private static final String KEY_TOKEN = "token";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        SharedPreferences myPrefs = getContext().getSharedPreferences("myPrefs", 0);
        String token = myPrefs.getString(KEY_TOKEN, "TOKEN");
        builder.setTitle("FCM Token").setMessage(token).setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("Copy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", token);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Token Copied!", Toast.LENGTH_LONG).show();
            }
        });
        return builder.create();
    }
}

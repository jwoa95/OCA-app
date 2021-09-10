package com.example.oncallapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String SHARED_PREF_NAME = "myPrefs";
    private static final String KEY_TOKEN = "token";
    private static final String APP_COLOUR = "#0070AD";

    /**
     * The Issue.
     */
    ListView issue;
    /**
     * The Token button.
     */
    Button tokenButton;
    /**
     * The Shared preferences.
     */
    SharedPreferences sharedPreferences;
    /**
     * The Btn download.
     */
    Button btnDownload;
    /**
     * The Btn view.
     */
    Button btnView;
    /**
     * The Scroll view.
     */
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OnCallAppNoBar);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        initUI();

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        tokenButton = (Button) findViewById(R.id.tokenBTN);
        tokenButton.setBackgroundColor(Color.parseColor(APP_COLOUR));

        tokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        btnView = (Button) findViewById(R.id.button3);
        btnView.setBackgroundColor(Color.parseColor(APP_COLOUR));

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, TicketDisplayActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(a);
            }
        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FAILED", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_TOKEN,token);
                        editor.apply();
                    }
                });
    }

    private void openDialog() {
        TokenDialog tokenDialog = new TokenDialog();
        tokenDialog.show(getSupportFragmentManager(), "Token Dialog");
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent a = new Intent(MainActivity.this, TicketDisplayActivity.class);
        a.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(a);
        String issues = issue.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(), "Clicked " + issues, Toast.LENGTH_SHORT).show();

    }


    private void initUI() {
        btnDownload = findViewById(R.id.button2);
        btnDownload.setBackgroundColor(Color.parseColor("#0070AD"));
        btnDownload.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, DownloadActivity.class)));
    }

}
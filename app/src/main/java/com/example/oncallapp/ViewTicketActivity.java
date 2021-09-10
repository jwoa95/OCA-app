package com.example.oncallapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

/**
 * The type View ticket activity.
 */
public class ViewTicketActivity extends AppCompatActivity {

    /**
     * The Ticket output.
     */
    TextView ticketOutput;
    /**
     * The Delete ticket btn.
     */
    Button deleteTicketBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ticket);

        ActionBar actionBar = super.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);

        ticketOutput = (TextView) findViewById(R.id.textView2);
        ticketOutput.setTextColor(Color.parseColor("#000000"));

        deleteTicketBtn = (Button) findViewById(R.id.button4);


        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            S3Ticket selectedTicket = (S3Ticket) intent.getSerializableExtra("data");

            Log.d("Selected Ticket is", selectedTicket.toString());
            ticketOutput.setText(selectedTicket.displayTicketDetails());
            ticketOutput.setMovementMethod(new ScrollingMovementMethod());
            actionBar.setTitle(selectedTicket.displayTicketName());


            deleteTicketBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedTicket.getTicketDetails().toString();
                    Log.d("Selected Ticket name is", selectedTicket.getName());
                    File dir = getExternalFilesDir(null);
                    File file = new File(dir, selectedTicket.getName());
                    Log.d("File to delete is", file.getName());
                    if (file.exists()) {
                        if (file.delete()) {
                            Log.d("File Deleted:", file.getName());
                        } else {
                            Log.d("File not deleted", file.getName());
                        }
                    }
                    Intent a = new Intent(ViewTicketActivity.this, TicketDisplayActivity.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(a);
                }
            });

        }

    }
}
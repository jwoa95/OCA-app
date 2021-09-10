package com.example.oncallapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * The type Ticket display activity.
 */
public class TicketDisplayActivity extends AppCompatActivity implements S3TicketAdapter.SelectedTicket {


    /**
     * The S 3 tickets.
     */
    ArrayList<S3Ticket> s3Tickets;
    /**
     * The Adapter.
     */
    S3TicketAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_OnCallApp);
        setContentView(R.layout.s3_tickets);

        ActionBar actionBar = super.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Downloaded Tickets");


        // Lookup the recyclerview in activity layout
        RecyclerView rvTickets = (RecyclerView) findViewById(R.id.rvContacts);

        File[] totalTickets = getExternalFilesDir(null).listFiles();
        List<String> ticketContent = new ArrayList<String>();
        List<String> ticketNames = new ArrayList<String>();
        Map<String, List<String>> tickets = new HashMap<String, List<String>>(totalTickets.length);

        for (File currentTicket : totalTickets) {
            if (currentTicket.isFile()) {
                Log.d("current file", currentTicket.getName());
                ticketNames.add(currentTicket.getName());
                try {
                    Scanner input = new Scanner(currentTicket);
                    ticketContent = new ArrayList<String>();
                    while (input.hasNextLine()) {
                        ticketContent.add(input.nextLine());
                    }

                    input.close();

                } catch (IOException ex) {
                    System.out.format("I/O error: %s%n", ex);
                }

            } else {
                // do something here with the file
            }

            tickets.put(currentTicket.getName(), ticketContent);
        }

        s3Tickets = S3Ticket.createTicketsList(ticketNames, tickets);
        adapter = new S3TicketAdapter(s3Tickets, this, this::selectedTicket);
        rvTickets.setAdapter(adapter);
        rvTickets.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void selectedTicket(S3Ticket ticket) {
        Log.d("Selected ticket is: ", ticket.displayTicketName());
        startActivity(new Intent(TicketDisplayActivity.this, ViewTicketActivity.class).putExtra("data", ticket));
    }
}


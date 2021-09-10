package com.example.oncallapp;



import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3Client;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class S3TicketTest {

    S3Ticket s3Ticket;
    String  name;
    List<String> ticketDetails;

    @Test
    public void testDisplayTicketName() {
        name = "ticket.json";
        ticketDetails = new ArrayList<>();
        s3Ticket = new S3Ticket(name, ticketDetails);
        assertEquals("TICKET", s3Ticket.displayTicketName());
    }

    @Test
    public void testDisplayTicketDetails() {
        name = "ticket.json";
        ticketDetails = new ArrayList<>();
        ticketDetails.add("{");
        ticketDetails.add("        \"title\": \"example glossary\"\n");
        ticketDetails.add("}");
        s3Ticket = new S3Ticket(name, ticketDetails);
        assertEquals("{\n" +
                "  \"title\": \"example glossary\"\n" +
                "}", s3Ticket.displayTicketDetails());
    }
}

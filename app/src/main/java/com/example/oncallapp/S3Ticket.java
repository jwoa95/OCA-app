package com.example.oncallapp;

import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.apache.commons.io.FilenameUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type S3 ticket.
 */
public class S3Ticket implements Serializable {

    private String ticketName;

    private List<String> ticketDetails;

    /**
     * Instantiates a new S3 ticket.
     *
     * @param name          the name
     * @param ticketDetails the ticket details
     */
    public S3Ticket(String name, List<String> ticketDetails) {
        this.ticketName = name;
        this.ticketDetails = ticketDetails;
    }

    /**
     * Create tickets list array list.
     *
     * @param ticketNames the ticket names
     * @param tickets     the tickets
     * @return the array list
     */
    public static ArrayList<S3Ticket> createTicketsList(List<String> ticketNames, Map<String, List<String>> tickets) {
        ArrayList<S3Ticket> s3Ticket = new ArrayList<S3Ticket>();

        for (int i = 0; i < ticketNames.size(); i++) {
            s3Ticket.add(new S3Ticket(ticketNames.get(i), tickets.get(ticketNames.get(i))));
        }

        return s3Ticket;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return ticketName;
    }

    /**
     * Gets ticket details.
     *
     * @return the ticket details
     */
    public List<String> getTicketDetails() {
        return ticketDetails;
    }

    /**
     * Sets ticket details.
     *
     * @param ticketDetails the ticket details
     */
    public void setTicketDetails(List<String> ticketDetails) {
        this.ticketDetails = ticketDetails;
    }

    /**
     * Display ticket name string.
     *
     * @return the string
     */
    public String displayTicketName() {
        return StringUtils.upperCase(FilenameUtils.removeExtension(this.ticketName));
    }

    /**
     * Display ticket details string.
     *
     * @return the string
     */
    public String displayTicketDetails() {
        StringBuilder ticketContent = new StringBuilder();
        String formattedTicketDetails;
        for (String currentLine : this.ticketDetails) {
            ticketContent.append(currentLine);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(ticketContent.toString());
        formattedTicketDetails = gson.toJson(je);
        return formattedTicketDetails;
    }

}

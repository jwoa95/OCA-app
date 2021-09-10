package com.example.oncallapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * The type S 3 ticket adapter.
 */
public class S3TicketAdapter extends RecyclerView.Adapter<S3TicketAdapter.ViewHolder> {

    /**
     * The M context.
     */
    Context mContext;
    /**
     * The M click listener.
     */
    ItemClickListener mClickListener;
    /**
     * The M tickets.
     */
    List<S3Ticket> mTickets;
    /**
     * The Selected ticket.
     */
    SelectedTicket selectedTicket;


    /**
     * Instantiates a new S 3 ticket adapter.
     *
     * @param s3Tickets      the s 3 tickets
     * @param context        the context
     * @param selectedTicket the selected ticket
     */
    public S3TicketAdapter(List<S3Ticket> s3Tickets, Context context, SelectedTicket selectedTicket) {
        this.mTickets = s3Tickets;
        this.mContext = context;
        this.selectedTicket = selectedTicket;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public S3TicketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.activity_ticket_display, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(S3TicketAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        S3Ticket ticket = mTickets.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        textView.setText(ticket.displayTicketName());
        Button button = holder.messageButton;
        button.setText("View Ticket");
        button.setEnabled(true);
        textView.setTypeface(Typeface.create("sans-serif", Typeface.BOLD));
        textView.setTextColor(Color.parseColor("#0070AD"));
        textView.setTextSize(15);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mTickets.size();
    }



    /**
     * The interface Selected ticket.
     */
    public interface SelectedTicket {
        /**
         * Selected ticket.
         *
         * @param contact the contact
         */
        void selectedTicket(S3Ticket contact);
    }

    /**
     * The interface Item click listener.
     */
// parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        /**
         * On item click.
         *
         * @param view     the view
         * @param position the position
         */
        void onItemClick(View view, int position);
    }

    /**
     * The type View holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * The Name text view.
         */
// Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        /**
         * The Message button.
         */
        public Button messageButton;


        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
// We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            messageButton = (Button) itemView.findViewById(R.id.message_button);
            nameTextView = (TextView) itemView.findViewById(R.id.ticket_id);
            nameTextView.setTextColor(Color.parseColor("#0070AD"));
            nameTextView.setAllCaps(true);

            messageButton.setOnClickListener(this);
            messageButton.setBackgroundColor(Color.parseColor("#0070AD"));
            messageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedTicket.selectedTicket(mTickets.get(getAdapterPosition()));
                }
            });

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }


    }


}

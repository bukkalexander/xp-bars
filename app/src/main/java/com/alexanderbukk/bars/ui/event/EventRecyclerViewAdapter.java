package com.alexanderbukk.bars.ui.event;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexanderbukk.bars.R;
import com.alexanderbukk.bars.data.event.Event;
import com.alexanderbukk.bars.ui.event.EventViewHolder;

import java.util.List;

public class EventRecyclerViewAdapter extends
        RecyclerView.Adapter<EventViewHolder> {

    private Context context;
    private List<Event> events;
    private int eventColor = 0;

    public EventRecyclerViewAdapter(Context context, List<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_view_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.cvEvent.setCardBackgroundColor(eventColor);
        holder.tvEvent.setText(events.get(position).name);
        holder.tvEvent.setTextColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        if(events == null || events.isEmpty())
            return 0;
        else
            return events.size();
    }


    public void setAllEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    public void setEventColor(int eventColor) {
        this.eventColor = eventColor;
    }
}

package com.alexanderbukk.bars.ui.event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexanderbukk.bars.R;
import com.alexanderbukk.bars.ui.event.EventViewHolder;

public class EventRecyclerViewAdapter extends
        RecyclerView.Adapter<EventViewHolder> {

    private Context context;
    private String[] eventNames;

    public EventRecyclerViewAdapter(Context context, String[] eventNames) {
        this.context = context;
        this.eventNames = eventNames;
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
        holder.tvEvent.setText(eventNames[position]);
    }

    @Override
    public int getItemCount() {
        return eventNames.length;
    }


}

package com.alexanderbukk.bars;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewEventAdapter extends
        RecyclerView.Adapter<ViewHolderEvent> {

    private Context context;
    private String[] eventNames;

    public RecyclerViewEventAdapter(Context context, String[] eventNames) {
        this.context = context;
        this.eventNames = eventNames;
    }

    @NonNull
    @Override
    public ViewHolderEvent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_view_event, parent, false);
        return new ViewHolderEvent(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderEvent holder, int position) {
        holder.tvEvent.setText(eventNames[position]);
    }

    @Override
    public int getItemCount() {
        return eventNames.length;
    }


}

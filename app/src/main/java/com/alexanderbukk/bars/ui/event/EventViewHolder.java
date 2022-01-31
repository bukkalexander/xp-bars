package com.alexanderbukk.bars.ui.event;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alexanderbukk.bars.data.event.Event;
import com.alexanderbukk.bars.data.group.Group;
import com.alexanderbukk.bars.ui.eventeditor.EventEditorActivity;
import com.alexanderbukk.bars.R;

public class EventViewHolder extends RecyclerView.ViewHolder{
    CardView cvEvent;
    TextView tvEvent;
    private Group group = null;
    private Event event = null;

    public EventViewHolder(@NonNull View itemView) {
        super(itemView);

        cvEvent = itemView.findViewById(R.id.cv_event);
        tvEvent = itemView.findViewById(R.id.tv_event);
        tvEvent.setTextColor(Color.WHITE);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Intent intent = new Intent(activity, EventEditorActivity.class);
                intent.putExtra("groupName", group.name);
                intent.putExtra("eventName", event.name);
                activity.startActivity(intent);
            }
        });
    }

    public void setEvent(Event event) {
        this.event = event;
        tvEvent.setText(event.name);
    }

    public void setGroup(Group group) {
        this.group = group;
        cvEvent.setCardBackgroundColor(group.color);
    }
}

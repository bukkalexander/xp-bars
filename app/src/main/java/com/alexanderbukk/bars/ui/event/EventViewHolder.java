package com.alexanderbukk.bars.ui.event;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alexanderbukk.bars.ui.eventeditor.EventEditorActivity;
import com.alexanderbukk.bars.R;

public class EventViewHolder extends RecyclerView.ViewHolder{
    CardView cvEvent;
    TextView tvEvent;

    public EventViewHolder(@NonNull View itemView) {
        super(itemView);

        cvEvent = itemView.findViewById(R.id.cv_event);
        tvEvent = itemView.findViewById(R.id.tv_event);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Intent intent = new Intent(activity, EventEditorActivity.class);
                intent.putExtra("event", tvEvent.getText());
                activity.startActivity(intent);
            }
        });
    }
}

package com.alexanderbukk.bars;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderEvent extends RecyclerView.ViewHolder{
    CardView cvEvent;
    TextView tvEvent;

    public ViewHolderEvent(@NonNull View itemView) {
        super(itemView);

        cvEvent = itemView.findViewById(R.id.cv_event);
        tvEvent = itemView.findViewById(R.id.tv_event);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Intent intent = new Intent(activity, EventAdderActivity.class);
                intent.putExtra("event", tvEvent.getText());
                activity.startActivity(intent);
            }
        });
    }
}

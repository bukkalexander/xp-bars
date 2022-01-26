package com.alexanderbukk.bars.ui.group;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alexanderbukk.bars.ui.event.EventActivity;
import com.alexanderbukk.bars.R;

public class GroupViewHolder extends RecyclerView.ViewHolder{
    CardView cvGroup;
    TextView tvGroup;

    public GroupViewHolder(@NonNull View itemView) {
        super(itemView);

        cvGroup = itemView.findViewById(R.id.cv_group);
        tvGroup = itemView.findViewById(R.id.tv_group);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Intent intent = new Intent(activity, EventActivity.class);
                intent.putExtra("group", tvGroup.getText());
                intent.putExtra("color", cvGroup.getSolidColor());
                activity.startActivity(intent);
            }
        });
    }
}
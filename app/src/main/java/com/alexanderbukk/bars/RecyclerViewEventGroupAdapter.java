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

public class RecyclerViewEventGroupAdapter extends
    RecyclerView.Adapter<RecyclerViewEventGroupAdapter.ViewHolderEventGroup> {

    private Context context;
    private String[] eventGroupNames;
    private int[] eventGroupColor;

    public RecyclerViewEventGroupAdapter(Context context, String[] eventGroupNames, int[] eventGroupColor) {
        this.context = context;
        this.eventGroupNames = eventGroupNames;
        this.eventGroupColor = eventGroupColor;
    }

    @NonNull
    @Override
    public ViewHolderEventGroup onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_view_event_group, parent, false);
        return new ViewHolderEventGroup(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderEventGroup holder, int position) {
        holder.tvEventGroup.setText(eventGroupNames[position]);
        holder.cvEventGroup.setCardBackgroundColor(eventGroupColor[position]);
    }

    @Override
    public int getItemCount() {
        return eventGroupNames.length;
    }

    public class ViewHolderEventGroup extends RecyclerView.ViewHolder{
        CardView cvEventGroup;
        TextView tvEventGroup;

        public ViewHolderEventGroup(@NonNull View itemView) {
            super(itemView);

            cvEventGroup = itemView.findViewById(R.id.cv_event_group);
            tvEventGroup = itemView.findViewById(R.id.tv_event_group);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Intent intent = new Intent(activity, EventListActivity.class);
                    intent.putExtra("eventGroup", tvEventGroup.getText());
                    activity.startActivity(intent);
                }
            });
        }
    }
}

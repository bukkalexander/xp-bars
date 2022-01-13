package com.alexanderbukk.bars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewEventGroupAdapter extends
    RecyclerView.Adapter<RecyclerViewEventGroupAdapter.ViewHolderEventGroup> {

    private Context context;
    private String[] eventGroupNames;

    public RecyclerViewEventGroupAdapter(Context context, String[] eventGroupNames) {
        this.context = context;
        this.eventGroupNames = eventGroupNames;
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
    }

    @Override
    public int getItemCount() {
        return eventGroupNames.length;
    }

    public class ViewHolderEventGroup extends RecyclerView.ViewHolder{
        TextView tvEventGroup;

        public ViewHolderEventGroup(@NonNull View itemView) {
            super(itemView);

            tvEventGroup = itemView.findViewById(R.id.tv_event_group);
        }
    }
}

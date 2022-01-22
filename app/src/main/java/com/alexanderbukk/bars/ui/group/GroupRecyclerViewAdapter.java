package com.alexanderbukk.bars.ui.group;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexanderbukk.bars.R;
import com.alexanderbukk.bars.data.group.Group;

import java.util.List;

public class GroupRecyclerViewAdapter extends
    RecyclerView.Adapter<GroupViewHolder> {

    private Context context;
    private List<Group> allGroups;

    public GroupRecyclerViewAdapter(Context context, List<Group> allGroups) {
        this.context = context;
        this.allGroups = allGroups;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_view_event_group, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        holder.tvEventGroup.setText(allGroups.get(position).name);
        holder.cvEventGroup.setCardBackgroundColor(allGroups.get(position).color);
    }

    @Override
    public int getItemCount() {

        if(allGroups == null || allGroups.isEmpty())
            return 0;
        else
            return allGroups.size();
    }

    public void setAllUsers(List<Group> allGroups) {
        this.allGroups = allGroups;
        notifyDataSetChanged();
    }


}

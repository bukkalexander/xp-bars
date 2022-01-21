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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.alexanderbukk.bars.ui.dashboard.DashboardViewModel;

import java.util.List;

public class RecyclerViewEventGroupAdapter extends
    RecyclerView.Adapter<RecyclerViewEventGroupAdapter.ViewHolderEventGroup> {

    private Context context;
    private List<User> allUsers;

    public RecyclerViewEventGroupAdapter(Context context, List<User> allUsers) {
        this.context = context;
        this.allUsers = allUsers;
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
        holder.tvEventGroup.setText(allUsers.get(position).firstName);
    }

    @Override
    public int getItemCount() {

        if(allUsers == null || allUsers.isEmpty())
            return 0;
        else
            return allUsers.size();
    }

    public void setAllUsers(List<User> allUsers) {
        this.allUsers = allUsers;
        notifyDataSetChanged();
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

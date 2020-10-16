package com.example.lab3map;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<RecyclerViewRow> recyclerViewRowList;
    private Fragment2 context;

    public RecyclerViewAdapter(List<RecyclerViewRow> recyclerViewRowList, Fragment2 fragment2) {
        this.recyclerViewRowList = recyclerViewRowList;
        this.context = fragment2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RecyclerViewRow recyclerViewRow = recyclerViewRowList.get(position);

        holder.textViewName.setText(recyclerViewRow.getName());
        holder.textViewAddress.setText(recyclerViewRow.getAddress());
        holder.textViewRating.setText(recyclerViewRow.getRating());


    }

    @Override
    public int getItemCount() {
        return recyclerViewRowList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public TextView textViewAddress;
        public TextView textViewRating;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.tv_name);
            textViewAddress = (TextView) itemView.findViewById(R.id.tv_address);
            textViewRating = (TextView) itemView.findViewById(R.id.tv_rating);
        }
    }
}

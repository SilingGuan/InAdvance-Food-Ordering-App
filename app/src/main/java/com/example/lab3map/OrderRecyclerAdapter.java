//package com.example.lab3map;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.RecyclerView;
//import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
//import android.os.Bundle;
//import android.provider.ContactsContract;
//import android.view.LayoutInflater;
//import com.google.firebase.firestore.DocumentSnapshot;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.TextView;
//
//import com.firebase.ui.firestore.FirestoreRecyclerOptions;
//
//public class OrderRecyclerAdapter extends FirestoreRecyclerAdapter<RecyclerViewRow, OrderRecyclerAdapter.OrderViewHolder> {
//
//    private static final String TAG = "OrderRecyclerAdapter";
//    OrderListener orderListener;
//
//    public OrderRecyclerAdapter(@NonNull FirestoreRecyclerOptions<RecyclerViewRow> options, OrderListener orderListener) {
//        super(options);
//        this.orderListener = orderListener;
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull RecyclerViewRow model) {
//                  holder.display_order_list.setText(model.getOrderList());
//                  holder.display_resName.setText(model.getResName());
//                  holder.display_resAddress.setText(model.getAddress());
//                  holder.display_price.setText(model.getPrice());
//                  holder.display_order_time.setText(model.getOrderTime());
//    }
//
//    @NonNull
//    @Override
//    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.recyclerview_display_orderlist,parent,false);
//        return new OrderViewHolder(view);
//    }
//
//
//    class OrderViewHolder extends RecyclerView.ViewHolder {
//        TextView display_order_list, display_order_time, display_price,display_resName, display_resAddress;
//
//        public OrderViewHolder(@NonNull View itemView) {
//            super(itemView);
//            display_resName = itemView.findViewById(R.id.tv_resName);
//            display_resAddress = itemView.findViewById(R.id.tv_resAddress);
//            display_order_list = itemView.findViewById(R.id.tv_order);
//            display_price = itemView.findViewById(R.id.tv_price);
//            display_order_time = itemView.findViewById(R.id.tv_time);
//        });
//    }
//    }

package com.example.lab3map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    String data1[], data2[];
    int image[];
    Context context;
    public CartAdapter(Context ct,String s1[], String s2[], int img[]){
        context = ct;
        data1 = s1;
        data2 = s2;
        image = img;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_cart_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText1.setText(data1[position]);
        holder.myText2.setText(data2[position]);
        holder.myImage.setImageResource(image[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView myText1, myText2;
        ImageView myImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.text_food_name);
            myText2 = itemView.findViewById(R.id.text_food_price);
            myImage = itemView.findViewById(R.id.image_cart);
        }
    }
}
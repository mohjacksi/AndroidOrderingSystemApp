package com.mjacksi.novapizza.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mjacksi.novapizza.R;

public class FoodViewHolder extends RecyclerView.ViewHolder {
    TextView name, description;
    ImageView imageView;
    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.text_view_name);
        description = itemView.findViewById(R.id.text_view_description);
        imageView = itemView.findViewById(R.id.image_view_food);
    }
}

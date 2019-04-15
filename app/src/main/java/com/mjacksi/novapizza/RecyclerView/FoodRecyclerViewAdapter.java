package com.mjacksi.novapizza.RecyclerView;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Transformations;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mjacksi.novapizza.Models.Food;
import com.mjacksi.novapizza.R;
import com.mjacksi.novapizza.RoomDatabase.FoodRoom;
import com.mjacksi.novapizza.Util.MyDiffCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FoodRecyclerViewAdapter extends RecyclerView.Adapter<FoodRecyclerViewAdapter.FoodViewHolder> {
    List<FoodRoom> foodList = new ArrayList<>();
    Context context;
    private OnItemClickListener listener;


    public FoodRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.food_recyclerview_item, viewGroup, false);
        FoodViewHolder holder = new FoodViewHolder(itemView);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int i) {
        FoodRoom currentFood = foodList.get(i);
        holder.name.setText(currentFood.getTitle());
        holder.description.setText(currentFood.getDescription());
        holder.imageView.setImageResource(currentFood.getImage());

        if (currentFood.getCount() > 0) { // add order to cart
            holder.linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.card_bg_ordered));
            holder.button.setText("Delete order");
            holder.button.setBackgroundColor(ContextCompat.getColor(context, R.color.button_bg_ordered));
            //holder.button.setTextColor(ContextCompat.getColor(context, R.color.card_bg_ordered));
        } else { // delete order
            holder.linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.card_bg_unordered));
            holder.button.setText("Order Now");
            holder.button.setBackgroundColor(ContextCompat.getColor(context, R.color.button_bg_order));
            holder.button.setTextColor(ContextCompat.getColor(context, R.color.button_text_order));
        }
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView name, description;
        ImageView imageView;
        Button button;


        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            name = itemView.findViewById(R.id.text_view_name);
            description = itemView.findViewById(R.id.text_view_description);
            imageView = itemView.findViewById(R.id.image_view_food);
            button = itemView.findViewById(R.id.order_button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(foodList.get(position), position);
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return foodList.size();
    }


    List<Integer> oldCount = new ArrayList<>();

    public void changeAt(List<FoodRoom> newFoodRooms, int position) {
        this.foodList = newFoodRooms;

        if (position != -1)
            notifyItemChanged(position);
        else
            notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(FoodRoom food, int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}

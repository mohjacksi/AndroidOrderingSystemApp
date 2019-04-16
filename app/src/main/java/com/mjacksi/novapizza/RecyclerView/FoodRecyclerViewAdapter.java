package com.mjacksi.novapizza.RecyclerView;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mjacksi.novapizza.Models.Food;
import com.mjacksi.novapizza.R;
import com.mjacksi.novapizza.RoomDatabase.FoodRoom;

import java.util.ArrayList;
import java.util.List;

public class FoodRecyclerViewAdapter extends RecyclerView.Adapter<FoodRecyclerViewAdapter.FoodViewHolder> {
    List<FoodRoom> foodList = new ArrayList<>();
    List<Food> cloneFoodList = new ArrayList<>();

    Context context;
    private OnItemClickListener listener;


    public FoodRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.food_item, viewGroup, false);
        FoodViewHolder holder = new FoodViewHolder(itemView);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int i) {
        FoodRoom currentFood = foodList.get(i);
        holder.name.setText(currentFood.getTitle());
        holder.description.setText(currentFood.getDescription());
        holder.imageView.setImageResource(currentFood.getImage());
        holder.price.setText("$" + String.valueOf(currentFood.getPrice()));
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
        TextView name, description, price;
        ImageView imageView;
        Button button;


        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            name = itemView.findViewById(R.id.text_view_name);
            description = itemView.findViewById(R.id.text_view_description);
            imageView = itemView.findViewById(R.id.image_view_food);
            button = itemView.findViewById(R.id.order_button);
            price = itemView.findViewById(R.id.text_view_price);

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


    void cloneList(List<FoodRoom> newFoodList){
        cloneFoodList.clear();
        for (int i = 0; i < newFoodList.size() ; i++) {
            FoodRoom current = newFoodList.get(i);
            cloneFoodList.add(new Food(current.getCount()));
        }
    }

    void checkDiff(){
        if(cloneFoodList.size() == foodList.size()){
            for (int i = 0; i < cloneFoodList.size(); i++) {
                if (cloneFoodList.get(i).getCount() != foodList.get(i).getCount()){
                    notifyItemChanged(i);
                }
            }
        }else{
            notifyDataSetChanged();
        }
    }

    public void changeAt(List<FoodRoom> newFoodRooms) {
        this.foodList = newFoodRooms;
        checkDiff();
        cloneList(newFoodRooms);
    }


    public interface OnItemClickListener {
        void onItemClick(FoodRoom food, int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}

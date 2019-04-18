package com.mjacksi.novapizza.Adapters;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.FoodViewHolder>{
    List<FoodRoom> foodList = new ArrayList<>();
    List<Food> cloneFoodList = new ArrayList<>();

    Context context;
    private FoodRecyclerViewAdapter.OnItemClickListener listenerInc;
    private FoodRecyclerViewAdapter.OnItemClickListener listenerDec;


    public OrderRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public OrderRecyclerViewAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.order_item, viewGroup, false);
        OrderRecyclerViewAdapter.FoodViewHolder holder = new OrderRecyclerViewAdapter.FoodViewHolder(itemView);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRecyclerViewAdapter.FoodViewHolder holder, int i) {
        FoodRoom currentFood = foodList.get(i);
        holder.name.setText(currentFood.getTitle());
        holder.imageView.setImageResource(currentFood.getImage());
        holder.price.setText("x" + currentFood.getCount());
        holder.total.setText("$" + currentFood.getCount() * currentFood.getPrice());

    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView name, price, total;
        ImageView imageView;
        ImageView inc_count, dec_count;
        Button button;


        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            name = itemView.findViewById(R.id.text_view_name);
            total = itemView.findViewById(R.id.text_view_total);
            imageView = itemView.findViewById(R.id.image_view_food);
            button = itemView.findViewById(R.id.order_button);
            price = itemView.findViewById(R.id.text_view_price);

            inc_count = itemView.findViewById(R.id.inc_count);
            dec_count = itemView.findViewById(R.id.dec_count);

            inc_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listenerInc != null && position != RecyclerView.NO_POSITION) {
                        listenerInc.onItemClick(foodList.get(position), position);
                    }
                }
            });

            dec_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listenerDec != null && position != RecyclerView.NO_POSITION) {
                        listenerDec.onItemClick(foodList.get(position), position);

                    }
                }
            });



//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (listener != null && position != RecyclerView.NO_POSITION) {
//                        listener.onItemClick(foodList.get(position), position);
//                    }
//                }
//            });
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
//        if (cloneFoodList.size() == 0)
//            notifyDataSetChanged();
        this.foodList = newFoodRooms;
        checkDiff();
        cloneList(newFoodRooms);
    }


    public interface OnItemClickListener {
        void onItemClick(FoodRoom food, int position);
    }

    public void setOnItemClickListenerInc(FoodRecyclerViewAdapter.OnItemClickListener listener) {
        this.listenerInc = listener;
    }
    public void setOnItemClickListenerDec(FoodRecyclerViewAdapter.OnItemClickListener listener) {
        this.listenerDec = listener;
    }

}

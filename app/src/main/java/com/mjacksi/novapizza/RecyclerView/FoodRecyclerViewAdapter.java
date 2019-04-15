package com.mjacksi.novapizza.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mjacksi.novapizza.Models.Food;
import com.mjacksi.novapizza.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class FoodRecyclerViewAdapter extends RecyclerView.Adapter<FoodViewHolder> {
    List<Food> foodList = new ArrayList<>();

    public FoodRecyclerViewAdapter(){
        foodList.add(new Food("Neapolitan Pizza",
                "Neapolitan is the original pizza. This delicious pie dates all the way back to 18th century in Naples, Italy.",
                4.99, R.drawable.neapolitanpizza));

        foodList.add(new Food("Chicago Pizza",
                "Chicago pizza, also commonly referred to as deep-dish pizza, gets its name from the city it was invented in.",
                5.59, R.drawable.chicagopizza));

        foodList.add(new Food("New York-Style Pizza",
                "With its characteristic large, foldable slices and crispy outer crust, New York-style pizza is one of America’s most famous regional pizza types.",
                5.00, R.drawable.newyorkstylepizza));

        foodList.add(new Food("Sicilian Pizza",
                "Sicilian pizza, also known as \"sfincione,\" provides a thick cut of pizza with pillowy dough, a crunchy crust, and robust tomato sauce.",
                4.59, R.drawable.sicilianpizza));

        foodList.add(new Food("Greek Pizza",
                "Greek pizza was created by Greek immigrants who came to America and were introduced to Italian pizza.",
                7.99, R.drawable.greekpizza));

        foodList.add(new Food("California Pizza",
                "California pizza, or gourmet pizza, is known for its unusual ingredients. ",
                5.79, R.drawable.californiapizza));
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        final Context context = viewGroup.getContext();
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.food_recyclerview_item, viewGroup, false);
        final FoodViewHolder holder = new FoodViewHolder(itemView);
        Button button = itemView.findViewById(R.id.order_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int i) {
        Food currentFood = foodList.get(i);
        holder.name.setText(currentFood.getName());
        holder.description.setText(currentFood.getDescription());
        holder.imageView.setImageResource(currentFood.getImage());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
}

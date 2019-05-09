package com.mjacksi.novapizza.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mjacksi.novapizza.Activities.OrderActivity;
import com.mjacksi.novapizza.Models.Food;
import com.mjacksi.novapizza.R;
import com.mjacksi.novapizza.RoomDatabase.FoodRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * The adapter for @{@link OrderActivity}
 * Display all items selected by user
 */
public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.FoodViewHolder>{
    List<FoodRoom> foodList = new ArrayList<>();
    List<Food> cloneFoodList = new ArrayList<>();

    Context context;
    private FoodRecyclerViewAdapter.OnItemClickListener listenerInc;
    private FoodRecyclerViewAdapter.OnItemClickListener listenerDec;


    /**
     * Get the context from constructor
     *
     * @param context the context of activity that use this adapter
     */
    public OrderRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    /**
     * Make ViewHolder for recycler view
     * Invoke that times of the items can fill the screen
     * @return view holder
     */
    @NonNull
    @Override
    public OrderRecyclerViewAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.order_item, viewGroup, false);
        OrderRecyclerViewAdapter.FoodViewHolder holder = new OrderRecyclerViewAdapter.FoodViewHolder(itemView);


        return holder;
    }

    /**
     * Binding the data from our database with our limited view holders
     * @param holder view holder in this position
     * @param i position
     */
    @Override
    public void onBindViewHolder(@NonNull OrderRecyclerViewAdapter.FoodViewHolder holder, int i) {
        FoodRoom currentFood = foodList.get(i);
        holder.name.setText(currentFood.getTitle());
        holder.imageView.setImageResource(currentFood.getImage());
        holder.price.setText("x" + currentFood.getCount());
        holder.total.setText("$" + currentFood.getCount() * currentFood.getPrice());

    }

    /**
     * Clone the old list view (class attribute one) with the new one came after changes
     *
     * @param newFoodList
     */
    void cloneList(List<FoodRoom> newFoodList) {
        cloneFoodList.clear();
        for (int i = 0; i < newFoodList.size(); i++) {
            FoodRoom current = newFoodList.get(i);
            cloneFoodList.add(new Food(current.getCount()));
        }
    }


    @Override
    public int getItemCount() {
        return foodList.size();
    }

    /**
     * Check the difference between the old and new list of items
     * to make the animation on list view in the appropriate position
     */
    void checkDiff() {
        if (cloneFoodList.size() == foodList.size()) {
            for (int i = 0; i < cloneFoodList.size(); i++) {
                if (cloneFoodList.get(i).getCount() != foodList.get(i).getCount()) {
                    notifyItemChanged(i);
                }
            }
        } else {
            notifyDataSetChanged();
        }
    }

    /**
     * To update the adapter from outside with new list of items
     *
     * @param newFoodRooms
     */
    public void newFoodRooms(List<FoodRoom> newFoodRooms) {
        this.foodList = newFoodRooms;
        checkDiff();
        cloneList(newFoodRooms);
    }

    /**
     * This interface will implemented
     * by the activity who implements
     * this recycler view adapter
     * to listen for click event
     */
    public void setOnItemClickListenerInc(FoodRecyclerViewAdapter.OnItemClickListener listener) {
        this.listenerInc = listener;
    }

    /**
     * This interface will implemented
     * by the activity who implements
     * this recycler view adapter
     * to listen for click event
     */
    public void setOnItemClickListenerDec(FoodRecyclerViewAdapter.OnItemClickListener listener) {
        this.listenerDec = listener;
    }

    /**
     * Holder class that defined all widgets inside
     * the view holder for this recycler view
     */
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
        }
    }

}

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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mjacksi.novapizza.Fragments.HomeFragment;
import com.mjacksi.novapizza.Models.Food;
import com.mjacksi.novapizza.R;
import com.mjacksi.novapizza.RoomDatabase.FoodRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * The adapter for @{@link HomeFragment} Home fragment
 * Display all items to select what user want to eat
 */
public class FoodRecyclerViewAdapter extends RecyclerView.Adapter<FoodRecyclerViewAdapter.FoodViewHolder> {
    List<FoodRoom> foodList = new ArrayList<>();
    List<Food> cloneFoodList = new ArrayList<>();

    Context context;
    private OnItemClickListener listener;


    /**
     * Get the context from constructor
     *
     * @param context the context of fragment that use this adapter
     */
    public FoodRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    /**
     * Make ViewHolder for recycler view
     * Invoke that times of the items can fill the screen
     * @param i position
     * @return view holder
     */
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // getting the list item for this recycler view
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.food_item, viewGroup, false);
        FoodViewHolder holder = new FoodViewHolder(itemView);
        return holder;
    }

    /**
     * Binding the data from our database with our limited view holders
     * @param holder view holder in this position
     * @param i position
     */
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int i) {
        FoodRoom currentFood = foodList.get(i);
        holder.name.setText(currentFood.getTitle());
        holder.description.setText(currentFood.getDescription());
        holder.imageView.setImageResource(currentFood.getImage());
        holder.price.setText("$" + currentFood.getPrice());
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

    /**
     * Clone the old list view (class attribute one) with the new one came after changes
     *
     * @param newFoodList
     */
    private void cloneList(List<FoodRoom> newFoodList) {
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
    private void checkDiff() {
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
    public void setNewList(List<FoodRoom> newFoodRooms) {
        this.foodList = newFoodRooms;
        checkDiff();
        cloneList(newFoodRooms);
    }

    /**
     * To set listener on cell in recycler view
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    /**
     * This interface will implemented
     * by the activity who implements
     * this recycler view adapter
     * to listen for click event
     */
    public interface OnItemClickListener {
        void onItemClick(FoodRoom food, int position);
    }

    /**
     * Holder class that defined all widgets inside
     * the view holder for this recycler view
     */
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

}

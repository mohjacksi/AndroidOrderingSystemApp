package com.mjacksi.novapizza.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mjacksi.novapizza.Fragments.MyOrdersFragment;
import com.mjacksi.novapizza.Models.FirebaseOrder;
import com.mjacksi.novapizza.R;
import com.mjacksi.novapizza.Utilises.Utilises;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The adapter for @{@link MyOrdersFragment}
 * Display all items selected by user
 */
public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {


    List<FirebaseOrder> orders = new ArrayList<>();
    Context context;

    /**
     * Get the context from constructor
     *
     * @param context the context of fragment that use this adapter
     */
    public OrdersAdapter(FragmentActivity context) {
        this.context = context;
    }

    /**
     * Make ViewHolder for recycler view
     * Invoke that times of the items can fill the screen
     * @return view holder
     */
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_orders_item, parent, false);
        OrderViewHolder holder = new OrderViewHolder(itemView);
        return holder;
    }

    /**
     * Binding the data from our database with our limited view holders
     * @param holder view holder in this position
     * @param position position
     */
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        FirebaseOrder currentOrder = orders.get(position);

        holder.orderPosition.setText("#" + (getItemCount() - position));
        holder.time.setText(Utilises.getTime(currentOrder.getTime()));

        String details = "";
        for (Map.Entry<String, Integer> entry : currentOrder.getOrders().entrySet()) {
            details = details + entry.getKey() + " x " + entry.getValue() + "\n";
        }
        details = details.substring(0, details.length() - 1);
        holder.orderDetails.setText(details);

        DecimalFormat df = new DecimalFormat("#.##");

        holder.total.setText("$" + df.format(currentOrder.getTotal()));
        String status = currentOrder.getStatus().replace("_", " ");
        holder.status.setText(status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    /**
     * To update the adapter from outside with new list of items
     * @param newOrders
     */
    public void setNewList(List<FirebaseOrder> newOrders) {
        this.orders = newOrders;
        notifyDataSetChanged();
    }

    /**
     * Holder class that defined all widgets inside
     * the view holder for this recycler view
     */
    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderPosition;
        TextView time;
        TextView orderDetails;
        TextView total;
        TextView status;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderPosition = itemView.findViewById(R.id.order_position);
            time = itemView.findViewById(R.id.order_time);
            orderDetails = itemView.findViewById(R.id.order_details);
            total = itemView.findViewById(R.id.order_total);
            status = itemView.findViewById(R.id.order_status);
        }
    }
}

package com.mjacksi.novapizza.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mjacksi.novapizza.Models.FirebaseOrder;
import com.mjacksi.novapizza.R;
import com.mjacksi.novapizza.Util.Utilises;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {


    List<FirebaseOrder> orders = new ArrayList<>();
    Context context;

    public OrdersAdapter(FragmentActivity activity) {
        context = activity;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_orders_item, parent, false);
        OrderViewHolder holder = new OrderViewHolder(itemView);


        return holder;
    }

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
        holder.status.setText(currentOrder.getStatus());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setNewList(List<FirebaseOrder> newOrders) {
        this.orders = newOrders;
        notifyDataSetChanged();
    }

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

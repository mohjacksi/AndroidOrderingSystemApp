package com.mjacksi.novapizza.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mjacksi.novapizza.Fragments.HomeFragment;
import com.mjacksi.novapizza.R;
import com.mjacksi.novapizza.RecyclerView.FoodRecyclerViewAdapter;
import com.mjacksi.novapizza.RecyclerView.OrderRecyclerViewAdapter;
import com.mjacksi.novapizza.RoomDatabase.FoodRoom;
import com.mjacksi.novapizza.RoomDatabase.FoodViewModel;

import java.text.DecimalFormat;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private static final String TAG = OrderActivity.class.getSimpleName();
    FoodViewModel foodViewModel;
    private TextView amountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Toolbar toolbar = (Toolbar) findViewById(R.id.order_toolbar);
        toolbar.setTitle("Review order");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final OrderRecyclerViewAdapter adapter = new OrderRecyclerViewAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.order_recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        foodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        foodViewModel.getAllOrderedFood().observe(this, new Observer<List<FoodRoom>>() {
            @Override
            public void onChanged(@Nullable List<FoodRoom> foods) {
                if (foods.size() == 0) finish();

                adapter.changeAt(foods);

                double totalAmount = 0;
                for (int i = 0; i < foods.size(); i++) {
                    if(foods.get(i).getCount() != 0) {
                        totalAmount += (foods.get(i).getPrice() * foods.get(i).getCount());
                    }
                }
                setAmount(totalAmount);
            }
        });

        amountTextView = findViewById(R.id.text_view_order_amount);



        adapter.setOnItemClickListenerInc(new FoodRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FoodRoom food, int position) {
                food.setCount(food.getCount()+1);
                foodViewModel.update(food);
            }
        });

        adapter.setOnItemClickListenerDec(new FoodRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FoodRoom food, int position) {
                food.setCount(food.getCount()-1);
                foodViewModel.update(food);
            }
        });
    }


    private void setAmount(double totalAmount) {
        DecimalFormat df = new DecimalFormat("#.##");
        amountTextView.setText("Total amount: $" + df.format(totalAmount));
    }



    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}

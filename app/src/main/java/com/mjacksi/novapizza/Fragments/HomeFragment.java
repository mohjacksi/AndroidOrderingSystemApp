package com.mjacksi.novapizza.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.mjacksi.novapizza.R;
import com.mjacksi.novapizza.RecyclerView.FoodRecyclerViewAdapter;
import com.mjacksi.novapizza.RoomDatabase.FoodRoom;
import com.mjacksi.novapizza.RoomDatabase.FoodViewModel;

import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    FoodViewModel foodViewModel;
    private TextView amountTextView;

    Animation fadeInAnimation;
    Animation fadeOutAndroidAnimation;

    public HomeFragment() {
        // Required empty public constructor
    }


    int lastPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        final FoodRecyclerViewAdapter adapter = new FoodRecyclerViewAdapter(getActivity());
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        foodViewModel = ViewModelProviders.of(HomeFragment.this).get(FoodViewModel.class);
        foodViewModel.getAllFood().observe(HomeFragment.this, new Observer<List<FoodRoom>>() {
            @Override
            public void onChanged(@Nullable List<FoodRoom> foods) {
                adapter.changeAt(foods);

                double totalAmount = 0;
                for (int i = 0; i < foods.size(); i++) {
                    if(foods.get(i).getCount() != 0) {
                        totalAmount += foods.get(i).getPrice();
                    }
                }
                setAmount(totalAmount);
            }
        });




        adapter.setOnItemClickListener(new FoodRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FoodRoom food, int position) {
                if (food.getCount() == 0) {
                    food.setCount(1);
                    foodViewModel.update(food);
                } else {
                    food.setCount(0);
                    foodViewModel.update(food);
                }
                lastPosition = position;
            }
        });

        amountTextView = v.findViewById(R.id.text_view_amount);

        amountTextView.setVisibility(View.GONE);
        fadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_animation);
        fadeOutAndroidAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out_animation);


        return v;
    }

    private void setAmount(double totalAmount) {
        DecimalFormat df = new DecimalFormat("#.##");
        if(totalAmount == 0){
            if(amountTextView.getVisibility() == View.VISIBLE)
                amountTextView.startAnimation(fadeOutAndroidAnimation);
            amountTextView.setVisibility(View.GONE);
        }else {
            if(amountTextView.getVisibility() == View.GONE)
                amountTextView.startAnimation(fadeInAnimation);
            amountTextView.setVisibility(View.VISIBLE);
            amountTextView.setText("Total amount: $" + df.format(totalAmount));
        }
    }

}

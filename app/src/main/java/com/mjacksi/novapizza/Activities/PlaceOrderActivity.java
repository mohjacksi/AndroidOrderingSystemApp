package com.mjacksi.novapizza.Activities;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mjacksi.novapizza.Fragments.BottomSheetDialog;
import com.mjacksi.novapizza.R;
import com.mjacksi.novapizza.RoomDatabase.FoodRoom;
import com.mjacksi.novapizza.RoomDatabase.FoodViewModel;

import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class PlaceOrderActivity extends AppCompatActivity {

    FoodViewModel foodViewModel;
    TextView totalOrderAmount;
    EditText addressEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        addressEditText = findViewById(R.id.addressEditText);
        addressEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                return false;
            }
        });
        totalOrderAmount = findViewById(R.id.total_order_amount);
        toolbarSetup();


        foodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        foodViewModel.getAllOrderedFood().observe(this, new Observer<List<FoodRoom>>() {
            @Override
            public void onChanged(@Nullable List<FoodRoom> foods) {
                if (foods.size() == 0) finish();
                double totalAmount = 0;
                for (int i = 0; i < foods.size(); i++) {
                    if (foods.get(i).getCount() != 0) {
                        totalAmount += (foods.get(i).getPrice() * foods.get(i).getCount());
                    }
                }
                setAmount(totalAmount);
            }
        });

    }

    private void setAmount(double totalAmount) {
        DecimalFormat df = new DecimalFormat("#.##");
        totalOrderAmount.setText("$" + df.format(totalAmount));
    }

    private void toolbarSetup() {
        Toolbar toolbar = findViewById(R.id.place_order_toolbar);
        toolbar.setTitle("Place order");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void orderNow(View view) {
        String address = addressEditText.getText().toString();
        if (!address.trim().isEmpty()) {
            String totalAmount = totalOrderAmount.getText().toString();
            BottomSheetDialog bottomSheet = new BottomSheetDialog();
            Bundle bundle = new Bundle();
            bundle.putString("totalAmount", totalAmount);
            bundle.putString("address", address);
            bottomSheet.setArguments(bundle);
            bottomSheet.show(getSupportFragmentManager(), "BottomSheetTag");
        } else {
            Toast.makeText(this, "Plase enter your address", Toast.LENGTH_LONG).show();
        }
    }
}

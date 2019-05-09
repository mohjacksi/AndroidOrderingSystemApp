package com.mjacksi.novapizza.Activities;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mjacksi.novapizza.Fragments.BottomSheetDialog;
import com.mjacksi.novapizza.Models.FirebaseOrder;
import com.mjacksi.novapizza.R;
import com.mjacksi.novapizza.RoomDatabase.FoodRoom;
import com.mjacksi.novapizza.RoomDatabase.FoodViewModel;
import com.mjacksi.novapizza.Utilises.InternetConnection;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceOrderActivity extends AppCompatActivity implements BottomSheetDialog.SheetListener {

    private static final String CLASS_TAG = PlaceOrderActivity.class.getSimpleName();
    FoodViewModel foodViewModel;
    TextView totalOrderAmount;
    EditText addressEditText;

    FirebaseUser currentUser;
    String userID;
    String userPhone;
    double totalAmount = 0;

    Map<String, Integer> orderPairs = new HashMap<>();

    /**
     * Get user information with order information and send the order to the server (Firebase readtime database)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = currentUser.getUid();
        userPhone = currentUser.getPhoneNumber();

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
                    FoodRoom currentFood = foods.get(i);
                    if (currentFood.getCount() != 0) {
                        totalAmount += (currentFood.getPrice() * currentFood.getCount());
                        orderPairs.put(currentFood.getTitle(), currentFood.getCount());
                    }
                }
                setAmount(totalAmount);
            }
        });

    }

    /**
     * Format the price amount from double to string
     *
     * @param totalAmount will return as "Total amount: $*.**"
     */
    private void setAmount(double totalAmount) {
        this.totalAmount = totalAmount;
        DecimalFormat df = new DecimalFormat("#.##");
        totalOrderAmount.setText("$" + df.format(totalAmount));
    }

    /**
     * Setup toolbar of the activity
     * to add back button in toolbar
     */
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

    /**
     * Make order button
     * @param view the button clicked
     */
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
            Toast.makeText(this, "Please enter your address", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Implemented method from "BottomSheetDialog.SheetListener" interface to handle confirm button order
     */
    @Override
    public void onButtonClicked() {
        if (InternetConnection.checkConnection(this)) {
            addOrderToFirebase();
            Toast.makeText(this, "Your order placed", Toast.LENGTH_SHORT).show();
            foodViewModel.resetAllOrders();
            finish();
        } else {
            Toast.makeText(this, "You need internet to complete order!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Add the order inside the "users" node and in "orders" node
     */
    private void addOrderToFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        String key = database.getReference().child("orders").push().getKey();

        DatabaseReference userRef = database.getReference("users/" + userID + "/orders/" + key);
        DatabaseReference orderRef = database.getReference("orders/" + key);

        FirebaseOrder order = getOrderObject(key);
        userRef.setValue(order);
        orderRef.setValue(order);
    }

    /**
     * Create order object to upload it to firebase
     * @param key the special key for each order
     * @return
     */
    private FirebaseOrder getOrderObject(String key) {
        return new FirebaseOrder(
                userID,
                key,
                userPhone,
                addressEditText.getText().toString(),
                "waiting",
                totalAmount,
                System.currentTimeMillis() / 1000L,
                orderPairs);

    }


}

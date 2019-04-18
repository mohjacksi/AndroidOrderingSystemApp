package com.mjacksi.novapizza.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mjacksi.novapizza.Adapters.FoodRecyclerViewAdapter;
import com.mjacksi.novapizza.Adapters.OrderRecyclerViewAdapter;
import com.mjacksi.novapizza.R;
import com.mjacksi.novapizza.RoomDatabase.FoodRoom;
import com.mjacksi.novapizza.RoomDatabase.FoodViewModel;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderActivity extends AppCompatActivity {
    private static final String TAG = OrderActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 333;
    FoodViewModel foodViewModel;
    private TextView amountTextView;
    FirebaseAuth firebaseAuth;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        firebaseAuth = FirebaseAuth.getInstance();
        toolbarSetup();

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


        fab = findViewById(R.id.fab_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                if (firebaseAuth.getCurrentUser() == null) {
                    signIn();
                } else {
                    placeOrderActivity();
                }
            }
        });

    }

    private void toolbarSetup() {
        Toolbar toolbar = findViewById(R.id.order_toolbar);
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
    }

    void placeOrderActivity() {
        Intent intent = new Intent(OrderActivity.this, PlaceOrderActivity.class);
        startActivity(intent);
    }

    void signIn() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build());
        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.AppTheme_NoActionBar)
                        .build(),
                RC_SIGN_IN);
    }


    private void setAmount(double totalAmount) {
        DecimalFormat df = new DecimalFormat("#.##");
        amountTextView.setText("Total amount: $" + df.format(totalAmount));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset_all_orders) {
            foodViewModel.resetAllOrders();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    Snackbar.make(getWindow().getDecorView().getRootView()
                            , "Failed login, retry again!", Snackbar.LENGTH_LONG)
                            .setAction("Failed", null).show();

                } else {
                    placeOrderActivity();
                }
                // ...
            } else {
                Snackbar.make(getWindow().getDecorView().getRootView()
                        , "Failed login, retry again!", Snackbar.LENGTH_LONG)
                        .setAction("Failed", null).show();
            }
        }
    }
}

package com.mjacksi.novapizza.RoomDatabase;

import android.content.Context;
import android.os.AsyncTask;

import com.mjacksi.novapizza.R;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {FoodRoom.class}, version = 1, exportSchema = false)
public abstract class FoodDatabase extends RoomDatabase {

    private static FoodDatabase instance;

    public abstract FoodDao foodDao();

    public static synchronized FoodDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FoodDatabase.class, "food_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private FoodDao foodDao;

        private PopulateDbAsyncTask(FoodDatabase db) {
            foodDao = db.foodDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            foodDao.insert(new FoodRoom("Neapolitan Pizza",
                    "Neapolitan is the original pizza. This delicious pie dates all the way back to 18th century in Naples, Italy.",
                    4.99, R.drawable.neapolitanpizza));

            foodDao.insert(new FoodRoom("Chicago Pizza",
                    "Chicago pizza, also commonly referred to as deep-dish pizza, gets its name from the city it was invented in.",
                    5.59, R.drawable.chicagopizza));

            foodDao.insert(new FoodRoom("New York-Style Pizza",
                    "With its characteristic large, foldable slices and crispy outer crust, New York-style pizza is one of America’s most famous regional pizza types.",
                    5.00, R.drawable.newyorkstylepizza));

            foodDao.insert(new FoodRoom("Sicilian Pizza",
                    "Sicilian pizza, also known as \"sfincione,\" provides a thick cut of pizza with pillowy dough, a crunchy crust, and robust tomato sauce.",
                    4.59, R.drawable.sicilianpizza));

            foodDao.insert(new FoodRoom("Greek Pizza",
                    "Greek pizza was created by Greek immigrants who came to America and were introduced to Italian pizza.",
                    7.99, R.drawable.greekpizza));

            foodDao.insert(new FoodRoom("California Pizza",
                    "California pizza, or gourmet pizza, is known for its unusual ingredients. ",
                    5.79, R.drawable.californiapizza));

            return null;
        }
    }
}

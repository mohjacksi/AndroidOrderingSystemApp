package com.mjacksi.novapizza.RoomDatabase;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class FoodRepository {
    
    private FoodDao foodDao;
    private LiveData<List<FoodRoom>> allFood;
    private LiveData<List<FoodRoom>> allOrderedFood;

    private LiveData<Integer> count;
    public FoodRepository(Application application) {
        FoodDatabase database = FoodDatabase.getInstance(application);
        foodDao = database.foodDao();
        allFood = foodDao.getAllFoods();
        allOrderedFood = foodDao.getAllOrderedFoods();
        count = foodDao.getCount();
    }

    public void insert(FoodRoom food) {
        new InsertFoodAsyncTask(foodDao).execute(food);
    }

    public void update(FoodRoom food) {
        new UpdateFoodAsyncTask(foodDao).execute(food);
    }

    public void delete(FoodRoom food) {
        new DeleteFoodAsyncTask(foodDao).execute(food);
    }

    public void deleteAllFoodRooms() {
        new DeleteAllFoodsAsyncTask(foodDao).execute();
    }

    public void resetAllOrders() {
        new ResetAllFoodsAsyncTask(foodDao).execute();
    }

    public LiveData<List<FoodRoom>> getAllFood() {
        return allFood;
    }

    public LiveData<List<FoodRoom>> getAllOrderedFood() {
        return allOrderedFood;
    }

    public LiveData<Integer> getCount() {
        return count;
    }

    private static class InsertFoodAsyncTask extends AsyncTask<FoodRoom, Void, Void> {
        private FoodDao foodDao;

        private InsertFoodAsyncTask(FoodDao foodDao) {
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(FoodRoom... foods) {
            foodDao.insert(foods[0]);
            return null;
        }
    }

    private static class UpdateFoodAsyncTask extends AsyncTask<FoodRoom, Void, Void> {
        private FoodDao foodDao;

        private UpdateFoodAsyncTask(FoodDao foodDao) {
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(FoodRoom... foods) {
            foodDao.update(foods[0]);
            return null;
        }
    }

    private static class DeleteFoodAsyncTask extends AsyncTask<FoodRoom, Void, Void> {
        private FoodDao foodDao;

        private DeleteFoodAsyncTask(FoodDao foodDao) {
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(FoodRoom... foods) {
            foodDao.delete(foods[0]);
            return null;
        }
    }

    private static class DeleteAllFoodsAsyncTask extends AsyncTask<Void, Void, Void> {
        private FoodDao foodDao;

        private DeleteAllFoodsAsyncTask(FoodDao foodDao) {
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            foodDao.deleteAllFoods();
            return null;
        }
    }


    private static class ResetAllFoodsAsyncTask extends AsyncTask<Void, Void, Void> {
        private FoodDao foodDao;

        private ResetAllFoodsAsyncTask(FoodDao foodDao) {
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            foodDao.resetAllOrders();
            return null;
        }
    }




    
}

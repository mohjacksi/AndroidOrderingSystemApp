package com.mjacksi.novapizza.RoomDatabase;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import androidx.annotation.NonNull;

public class FoodViewModel extends AndroidViewModel {
    private FoodRepository repository;
    private LiveData<List<FoodRoom>> allFood;

    private LiveData<List<FoodRoom>> allOrderedFood;
    private LiveData<Integer> count;

    public FoodViewModel(@NonNull Application application) {
        super(application);
        repository = new FoodRepository(application);
        allFood = repository.getAllFood();

        allOrderedFood = repository.getAllOrderedFood();
        count = repository.getCount();
    }

    public void insert(FoodRoom food) {
        repository.insert((food));
    }

    public void update(FoodRoom food) {
        repository.update((food));
    }

    public void delete(FoodRoom food) {
        repository.delete((food));
    }

    public void deleteAllFoods() {
        repository.deleteAllFoodRooms();
    }

    public void resetAllOrders(){
        repository.resetAllOrders();
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
}


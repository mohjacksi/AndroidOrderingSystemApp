package com.mjacksi.novapizza.Util;

import com.mjacksi.novapizza.RoomDatabase.FoodRoom;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

public class MyDiffCallback extends DiffUtil.Callback{

    List<FoodRoom> oldFoods;
    List<FoodRoom> newFoods;

    public MyDiffCallback(List<FoodRoom> newPersons, List<FoodRoom> oldPersons) {
        this.newFoods = newPersons;
        this.oldFoods = oldPersons;
    }

    @Override
    public int getOldListSize() {
        return oldFoods.size();
    }

    @Override
    public int getNewListSize() {
        return newFoods.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldFoods.get(oldItemPosition).getId() == newFoods.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldFoods.get(oldItemPosition).getCount()== newFoods.get(newItemPosition).getCount();
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }

}

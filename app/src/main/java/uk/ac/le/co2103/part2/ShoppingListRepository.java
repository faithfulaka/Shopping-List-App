package uk.ac.le.co2103.part2;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;


public class ShoppingListRepository {
    private ShoppingListDao shoppingListDao;
    private LiveData<List<ShoppingList>> allShoppingLists;

    ShoppingListRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        shoppingListDao = db.shoppingListDao();
        allShoppingLists = shoppingListDao.getAll();
    }

    LiveData<List<ShoppingList>> getAllShoppingLists() {
        return allShoppingLists;
    }

    void insert(ShoppingList shoppingList) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            shoppingListDao.insert(shoppingList);
        });
    }

    void delete(ShoppingList shoppingList) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            shoppingListDao.delete(shoppingList);
        });
    }

}

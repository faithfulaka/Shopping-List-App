package uk.ac.le.co2103.part2;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ShoppingList.class, Product.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract ShoppingListDao shoppingListDao();
    public abstract ProductDao productDao();

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "shopping_db").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                ShoppingListDao shoppingListDao = INSTANCE.shoppingListDao();
                ProductDao productDao = INSTANCE.productDao();
                shoppingListDao.deleteAll();
                productDao.deleteAll();
            });
        }
    };

    public void emptyDatabase() {
        databaseWriteExecutor.execute(() -> {
            ShoppingListDao shoppingListDao = INSTANCE.shoppingListDao();
            ProductDao productDao = INSTANCE.productDao();
            shoppingListDao.deleteAll();
            productDao.deleteAll();
        });
    }
}

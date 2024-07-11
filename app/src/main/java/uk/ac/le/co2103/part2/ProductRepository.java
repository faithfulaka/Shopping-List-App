package uk.ac.le.co2103.part2;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;


public class ProductRepository {
    private ProductDao productDao;
    private LiveData<List<Product>> allProducts;

    ProductRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        productDao = db.productDao();
        allProducts = productDao.getAll();
    }

    LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    LiveData<List<Product>> getProductsByShoppingListId(int shoppingListId) {
        return productDao.getProductsByShoppingListId(shoppingListId);
    }

    void insert(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            productDao.insert(product);
        });
    }

    Boolean productExistsInList(String name, int shoppingListId) {
        return productDao.productExistsInList(name, shoppingListId);
    }

    Boolean productExistsInListAndIdSame(String name, int shoppingListId, int productId) {
        return productDao.productExistsInListAndIdSame(name, shoppingListId, productId);
    }

    void delete(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            productDao.delete(product);
        });
    }

    void updateProductGivenId(int productId, String newName, String newQuantity, String newUnit) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            productDao.updateProductGivenId(productId, newName, newQuantity, newUnit);
        });
    }

    void update(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            productDao.update(product);
        });
    }
}

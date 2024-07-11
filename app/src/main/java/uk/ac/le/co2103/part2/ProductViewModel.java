package uk.ac.le.co2103.part2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class ProductViewModel extends AndroidViewModel {
    private ProductRepository repo;
    private final LiveData<List<Product>> allProducts;

    public ProductViewModel(Application application) {
        super(application);
        repo = new ProductRepository(application);
        allProducts = repo.getAllProducts();
    }

    LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    LiveData<List<Product>> getProductsByShoppingListId(int shoppingListId) {
        return repo.getProductsByShoppingListId(shoppingListId);
    }

    public void insert(Product product) {
        repo.insert(product);
    }

    Boolean productExistsInList(String name, int shoppingListId) {
        return repo.productExistsInList(name, shoppingListId);
    }

    Boolean productExistsInListAndIdSame(String name, int shoppingListId, int productId) {
        return repo.productExistsInListAndIdSame(name, shoppingListId, productId);
    }

    public void delete(Product product) {
        repo.delete(product);
    }

    void updateProductGivenId(int productId, String newName, String newQuantity, String newUnit) {
        repo.updateProductGivenId(productId, newName, newQuantity, newUnit);
    }

    void update(Product product) {
        repo.update(product);
    }
}

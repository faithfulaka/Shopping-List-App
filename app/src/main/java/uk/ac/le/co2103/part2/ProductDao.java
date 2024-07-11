package uk.ac.le.co2103.part2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {


    @Insert
    void insert(Product product);


    @Query("SELECT * FROM products ORDER BY name ASC")
    LiveData<List<Product>> getAll();

    @Query("DELETE FROM products")
    void deleteAll();


    @Query("SELECT * FROM products WHERE shoppingListId = :shoppingListId")
    LiveData<List<Product>> getProductsByShoppingListId(int shoppingListId);


    @Query("SELECT EXISTS(SELECT * FROM products WHERE name = :name AND shoppingListId =:shoppingListId)")
    Boolean productExistsInList(String name, int shoppingListId);


    @Query("SELECT EXISTS(SELECT * FROM products WHERE name = :name AND shoppingListId =:shoppingListId AND productId =:productId)")
    Boolean productExistsInListAndIdSame(String name, int shoppingListId, int productId);


    @Delete
    void delete(Product product);

    @Query("UPDATE products SET name =:newName, quantity =:newQuantity, unit =:newUnit WHERE productId =:productId")
    void updateProductGivenId(int productId, String newName, String newQuantity, String newUnit);


    @Update
    void update(Product product);
}

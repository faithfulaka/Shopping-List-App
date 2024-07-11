package uk.ac.le.co2103.part2;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "products",
        foreignKeys = @ForeignKey(entity = ShoppingList.class, parentColumns = "listId", childColumns = "shoppingListId", onDelete = CASCADE),
        indices = {@Index(value = "shoppingListId")}) // Adding an index for shoppingListId
public class Product {
    @PrimaryKey(autoGenerate = true)
    public int productId;
    @NonNull
    @ColumnInfo(name = "name")
    public String name;
    @NonNull
    @ColumnInfo(name = "quantity")
    public String quantity;
    @NonNull
    @ColumnInfo(name = "unit")
    public String unit;
    public int shoppingListId;

    public Product(@NonNull String name, @NonNull String quantity, @NonNull String unit, int shoppingListId) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.shoppingListId = shoppingListId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @NonNull
    public String getUnit() {
        return unit;
    }

    public void setUnit(@NonNull String unit) {
        this.unit = unit;
    }

    public int getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(int shoppingListId) {
        this.shoppingListId = shoppingListId;
    }
}

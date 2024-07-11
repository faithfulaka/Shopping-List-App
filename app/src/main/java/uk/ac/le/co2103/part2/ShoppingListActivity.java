package uk.ac.le.co2103.part2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ShoppingListActivity extends AppCompatActivity implements SelectProductListener {
    private ProductViewModel productViewModel;
    static final int NEW_PRODUCT_CODE = 3;
    static final int UPDATE_PRODUCT_CODE = 5;

    // The id of the shopping list being displayed, passed via intent extra
    private int shoppingListId;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseReadExecuter = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerview2);
        final SecondListAdapter adapter = new SecondListAdapter(new SecondListAdapter.ProductDiff(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        Intent intent = getIntent();
        shoppingListId = intent.getIntExtra("shoppingListId", -1);
        productViewModel.getProductsByShoppingListId(shoppingListId).observe(this, items -> {
            adapter.submitList(items);
        });
    }


    public void openAddProductActivity(View view) {
        Intent intent = new Intent(this, AddProductActivity.class);
        startActivityForResult(intent, NEW_PRODUCT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Return from AddProductActivity
        if (requestCode == NEW_PRODUCT_CODE && resultCode == RESULT_OK) {
            String productName = data.getStringExtra("productName");
            String productQuantity = data.getStringExtra("productQuantity");
            String productUnit = data.getStringExtra("productUnit");

            // To access non-live data, must be run on seperate thread to UI
            // Check if product with same name exists in list
            databaseReadExecuter.execute(() -> {
                boolean exists = productViewModel.productExistsInList(productName, shoppingListId);
                if (!exists) {
                    // Add new product
                    Product product = new Product(productName, productQuantity, productUnit, shoppingListId);
                    productViewModel.insert(product);
                } else {
                    // Display toast by switching to UI thread
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Product already exists", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }

        // Return from UpdateProductActivity
        if (requestCode == UPDATE_PRODUCT_CODE && resultCode == RESULT_OK) {
            int productId = data.getIntExtra("productId", -1);
            String productName = data.getStringExtra("productName");
            String productQuantity = data.getStringExtra("productQuantity");
            String productUnit = data.getStringExtra("productUnit");

            // To access non-live data, must be run on seperate thread to UI
            // Check if product with same name exists in list, ignoring the current one
            databaseReadExecuter.execute(() -> {
                boolean existsInList = productViewModel.productExistsInList(productName, shoppingListId);
                boolean existsInListWithSameId = productViewModel.productExistsInListAndIdSame(productName, shoppingListId, productId);
                if (!existsInList || existsInListWithSameId) {
                    // Update product if product name not present in list, or if name was not changed - same id
                    productViewModel.updateProductGivenId(productId, productName, productQuantity, productUnit);
                } else {
                    // Display toast by switching to UI thread if product with same name exists already
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Product with same name exists", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }

    }


    @Override
    public void onProductClicked(Product product) {
        // Display a toast containing products details
        Toast.makeText(this, product.getName() + "\nQuantity: " + product.getQuantity() + ", Unit: " + product.getUnit(), Toast.LENGTH_SHORT).show();

        // Create an alert box to be displayed
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(product.getName());
        builder.setMessage("Select an option")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // If delete is clicked, delete product from database and display toast confirmation
                        productViewModel.delete(product);
                        Toast.makeText(ShoppingListActivity.this, "Product deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // If edit is clicked, start UpdateProductActivity
                        // Add extras to intent containing product id, name, quantity, and unit
                        Intent intent = new Intent(ShoppingListActivity.this, UpdateProductActivity.class);
                        intent.putExtra("productId", product.getProductId());
                        intent.putExtra("productName", product.getName());
                        intent.putExtra("productQuantity", product.getQuantity());
                        intent.putExtra("productUnit", product.getUnit());
                        startActivityForResult(intent, UPDATE_PRODUCT_CODE);
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // If cancel is clicked, dismiss
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
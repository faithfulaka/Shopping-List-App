package uk.ac.le.co2103.part2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * AddProductActivity -> activity_add_product.xml
 * Create and add a new product to a shopping list
 */
public class AddProductActivity extends AppCompatActivity {

    private EditText productName_et;
    private EditText productQuantity_et;
    private Spinner productUnit_spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        productName_et = findViewById(R.id.editTextName);
        productQuantity_et = findViewById(R.id.editTextQuantity);
        productUnit_spinner = findViewById(R.id.spinner);
    }

    public void returnAddProduct(View view) {
        Intent replyIntent = new Intent(this, ShoppingListActivity.class);
        // Get data from form views
        String productName = productName_et.getText().toString();
        String productQuantity = productQuantity_et.getText().toString();
        String productUnit = productUnit_spinner.getSelectedItem().toString();

        // Validate productName and productQuantity is not empty, if it is empty display toast
        if (productName.length() <= 0) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
        }
        else if (productQuantity.length() <= 0) {
            Toast.makeText(this, "Please enter a quantity", Toast.LENGTH_SHORT).show();
        }
        else {
            // Send replyIntent with product details extras
            replyIntent.putExtra("productName", productName);
            replyIntent.putExtra("productQuantity", productQuantity);
            replyIntent.putExtra("productUnit", productUnit);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }
}
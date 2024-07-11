package uk.ac.le.co2103.part2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class UpdateProductActivity extends AppCompatActivity {

    private TextView editNameEt;
    private TextView editQuantityTv;
    private Spinner editUnitSpinner;
    private Button plusButton;
    private Button minusButton;

    // The id of the product being updated, passed via intent extra
    private int productId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        Intent intent = getIntent();

        // Finding views
        editNameEt = findViewById(R.id.editTextName_edit);
        editQuantityTv = findViewById(R.id.textViewQuantity_edit);
        editUnitSpinner = findViewById(R.id.spinner_edit);
        plusButton = findViewById(R.id.plusButton);
        minusButton = findViewById(R.id.minusButton);

        // Getting product information extras from intent
        productId = intent.getIntExtra("productId", -1);
        String currentName = intent.getStringExtra("productName");
        String currentQuantity = intent.getStringExtra("productQuantity");
        String currentUnit = intent.getStringExtra("productUnit");

        // Setting product information to pre-populate form fields
        editNameEt.setText(currentName);
        editQuantityTv.setText(currentQuantity);
        // Finding index of unit to display on spinner
        int spinnerIndex;
        switch (currentUnit) {
            case "Kg":
                spinnerIndex = 1;
                break;
            case "Liter":
                spinnerIndex = 2;
                break;
            default:
                spinnerIndex = 0;
        }
        editUnitSpinner.setSelection(spinnerIndex);

        // Set onClick listener for plus and minus buttons, and update the quantity text view
        plusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Convert to int, add 1, convert back to string to update text view
                int currentValue = Integer.parseInt(editQuantityTv.getText().toString());
                int newValue = currentValue + 1;
                editQuantityTv.setText(String.valueOf(newValue));
            }
        });
        minusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Convert to int, minus 1, convert back to string to update text view
                int currentValue = Integer.parseInt(editQuantityTv.getText().toString());
                int newValue = currentValue - 1;
                // Check if newValue is positive, ensures quantity cannot be a negative number
                if (newValue >= 0) {
                    editQuantityTv.setText(String.valueOf(newValue));
                }
            }
        });
    }


    public void returnUpdateProduct(View view) {
        // Get the string values of the form fields
        Intent replyIntent = new Intent(this, ShoppingListActivity.class);
        String productName = editNameEt.getText().toString();
        String productQuantity = editQuantityTv.getText().toString();
        String productUnit = editUnitSpinner.getSelectedItem().toString();

        // Check the name field is not empty, if it is empty display a toast
        if (productName.length() <= 0) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
        } else {
            // Add updated product information to intent extras
            replyIntent.putExtra("productId", productId);
            replyIntent.putExtra("productName", productName);
            replyIntent.putExtra("productQuantity", productQuantity);
            replyIntent.putExtra("productUnit", productUnit);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }

}
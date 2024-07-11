package uk.ac.le.co2103.part2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements SelectShoppingListListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    static final int NEW_LIST_CODE = 1;
    private ShoppingListViewModel shoppingListViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_main);

        // Set adapter to recycler view and assign all shopping lists to display via adapter
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final MainListAdapter adapter = new MainListAdapter(new MainListAdapter.ShoppingListDiff(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shoppingListViewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
        shoppingListViewModel.getAllShoppingLists().observe(this, items -> {
            adapter.submitList(items);
        });

        // OnClick listener for fab - when clicked call CreateListActivity
        final FloatingActionButton button = findViewById(R.id.fab);
        button.setOnClickListener(view -> {
            Log.d(TAG, "Floating action button clicked.");
            openCreateListActivity(view);
        });
    }

    public void openCreateListActivity(View view) {
        Intent intent = new Intent(this, CreateListActivity.class);
        startActivityForResult(intent, NEW_LIST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_LIST_CODE && resultCode == RESULT_OK) {
            // Get the new list name and image bit map
            String listNameReply = data.getStringExtra("shoppingListName");
            Uri listImageUri = data.getParcelableExtra("shoppingListImageUri");

            if (listImageUri == null) {
                // Create the new shopping list with no image
                ShoppingList shoppingList = new ShoppingList(listNameReply, null);
                // Insert the new shopping list into the database via viewModel
                shoppingListViewModel.insert(shoppingList);
            } else {
                // Convert image to bitmap
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), listImageUri);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                // Compresses image bitmap
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                // Convert bitmap to byte array
                byte[] listImageByteArray = bs.toByteArray();
                // Create the new shopping list with the given details
                ShoppingList shoppingList = new ShoppingList(listNameReply, listImageByteArray);
                // Insert the new shopping list into the database via viewModel
                shoppingListViewModel.insert(shoppingList);
            }
        }
    }

    @Override
    public void onListClicked(ShoppingList shoppingList) {
        Intent intent = new Intent(this, ShoppingListActivity.class);
        intent.putExtra("shoppingListId", shoppingList.getListId());
        startActivity(intent);
    }


    @Override
    public void onLongClickList(ShoppingList shoppingList) {
        // Create an alert box to be displayed
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + shoppingList.getName() + "?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // If delete is clicked, delete list from database and display toast confirmation
                        shoppingListViewModel.delete(shoppingList);
                        Toast.makeText(MainActivity.this, "Shopping list deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // If cancel is clicked, dismiss
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
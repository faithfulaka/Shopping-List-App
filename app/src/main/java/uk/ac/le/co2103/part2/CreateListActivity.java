package uk.ac.le.co2103.part2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class CreateListActivity extends AppCompatActivity {
    private static final String TAG = CreateListActivity.class.getSimpleName();
    private EditText replyEditText;
    private ImageView replyImageView;
    private Button selectImageButton;
    static final int SELECT_IMAGE_CODE = 2;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        // Get data from form views
        replyEditText = findViewById(R.id.createListName_et);
        replyImageView = findViewById(R.id.selectListImage_iv);
        selectImageButton = findViewById(R.id.selectListImage_button);
        // Set global URI variable is null and make image view empty
        imageUri = null;
        replyImageView.setImageURI(null);

        // OnClick listener for selectImageButton
        selectImageButton.setOnClickListener(view -> {
            Log.d(TAG, "Select Image button clicked");
            // New intent to select image from gallary
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_IMAGE_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE_CODE && resultCode == RESULT_OK && data != null) {
            // Get URI of selected image
            Uri uri = data.getData();
            // Update imageView and URI variable with selected image
            imageUri = uri;
            replyImageView.setImageURI(imageUri);
        }
    }

    public void returnReply(View view) {
        Intent replyIntent = new Intent(this, MainActivity.class);
        String listName = replyEditText.getText().toString();
        // Validate list name is not empty
        if (listName.length() > 0) {
            // Send listName as extra
            replyIntent.putExtra("shoppingListName", listName);
            // Get the URI of the current image and send as extra
            replyIntent.putExtra("shoppingListImageUri", imageUri);
            setResult(RESULT_OK, replyIntent);
            finish();
        } else {
            // If list name is empty display toast
            Toast.makeText(this, "Please enter a list name", Toast.LENGTH_SHORT).show();
        }
    }

}
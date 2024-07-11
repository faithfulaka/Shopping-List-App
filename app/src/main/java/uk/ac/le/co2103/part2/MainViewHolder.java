package uk.ac.le.co2103.part2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MainViewHolder extends RecyclerView.ViewHolder {
    private final TextView shoppingListTextView;
    private ImageView shoppingListImageView;

    private MainViewHolder(View itemView) {
        super(itemView);
        shoppingListTextView = itemView.findViewById(R.id.listName_tv);
        shoppingListImageView = itemView.findViewById(R.id.listImage_iv);
    }

    public void bind(String text, byte[] imageByteArray) {
        // Set text of recycler view item
        shoppingListTextView.setText(text);
        if (imageByteArray == null) {
            // If no image for shopping list, hide image view
            shoppingListImageView.setVisibility(View.GONE);
        } else {
            // If the shopping list has an image
            // Convert image from byte array to bit map
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            // Set image of recycler view item if it exists
            shoppingListImageView.setImageBitmap(imageBitmap);
            // Visibility is 'GONE' by default for lists that do not have an image
            shoppingListImageView.setVisibility(View.VISIBLE);
        }
    }

    static MainViewHolder create(ViewGroup parent) {
        // Inflate recyclerview_item layout for MainActivity recycler view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new MainViewHolder(view);
    }

}

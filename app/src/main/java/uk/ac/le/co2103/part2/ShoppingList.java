package uk.ac.le.co2103.part2;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shoppingLists")
public class ShoppingList {
    @PrimaryKey(autoGenerate = true)
    public int listId;
    @NonNull
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "image")
    public byte[] image;


    public ShoppingList(@NonNull String name, byte[] image) {
        this.name = name;
        this.image = image;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

package uk.ac.le.co2103.part2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class SecondViewHolder extends RecyclerView.ViewHolder {
    private TextView productNameTv;
    private TextView productQuantityTv;
    private TextView productUnitTv;

    private SecondViewHolder(View itemView) {
        super(itemView);
        productNameTv = itemView.findViewById(R.id.itemName_tv);
        productQuantityTv = itemView.findViewById(R.id.itemQuantity_tv);
        productUnitTv = itemView.findViewById(R.id.itemUnit_tv);
    }

    public void bind(String name, String quantity, String unit) {
        // Set the text views for recycler view item displaying each products details
        productNameTv.setText(name);
        productQuantityTv.setText(quantity);
        productUnitTv.setText(unit);
    }

    static SecondViewHolder create(ViewGroup parent) {
        // Inflate list_recyclerview_item layout for ShoppingListActivity recycler view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_recyclerview_item, parent, false);
        return new SecondViewHolder(view);
    }

}

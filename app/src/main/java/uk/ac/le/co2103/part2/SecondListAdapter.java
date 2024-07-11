package uk.ac.le.co2103.part2;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class SecondListAdapter extends ListAdapter<Product, SecondViewHolder> {

    private SelectProductListener listener;

    public SecondListAdapter(@NonNull DiffUtil.ItemCallback<Product> diffCallback, SelectProductListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @Override
    public SecondViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return SecondViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(SecondViewHolder holder, int position) {
        // Get the name, quantity and unit of the product to display
        Product current = getItem(position);
        holder.bind(current.getName(), current.getQuantity(), current.getUnit());

        // Set onClick listener for each item in recycler view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When clicked call ShoppingListActivity onProductClicked
                listener.onProductClicked(current);
            }
        });
    }

    static class ProductDiff extends DiffUtil.ItemCallback<Product> {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }

}

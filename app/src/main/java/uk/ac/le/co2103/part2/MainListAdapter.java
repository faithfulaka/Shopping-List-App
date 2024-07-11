package uk.ac.le.co2103.part2;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class MainListAdapter extends ListAdapter<ShoppingList, MainViewHolder> {
    private SelectShoppingListListener listener;

    public MainListAdapter(@NonNull DiffUtil.ItemCallback<ShoppingList> diffCallback, SelectShoppingListListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return MainViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        // Get the name and image of shopping lists to display
        ShoppingList current = getItem(position);
        holder.bind(current.getName(), current.getImage());

        // Set onClick listener for each item in recycler view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When clicked call MainActivity onListClicked
                listener.onListClicked(current);
            }
        });

        // Set onClick listener for each item in recycler view
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // When long clicked call MainActivity onLongClickList
                listener.onLongClickList(current);
                return true;
            }
        });
    }

    static class ShoppingListDiff extends DiffUtil.ItemCallback<ShoppingList> {
        @Override
        public boolean areItemsTheSame(@NonNull ShoppingList oldItem, @NonNull ShoppingList newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ShoppingList oldItem, @NonNull ShoppingList newItem) {
            return oldItem.getListId() == newItem.getListId();
        }
    }

}

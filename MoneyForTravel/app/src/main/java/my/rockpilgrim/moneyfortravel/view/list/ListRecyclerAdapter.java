package my.rockpilgrim.moneyfortravel.view.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import my.rockpilgrim.moneyfortravel.R;
import my.rockpilgrim.moneyfortravel.presenter.list.ListPresenter;

public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerHolder> implements IAdapterToSwipeController {

    private ListPresenter presenter;

    public ListRecyclerAdapter(ListPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ListRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_count_layout, parent, false);
        return new ListRecyclerHolder(root, presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRecyclerHolder countHolder, int position) {
        countHolder.bind(position
                , presenter.getCount(position)
                , presenter.getTag(position)
                , presenter.getDescription(position));
    }

    @Override
    //// SwipeController ////
    public void deleteView(int position){
        presenter.deleteTransaction(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return presenter.getSize();
    }


    public void updateList() {
        notifyDataSetChanged();
    }
}

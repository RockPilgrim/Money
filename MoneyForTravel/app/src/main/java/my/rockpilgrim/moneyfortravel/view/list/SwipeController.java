package my.rockpilgrim.moneyfortravel.view.list;

import android.graphics.Canvas;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeController extends ItemTouchHelper.Callback {


    public static final String TAG = "SwipeController";
    private boolean swipeBack = false;
    private IAdapterToSwipeController adapter;

    public SwipeController(IAdapterToSwipeController adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (direction == ItemTouchHelper.LEFT) {
            adapter.deleteView(viewHolder.getAdapterPosition());
            Log.i(TAG, "onSwiped: left");
        } else if (direction == ItemTouchHelper.RIGHT) {
            Log.i(TAG, "onSwiped: right");
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        Log.i(TAG, "clearView");
    }


    @Override
    public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
        Log.i(TAG, "onMoved: " + x + " from: " + fromPos + " to " + toPos);
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        Log.i(TAG, "convertToAbsoluteDirection " + flags + " / " + layoutDirection);
        if (swipeBack) {
            swipeBack = false;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (dX >= c.getWidth()*0.3) {
            dX = (float) (c.getWidth()*0.3);
            swipeBack = true;
        }
        if (dX < 0) {
//            swipeBack = true;
        }
//        Log.i(TAG, "onChildDraw x: " + dX);
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}

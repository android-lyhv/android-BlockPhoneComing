package halo.com.limitedphone_receviesms_contentporvider.recycleverview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by HoVanLy on 6/14/2016.
 */
public class ClickItemRecyclerView implements RecyclerView.OnItemTouchListener {
    private GestureDetector gestureDetector;
    private IClickItem iClickItem;

    public ClickItemRecyclerView(final Context context, final RecyclerView recyclerView, final IClickItem iClickItem) {
        this.iClickItem = iClickItem;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View viewChild = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (viewChild != null && iClickItem != null) {
                    iClickItem.onLongClick(viewChild, recyclerView.getChildPosition(viewChild));
                }
            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View viewChild = rv.findChildViewUnder(e.getX(), e.getY());
        if (viewChild != null && iClickItem != null && gestureDetector.onTouchEvent(e)) {
            iClickItem.onClick(viewChild, rv.getChildPosition(viewChild));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}

package benny.dev.flickerbrowser;

import android.content.Context;
import android.icu.util.ValueIterator;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener{
    private static final String TAG = "RecyclerItemClickListen";

    // All the approaches to implement a recycler click listener uses callback
    interface OnRecyclerClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private final OnRecyclerClickListener mListener;
    private final GestureDetectorCompat mGestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnRecyclerClickListener listener) {
        mListener = listener;
        // create an anonymous class that extends SimpleOnGestureListener so we can override the methods we are interested in, which are the normal tap and long tap
        mGestureDetector= new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "onSingleTapUp: starts");
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY()); // gets the coordinate of the view under the MotionEvent e.
                if(childView != null && mListener != null){
                    Log.d(TAG, "onSingleTapUp: calling listner.onItemClick()");
                    mListener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress: starts");
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(childView != null && mListener != null){
                    Log.d(TAG, "onLongPress: calling listner.onItemLongClick()");
                    mListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: start");
        // theory here is that anything the gesture detector onTouchEvent handles should return true, else false
        if(mGestureDetector != null){
            boolean result = mGestureDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent: returned -> " + result);
            return result;
        } else {
            Log.d(TAG, "onInterceptTouchEvent: returned false");
            return false;
        }
    }
}

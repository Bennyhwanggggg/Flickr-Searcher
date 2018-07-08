package benny.dev.flickerbrowser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickerImageViewHolder> {
    private static final String TAG = "FlickrRecyclerViewAdapt";
    private List<Photo> mPhotoList;
    private Context mContext;

    public FlickrRecyclerViewAdapter(List<Photo> photoList, Context context) {
        mPhotoList = photoList;
        mContext = context;
    }

    @NonNull
    @Override
    public FlickerImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // this function is called by layout manager when it needs a new view
        Log.d(TAG, "onCreateViewHolder: New View requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false); // parent is passed to get the parent layout. False tells inflator to not attach the inflated root to the parent as recycle view will do it.
        return new FlickerImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlickerImageViewHolder holder, int position) {

        if(mPhotoList == null || mPhotoList.size() == 0){
            holder.thumbnail.setImageResource(R.drawable.placeholder);
            holder.title.setText(R.string.empty_photo);
        } else {
            // Called by layout manager when it wants new data in an existing row.
            Photo photoItem = mPhotoList.get(position);
            Log.d(TAG, "onBindViewHolder: " + photoItem.getTitle() + " -> " + position);
            // load gets the image from an url, so we get the thumbnail url here then chain the methods to use placeholder image (which we downloaded) if there is an error. into put it into the image view. Picasso automatically does the background download.
            Picasso.get().load(photoItem.getImage()).error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(holder.thumbnail);
            holder.title.setText(photoItem.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return ((mPhotoList != null) && (mPhotoList.size() != 0) ? mPhotoList.size() : 1);
    }

    // when query changes, we need to provide a new list
    void loadNewData(List<Photo> newPhotos){
        mPhotoList = newPhotos;
        notifyDataSetChanged(); // tells any registered observers that data has changed. Here the registered observer is the recycle view.
    }

    public Photo getPhoto(int position){
        return ((mPhotoList != null) && (mPhotoList.size() != 0) ? mPhotoList.get(position) : null);
    }

    // static class defined in this file is no different than a class defined in another file
    // difference between this and the normal way is the way refer to it hence we extend it from this same class as seen above.
    static class FlickerImageViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "FlickerImageViewHolder";
        ImageView thumbnail = null;
        TextView title = null;

        public FlickerImageViewHolder(View itemView){
            super(itemView);
            Log.d(TAG, "FlickerImageViewHolder: starts");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}

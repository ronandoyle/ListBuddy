package nanorstudios.ie.listbuddy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Firebase;

/**
 * A Fragment to maintain the lists.
 *
 * @author Ronan Doyle
 */
public class ListFragment extends Fragment {

    private Firebase mRootRef;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootRef = new Firebase("https://list-buddy.firebaseio.com/");
    }

    public Firebase getRootRef() {
        return mRootRef;
    }

    public void addItem(String listItem) {
    }

    public static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public ListItemViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(android.R.id.text1);
        }
    }
}

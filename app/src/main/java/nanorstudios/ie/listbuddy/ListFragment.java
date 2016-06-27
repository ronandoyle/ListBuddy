package nanorstudios.ie.listbuddy;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    public void addItem(Item item) {
    }

    public static class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout mRelativeLayoutContainer;
        TextView mTextViewTitle;
        CheckBox mCheckBoxCompleted;

        public ListItemViewHolder(View view) {
            super(view);
            mRelativeLayoutContainer = (RelativeLayout) view.findViewById(R.id.list_item_container);
            mTextViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
            mCheckBoxCompleted = (CheckBox) view.findViewById(R.id.checkBoxCompleted);
        }

        @Override
        public void onClick(View v) {
            if (v == mRelativeLayoutContainer) {
                Toast.makeText(mRelativeLayoutContainer.getContext(), "I'm to be opened up in a new screen", Toast.LENGTH_SHORT).show();
            } else if (v == mCheckBoxCompleted) {
                Toast.makeText(mRelativeLayoutContainer.getContext(), "Strike me through and complete me", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

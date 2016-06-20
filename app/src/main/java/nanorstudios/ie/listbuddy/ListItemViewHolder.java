package nanorstudios.ie.listbuddy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Manages the view for the basic list item.
 */
class ListItemViewHolder extends RecyclerView.ViewHolder {
    final TextView mTextView;

    public ListItemViewHolder(View view) {
        super(view);
        mTextView = (TextView) view.findViewById(android.R.id.text1);
    }
}

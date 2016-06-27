package nanorstudios.ie.listbuddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;

/**
 * A Fragment to maintain the group lists.
 *
 * @author Ronan Doyle
 */
public class GroupListFragment extends ListFragment {

    public static final String FRAGMENT_TAG = "GroupListFragment";

    private RecyclerView mRecyclerView;
    private Firebase mItemsRef;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemsRef = getRootRef().child("items/group");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_list, container, false);
        setupRecyclerView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Item, ListItemViewHolder> recyclerAdapter =
                new FirebaseRecyclerAdapter<Item, ListItemViewHolder>(
                        Item.class,
                        android.R.layout.two_line_list_item,
                        ListItemViewHolder.class,
                        mItemsRef) {
                    @Override
                    protected void populateViewHolder(ListItemViewHolder listItemViewHolder, Item item, int i) {
                        if (listItemViewHolder == null || listItemViewHolder.mTextViewTitle == null) {
                            return;
                        }
                        listItemViewHolder.mTextViewTitle.setText(item.getTitle());
                    }
                };
        mRecyclerView.setAdapter(recyclerAdapter);
    }

    private void setupRecyclerView(View view) {
        if (view == null) {
            return;
        }
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_list);
        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    @Override
    public void addItem(Item item) {
        mItemsRef.push().setValue(item);
    }
}
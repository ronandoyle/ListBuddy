package nanorstudios.ie.listbuddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A Fragment to maintain the group lists.
 *
 * @author Ronan Doyle
 */
public class GroupListFragment extends ListFragment {

    public static final String FRAGMENT_TAG = "GroupListFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group_list, container, false);
    }
}

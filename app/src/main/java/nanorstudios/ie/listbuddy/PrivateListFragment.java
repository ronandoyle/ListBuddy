package nanorstudios.ie.listbuddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A Fragment to maintain the private lists.
 *
 * @author Ronan Doyle
 */
public class PrivateListFragment extends ListFragment {

    public static final String FRAGMENT_TAG = "PrivateListFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_private_list, container, false);
    }
}

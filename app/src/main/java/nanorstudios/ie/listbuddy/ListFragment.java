package nanorstudios.ie.listbuddy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

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
}

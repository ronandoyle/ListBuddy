package nanorstudios.ie.listbuddy;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * TODO Update this line
 *
 * @author Ronan Doyle <ronan@donedeal.ie>
 */
public class ListBuddyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}

package nanorstudios.ie.listbuddy;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Firebase mRootRef;
    private Firebase mListsRef;
    private RecyclerView mRecyclerView;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRootRef = new Firebase("https://list-buddy.firebaseio.com/");
        mListsRef = mRootRef.child("lists");
        setupRecyclerView();
        setupToolbar();
        setupFAB();
        setupBottomBar(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<String, ListItemViewHolder> recyclerAdapter =
                new FirebaseRecyclerAdapter<String, ListItemViewHolder>(
                        String.class,
                        android.R.layout.simple_list_item_2,
                        ListItemViewHolder.class,
                        mListsRef) {
            @Override
            protected void populateViewHolder(ListItemViewHolder listItemViewHolder, String s, int i) {
                listItemViewHolder.mTextView.setText(s);
            }
        };
        mRecyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_login:
                login(null, null);
                break;
            case R.id.action_signup:
                signUp();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_list);
        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    displayDialog();
                }
            });
        }
    }

    private void setupBottomBar(Bundle savedInstanceState) {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        BottomBar bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.menu_bottom_bar, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case R.id.item_private:
                        addFragment(new PrivateListFragment());
                        break;
                    case R.id.item_group:
                        addFragment(new GroupListFragment());
                        break;
                    default:
                        break;
                }
            }
        });
        bottomBar.setActiveTabColor("#C2185B");
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void addItem(String listItem) {
        mListsRef.push().setValue(listItem);
    }

    private void displayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a new list");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addItem(input.getText().toString());
            }
        });

        builder.show();
    }

    private void signUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create an account");


        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final TextInputEditText emailInputLayout = new TextInputEditText(this);
        emailInputLayout.setHint("Email Address");
        layout.addView(emailInputLayout);

        final TextInputEditText passwordInputLayout = new TextInputEditText(this);
        passwordInputLayout.setHint("Password");
        layout.addView(passwordInputLayout);

        builder.setView(layout);

        builder.setPositiveButton("Sign  Up", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final String email = emailInputLayout.getText().toString();
                final String password = passwordInputLayout.getText().toString();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                    mRootRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                                @Override
                                public void onSuccess(Map<String, Object> stringObjectMap) {
                                    login(email, password);
                                }

                                @Override
                                public void onError(FirebaseError firebaseError) {
                                    Toast.makeText(getApplicationContext(), "Damn it, looks like we messed up. Please try again later.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        builder.show();
    }

    private void login(String email, String password) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mRootRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    Toast.makeText(getApplicationContext(), "Woohoo, logged in!",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    Toast.makeText(getApplicationContext(), "Feck it, log in failed.",
                            Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login");


        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final TextInputEditText emailInputLayout = new TextInputEditText(this);
        emailInputLayout.setHint("Email Address");
        layout.addView(emailInputLayout);

        final TextInputEditText passwordInputLayout = new TextInputEditText(this);
        passwordInputLayout.setHint("Password");
        layout.addView(passwordInputLayout);

        builder.setView(layout);

        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final String email = emailInputLayout.getText().toString();
                final String password = passwordInputLayout.getText().toString();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    login(email, password);
                }
            }
        });

        builder.show();

    }
}
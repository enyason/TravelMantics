package com.enyason.travelmantics.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enyason.travelmantics.R;
import com.enyason.travelmantics.utils.FirebaseUtil;
import com.enyason.travelmantics.view.adapter.DealAdapter;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static com.enyason.travelmantics.utils.FirebaseUtil.RC_SIGN_IN;

public class MainActivity extends AppCompatActivity {

//    private FirebaseDatabase mFirebaseDatabase;
//    private DatabaseReference mDatabaseReference;
//    private RecyclerView mRVDeals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//
//        FirebaseUtil.openFbReference("traveldeals", this);
//        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
//        mDatabaseReference = FirebaseUtil.mDatabaseReference;

//        mRVDeals = findViewById(R.id.rv_deals);
//        final DealAdapter adapter = new DealAdapter();
//        mRVDeals.setAdapter(adapter);
//        LinearLayoutManager dealsLayoutManager =
//                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        mRVDeals.setLayoutManager(dealsLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_menu, menu);
        MenuItem insertMenu = menu.findItem(R.id.insert_menu);
        if (FirebaseUtil.isAdmin == true) {
            insertMenu.setVisible(true);
        } else {
            insertMenu.setVisible(false);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert_menu:
                Intent intent = new Intent(this, InsertActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout_menu:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("Logout", "User Logged Out");
                                FirebaseUtil.attachListener();
                            }
                        });
                FirebaseUtil.detachListener();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtil.openFbReference("traveldeals", this);
        RecyclerView rvDeals = findViewById(R.id.rv_deals);
        final DealAdapter adapter = new DealAdapter();
        rvDeals.setAdapter(adapter);
        LinearLayoutManager dealsLayoutManager =
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvDeals.setLayoutManager(dealsLayoutManager);
        FirebaseUtil.attachListener();

    }

    public void showMenu() {
        invalidateOptionsMenu();
    }


}

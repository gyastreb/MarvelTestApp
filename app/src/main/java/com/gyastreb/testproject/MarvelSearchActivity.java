package com.gyastreb.testproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.gyastreb.testproject.db.DBFactory;
import com.gyastreb.testproject.fragments.ListAndSearchFragment;

/**
 * Created by gyastreb on 9/9/16.
 */
public class MarvelSearchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marvel_search_and_list_activity);
        DBFactory.initManager(this);
        openSearchFragment();
    }

    private void openSearchFragment() {
        ListAndSearchFragment listAndSearchFragment = new ListAndSearchFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, listAndSearchFragment, "marvel list")
                .addToBackStack(null)
                .commit();
    }
}

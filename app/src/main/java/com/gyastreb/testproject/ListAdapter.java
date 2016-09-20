package com.gyastreb.testproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gyastreb.testproject.fragments.CharacterDetailFragment;
import com.gyastreb.testproject.fragments.ListAndSearchFragment;
import com.gyastreb.testproject.models.Result;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by gyastreb on 9/20/16.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    ArrayList<Result> results = new ArrayList<>();
    ListAndSearchFragment listAndSearchFragment;

    public ListAdapter(ArrayList<Result> results, ListAndSearchFragment listAndSearchFragment) {
        this.results = results;
        this.listAndSearchFragment = listAndSearchFragment;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw, parent, false);
        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, final int position) {
            holder.list_row.setText(results.get(position).getName());
        holder.list_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundles = new Bundle();
                bundles.putSerializable("result", (Serializable) results.get(position));

                CharacterDetailFragment characterDetailFragment = new CharacterDetailFragment();
                characterDetailFragment.setArguments(bundles);
                FragmentManager manager = listAndSearchFragment.getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.container, characterDetailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public TextView list_row;

        public ListViewHolder(View itemView) {
            super(itemView);
            list_row = (TextView) itemView.findViewById(R.id.list_raw);
        }
    }
}

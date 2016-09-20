package com.gyastreb.testproject.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gyastreb.testproject.ApiController;
import com.gyastreb.testproject.ListAdapter;
import com.gyastreb.testproject.R;
import com.gyastreb.testproject.db.ResultDAO;
import com.gyastreb.testproject.models.Main;
import com.gyastreb.testproject.models.Result;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gyastreb on 9/14/16.
 */
public class ListAndSearchFragment extends Fragment {
    Context context;
    View view;
    EditText nameCharacter;
    Button searchButton;
    Main mainModel;
    RecyclerView lastRequestList;
    List<Result> resultList = new ArrayList<>();
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_and_search_fragment, null);
        context = getActivity();

        init();

        try {
            getMarvelCharecter();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {

            readDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return view;
    }

    public void init() {
        nameCharacter = (EditText) view.findViewById(R.id.name_character);
        searchButton = (Button) view.findViewById(R.id.search_button);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        lastRequestList = (RecyclerView) view.findViewById(R.id.last_request_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        lastRequestList.setLayoutManager(linearLayoutManager);
    }

    public void getMarvelCharecter() throws NoSuchAlgorithmException, UnsupportedEncodingException {

        final long timeStamp = System.currentTimeMillis();
        String hash = timeStamp
                + getString(R.string.marvel_private_api_key)
                + getString(R.string.marvel_public_api_key);


        final StringBuffer sb = getMd5Hash(hash);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushSearchButton(timeStamp, sb);
            }
        });

    }

    @NonNull
    private StringBuffer getMd5Hash(String hash) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(hash.getBytes());
        byte byteData[] = md.digest();

        final StringBuffer sb = new StringBuffer();
        for (byte aByteData : byteData) {
            sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
        }
        return sb;
    }

    private void pushSearchButton(long timeStamp, StringBuffer sb) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", nameCharacter.getText().toString());
        parameters.put("apikey", getString(R.string.marvel_public_api_key));
        parameters.put("ts", timeStamp + "");
        parameters.put("hash", sb.toString());
        progressBar.setVisibility(View.VISIBLE);
        final Call<Main> call = new ApiController(context).getProduct(parameters);
        call.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(Call<Main> call, Response<Main> response) {
                progressBar.setVisibility(View.INVISIBLE);
                mainModel = response.body();
                if (mainModel.getData().getResults().isEmpty()) {
                    Toast toast = Toast.makeText(context, "Name is wrong", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Bundle bundles = new Bundle();
                    for (Result result : mainModel.getData().getResults()) {
                        bundles.putSerializable("result", (Serializable) result);
                    }

                    try {
                        for (Result result : mainModel.getData().getResults())
                            ResultDAO.getInstance().save(result);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    replaceToDetailFragment(bundles);
                }
            }

            @Override
            public void onFailure(Call<Main> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast toast = Toast.makeText(context, "Network error", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void readDb() throws SQLException {
        ArrayList<Result> results = new ArrayList<>();
        if (ResultDAO.getInstance().get() != null) {
            results.addAll(ResultDAO.getInstance().get());
        }
        ListAdapter listAdapter = new ListAdapter(results,this);
        lastRequestList.setAdapter(listAdapter);


    }

    private void replaceToDetailFragment(Bundle bundles) {
        CharacterDetailFragment characterDetailFragment = new CharacterDetailFragment();
        characterDetailFragment.setArguments(bundles);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, characterDetailFragment)
                .addToBackStack(null)
                .commit();
    }
}

package com.gyastreb.testproject.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyastreb.testproject.R;
import com.gyastreb.testproject.models.Main;
import com.gyastreb.testproject.models.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by gyastreb on 9/16/16.
 */
public class CharacterDetailFragment extends Fragment {

    Context context;
    View view;
    TextView characterName;
    TextView characterUrl;
    TextView characterDate;
    TextView characterDesc;
    ImageView characterImage;
    Result results;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.details_fragment, null);
        context = getActivity();

        Bundle bundle = getArguments();
        results = (Result) bundle.getSerializable("result");

        init();

        getCharacterInfo();

        return view;
    }

    private void init() {
        characterName = (TextView) view.findViewById(R.id.character_name);
        characterDesc = (TextView) view.findViewById(R.id.character_desc);
        characterUrl = (TextView) view.findViewById(R.id.character_url);
        characterDate = (TextView) view.findViewById(R.id.character_date);
        characterImage = (ImageView) view.findViewById(R.id.character_image);
    }

    private void getCharacterInfo() {
            characterName.setText(results.getName());
            characterDate.setText(results.getModified().toString());
            characterDesc.setText(results.getDescription());

            String imageRes = results.getThumbnail().getPath() + "." + results.getThumbnail().getExtension();
            Picasso.with(context)
                    .load(imageRes)
                    .into(characterImage);


    }

}

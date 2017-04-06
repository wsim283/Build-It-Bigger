package com.udacity.gradle.builditbigger.paid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.udacity.gradle.builditbigger.R;


public class MainActivityFragment extends Fragment {


    ProgressBar loadingBar;
    Button retrieveJokeBtn;

    public MainActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        retrieveJokeBtn = (Button)root.findViewById(R.id.jokeBtn);
        loadingBar = (ProgressBar)root.findViewById(R.id.loadingBar);

        return root;
    }

    public void showFetchLoadingBar(){
        loadingBar.setVisibility(View.VISIBLE);
        retrieveJokeBtn.setEnabled(false);
    }

    public void hideFetchLoadingBar(){
        loadingBar.setVisibility(View.GONE);
        retrieveJokeBtn.setEnabled(true);
    }

}

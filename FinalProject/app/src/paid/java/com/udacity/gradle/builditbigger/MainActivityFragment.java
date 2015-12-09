package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.android.jokesandroid.JokeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment {

    private ProgressBar spinner;
    public MainActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        spinner = (ProgressBar) root.findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);


        Button tellJokeButton = (Button) root.findViewById(R.id.tell_joke_button);
        tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new JokeAsyncTask(new OnJokeListener() {
                        @Override
                        public void onGetJoke(String joke) {
                            Intent intent = new Intent(getActivity(), JokeActivity
                                    .class);
                            intent.putExtra(JokeActivity.EXTRA_JOKE, joke);
                            startActivity(intent);
                        }
                    }).execute();
                spinner.setVisibility(View.VISIBLE);
            }
        });
        return root;
    }

}

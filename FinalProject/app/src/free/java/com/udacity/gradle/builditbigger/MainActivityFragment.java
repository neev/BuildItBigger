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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivityFragment extends Fragment {

    InterstitialAd mInterstitialAd;
    private ProgressBar spinner;
    boolean pbFlag = false;
    public MainActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        //spinner progress bar
        spinner=(ProgressBar) root.findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        pbFlag=false;


        //Interstitial ad

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                new JokeAsyncTask(new OnJokeListener() {
                    @Override
                    public void onGetJoke(String joke) {
                        Intent intent = new Intent(getActivity(), JokeActivity.class);
                        intent.putExtra(JokeActivity.EXTRA_JOKE, joke);
                        startActivity(intent);
                    }
                }).execute();

               spinner.setVisibility(View.VISIBLE);
                pbFlag=true;
            }
        });

        requestNewInterstitial();

        Button tellJokeButton = (Button) root.findViewById(R.id.tell_joke_button);
        tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
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
                    pbFlag=true;
                }
            }
        });




        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    /*@Override
    public void onAttach(Context c){
        if(pbFlag==true){
            spinner.setVisibility(View.GONE);
        }
        else {
            super.onAttach(c);
        }

    }*/

}

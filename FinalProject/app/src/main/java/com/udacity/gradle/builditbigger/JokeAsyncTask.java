package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.example.neeraja.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * Call Joke Api server.
 */
public class JokeAsyncTask extends AsyncTask<Void, Void, String> {
    private MyApi jokeApiService = null;

    private OnJokeListener listener;

    public JokeAsyncTask(OnJokeListener listener){
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... params) {
        if(jokeApiService == null) {  // Only do this once
            /*MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
*/
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://builditbigger-1153.appspot.com/_ah/api/");
            jokeApiService = builder.build();
        }
        try {
            return jokeApiService.joke().execute().getJoke();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        listener.onGetJoke(result);
    }
}

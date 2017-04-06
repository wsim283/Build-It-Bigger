package com.udacity.gradle.builditbigger;


import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.example.mulya.myapplication.backend.jokeApi.JokeApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class FetchJokeTask extends AsyncTask<Void,Void,String> {

    private JokeApi jokeApi = null;
    private OnFetchCompleteListener fetchCompleteListener;

    public FetchJokeTask(OnFetchCompleteListener fetchCompleteListener){
        initApi();

        this.fetchCompleteListener = fetchCompleteListener;
    }

    FetchJokeTask(){
        initApi();
        fetchCompleteListener = null;
    }

    private void initApi(){
        JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                        request.setDisableGZipContent(true);
                    }
                });

        jokeApi = builder.build();
    }

    @Override
    protected String doInBackground(Void... voids) {

        try{
            return jokeApi.retrieveJoke().execute().getJoke();
        }catch (IOException e){
            Log.e(FetchJokeTask.class.getSimpleName(), "IO Exception, could not retrieve joke");
            return null;
        }

    }

    @Override
    protected void onPostExecute(String joke) {
        if(TextUtils.isEmpty(joke)){
            return;
        }

        if(fetchCompleteListener != null) {
            fetchCompleteListener.fetchCompleted(joke);
        }

    }
}
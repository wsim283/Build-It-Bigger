package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.JavaJokes;
import com.example.androidjokesprovider.JokeActivity;
import com.example.mulya.myapplication.backend.jokeApi.JokeApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        FetchJokesTask fetchJokesTask = new FetchJokesTask((Button)view);
        fetchJokesTask.execute();

    }

    private class FetchJokesTask extends AsyncTask<Void,Void,String>{

        private JokeApi jokeApi = null;
        private Button jokeButton;
        private ProgressBar loadingBar;

        public FetchJokesTask(Button jokeButton){
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });

            jokeApi = builder.build();
            this.jokeButton = jokeButton;
            loadingBar = (ProgressBar)findViewById(R.id.loadingJokeBar);
        }

        @Override
        protected void onPreExecute() {
            jokeButton.setEnabled(false);
            loadingBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {

            try{
                return jokeApi.retrieveJoke().execute().getJoke();
            }catch (IOException e){
                Log.e(FetchJokesTask.class.getSimpleName(), "IO Exception, could not retrieve joke");
                return null;
            }

        }

        @Override
        protected void onPostExecute(String joke) {
            if(TextUtils.isEmpty(joke)){
                return;
            }

            jokeButton.setEnabled(true);
            loadingBar.setVisibility(View.GONE);
            Intent jokeIntent = new Intent(MainActivity.this, JokeActivity.class);
            jokeIntent.putExtra(getString(R.string.joke_extra), joke);
            startActivity(jokeIntent);
        }
    }

}

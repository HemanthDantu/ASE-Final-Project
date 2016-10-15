package com.example.daras.robocare;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchMovie extends AppCompatActivity {



    EditText SearchText;
    EditText ReleaseYear;
    ListView Results;
    String JsonResult;
    ArrayList<String> Movies = new ArrayList<String>();
    ArrayList<String>ImgaeURI = new ArrayList<String>();
    private GridView gridView;
    private GridViewAdapter gridAdapter;
     ArrayList<ImageItem> imageItems = new ArrayList<>();
    ProgressBar Progress;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchdoctor);
        SearchText = (EditText) findViewById(R.id.txtSearch);
        //Results = (ListView)findViewById(R.id.lstResults);
       /* ReleaseYear= (EditText)findViewById(R.id.txtReleaseYear);
        gridView = (GridView) findViewById(R.id.gridView);
        Progress = (ProgressBar)findViewById(R.id.progressBar);*/
       // Progress.setProgressTintList(ColorStateList.valueOf(Color.RED));
       /* Progress.getProgressDrawable().setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);*/

      /*  gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                String MovieName = item.getTitle();
                OnImageClick(MovieName);
            }
        });*/

    }

   /* ImageItem item = (ImageItem) parent.getItemAtPosition(position);
    String MovieName = item.getTitle();
    OnImageClick(MovieName);*/

    public void OnImageClick(final String MovieName)
    {
        try
        {
          //  Toast.makeText(SearchMovie.this, MovieName + " added to Wish List", Toast.LENGTH_SHORT).show();

           final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add to Wish List");
            builder.setMessage("Do you want to add movie to the wish list");
            builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(SearchMovie.this, MovieName + " added to Wish List", Toast.LENGTH_SHORT).show();
                }
            })
                    .setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builder.show();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void SearchOnclick(View view)
    {
        try {
            Movies = new ArrayList<String>();
            imageItems = new ArrayList<>();
            ImgaeURI = new ArrayList<>();
            gridView.setAdapter(null);

          //  Results.setAdapter(null);

            new MyAsyncTask().execute();

            /*if(JsonResult!=null)
            {
                ParseforMovieData(JsonResult);
                ParseforImageData(JsonResult);
                //AddItemsListView();
                new DownloadImages().execute();
               // setGridView();
            }
            else
            {
                Toast.makeText(SearchMovie.this, "Ooops did not find the movie", Toast.LENGTH_SHORT).show();
            }*/

        } catch (Exception ex) {
          ex.printStackTrace();
        }
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

        public void SearchMovie()
        {
            try {
                String strSearchText = SearchText.getText().toString();
                String strSearchYear = ReleaseYear.getText().toString();
                if (strSearchYear.length()==0)
                {
                    strSearchYear = "2016";
                }


                String Encode =  URLEncoder.encode(strSearchText,"UTF-8");
                String URI = "http://api.openweathermap.org/data/2.5/weather?q=kansascity&APPID=a149d0dfbedce700edce4a82cccb6c32";
                //String URI = "http://api.themoviedb.org/3/search/movie?api_key=500cb0f7582d5a751ce8cd572fe4d971&query="+Encode+"&page=1&primary_release_year="+strSearchYear;
                URL myURL = new URL(URI);

                HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                connection.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder results = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    results.append(line);
                }
                JsonResult = results.toString();
                connection.disconnect();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }


        }

        @Override
        protected Double doInBackground(String... params) {
            SearchMovie();
            return null;
        }

        @Override
        protected void onPostExecute(Double result)
        {

            if(JsonResult!=null)
            {
                ParseforMovieData(JsonResult);
                ParseforImageData(JsonResult);
                //AddItemsListView();
                new DownloadImages().execute();
                // setGridView();
            }
            else
            {
                Toast.makeText(SearchMovie.this, "Ooops did not find the movie", Toast.LENGTH_SHORT).show();
            }

        }


    }

    private void ParseforMovieData(String Json)
    {
        try {

            JSONObject reader = new JSONObject(Json);
            JSONArray array = reader.getJSONArray("results");
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject movie = array.getJSONObject(i);
                Movies.add(movie.getString("original_title"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void ParseforImageData(String Json)
    {
        try
        {
            String BaseUri = "http://image.tmdb.org/t/p/w185/";
            ArrayList<String> Posterpath = new ArrayList<>();
            JSONObject reader = new JSONObject(Json);
            JSONArray array = reader.getJSONArray("results");
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject movie = array.getJSONObject(i);
                Posterpath.add(movie.getString("poster_path"));
            }
            for (String ss:Posterpath)
            {
                String ImageUri = BaseUri.concat(ss);
                ImgaeURI.add(ImageUri);
            }



        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }



    private void AddItemsListView()
    {
        try
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Movies);
             Results.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private void setGridView()
    {
        try
        {
            if(!imageItems.isEmpty())
            {
                gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout,imageItems);
                gridView.setAdapter(gridAdapter);
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public class DownloadImages extends  AsyncTask <String,Integer,Double>
    {


        private void getData() {

            try
            {
                int count =0;
                int ProgressCount = 1;
                for (String URI:ImgaeURI
                        ) {
                    if(URI!=null)
                    {
                        publishProgress((int) ((ProgressCount / ImgaeURI.size()) * 100));
                        Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(URI).getContent());
                        imageItems.add(new ImageItem(bitmap, Movies.get(count)));
                        ProgressCount++;
                        count++;
                    }

                }
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }
        @Override
        protected Double doInBackground(String... params) {
            getData();
            return null;
        }
        @Override
        protected void onProgressUpdate(final Integer... progress) {
            Progress.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(Double result)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    setGridView();

                }
            });
        }
    }




    }


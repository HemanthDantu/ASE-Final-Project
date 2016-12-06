package ase.smalloven;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchRecpie extends AppCompatActivity {

    Button btnSearch;
    String JsonResult;
    String AutoComplete;
    String RandomRecipe;
    ArrayList<String> ImageURI;
    ArrayList<String> Titles;
    ArrayList<String> Descriptions;
    ArrayList<String> Id;
    Spinner Cusine;
    Spinner Diet;
    ImageView Imageview1;
    ImageView Imageview2;
    ImageView Imageview3;
    private AutoCompleteTextView SearchText;
    public ArrayAdapter<String> aAdapter;
    private ArrayList<String> AutoCompletelist;
    private ArrayList<String> RandompairIamges;
    private ArrayList<String>RandompairsID;
    static ArrayList<Landscape> Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recpie);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        Cusine = (Spinner)findViewById(R.id.SpinnerCusine);
        Diet = (Spinner)findViewById(R.id.SpinnerDiet);
       SearchText = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        Imageview1 = (ImageView)findViewById(R.id.imageView1);
        Imageview2 = (ImageView)findViewById(R.id.imageView2);
        Imageview3 = (ImageView)findViewById(R.id.imageView3);

        new Random().execute();

        Imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Image1Intent = new Intent(SearchRecpie.this, RecpieDetails.class);
                Image1Intent.putExtra("id", RandompairsID.get(0).toString());
                startActivity(Image1Intent);

            }
        });
        Imageview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Image2Intent = new Intent(SearchRecpie.this, RecpieDetails.class);
                Image2Intent.putExtra("id", RandompairsID.get(1).toString());
                startActivity(Image2Intent);
            }
        });

        Imageview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Image3Intent = new Intent(SearchRecpie.this, RecpieDetails.class);
                Image3Intent.putExtra("id", RandompairsID.get(2).toString());
                startActivity(Image3Intent);
            }
        });

        SearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() >= 3) {
                    String WatchText = s.toString();
                    new Autocomplete().execute(WatchText);
                }
            }

        });




        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SearchText.getText().toString()!="")
                {
                   new MyAsyncTask().execute();
                }
                else
                {
                    Toast.makeText(SearchRecpie.this,"Please Enter the Search Term",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private class Autocomplete extends AsyncTask<String, Integer, Double> {

        private void AutoComplete(String SearchText)
        {

            try
            {
                AutoCompletelist = new ArrayList<>();
                String EncodedTerm =  URLEncoder.encode(SearchText, "UTF-8");
                String URI = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/autocomplete?number=5&query=" + EncodedTerm;
                URL myURL = new URL(URI);
                HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("X-Mashape-Key", "El3l83nicKmshKJpa7frTZH5wp6rp1cZZlDjsnxgPkFMS2J75S");
                connection.setDoInput(true);
                connection.connect();
                int ss = connection.getResponseCode();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder results = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    results.append(line);
                }
                AutoComplete = results.toString();
                connection.disconnect();

            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        @Override
        protected Double doInBackground(String... params) {
            AutoComplete(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Double result)
        {
            try
            {
                if(AutoComplete!=null)
                {
                    ParseforAutoComplete();
                    UI();
                }

            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        private void UI()
        {
            try
            {
                runOnUiThread(new Runnable(){
                    public void run(){
                        aAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.textview,R.id.Results,AutoCompletelist);
                        SearchText.setAdapter(aAdapter);
                        aAdapter.notifyDataSetChanged();
                    }
                });
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        private void ParseforAutoComplete()
        {
            try
            {

                JSONArray arrays = new JSONArray(AutoComplete);
                for(int j=0;j<arrays.length();j++)
                {
                    AutoCompletelist.add(arrays.getJSONObject(j).getString("title"));
                }


            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }



    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {



        private void SearchRecpie()
        {

            try
            {
                ImageURI = new ArrayList<>();
                Titles = new ArrayList<>();
                Descriptions = new ArrayList<>();
                Id = new ArrayList<>();
                Data = new ArrayList<>();
                String strCusine = Cusine.getSelectedItem().toString();
                String strDiet = Diet.getSelectedItem().toString();
                String SearchTerm = SearchText.getText().toString();
                String EncodedTerm =  URLEncoder.encode(SearchTerm, "UTF-8");

               // String URI = "https://api.edamam.com/search?_app_id=d76d6dce&app_key=7921db5bb483ee6fb7dbf918c3bbab34&q="+EncodedTerm ;
               // String URI = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/food/products/search?number=10&offset=0&query="+ EncodedTerm;
                String URI = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/search?limitLicense=false&number=10&offset=0&query=" + EncodedTerm;
               // String URI = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/food/ingredients/autocomplete?metaInformation=false&number=5&query=appl";

                URL myURL = new URL(URI);
                HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("X-Mashape-Key", "El3l83nicKmshKJpa7frTZH5wp6rp1cZZlDjsnxgPkFMS2J75S");
                connection.setDoInput(true);
                //connection.setDoOutput(true);
                connection.connect();
                int ss = connection.getResponseCode();
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
            SearchRecpie();
            return null;
        }

        @Override
        protected void onPostExecute(Double result)
        {
            try
            {
                if(JsonResult!=null)
                {
                    ParseforMovieData();
                    new DownloadImages().execute();

                }

            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        private void ParseforMovieData()
        {
            try {

                JSONObject reader = new JSONObject(JsonResult);
                JSONArray array = reader.getJSONArray("results");

                for (int i = 0; i < array.length(); i++)
                {
                    JSONObject Recpie = array.getJSONObject(i);
                    String id =  Recpie.getString("id");
                    String title = Recpie.getString("title");
                    String Time = Recpie.getString("readyInMinutes");
                    String Image = Recpie.getString("image");
                    ImageURI.add(Image);
                    Titles.add(title);
                    Id.add(id);
                    Descriptions.add(Time + " mins");
                }

            } catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }

    }

    private class DownloadImages extends  AsyncTask <String,Integer,Double>
    {


        private void getData() {

            try
            {
                for(int i=0;i<ImageURI.size();i++)
                {
                    String BaseURI = "https://spoonacular.com/recipeImages/";
                    String URI = BaseURI + ImageURI.get(i);
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(URI).getContent());
                    Data.add(new Landscape(bitmap, Titles.get(i),Descriptions.get(i),Id.get(i)));
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
        protected void onPostExecute(Double result)
        {
            try
            {
                Intent intent = new Intent(SearchRecpie.this, DisplayResults.class);
                startActivity(intent);



            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }



    public static ArrayList<Landscape> GetData()
    {
         return Data;
    }

    private class Random extends AsyncTask<String, Integer, Double> {

        private void RandomRecipe()
        {

            try
            {
                RandompairsID = new ArrayList<>();
                RandompairIamges = new ArrayList<>();
                String URI = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/random?limitLicense=false&number=3";
                URL myURL = new URL(URI);
                HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("X-Mashape-Key", "El3l83nicKmshKJpa7frTZH5wp6rp1cZZlDjsnxgPkFMS2J75S");
                connection.setDoInput(true);
                connection.connect();
                int ss = connection.getResponseCode();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder results = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    results.append(line);
                }
                RandomRecipe = results.toString();
                connection.disconnect();

            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        @Override
        protected Double doInBackground(String... params) {
            RandomRecipe();
            return null;
        }

        @Override
        protected void onPostExecute(Double result)
        {
            try
            {
                if(RandomRecipe!=null)
                {

                    parseforRandomRecipe();
                    SetData();
                }

            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }


        private void parseforRandomRecipe()
        {
            try
            {
                JSONObject Obj = new JSONObject(RandomRecipe);
                JSONArray Recipes = Obj.getJSONArray("recipes");
                for(int j=0;j<Recipes.length();j++)
                {
                    String Image = Recipes.getJSONObject(j).getString("image");
                    String Id = Recipes.getJSONObject(j).getString("id");
                    RandompairIamges.add(Image);
                    RandompairsID.add(Id);

                }


            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        private void SetData()
        {
            try
            {
                Picasso.with(SearchRecpie.this).load(RandompairIamges.get(0)).into(Imageview1);
                Picasso.with(SearchRecpie.this).load(RandompairIamges.get(1)).into(Imageview2);
                Picasso.with(SearchRecpie.this).load(RandompairIamges.get(2)).into(Imageview3);
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }



}

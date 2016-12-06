package ase.smalloven;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.mongodb.util.JSON;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.RecyclerAdapter;
import adapter.RecyclerAdapterIng;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IngredientsInfo extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    List<Landscape> IngredientData;
    String IngData;
    List<String> JSONS = new ArrayList<>();
    List<String> Ingredients = new ArrayList<>();
    List<String> Items;
    HashMap<String,List<String>> Data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_info);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        JSONS = new ArrayList<>();
        Data = new HashMap<>();
        Ingredients = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Small Oven");
        toolbar.setSubtitle("Everyone Can Cook");
        new DownloadImages().execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ingredients, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    switch (item.getItemId())
    {

        case R.id.SearchStore:
        //Toast.makeText(IngredientsInfo.this,"Search",Toast.LENGTH_LONG).show();

            if(RecyclerAdapterIng.Favorites.size() == 0)
            {
                new AlertDialog.Builder(this)
                        .setTitle("Empty List")
                        .setMessage("Choose items to add to Shop")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(R.drawable.ic_info)
                        .show();
            }
            else

            {
                Ingredients = RecyclerAdapterIng.Favorites;
                new  GetIngredientData().execute();

            }
        break;
    }
        return super.onOptionsItemSelected(item);
    }

    private void SetupRecyclerview()
    {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview1);
        RecyclerAdapterIng adapter = new RecyclerAdapterIng(this,IngredientData);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    private class DownloadImages extends AsyncTask<String,Integer,Double>
    {


        private void getData() {

            try
            {
                List<Ingredient> Ingredients = RecpieDetails.GetData();
                IngredientData = new ArrayList<>();
                for(int i=0;i<Ingredients.size();i++)
                {
                    String URI = Ingredients.get(i).getImageURI();
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(URI).getContent());
                    String Title = Ingredients.get(i).getName();
                    String Description = Ingredients.get(i).getAmount() + " "+ Ingredients.get(i).getUnit();
                    String Id = Ingredients.get(i).getID();
                    IngredientData.add(new Landscape(bitmap, Title, Description, Id));
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
                SetupRecyclerview();
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    private class GetIngredientData extends AsyncTask<String, Integer, Double> {

        private void RandomRecipe()
        {

            try
            {
                for (String Ingredient:Ingredients) {

                    String EncodedTerm =  URLEncoder.encode(Ingredient, "UTF-8");
                    String URI = "https://api.nutritionix.com/v1_1/search/" + EncodedTerm + "?results=0%3A5&cal_min=0&cal_max=50000&fields=item_name%2Cbrand_name%2Citem_id%2Cbrand_id&appId=57c8fbd3&appKey=f06cab2acb1fc95e3b46dbb5239b34ac";
                    URL myURL = new URL(URI);
                    HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.connect();
                    int ss = connection.getResponseCode();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder results = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        results.append(line);
                    }
                    IngData = results.toString();
                    JSONS.add(IngData);
                    connection.disconnect();
                }



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
                if(IngData!=null)
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
                for (int i=0;i<JSONS.size();i++) {
                    Items = new ArrayList<>();
                    String ss = JSONS.get(i);

                    JSONObject Obj = new JSONObject(ss);
                    JSONArray Hits = Obj.getJSONArray("hits");

                    for(int j=0;j<Hits.length();j++)
                    {
                        JSONObject HitsObj =  Hits.getJSONObject(j);
                        JSONObject Fields =  HitsObj.getJSONObject("fields");
                        String Ss = Fields.getString("item_name");
                        Items.add(Ss);
                    }
                    Data.put(Ingredients.get(i),Items);
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
                Intent IngredientListView = new Intent(IngredientsInfo.this,IngredientsResults1.class);
                IngredientListView.putExtra("Data",Data);
                startActivity(IngredientListView);

            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

}

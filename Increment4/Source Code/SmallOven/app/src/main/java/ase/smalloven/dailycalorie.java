package ase.smalloven;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class dailycalorie extends AppCompatActivity {

    String WeeklyMeal;
    String RecpieInfo;
    GridView gridView;
    private GridViewAdapter gridAdapter;
    ArrayList<String> ListRecpieInfo;
    ArrayList<String> ImageURI;
    ArrayList<String> Names;
    ArrayList<String>Ids;

    ArrayList<ImageItem> imageItems;
    HashMap<String,String>data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailycalorie);
        gridView = (GridView) findViewById(R.id.gridView);
        new DailyCalorie().execute("2000");

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                String Name = item.getTitle();
                String Id = data.get(Name).toString();
                Intent II = new Intent(dailycalorie.this,RecpieDetails.class);
                II.putExtra("id", Id);
                startActivity(II);

            }
        });

    }

    private class DailyCalorie extends AsyncTask<String, Integer, Double> {



        private void GetWeeklyMeal(int Caloriecount)
        {

            try
            {

                Ids = new ArrayList<>();
                ListRecpieInfo = new ArrayList<>();
                ImageURI = new ArrayList<>();
                Names = new ArrayList<>();
                imageItems = new ArrayList<>();
                data = new HashMap<>();
                if (Caloriecount==0)
                {
                    Caloriecount = 2000;
                }
                String strCalorieCount = Integer.toString(Caloriecount);
                String EncodedTerm =  URLEncoder.encode(strCalorieCount, "UTF-8");
                String URI = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/mealplans/generate?timeFrame=week&targetCalories=" + EncodedTerm;
                URL myURL = new URL(URI);
                HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("X-Mashape-Key", "El3l83nicKmshKJpa7frTZH5wp6rp1cZZlDjsnxgPkFMS2J75S");
                connection.setDoInput(true);
                connection.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder results = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    results.append(line);
                }
                WeeklyMeal = results.toString();
                connection.disconnect();
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        @Override
        protected Double doInBackground(String... params) {
            GetWeeklyMeal(Integer.parseInt(params[0]));
            return null;
        }

        @Override
        protected void onPostExecute(Double result)
        {
            try
            {
                if(WeeklyMeal!=null)
                {
                    ParseforMealData();
                }

                new DownloadRecpieInfo().execute();
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        private void ParseforMealData()
        {
            try
            {
                JSONObject obj = new JSONObject(WeeklyMeal);
                JSONArray Meals =  obj.getJSONArray("items");
                for (int i=0;i<Meals.length();i++)
                {
                   String Daily =  Meals.getJSONObject(i).getString("value");
                   String[] Values = Daily.split(",");
                    for (String str:Values)
                    {
                       String Id = str.replaceAll("[^A-Za-z0-9]+", "");
                       String[]Keys =  Id.split("id");
                        StringBuilder strBuilder = new StringBuilder();
                        strBuilder.append(Keys[1]);
                        Ids.add(strBuilder.toString());
                        break;
                    }
                }
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }

    }

    private class DownloadRecpieInfo  extends  AsyncTask<String, Integer, Double>
    {
        private void GetRecpieInfo()
        {

            try
            {
                for (String RecpieID:Ids)
                {
                    String EncodedTerm =  URLEncoder.encode(RecpieID, "UTF-8");
                    String URI = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/"+EncodedTerm+"/information?includeNutrition=true";
                    URL myURL = new URL(URI);
                    HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("X-Mashape-Key", "El3l83nicKmshKJpa7frTZH5wp6rp1cZZlDjsnxgPkFMS2J75S");
                    connection.setDoInput(true);
                    connection.connect();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder results = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        results.append(line);
                    }
                    RecpieInfo = results.toString();
                    ListRecpieInfo.add(RecpieInfo);
                    connection.disconnect();

                }

            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }


        @Override
        protected Double doInBackground(String... params) {
            GetRecpieInfo();
            return null;
        }

        @Override
        protected void onPostExecute(Double result)
        {
            try
            {
                if(ListRecpieInfo.size()!=0)
                {
                    parseforRecpieInfo();
                }

                new DownloadImages().execute();

            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        private void parseforRecpieInfo()
        {
            try
            {
                for (String strRecpie: ListRecpieInfo)
                {
                    JSONObject objRecpieInfo =  new JSONObject(strRecpie);
                    ImageURI.add(objRecpieInfo.getString("image"));
                    Names.add(objRecpieInfo.getString("title"));
                    data.put(objRecpieInfo.getString("title"), objRecpieInfo.getString("id"));
                }
            }catch (Exception ex)
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
                for (int i=0;i<ImageURI.size();i++)
                {
                    String URI = ImageURI.get(i);
                    String Name = Names.get(i);
                    if(URI!=null)
                    {
                        Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(URI).getContent());
                        imageItems.add(new ImageItem(bitmap, Name));
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

}

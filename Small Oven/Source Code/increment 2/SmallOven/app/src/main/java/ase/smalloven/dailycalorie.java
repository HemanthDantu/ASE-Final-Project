package ase.smalloven;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class dailycalorie extends AppCompatActivity {

    String WeeklyMeal;
    ArrayList<String>Ids;
     GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailycalorie);

       new DailyCalorie().execute("2000");

    }

    private class DailyCalorie extends AsyncTask<String, Integer, Double> {



        private void GetWeeklyMeal(int Caloriecount)
        {

            try
            {
                Ids = new ArrayList<>();
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
}

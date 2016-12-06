package ase.smalloven;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.List;

public class
        RecpieDetails extends AppCompatActivity {

    String RecpieInfo;
    TextView RecpieTitle;
    ImageView RecpieImage;
    TextView RecpiePrepTime;
    TextView RecpieServings;
    ImageView imgVegeterain;
    ImageView imgVegean;
    ImageView imgDairyFree;
    ImageView imgGlutenFree;
    TextView RecpieInstructions;
    Button IngredientInfo;
    Button NutretionInfo;
    HashMap<String,String> Nurition;
    HashMap<String,String> CaloricBreak;
    static List<Ingredient>Ingredients;
    RecpieData objRecpie;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recpie_details);
        Bundle extras = getIntent().getExtras();
        RecpieTitle = (TextView)findViewById(R.id.txtRecpieTitle);
        RecpieImage = (ImageView)findViewById(R.id.imgRecpieImage);
        RecpiePrepTime = (TextView)findViewById(R.id.txtRecpiePrepTime);
        RecpieServings = (TextView)findViewById(R.id.txtRecpeServings);
        imgVegeterain = (ImageView)findViewById(R.id.imgRecpieIsVegeetarian);
        imgVegean = (ImageView)findViewById(R.id.imgRecpieVegan);
        imgDairyFree = (ImageView)findViewById(R.id.imgRecpieDairyFree);
        imgGlutenFree = (ImageView)findViewById(R.id.imgRecpieGlutenFree);
        RecpieInstructions = (TextView)findViewById(R.id.txtRecpieInstructions);
        IngredientInfo = (Button)findViewById(R.id.btnRecpieIngredientInfo);
        NutretionInfo = (Button)findViewById(R.id.btnRecpieNutrtionInfo);


        if (extras != null) {
            String value = extras.getString("id");
            //Toast.makeText(this,value,Toast.LENGTH_SHORT).show();
            new DownloadRecpieInfo().execute(value);
        }

        NutretionInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NutrientIntent = new Intent(RecpieDetails.this,nutritiondetails.class);
                NutrientIntent.putExtra("Nutrition",objRecpie.getNutrition());
                NutrientIntent.putExtra("CaloricBreak",objRecpie.getCaloricBreakDown());
                startActivity(NutrientIntent);
            }
        });

        IngredientInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IngredientIntent = new Intent(RecpieDetails.this,IngredientsInfo.class);
                startActivity(IngredientIntent);
            }
        });

    }


    private class DownloadRecpieInfo  extends AsyncTask<String, Integer, Double>
    {
        private void GetRecpieInfo(String RecpieID)
        {

            try
            {
                    Nurition = new HashMap<>();
                    objRecpie = new RecpieData();
                    Ingredients = new ArrayList<>();
                    CaloricBreak = new HashMap<>();
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
                    connection.disconnect();
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }


        @Override
        protected Double doInBackground(String... params) {
            GetRecpieInfo(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Double result)
        {
            try
            {
                if(RecpieInfo!=null)
                {
                    parseforRecpieInfo();
                    SetData();
                }


            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        private void parseforRecpieInfo()
        {
            try
            {

                    JSONObject objRecpieInfo =  new JSONObject(RecpieInfo);


                    objRecpie.setID(objRecpieInfo.getString("id"));
                    objRecpie.setName(objRecpieInfo.getString("title"));
                    objRecpie.setImageURI(objRecpieInfo.getString("image"));
                    objRecpie.setIsvegetarian(objRecpieInfo.getString("vegetarian"));
                    objRecpie.setIsvegan(objRecpieInfo.getString("vegan"));
                    objRecpie.setIsdairyFree(objRecpieInfo.getString("dairyFree"));
                    objRecpie.setIsglutenFree(objRecpieInfo.getString("glutenFree"));
                    objRecpie.setInstructions(objRecpieInfo.getString("instructions"));
                    objRecpie.setPreprationTime(objRecpieInfo.getString("readyInMinutes"));
                    objRecpie.setServings(objRecpieInfo.getString("servings"));

                    JSONObject Nutrition = objRecpieInfo.getJSONObject("nutrition");
                    JSONArray NutrientsArray = Nutrition.getJSONArray("nutrients");
                    JSONObject ObjNutrients =   Nutrition.getJSONObject("caloricBreakdown");
                     CaloricBreak.put("percentProtein", ObjNutrients.getString("percentProtein"));
                     CaloricBreak.put("percentFat", ObjNutrients.getString("percentFat"));
                     CaloricBreak.put("percentCarbs", ObjNutrients.getString("percentCarbs"));


                    JSONArray IngredientArray = objRecpieInfo.getJSONArray("extendedIngredients");

                    for (int j = 0; j < IngredientArray.length(); j++) {
                        Ingredient objIngredient = new Ingredient();
                        objIngredient.setID(IngredientArray.getJSONObject(j).getString("id"));
                        objIngredient.setImageURI(IngredientArray.getJSONObject(j).getString("image"));
                        objIngredient.setName(IngredientArray.getJSONObject(j).getString("name"));
                        objIngredient.setAmount(IngredientArray.getJSONObject(j).getString("amount"));
                        objIngredient.setUnit(IngredientArray.getJSONObject(j).getString("unit"));
                        Ingredients.add(objIngredient);
                    }
                    for (int i=0;i<NutrientsArray.length();i++) {
                        String Title = NutrientsArray.getJSONObject(i).getString("title");
                        String Amount = NutrientsArray.getJSONObject(i).getString("amount");
                        Nurition.put(Title,Amount);
                    }
                    objRecpie.setIngredientSet(Ingredients);
                    objRecpie.setNutrition(Nurition);
                    objRecpie.setCaloricBreakDown(CaloricBreak);
                    Picasso.with(RecpieDetails.this).load(objRecpie.getImageURI()).into(RecpieImage);
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }

        private void SetData()
        {
            try
            {
                if(objRecpie !=null) {
                    RecpieTitle.setText(objRecpie.getName());
                    RecpieInstructions.setText(objRecpie.getInstructions());
                    RecpiePrepTime.setText(objRecpie.getPreprationTime()+ " Mins");
                    RecpieServings.setText(objRecpie.getServings());
                    if(objRecpie.getIsvegetarian() == "true")
                    {
                        imgVegeterain.setImageResource(R.drawable.ok);
                    }
                    else
                    {
                        imgVegeterain.setImageResource(R.drawable.cancel);
                    }
                    if(objRecpie.getIsvegan() == "true")
                    {
                        imgVegean.setImageResource(R.drawable.ok);
                    }
                    else
                    {
                        imgVegean.setImageResource(R.drawable.cancel);
                    }
                    if(objRecpie.getIsdairyFree() == "true")
                    {
                        imgDairyFree.setImageResource(R.drawable.ok);
                    }
                    else
                    {
                        imgDairyFree.setImageResource(R.drawable.cancel);
                    }
                    if(objRecpie.getIsglutenFree() == "true")
                    {
                        imgGlutenFree.setImageResource(R.drawable.ok);
                    }
                    else
                    {
                        imgGlutenFree.setImageResource(R.drawable.cancel);
                    }

                }

            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }



    public static List<Ingredient> GetData()
    {
        return Ingredients;
    }


}

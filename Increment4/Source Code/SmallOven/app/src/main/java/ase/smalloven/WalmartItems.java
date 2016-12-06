package ase.smalloven;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerAdapterIng;
import adapter.WalmartItemsAdapter;

public class WalmartItems extends AppCompatActivity {

    String ItemName;
    String Result;
   static List<WalmartItemDetails> lstWalmartData;
    List<Landscape> LandscapeData;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walmart_items);
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
           ItemName =   extras.getString("Item");
            new Walmart().execute();
        }

        recyclerView = (RecyclerView)findViewById(R.id.WalmartItems);
        lstWalmartData = new ArrayList<>();

    }
    private class Walmart extends AsyncTask<String, Integer, Double> {

        private void RandomRecipe()
        {

            try
            {
                String Encode = URLEncoder.encode(ItemName, "UTF-8");
                String URI = "http://api.walmartlabs.com/v1/search?query="+Encode+"&format=json&categoryId=976759&apiKey=vgncvgzdrgc7mwh87vwd554v";
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
                Result = results.toString();
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
                if(Result!=null)
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
                WalmartItemDetails ObjWalmartDetails = new WalmartItemDetails();
                JSONObject Obj = new JSONObject(Result);
                JSONArray Items = Obj.getJSONArray("items");
                for(int j=0;j<Items.length();j++)
                {
                    ObjWalmartDetails = new WalmartItemDetails();
                    ObjWalmartDetails.setItemID(Items.getJSONObject(j).getString("itemId"));
                    ObjWalmartDetails.setName(Items.getJSONObject(j).getString("name"));
                    ObjWalmartDetails.setPrice(Items.getJSONObject(j).getString("salePrice"));
                    ObjWalmartDetails.setStandardShipRate(Items.getJSONObject(j).getString("standardShipRate"));
                    ObjWalmartDetails.setUPCNo(Items.getJSONObject(j).getString("upc"));
                    ObjWalmartDetails.setDescription(Items.getJSONObject(j).getString("longDescription"));
                    ObjWalmartDetails.setImage(Items.getJSONObject(j).getString("thumbnailImage"));
                    ObjWalmartDetails.setAddToCartUrl(Items.getJSONObject(j).getString("addToCartUrl"));
                    ObjWalmartDetails.setAvailableOnline(Items.getJSONObject(j).getString("availableOnline"));
                    lstWalmartData.add(ObjWalmartDetails);
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
                new DownloadImages().execute();
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }

    public static  List<WalmartItemDetails> getData()
    {
        return lstWalmartData;
    }
    private class DownloadImages extends AsyncTask<String,Integer,Double>
    {


        private void getData() {

            try
            {
                LandscapeData = new ArrayList<>();
                for(int i=0;i<lstWalmartData.size();i++)
                {
                    String URI = lstWalmartData.get(i).getImage();
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(URI).getContent());
                    LandscapeData.add(new Landscape(bitmap,lstWalmartData.get(i).getName(), "$ " +lstWalmartData.get(i).getPrice(),lstWalmartData.get(i).getItemID()));
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

    private void SetupRecyclerview()
    {
        recyclerView = (RecyclerView)findViewById(R.id.WalmartItems);
        WalmartItemsAdapter adapter = new WalmartItemsAdapter(this,LandscapeData);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }
}

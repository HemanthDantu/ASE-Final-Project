package ase.smalloven;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerAdapter;
import adapter.RecyclerAdapterIng;

public class IngredientsInfo extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    List<Landscape> IngredientData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_info);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
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
        Toast.makeText(IngredientsInfo.this,"Search",Toast.LENGTH_LONG).show();
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

}

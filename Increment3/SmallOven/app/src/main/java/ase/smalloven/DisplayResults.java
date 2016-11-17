package ase.smalloven;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DisplayResults extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Small Oven");
        toolbar.setSubtitle("Everyone Can Cook");
        SetupRecyclerview();
        SetupDrawer();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        String msg="";
        switch (item.getItemId())
        {
            case R.id.Add:
              msg= item.getTitle().toString();
              if(RecyclerAdapter.Favorites.size() == 0)
              {
                  new AlertDialog.Builder(this)
                          .setTitle("Empty List")
                          .setMessage("Choose items to add to favorites")
                          .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int which) {
                                  // continue with delete
                              }
                          })
                          .setIcon(R.drawable.ic_info)
                          .show();
              }
                else

              {
                  AddData(RecyclerAdapter.Favorites);

              }

                break;
            case R.id.Selectall:
                break;
        }

       // Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    private void SetupRecyclerview()
    {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        RecyclerAdapter adapter = new RecyclerAdapter(this,SearchRecpie.GetData());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    private void SetupDrawer()
    {
        NavigationDrawerfragment drawerfragment = (NavigationDrawerfragment) getSupportFragmentManager().findFragmentById(R.id.nav_drw_Frag);
        DrawerLayout layout = (DrawerLayout)findViewById(R.id.drawerLayout);
        drawerfragment.setupNavDrawFrag(R.id.nav_drw_Frag, layout, toolbar);


    }

    private void AddItemsAsFavorites(List<String> Items)
    {
        try
        {
            String UserID = constants.UserName;
            String urlEncodedName = URLEncoder.encode(UserID, "UTF-8");
            String URL = "https://api.mlab.com/api/1/databases/recpie/collections/userdetails?q={%22EMailID%22:%22"+urlEncodedName+"%22}&apiKey=WQdetFzPianTtgBryFsYkPkNE-osQ-Ue";
            OkHttpClient client = new OkHttpClient();
            //Request request = new Request.Builder().url(URL).build();
            Gson objGson = new Gson();
            String UserJson = objGson.toJson(Items);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            // RequestBody body = RequestBody.create(JSON, UserJson);
            // RequestBody body = RequestBody.create(JSON, " { \"$set\" : { \"arrays\" : \"ss\" } }  ");
            RequestBody body = RequestBody.create(JSON, " { \"$set\" : { \"favorites\" : "+ UserJson+" } }");
            Request request = new Request.Builder().url(URL).put(body).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String ss = response.body().toString();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(DisplayResults.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });





        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void AddData(final List<String> Items)
    {
        try
        {
            String UserName = constants.UserName;
            String urlEncodedName = URLEncoder.encode(UserName, "UTF-8");
            String URL = "https://api.mlab.com/api/1/databases/recpie/collections/userdetails?q={%22EMailID%22:%22"+urlEncodedName+"%22}&apiKey=WQdetFzPianTtgBryFsYkPkNE-osQ-Ue";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(URL).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String respJson = response.body().string();
                    if(respJson!="")
                    {
                        try {
                            JSONArray responseJson = new JSONArray(respJson);
                            JSONObject jObj = (JSONObject) responseJson.get(0);
                            JSONArray array = jObj.getJSONArray("favorites");
                            for(int i=0;i<array.length();i++)
                            {
                                Items.add(array.get(i).toString());
                            }

                            AddItemsAsFavorites(Items);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

package ase.smalloven;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class scan extends AppCompatActivity {

    private Button scanBtn;
    private TextView formatTxt, contentTxt;
    String UPCNO;
    String JsonResult;
    TextView Title;
    TextView Brand;
    TextView Model;
    TextView Price;
    TextView Description;
    ImageView Image;
    LinearLayout upcdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        scanBtn = (Button)findViewById(R.id.scan_button);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);

        Title = (TextView)findViewById(R.id.txtupctitle);
        Brand = (TextView)findViewById(R.id.txtupcbrand);
        Model = (TextView)findViewById(R.id.txtupcmodel);
        Price = (TextView)findViewById(R.id.txtupcprice);
        Description = (TextView)findViewById(R.id.txtupcdescription);
        Image = (ImageView)findViewById(R.id.imgupc);
        upcdetails = (LinearLayout)findViewById(R.id.layoutupc);




        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.scan_button){

                    //scan
                    IntentIntegrator scanIntegrator = new IntentIntegrator(scan.this);
                    scanIntegrator.initiateScan();

                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
//we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            UPCNO = scanContent;
            formatTxt.setText("FORMAT: " + scanFormat);
            contentTxt.setText("CONTENT: " + scanContent);
            new Scan().execute();
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    private class Scan extends AsyncTask<String, Integer, Double> {



        private void SearchRecpie()
        {

            try
            {
                String URI = "https://api.upcitemdb.com/prod/trial/lookup?upc=" + UPCNO;
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
                JSONArray Result = reader.getJSONArray("items");
                String strTitle =  Result.getJSONObject(0).getString("title");
                String strBrand =   Result.getJSONObject(0).getString("brand");
                String strModel = Result.getJSONObject(0).getString("model");
                String strPrice = Result.getJSONObject(0).getString("lowest_recorded_price");
                String strDescription = Result.getJSONObject(0).getString("description");
                /*String strImage = Result.getJSONObject(0).getJSONArray("images").toString();
              String FinalImage=  strImage.substring(strImage.indexOf('[')+1, strImage.indexOf(']'));*/

                Title.setText(strTitle);
                Brand.setText(strBrand);
                Model.setText(strModel);
                Price.setText("$ 4.99");
                Description.setText(Html.fromHtml(strDescription));
                //Picasso.with(scan.this).load(FinalImage).into(Image);
                Image.setImageResource(R.drawable.lemon);
                upcdetails.setVisibility(View.VISIBLE);


            } catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }

    }


}

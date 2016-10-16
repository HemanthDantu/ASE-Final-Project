package ase.smalloven;

import android.os.AsyncTask;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by daras on 09-Oct-16.
 */
public class mongoDB {

    public String App_Key = "WQdetFzPianTtgBryFsYkPkNE-osQ-Ue";

    public void List_databases() {


    }

    private class Autocomplete extends AsyncTask<String, Integer, Double> {

        public void ListDatabase()
        {
            String Result = "";
            try {
                String URI = "https://api.mlab.com/api/1/databases/recpie/collections/userdetails?apiKey=" + App_Key;
                URL myURL = new URL(URI);
                HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder results = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    results.append(line);
                }
                Result = results.toString();
                connection.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        protected Double doInBackground(String... params) {
            ListDatabase();
            return null;
        }

        @Override
        protected void onPostExecute(Double result)
        {
            try
            {

            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }
}
package ase.smalloven;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class settings extends AppCompatActivity {

    EditText CurrentPassword;
    EditText NewPassword;
    EditText CalorieCount;
    EditText Pincode;
    Button UpdatePassword;
    Button UpdateCalories;
    Button UpdatePincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CurrentPassword = (EditText)findViewById(R.id.txtCurrentPass);
        NewPassword = (EditText)findViewById(R.id.txtNewPass);
        CalorieCount = (EditText)findViewById(R.id.txtCalorieCount);
        Pincode = (EditText)findViewById(R.id.txtUpdatePincode);
        UpdatePassword = (Button)findViewById(R.id.btnUpdatePassword);
        UpdateCalories = (Button)findViewById(R.id.btnUpdateCalorieCount);
        UpdatePincode = (Button)findViewById(R.id.btnUpdatePin);

        UpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePassword();
            }
        });

        UpdateCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateCallforcalorie();
            }
        });

        UpdatePincode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateCallforPincode();
            }
        });

    }

    private void UpdatePassword()

    {

        try
        {
           String strCurrentPass = CurrentPassword.getText().toString();
            if(strCurrentPass=="")
            {
                Toast.makeText(this,"Please Enter Current Password",Toast.LENGTH_SHORT).show();

            }
            else
            {
                AddData();
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    private void AddData()
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
                    String CurrentPass = CurrentPassword.getText().toString();
                    final String respJson = response.body().string();
                    if(respJson!="")
                    {
                        try {
                            JSONArray responseJson = new JSONArray(respJson);
                            JSONObject jObj = (JSONObject) responseJson.get(0);
                            String Password = jObj.getString("Password").toString();
                            CheckPassword(CurrentPass,Password);
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

    private void CheckPassword(String CurrentPass,String Password)
    {
        try
        {
            if(CurrentPass.trim().equals(Password.trim()) )
            {

                UpdateCall();
            }
            else
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Failed();
                    }
                });

            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void UpdateCall()
    {
        try
        {
            String UserName = constants.UserName;
            String NewPass = NewPassword.getText().toString();
            String urlEncodedName = URLEncoder.encode(UserName, "UTF-8");
            String URL = "https://api.mlab.com/api/1/databases/recpie/collections/userdetails?q={%22EMailID%22:%22"+urlEncodedName+"%22}&apiKey=WQdetFzPianTtgBryFsYkPkNE-osQ-Ue";
            OkHttpClient client = new OkHttpClient();
            Gson objGson = new Gson();
            String UserJson = objGson.toJson(NewPass);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, " { \"$set\" : { \"Password\" : "+ UserJson+" } }");
            Request request = new Request.Builder().url(URL).put(body).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String ss = response.body().toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Pass();
                        }
                    });

                }
            });

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void UpdateCallforcalorie()
    {
        try
        {
            String UserName = constants.UserName;
            String NewPass = CalorieCount.getText().toString();
            String urlEncodedName = URLEncoder.encode(UserName, "UTF-8");
            String URL = "https://api.mlab.com/api/1/databases/recpie/collections/userdetails?q={%22EMailID%22:%22"+urlEncodedName+"%22}&apiKey=WQdetFzPianTtgBryFsYkPkNE-osQ-Ue";
            OkHttpClient client = new OkHttpClient();
            Gson objGson = new Gson();
            String UserJson = objGson.toJson(NewPass);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, " { \"$set\" : { \"CalorieCount\" : "+ UserJson+" } }");
            Request request = new Request.Builder().url(URL).put(body).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String ss = response.body().toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Pass();
                        }
                    });

                }
            });

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void UpdateCallforPincode()
    {
        try
        {
            String UserName = constants.UserName;
            String NewPass = Pincode.getText().toString();
            String urlEncodedName = URLEncoder.encode(UserName, "UTF-8");
            String URL = "https://api.mlab.com/api/1/databases/recpie/collections/userdetails?q={%22EMailID%22:%22"+urlEncodedName+"%22}&apiKey=WQdetFzPianTtgBryFsYkPkNE-osQ-Ue";
            OkHttpClient client = new OkHttpClient();
            Gson objGson = new Gson();
            String UserJson = objGson.toJson(NewPass);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, " { \"$set\" : { \"Pincode\" : "+ UserJson+" } }");
            Request request = new Request.Builder().url(URL).put(body).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String ss = response.body().toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Pass();
                        }
                    });

                }
            });

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    private void Failed()
    {
        try
        {
            Toast.makeText(this, "Password is incorrect", Toast.LENGTH_SHORT).show();

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void Pass()
    {
        try
        {


            Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

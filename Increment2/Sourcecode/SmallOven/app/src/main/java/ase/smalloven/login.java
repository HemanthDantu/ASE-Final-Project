package ase.smalloven;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class login extends AppCompatActivity {

    EditText txtLogin;
    EditText txtPassword;
    Button btnLogin;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtLogin = (EditText)findViewById(R.id.txtLoginEmail);
        txtPassword = (EditText)findViewById(R.id.txtLoginPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Update(v);

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent RegisterIntent = new Intent(login.this,register.class);
                startActivity(RegisterIntent);
            }
        });
    }

    private void Login(View view)
    {
        try
        {
            String UserName = txtLogin.getText().toString();
            String urlEncodedName = URLEncoder.encode(UserName, "UTF-8");
            String URL = "https://api.mlab.com/api/1/databases/recpie/collections/userdetails?q={%22EMailID%22:%22"+urlEncodedName+"%22}&apiKey=WQdetFzPianTtgBryFsYkPkNE-osQ-Ue";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(URL).build();
            final Intent Home = new Intent(login.this,SearchRecpie.class);
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
                            String Password = txtPassword.getText().toString();
                            JSONArray responseJson = new JSONArray(respJson);
                            JSONObject jObj = (JSONObject) responseJson.get(0);
                            String pass = jObj.get("Password").toString();
                            String user = jObj.get("EMailID").toString();
                            if (!Password.equalsIgnoreCase(pass)) {
                                Log.i("password doesn't match", "-------");
                                //startActivity(mainIntent);
                            } else {
                                startActivity(Home);
                            }
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

    private void Update(View v)
    {
        try
        {
            String UserName = txtLogin.getText().toString();
            String urlEncodedName = URLEncoder.encode(UserName, "UTF-8");
            String URL = "https://api.mlab.com/api/1/databases/recpie/collections/userdetails?q={%22EMailID%22:%22"+urlEncodedName+"%22}&apiKey=WQdetFzPianTtgBryFsYkPkNE-osQ-Ue";
            OkHttpClient client = new OkHttpClient();
            //Request request = new Request.Builder().url(URL).build();
            List<String> foo = new ArrayList<String>();
            foo.add("A");
            foo.add("B");
            foo.add("C");
            Gson objGson = new Gson();
            String UserJson = objGson.toJson(foo);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
           // RequestBody body = RequestBody.create(JSON, UserJson);
           // RequestBody body = RequestBody.create(JSON, " { \"$set\" : { \"arrays\" : \"ss\" } }  ");
            RequestBody body = RequestBody.create(JSON, " { \"$set\" : { \"arrays\" : "+ UserJson+" } }");
            Request request = new Request.Builder().url(URL).put(body).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String ss = response.body().toString();
                }
            });

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

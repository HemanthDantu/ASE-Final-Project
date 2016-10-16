package ase.smalloven;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class register extends AppCompatActivity {

    Button Register;
    EditText FirstName;
    EditText LastName;
    EditText EmailID;
    EditText Password;
    EditText Pincode;
    EditText TotalCalorieCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Register = (Button)findViewById(R.id.BtnRegister);
        FirstName = (EditText)findViewById(R.id.txtFirstName);
        LastName = (EditText)findViewById(R.id.txtLastName);
        EmailID = (EditText)findViewById(R.id.txtEmail);
        Password = (EditText)findViewById(R.id.txtPassword);
        Pincode = (EditText)findViewById(R.id.txtPincode);
        TotalCalorieCount = (EditText)findViewById(R.id.txtWeeklyCalorieTarget);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register(v);
            }
        });
    }

    public void Register(View view)  {

        UserDetails objUser = new UserDetails();
        Gson objGson = new Gson();
        objUser.setFirstName(FirstName.getText().toString());
        objUser.setLastName(LastName.getText().toString());
        objUser.setEMailID(EmailID.getText().toString());
        objUser.setPassword(Password.getText().toString());
        objUser.setPincode(Pincode.getText().toString());
        objUser.setCalorieCount(TotalCalorieCount.getText().toString());
        String UserJson = objGson.toJson(objUser);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, UserJson);
        String requestUrl = "https://api.mlab.com/api/1/databases/recpie/collections/userdetails?apiKey=WQdetFzPianTtgBryFsYkPkNE-osQ-Ue";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(requestUrl).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


            }
        });



    }



}

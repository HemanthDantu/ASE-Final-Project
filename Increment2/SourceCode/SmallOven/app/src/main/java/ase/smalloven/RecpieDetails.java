package ase.smalloven;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class RecpieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recpie_details);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("id");
            //The key argument here must match that used in the other activity
            Toast.makeText(this,value,Toast.LENGTH_SHORT).show();
        }
    }
}

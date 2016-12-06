package ase.smalloven;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;

public class home extends AppCompatActivity {

    LinearLayout lySearchRecipe;
    LinearLayout Stores;
    LinearLayout weeklymeal;
    LinearLayout Setings;
    LinearLayout ScanItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        lySearchRecipe = (LinearLayout)findViewById(R.id.layout_search);
        Stores = (LinearLayout)findViewById(R.id.layout_stores);
        weeklymeal = (LinearLayout)findViewById(R.id.layout_weeklymeal);
        Setings = (LinearLayout)findViewById(R.id.layout_settings);
        ScanItem= (LinearLayout)findViewById(R.id.layout_scanitem);


        lySearchRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Search = new Intent(home.this,SearchRecpie.class);
                startActivity(Search);

            }
        });

        Stores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Stores = new Intent(home.this,WalmartLocation.class);
                startActivity(Stores);

            }
        });

        weeklymeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent WeeklyMeal = new Intent(home.this,dailycalorie.class);
                startActivity(WeeklyMeal);

            }
        });

        Setings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Settings = new Intent(home.this,settings.class);
                startActivity(Settings);
            }
        });

        ScanItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent scan = new Intent(home.this,scan.class);
                startActivity(scan);
            }
        });
    }
}

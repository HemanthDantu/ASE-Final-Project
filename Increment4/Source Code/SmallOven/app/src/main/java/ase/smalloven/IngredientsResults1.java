package ase.smalloven;

import android.app.WallpaperInfo;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import adapter.ExpandableListAdapter;

public class IngredientsResults1 extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String,List<String>> Data = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_results1);
        Bundle extras = getIntent().getExtras();
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        if (extras != null) {
            Data = (HashMap<String,List<String>>) extras.getSerializable("Data");
        }
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, Data);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
               /* listDataHeader.get(groupPosition)
                        + " : "
                        + listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPositio*/
                String Item=    Data.get(listDataHeader.get(groupPosition)).get(childPosition).toString();
                Toast.makeText(getApplicationContext(), Item, Toast.LENGTH_SHORT).show();
                Intent walmart = new Intent(IngredientsResults1.this, WalmartItems.class);
                walmart.putExtra("Item",Item);
                startActivity(walmart);
                return false;
            }
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        for (String Str:Data.keySet()) {
            listDataHeader.add(Str);
        }

    }
}


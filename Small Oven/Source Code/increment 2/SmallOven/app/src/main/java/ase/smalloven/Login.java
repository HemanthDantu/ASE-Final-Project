package ase.smalloven;

import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
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

import adapter.RecyclerAdapter;

public class Login extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        switch (item.getItemId()) {
            case R.id.Add:
                msg= item.getTitle().toString();

                break;
            case R.id.Selectall:

              //  List<SearchRecpie.Landscape> Items = ((RecyclerAdapter) recyclerView.getAdapter()).getData();

                break;
        }

        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
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
drawerfragment.setupNavDrawFrag(R.id.nav_drw_Frag,layout,toolbar);


    }
}

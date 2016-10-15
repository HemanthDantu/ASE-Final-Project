package ase.smalloven;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adapter.NavigationDrawerAdapter;


/**
 * Created by daras on 14-Oct-16.
 */
public class NavigationDrawerfragment extends android.support.v4.app.Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_nav_drawer,container,false);
        SetupRecyclerView(view);
        return view;
    }

    private void SetupRecyclerView(View view)
    {
        RecyclerView recyclerview = (RecyclerView)view.findViewById(R.id.drawerlist);
        NavigationDrawerAdapter adapter =  new NavigationDrawerAdapter(getActivity(),NavigationDrawerItem.getData());
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));


    }

    public void setupNavDrawFrag(int fragid,DrawerLayout drawerLayout, Toolbar toolbar)
    {
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,R.string.DrawOpen,R.string.DrawClose){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }


}

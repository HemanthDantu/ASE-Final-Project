package adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

import ase.smalloven.NavigationDrawerItem;
import ase.smalloven.R;

/**
 * Created by daras on 14-Oct-16.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {

    private List<NavigationDrawerItem> mdata = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public NavigationDrawerAdapter(Activity activity, List<NavigationDrawerItem> data) {

        this.mdata = data;
        this.context = activity;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.nav_drawer_listitem,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        NavigationDrawerItem current = mdata.get(position);
        holder.imgIcon.setImageResource(current.getImageID());
        holder.title.setText(current.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,holder.title.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });



    }


    @Override
    public int getItemCount() {
        return mdata.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView imgIcon;
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgIcon = (ImageView)itemView.findViewById(R.id.nav_Image);
            title = (TextView)itemView.findViewById(R.id.nav_text);

        }


    }

}



package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ase.smalloven.Landscape;
import ase.smalloven.R;
import ase.smalloven.RecpieDetails;
import ase.smalloven.SearchRecpie;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by daras on 08-Oct-16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<Landscape> mdata;
    private LayoutInflater mInflater;
    public static List<String> Favorites;
    public RecyclerAdapter(Context context,List<Landscape> data)
    {
        this.mdata = data;

        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = mInflater.inflate(R.layout.list_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Favorites = new ArrayList<>();
        Landscape CurrentObj = mdata.get(position);
       holder.setData(CurrentObj,position);
        holder.setListners();

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public void removeItem(int position)
    {
        try
        {
            mdata.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,mdata.size());
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final Context context;
        TextView title;
        TextView description;
        ImageView imgTumb;
        ImageView imgDelete;
        CheckBox chkBox;
        int position;
        Landscape current;

        public MyViewHolder(View itemView) {

            super(itemView);
            title = (TextView)itemView.findViewById(R.id.heading);
            imgTumb = (ImageView)itemView.findViewById(R.id.imageButton);
            description = (TextView)itemView.findViewById(R.id.sub);
            imgDelete = (ImageView)itemView.findViewById(R.id.delete);
            chkBox = (CheckBox)itemView.findViewById(R.id.checkBox);
            context = itemView.getContext();
        }


        public void setData(Landscape currentObj, int position) {
            this.title.setText(currentObj.getTitle());
            this.imgTumb.setImageBitmap(currentObj.getImage());
            this.description.setText(currentObj.getDescription());
            this.position= position;
            this.current = currentObj;

        }

        public void setListners()
        {
            imgDelete.setOnClickListener(MyViewHolder.this);
            chkBox.setOnClickListener(MyViewHolder.this);
            title.setOnClickListener(MyViewHolder.this);


        }



        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.delete:
                    removeItem(position);
                    break;
                case R.id.checkBox:
                    CheckBox cb = (CheckBox)v;
                    String obj = mdata.get(position).getId();
                  if(cb.isSelected())
                  {
                      cb.setSelected(false);

                      Favorites.remove(obj);

                  }
                    else
                  {
                      cb.setSelected(true);
                      Favorites.add(obj);
                  }

                    mdata.get(position).setIsSeleted(cb.isChecked());
                   // Toast.makeText(v.getContext(),mdata.get(position).getTitle()+ " " + mdata.get(position).getId()+ "is " + cb.isChecked(),Toast.LENGTH_LONG).show();
                   break;
                case R.id.heading:
                    Intent ii = new Intent(context, RecpieDetails.class);
                    ii.putExtra("id",mdata.get(position).getId());
                    context.startActivity(ii);
                    break;
            }
        }
    }

}


package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import ase.smalloven.Landscape;
import ase.smalloven.R;
import ase.smalloven.RecpieDetails;
import ase.smalloven.walmartitemdetail;

/**
 * Created by daras on 03-Dec-16.
 */
public class WalmartItemsAdapter extends RecyclerView.Adapter<WalmartItemsAdapter.MyViewHolder> {

    private List<Landscape> mdata;
    private LayoutInflater mInflater;

    public WalmartItemsAdapter(Context context, List<Landscape> data)
    {
        this.mdata = data;

        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.ls, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

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
        int position;
        Landscape current;

        public MyViewHolder(View itemView) {

            super(itemView);
            title = (TextView)itemView.findViewById(R.id.heading);
            imgTumb = (ImageView)itemView.findViewById(R.id.imageButton);
            description = (TextView)itemView.findViewById(R.id.sub);
            imgDelete = (ImageView)itemView.findViewById(R.id.delete);
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
            title.setOnClickListener(MyViewHolder.this);


        }



        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.delete:
                    removeItem(position);
                    break;

                case R.id.heading:
                    Intent ii = new Intent(context, walmartitemdetail.class);
                    ii.putExtra("Title",mdata.get(position).getId());
                    context.startActivity(ii);
                    /*Toast.makeText(v.getContext(),"Its Working",Toast.LENGTH_SHORT).show();
                    break;*/
            }
        }
    }

}



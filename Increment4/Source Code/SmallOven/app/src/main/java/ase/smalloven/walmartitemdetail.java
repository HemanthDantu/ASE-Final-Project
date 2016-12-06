package ase.smalloven;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class walmartitemdetail extends AppCompatActivity {

    String ID;
    String ImageURL;
    String UPC;
    String Price;
    String ShippingRate;
    String Stock;
    String Discription;
    String Title;
    String AddtoCartURL;
    TextView txtUPC;
    TextView txtPrice;
    TextView txtShippingRate;
    TextView txtStock;
    TextView txtDispription;
    ImageView Item_Image;
    TextView txtName;
    Bitmap bitmap;
    Button AddtoCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walmartitemdetail);
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
             ID = extras.getString("Title");
           // Toast.makeText(this, ID,Toast.LENGTH_SHORT).show();
        }
        txtUPC = (TextView)findViewById(R.id.txtUPC);
        txtPrice = (TextView)findViewById(R.id.txtPrice);
        txtShippingRate = (TextView)findViewById(R.id.txtShippingRate);
        txtStock = (TextView)findViewById(R.id.txtStock);
        txtDispription = (TextView)findViewById(R.id.txtWalmartDescription);
        Item_Image = (ImageView)findViewById(R.id.imgWalmartItem);
        txtName = (TextView)findViewById(R.id.txtItemName);
        AddtoCart = (Button)findViewById(R.id.btnAddtoCart);


        List<WalmartItemDetails> Data = WalmartItems.getData();
        AddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AddtoCartURL!="")
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AddtoCartURL));
                    startActivity(browserIntent);
                }

            }
        });

        for(int i=0;i<Data.size();i++)
        {
            String Value = Data.get(i).getItemID();

            if(Value.trim().equals(ID.trim()))
            {
                GetData(Data.get(i));
                break;
            }


        }

    }

    private void GetData(WalmartItemDetails obj)
    {
        try {

            AddtoCartURL=obj.getAddToCartUrl();
            Title = obj.getName();
            ImageURL = obj.getImage();
            UPC = obj.getUPCNo();
            Price = obj.getPrice();
            ShippingRate = obj.getStandardShipRate();
            Stock = obj.getAvailableOnline();
            Discription = obj.getDescription();
            new DownloadImages().execute();
            txtName.setText(Title);
            txtUPC.setText(UPC);
            txtPrice.setText("$ " + Price);
            txtShippingRate.setText("$ " +ShippingRate);
            txtStock.setText(Stock);
            String Data = Html.fromHtml(Discription).toString();
            txtDispription.setText(Html.fromHtml(Data));
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private class DownloadImages extends AsyncTask<String,Integer,Double>
    {


        private void getData() {

            try
            {

                     bitmap = BitmapFactory.decodeStream((InputStream) new URL(ImageURL).getContent());



            }catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }
        @Override
        protected Double doInBackground(String... params) {
            getData();
            return null;
        }

        @Override
        protected void onPostExecute(Double result)
        {
            try
            {
                Item_Image.setImageBitmap(bitmap);
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }
}

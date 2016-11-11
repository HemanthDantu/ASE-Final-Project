package ase.smalloven;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class nutritiondetails extends AppCompatActivity {

    TextView Protein;
    TextView Fat;
    TextView Carbohydrates;

    BarChart Chart1;
    PieChart Chart2;
    HashMap<String,String> Nutrition;
    HashMap<String,String> CaloricBreak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritiondetails);
        Bundle extras = getIntent().getExtras();
        Nutrition = new HashMap<>();
        CaloricBreak =  new HashMap<>();

        if (extras != null) {
            Nutrition =(HashMap<String,String>) extras.get("Nutrition");
            CaloricBreak =(HashMap<String,String>) extras.get("CaloricBreak");
        }

        Protein = (TextView)findViewById(R.id.txtProtein);
        Fat = (TextView)findViewById(R.id.txtFat);
        Carbohydrates = (TextView)findViewById(R.id.txtCarbo);

        Chart1 = (BarChart)findViewById(R.id.Chart1);
        Chart2 = (PieChart)findViewById(R.id.Chart2);
        setTextValues();
        setChart1();
        setChat2();
    }

    private void setChart1()
    {
        try
        {

            final  String[] strChart1 = new String[]{"Calories","Fat","Carbohydrates","Sugar","Cholesterol","Sodium"};
            List<BarEntry> Entry1 = new ArrayList<>();
            Float Calories = Float.parseFloat(Nutrition.get("Calories"));
            Float Fat = Float.parseFloat(Nutrition.get("Fat"));

            Float Carbohydrates = Float.parseFloat(Nutrition.get("Carbohydrates"));
            Float Sugar = Float.parseFloat(Nutrition.get("Sugar"));
            Float Cholesterol = Float.parseFloat(Nutrition.get("Cholesterol"));
            Float Sodium = Float.parseFloat(Nutrition.get("Sodium"));

            Entry1.add(new BarEntry(0,Calories,"Calories"));
            Entry1.add(new BarEntry(1,Fat,"Fat"));
            Entry1.add(new BarEntry(2,Carbohydrates,"Carbohydrates"));
            Entry1.add(new BarEntry(3,Sugar,"Sugar"));
            Entry1.add(new BarEntry(4,Cholesterol,"Cholesterol"));
            Entry1.add(new BarEntry(5,Sodium,"Sodium"));

            IAxisValueFormatter formatter = new IAxisValueFormatter() {

                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return strChart1[(int) value];
                }

                // we don't draw numbers, so no decimal digits needed
                @Override
                public int getDecimalDigits() {  return 0; }
            };

            BarDataSet data = new BarDataSet(Entry1,"");
            data.setBarBorderWidth(1f);
            data.setValueTextSize(8f);
            ArrayList<Integer> colors = new ArrayList<Integer>();

            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.LIBERTY_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.PASTEL_COLORS)
                colors.add(c);
            data.setColors(colors);
            BarData finaldata = new BarData(data);

            XAxis axis =  Chart1.getXAxis();
            axis.setValueFormatter(formatter);
            axis.setGranularity(1f);
            Chart1.setData(finaldata);
            Chart1.animateXY(2000, 2000);
            Chart1.invalidate();
            Chart1.getDescription().setEnabled(false);

            Chart1.setHighlightPerTapEnabled(true);

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void setChat2()
        {
            try
            {
                List<PieEntry> Entry = new ArrayList<>();
                Float protein=0f;
                Float Calcium=0f;
                Float Iron=0f;
                Float Fiber=0f;
                if(Nutrition.containsKey("Protein"))
                {
                     protein = Float.parseFloat(Nutrition.get("Protein"));
                    Entry.add(new PieEntry(protein+10,"Protein"));
                }
                if(Nutrition.containsKey("Calcium"))
                {
                     Calcium = Float.parseFloat(Nutrition.get("Calcium"));
                    Entry.add(new PieEntry((Calcium)/10,"Calcium"));

                }

                if(Nutrition.containsKey("Iron"))
                {
                    Iron = Float.parseFloat(Nutrition.get("Iron"));
                    Entry.add(new PieEntry(Iron+10,"Iron"));

                }
                if(Nutrition.containsKey("Fiber"))
                {
                    Fiber = Float.parseFloat(Nutrition.get("Fiber"));
                    Entry.add(new PieEntry(Fiber+10, "Fiber"));
                }

                ArrayList<Integer> colors = new ArrayList<Integer>();

                for (int c : ColorTemplate.VORDIPLOM_COLORS)
                    colors.add(c);

                for (int c : ColorTemplate.JOYFUL_COLORS)
                    colors.add(c);

                for (int c : ColorTemplate.COLORFUL_COLORS)
                    colors.add(c);

                for (int c : ColorTemplate.LIBERTY_COLORS)
                    colors.add(c);

                for (int c : ColorTemplate.PASTEL_COLORS)
                    colors.add(c);



                PieDataSet Data = new PieDataSet(Entry,"");
                Data.setColors(ColorTemplate.COLORFUL_COLORS);
                Data.setValueTextSize(8f);
                //Data.setDrawValues(false);
                Data.setValueTextColor(R.color.Grey700);
                PieData FinalData = new PieData(Data);
                Chart2.setData(FinalData);
                Chart2.animateXY(2000, 2000);
                Chart2.invalidate();
                Chart2.setSelected(true);
                Chart2.getDescription().setEnabled(false);


            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    private void setTextValues()
    {
        try
        {
            if(CaloricBreak.containsKey("percentProtein"))
            {
                Protein.setText(CaloricBreak.get("percentProtein"));
            }
            if(CaloricBreak.containsKey("percentFat"))
            {
                Fat.setText(CaloricBreak.get("percentFat"));
            }
            if(CaloricBreak.containsKey("percentCarbs"))
            {
                Carbohydrates.setText(CaloricBreak.get("percentCarbs"));
            }


        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

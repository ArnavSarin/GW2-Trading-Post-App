package com.example.arnavsarin.gw2app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;



import java.util.ArrayList;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.DataPointInterface;


/**
 * Created by Arnav Sarin on 1/13/2018.
 */

public class SecondScreenMain extends AppCompatActivity {
    LineGraphSeries<DataPoint> series;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_view);
        Intent intent = getIntent();
        Product prod = (Product) intent.getSerializableExtra("Gson file");
        graphProduct(prod);


    }

    public void graphProduct(Product prod) {
        GraphView graph = (GraphView) findViewById(R.id.graph);
        GraphViewSeries exampleSeries;
        GraphView.GraphViewData [] g1 = new GraphView.GraphViewData  [prod.getSells().size()];
        for(int i=0; i<prod.getSells().size();i++){
              g1[i]=new GraphView.GraphViewData(prod.getSells().get(i).getUnit_price(),prod.getSells().get(i).getListings());
        }
        exampleSeries = new GraphViewSeries(g1);
        graph.addSeries(exampleSeries);
        //series= new LineGraphSeries<DataPoint>();
        /*DataPoint[] dataPoints= new DataPoint[prod.getSells().size()];
        for (int i = 0; i < prod.getSells().size(); i++) {
            dataPoints[i]=new DataPoint((prod.getSells().get(i).getUnit_price(),prod.getSells().get(i).getListings());
            //series.appendData(new DataPoint(prod.getSells().get(i).getUnit_price(), prod.getSells().get(i).getListings()), true, prod.getSells().size());
        }
        series = new LineGraphSeries<DataPoint>(dataPoints);*/
    }
}

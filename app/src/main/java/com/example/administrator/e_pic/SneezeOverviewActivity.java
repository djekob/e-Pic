package com.example.administrator.e_pic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;


public class SneezeOverviewActivity extends ActionBarActivity {
    private ArrayList<Sneeze> sneezeList;
    TreeMap<Integer, Sneeze> sneezeMapDef;
    private LineGraphSeries<DataPoint> series;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sneeze_overview);
        GraphView graph = (GraphView) findViewById(R.id.graph);

        username = getIntent().getStringExtra(Connections.NAAM_VAR_USER);

        ArrayListVuller();

        series = new LineGraphSeries<DataPoint>(generateData());
        graph.addSeries(series);

    }

    private void ArrayListVuller() {

        sneezeList = new ArrayList<>();

        sneezeMapDef= new TreeMap<>((Map<Integer, Sneeze>)  getIntent().getSerializableExtra(Connections.TAG_SNEEZES));

        for(Integer i : sneezeMapDef.keySet()) {
            sneezeList.add(sneezeMapDef.get(i));
        }
    }

    private DataPoint[] generateData()  {
        int width = 5;
        DataPoint values[] = new DataPoint[width];

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date current = new Date();
        int datum = current.getDate();

        for(int i=0;i<width;i++){
            double x = i+datum-4;
            double y = countPerDate(x);
            DataPoint v = new DataPoint(x,y);
            values[i]=v;
        }

        return values;
    }

    private double countPerDate(double datum)  {
        double i=0;

        for(int k=0;k<sneezeList.size();k++){
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String s = sneezeList.get(k).getTime().substring(0,10);
            String name = sneezeList.get(k).getUser().getName();
            System.out.println(username + "=" + name);
            try {
                if((double) format.parse(s).getDate() == datum && name.compareToIgnoreCase(username)==0){
                    i++;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return i;
    }
}

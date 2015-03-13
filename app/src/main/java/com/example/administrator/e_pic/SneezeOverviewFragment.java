package com.example.administrator.e_pic;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class SneezeOverviewFragment extends android.support.v4.app.Fragment implements Runnable {

    public ArrayList<Sneeze> sneezeList;
    private LineGraphSeries<DataPoint> series;
    public GraphView graph;
    private TextView startTitle;
    private TextView endTitle;
    private Calendar myCalendarStart;
    private Calendar myCalendarEnd;
    private DatePickerDialog.OnDateSetListener dateStart;
    private DatePickerDialog.OnDateSetListener dateEnd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("SneezeOverviewFragment werkt");
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.activity_sneeze_overview, container, false);
        initialize(rootView);

        dateEnd = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR,year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }

        };

        dateStart = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }

        };

        startTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog dia = new DatePickerDialog(getActivity(), dateStart, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH));
                dia.getDatePicker().setMaxDate(myCalendarEnd.getTime().getTime() - 24 * 60 * 60 * 1000);
                dia.show();
            }
        });

        endTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog dia = new DatePickerDialog(getActivity(), dateEnd, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH));
                dia.getDatePicker().setMaxDate(new Date().getTime());
                dia.show();
            }
        });
        return rootView;
        // Inflate the layout for this fragment
    }

    private void update(){
        long diff =myCalendarEnd.getTimeInMillis()-myCalendarStart.getTimeInMillis();
        long days = (diff / (24 * 60 * 60 * 1000))+1;
        System.out.println("aantal dagen : " + days);

        DataPoint data[] = new DataPoint[(int)days];
        String[] dagen = new String[(int) days];
        String[] xlabels = new String[(int) days];

        Calendar time = Calendar.getInstance();

        for(int i=0;i<days;i++){
            time.setTimeInMillis(myCalendarStart.getTimeInMillis() + (i * 24 * 60 * 60 * 1000));

            int dag = time.get(Calendar.DAY_OF_MONTH);
            int maand = time.get(Calendar.MONTH)+1;
            int jaar = time.get(Calendar.YEAR);

            String t = jaar+"-";

            if(maand>10){
                t=t+maand;
            }
            else{
                t=t+0+maand;
            }
            t=t+"-";
            if(dag>10){
                t=t+dag;
            }
            else{
                t=t+0+dag;
            }



            dagen[i]=t;
            xlabels[i]=dag+"/"+maand;
            DataPoint v = new DataPoint(myCalendarStart.getTimeInMillis() + (i * 24 * 60 * 60 * 1000),0); //x is time in milllis
            data[i]=v;
        }

        for(int k=0;k<sneezeList.size();k++) {
            String s = sneezeList.get(k).getTime().substring(0, 10);
            for(int i=0;i<days;i++){
                if(s.equals(dagen[i])){
                    data[i]=new DataPoint(data[i].getX(),data[i].getY()+1);
                }
            }
        }

        boolean empty = true;

        for(DataPoint d:data) {
            if (empty) {
                if (d.getY() != 0) {
                    empty = false;
                }
            }
        }

        if(!empty){
            series.resetData(data);
            StaticLabelsFormatter labelsFormatter = new StaticLabelsFormatter(graph);
            labelsFormatter.setHorizontalLabels(xlabels);
            System.out.println(graph.getLegendRenderer().getSpacing()+" de plaats tussen legende");
            graph.getGridLabelRenderer().setLabelFormatter(labelsFormatter);
            graph.addSeries(series);
            graph.refreshDrawableState();
            graph.setTitle("Overview Sneezes last days!");
        }
        else{
            graph.setTitle("Niet geniest in deze periode!");
        }
    }

    private void updateLabelStart(){

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        startTitle.setText(sdf.format(myCalendarStart.getTime()));
        update();
    }

    private void updateLabelEnd(){

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        endTitle.setText(sdf.format(myCalendarEnd.getTime()));
        update();
    }

    private void initialize(ViewGroup rootview) {
        graph = (GraphView) rootview.findViewById(R.id.graph);
        graph.setTitle("Overview Sneezes last days!");
        graph.setTitleTextSize(30);
        graph.setTitleColor(this.getResources().getColor(R.color.indigo_licht));
        graph.getViewport().setScrollable(true);
        //graph.getViewport().setScalable(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(new Date().getTime() - (10 * 24 * 60 * 60 * 1000));
        graph.getViewport().setMaxX(new Date().getTime());
        graph.getViewport().setXAxisBoundsStatus(Viewport.AxisBoundsStatus.AUTO_ADJUSTED);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        //graph.getGridLabelRenderer().setNumHorizontalLabels(data.length);

        series = new LineGraphSeries<>();
        series.setColor(this.getResources().getColor(R.color.indigo_licht));

        myCalendarEnd  = Calendar.getInstance();
        myCalendarStart = Calendar.getInstance();
        myCalendarStart.setTimeInMillis(myCalendarEnd.getTimeInMillis() - (4 * 24 * 60 * 60 * 1000));

        startTitle = (TextView) rootview.findViewById(R.id.title_startCalendar);
        startTitle.setClickable(true);
        endTitle = (TextView) rootview.findViewById(R.id.title_endCalendar);
        endTitle.setClickable(true);

        String myFormat = "yyyy-MM-dd ";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        startTitle.setText(sdf.format(myCalendarStart.getTime()));
        endTitle.setText(sdf.format(myCalendarEnd.getTime()));

        sneezeList = new ArrayList<>();
        BigClass bigClass = BigClass.ReadData(getActivity());
        if(bigClass!=null) {
            sneezeList.addAll(bigClass.getOwnSneezes());
            System.out.println(bigClass.getOwnSneezes());
            System.out.println("bigClass != null");
        }
        //(ArrayList<Sneeze>) getIntent().getSerializableExtra(Connections.TAG_SNEEZES);

        update();
    }

    @Override
    public void run() {
        BigClass bigClass = BigClass.ReadData(getActivity());
        if(bigClass!=null) {
            sneezeList.clear();
            sneezeList.addAll(bigClass.getOwnSneezes());
            sneezeList.addAll(bigClass.getNotSendSneezes());
            System.out.println(bigClass.getOwnSneezes());
            System.out.println("bigClass != null");
        }
        update();
        //System.out.println(sneezeList);
        //Toast.makeText(getActivity(), "update", Toast.LENGTH_SHORT).show();
    }
}

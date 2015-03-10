package com.example.administrator.e_pic;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 *
 */
public class ScreenSlidePageFragment extends android.support.v4.app.Fragment implements Runnable{
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    private TextView myNameTextView;
    private Button isneeze_image_button;
    private String username;
    private User user;

    public ArrayList<Sneeze> sneezeList;
    private LineGraphSeries<DataPoint> series;
    private GraphView graph;
    private TextView startTitle;
    private TextView endTitle;
    private Calendar myCalendarStart;
    private Calendar myCalendarEnd;
    private DatePickerDialog.OnDateSetListener dateStart;
    private DatePickerDialog.OnDateSetListener dateEnd;



    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(int pageNumber) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView=null;
        // Inflate the layout containing a title and body text.
        user = SaveSharedPreference.getUser(getActivity());
        username = user.getUsername();
        switch (mPageNumber) {
            case 0:
                new Connections(getActivity(), Connections.GET_ALL_SNEEZES_GRAPH_CODE);
                rootView = (ViewGroup) inflater
                        .inflate(R.layout.activity_sneeze_overview, container, false);

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
            break;
            case 1:
                rootView = (ViewGroup) inflater
                        .inflate(R.layout.fragment_i_sneeze, container, false);
                isneeze_image_button = (Button) rootView.findViewById(R.id.i_sneeze_button_i_sneeze_fragment);
                myNameTextView = (TextView) rootView.findViewById(R.id.my_name_textview);

                myNameTextView.setText(username);

                isneeze_image_button.setOnClickListener(new SneezeClickListener());
                break;
            case 2:
                break;

        }






        // Set the title view to show the page number.
        /*((TextView) rootView.findViewById(android.R.id.text1)).setText(
                getString(R.string.title_template_step, mPageNumber + 1));*/

        return rootView;
    }

    @Override
    public void run() {

    }


    /*
    public class SneezeClickListenertest implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent i = new Intent(getActivity(), test.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            getActivity().startActivity(i);

        }
    }
    */

    public class SneezeClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            new Connections(getActivity(), Connections.CREATE_SNEEZE_CODE);

        }
    }

    private void update(){
        long diff =myCalendarEnd.getTimeInMillis()-myCalendarStart.getTimeInMillis();
        long days = (diff / (24 * 60 * 60 * 1000))+1;
        System.out.println("aantal dagen : " + days);

        DataPoint data[] = new DataPoint[(int)days];
        String[] dagen = new String[(int) days];
        //String[] xlabels = new String[(int) days];

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

            t=t+"-";

            if(dag>10){
                t=t+dag;
            }
            else{
                t=t+0+dag;
            }

            dagen[i]=t;
            //xlabels[i]=dag+"/"+maand;
            DataPoint v = new DataPoint(myCalendarStart.getTimeInMillis() + (i * 24 * 60 * 60 * 1000),0); //x is time in milllis
            data[i]=v;
        }

        for(int k=0;k<sneezeList.size();k++) {
            String s = sneezeList.get(k).getTime().substring(0, 10);
            for(int i=0;i<days;i++){
                //System.out.println(dagen[i]);
                // System.out.println(s);
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
            //labelsFormatter.setHorizontalLabels(xlabels);
            graph.getGridLabelRenderer().setLabelFormatter(labelsFormatter);
            graph.addSeries(series);
            graph.refreshDrawableState();
        }
        else{
            Toast.makeText(getActivity(), "Je hebt de laatste dagen niet geniest!", Toast.LENGTH_SHORT).show();
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
        graph.setTitleColor(this.getResources().getColor(R.color.darkorange));
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(new Date().getTime() - (10 * 24 * 60 * 60 * 1000));
        graph.getViewport().setMaxX(new Date().getTime());
        graph.getViewport().setXAxisBoundsStatus(Viewport.AxisBoundsStatus.AUTO_ADJUSTED);

        //graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.BOTH);
        //graph.getGridLabelRenderer().setNumHorizontalLabels(data.length);

        series = new LineGraphSeries<>();
        series.setColor(this.getResources().getColor(R.color.orange));

        myCalendarEnd  =Calendar.getInstance();
        myCalendarStart = Calendar.getInstance();
        myCalendarStart.setTimeInMillis(myCalendarEnd.getTimeInMillis() - (4 * 24 * 60 * 60 * 1000));

        startTitle = (TextView) rootview.findViewById(R.id.title_startCalendar);
        startTitle.setClickable(true);
        endTitle = (TextView) rootview.findViewById(R.id.title_endCalendar);
        endTitle.setClickable(true);

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        startTitle.setText(sdf.format(myCalendarStart.getTime()));
        endTitle.setText(sdf.format(myCalendarEnd.getTime()));

        sneezeList = new ArrayList<>();//(ArrayList<Sneeze>) getIntent().getSerializableExtra(Connections.TAG_SNEEZES);

        update();
    }
    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}

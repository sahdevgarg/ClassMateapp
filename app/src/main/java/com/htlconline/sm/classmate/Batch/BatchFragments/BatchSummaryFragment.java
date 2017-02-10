package com.htlconline.sm.classmate.Batch.BatchFragments;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.htlconline.sm.classmate.Batch.BatchActivity;
import com.htlconline.sm.classmate.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BatchSummaryFragment extends Fragment {


    private CheckedTextView mToolbarToggle;
    private TextView batch_title, batch_academic_year, summary_academic_year_value,
            summary_product_value, summary_centre_value, summary_subject_value, summary_instructor_value;
    private PieChart pieChart;
    private float[] yData = {18.8f, 35.7f, 45.5f, 0f};
    private String[] xData = {"Absent", "Present", "Late Joined", "Late present"};
    private Description description = new Description();
    ArrayList<LegendEntry> xEntries;

    public BatchSummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(BatchActivity.title);
        mToolbarToggle = (CheckedTextView) getActivity().findViewById(R.id.toolbar_toggle);
        mToolbarToggle.setVisibility(View.GONE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_batch_summary, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(BatchActivity.title);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        batch_title = (TextView) view.findViewById(R.id.batch_title);
        batch_academic_year = (TextView) view.findViewById(R.id.batch_academic_year);
        summary_academic_year_value = (TextView) view.findViewById(R.id.summary_academic_year_value);
        summary_centre_value = (TextView) view.findViewById(R.id.summary_centre_value);
        summary_instructor_value = (TextView) view.findViewById(R.id.summary_instructor_value);
        summary_product_value = (TextView) view.findViewById(R.id.summary_product_value);
        summary_subject_value = (TextView) view.findViewById(R.id.summary_subject_value);
        pieChart = (PieChart) view.findViewById(R.id.summary_attendance_chart);

        //configure pie chart
        pieChart.setUsePercentValues(true);
        description.setTextSize(10);
        description.setText("Attendance Summary");
        pieChart.setDescription(description);
        //enable hole and configure
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(20);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setTransparentCircleRadius(30);

        //enable rotation by touch

        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);

        addData();

        Legend l = pieChart.getLegend();
        int colorCodes[] = l.getColors();
        int i = 0;
        xEntries = new ArrayList<>();

        for (String aXData : xData) {
            xEntries.add(new LegendEntry(aXData, Legend.LegendForm.SQUARE, 6f, 6f, null, colorCodes[i++]));
        }

        l.setCustom(xEntries);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(5f);


    }

    private void addData() {


        ArrayList<PieEntry> yEntries = new ArrayList<>();
        for (int i = 0; i < yData.length; i++)
            yEntries.add(new PieEntry(yData[i], i));

        PieDataSet dataSet = new PieDataSet(yEntries, "Attendance");

        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);
        dataSet.setValueTextColor(Color.BLACK);

        //add colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.MATERIAL_COLORS)
            colors.add(c);


        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);


        //instantiate pie data object

        PieData data = new PieData(dataSet);

        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);


        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

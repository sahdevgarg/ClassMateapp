package com.htlconline.sm.classmate.Batch.BatchFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.htlconline.sm.classmate.Batch.Adapters.BatchFilterListAdapter;
import com.htlconline.sm.classmate.Batch.Data.BatchFilterData;
import com.htlconline.sm.classmate.Batch.Data.BatchFilterResults;
import com.htlconline.sm.classmate.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BatchFilterFragment extends Fragment implements View.OnClickListener {

    private TextView instructor;
    private TextView academic_year;
    private TextView class_filter;
    private TextView subject;
    private TextView products;
    private TextView zones;
    private TextView centre;
    private PopupWindow pw;
    private String[] choiceArray;
    public static BatchFilterData[] batchFilterData = null;
    public static HashMap<String, Integer> map = new HashMap<>();
    public static List<String> results;
    private final String Instructor = "Instructors";
    private final String AcademicYear = "Academic Year";
    private final String ClassFilter = "Classes";
    private final String Subject = "Subjects";
    private final String Products = "Products";
    private final String Zones = "Zones";
    private final String Centre = "Centres";


    public BatchFilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_batch_filter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        instructor = (TextView) view.findViewById(R.id.instructor);
        instructor.setOnClickListener(BatchFilterFragment.this);
        academic_year = (TextView) view.findViewById(R.id.academic_year);
        academic_year.setOnClickListener(BatchFilterFragment.this);
        class_filter = (TextView) view.findViewById(R.id.class_filter);
        class_filter.setOnClickListener(BatchFilterFragment.this);
        subject = (TextView) view.findViewById(R.id.subject);
        subject.setOnClickListener(BatchFilterFragment.this);
        products = (TextView) view.findViewById(R.id.products);
        products.setOnClickListener(BatchFilterFragment.this);
        zones = (TextView) view.findViewById(R.id.zones);
        zones.setOnClickListener(BatchFilterFragment.this);
        centre = (TextView) view.findViewById(R.id.centre);
        centre.setOnClickListener(BatchFilterFragment.this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.popBackStack();
        }


        return true;

    }

    @Override
    public void onClick(View view) {

        Log.d("Test", "clicked");
        String Title = null;
        if (view == instructor) {
            choiceArray = getActivity().getResources().getStringArray(R.array.choices_dummy);
            Title = Instructor;
        } else if (view == academic_year) {
            choiceArray = getActivity().getResources().getStringArray(R.array.choices_dummy);
            Title = AcademicYear;

        } else if (view == class_filter) {
            choiceArray = getActivity().getResources().getStringArray(R.array.choices_dummy);
            Title = ClassFilter;

        } else if (view == subject) {
            choiceArray = getActivity().getResources().getStringArray(R.array.choices_dummy);
            Title = Subject;

        } else if (view == products) {
            choiceArray = getActivity().getResources().getStringArray(R.array.choices_dummy);
            Title = Products;

        } else if (view == zones) {
            choiceArray = getActivity().getResources().getStringArray(R.array.choices_dummy);
            Title = Zones;


        } else if (view == centre) {
            choiceArray = getActivity().getResources().getStringArray(R.array.choices_dummy);
            Title = Centre;

        }
        batchFilterData = new BatchFilterData[choiceArray.length];
        for (int i = 0; i < choiceArray.length; i++) {
           // if (!map.containsKey(choiceArray[i])) {
                batchFilterData[i] = new BatchFilterData(choiceArray[i], 0);
                map.put(choiceArray[i], i);
            //}
        }
        initiatePopupWindow(view, batchFilterData, Title);

    }

    private void initiatePopupWindow(View view, final BatchFilterData[] batchFilterData, final String title) {
        Log.d("Test pop", batchFilterData.length + "");
        final ListView listView;
        EditText editText;
        TextView textView;
        Button button;
        TextInputLayout textInputLayout;
        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.batch_filter_choices,
                    (ViewGroup) view.findViewById(R.id.pop_up));
            // create a 300px width and 470px height PopupWindow
            pw = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            // display the popup in the center
            pw.showAtLocation(view, Gravity.CENTER, 0, 0);
            pw.setFocusable(true);
            pw.update();
            button = (Button) layout.findViewById(R.id.filter_done);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setResults(title);
                }
            });
            textView = (TextView) layout.findViewById(R.id.filter_title);
            listView = (ListView) layout.findViewById(R.id.choiceListView);
            editText = (EditText) layout.findViewById(R.id.choice_text);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    updateList(charSequence, listView);

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            textInputLayout = (TextInputLayout) layout.findViewById(R.id.choice_search_layout);
            if (view != instructor) {
                textInputLayout.setVisibility(View.GONE);
            } else textInputLayout.setVisibility(View.VISIBLE);
            textView.setText(title);
            BatchFilterListAdapter batchFilterListAdapter = new BatchFilterListAdapter(getActivity(), batchFilterData);
            listView.setAdapter(batchFilterListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                }
            });

        } catch (Exception e) {
            // Log.d("Test", "Exception Raised");
            e.printStackTrace();
        }
    }

    private void setResults(String title) {
        results = new ArrayList<>();
        for (BatchFilterData aBatchFilterData : batchFilterData) {
            if (aBatchFilterData.getValue() == 1) {
                Log.d("Test", aBatchFilterData.getName());
                results.add(aBatchFilterData.getName());
            }
        }
        fitResults(title);

    }

    private void fitResults(String title) {
        BatchFilterResults temp = new BatchFilterResults();
        switch (title) {
            case Instructor:
                Log.d("Test ins", results.toString());
                temp.setInstructors(results);
                break;
            case Subject:
                Log.d("Test sub", results.toString());
                temp.setSubjects(results);
                break;
            case Centre:
                Log.d("Test cen", results.toString());
                temp.setCentres(results);
                break;
            case Zones:
                Log.d("Test zon ", results.toString());
                temp.setZones(results);
                break;
            case AcademicYear:
                Log.d("Test aca", results.toString());
                temp.setAcademic_Year(results);
                break;
            case Products:
                Log.d("Test pro", results.toString());
                temp.setProducts(results);
                break;
            case ClassFilter:
                Log.d("Test class", results.toString());
                temp.setClasses(results);
                break;
            default:
                break;

        }
    }

    private void updateList(CharSequence charSequence, ListView listView) {
        String required = charSequence.toString().toLowerCase();
        List<BatchFilterData> requiredFields = new ArrayList<>();
        for (BatchFilterData data : batchFilterData) {
            if (data.getName().toLowerCase().contains(required)) {
                requiredFields.add(new BatchFilterData(data.getName(), data.getValue()));
            }

        }
        BatchFilterData[] requiredData = new BatchFilterData[requiredFields.size()];
        BatchFilterData Data;
        for (int l = 0; l < requiredFields.size(); l++) {
            Data = requiredFields.get(l);
            requiredData[l] = new BatchFilterData(Data.getName(), Data.getValue());
        }
        BatchFilterListAdapter batchFilterListAdapter = new BatchFilterListAdapter(getActivity(), requiredData);
        listView.setAdapter(batchFilterListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (pw != null)
                        if (pw.isShowing())
                            pw.dismiss();
                        else getActivity().getSupportFragmentManager().popBackStack();
                    else {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }


                    return true;
                }
                return false;
            }
        });
    }


}

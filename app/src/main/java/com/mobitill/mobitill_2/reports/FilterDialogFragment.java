package com.mobitill.mobitill_2.reports;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.utils.DatePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by james on 11/9/2016.
 */

public class FilterDialogFragment extends DialogFragment {

    private static final String ARGS_MODELS = "args_models";

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.button_filter) ImageButton mFilterButton;
    @BindView(R.id.button_to) Button mToButton;
    @BindView(R.id.button_from) Button mFromButton;
    @BindView(R.id.spinner_layout) LinearLayout mSpinnerLinearLayout;
    private static final String DIALOG_DATE = " DialogDate";
    private static final int REQUEST_FROM_DATE = 0;
    private static final int REQUEST_TO_DATE = 1;
    private List<Long> mDates = new ArrayList<>();
    private HashMap<String, String> mFilterItems  = new HashMap<>();
    private HashMap<String, List<HashMap<String, String>>>  mModels = new HashMap<>();



    private Unbinder mUnbinder;

    public FilterDialogFragment(){

    }

    public interface FilterDialogListener{
        void onFinishedFiltering(List<Long> range, HashMap<String, String> items);
    }

    public static FilterDialogFragment newInstance(HashMap<String, List<HashMap<String, String>>> filterItems) {
        
        Bundle args = new Bundle();
        args.putSerializable(ARGS_MODELS, filterItems);
        FilterDialogFragment fragment = new FilterDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARGS_MODELS, mModels);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_filter_dialog, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis to get midnight
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        mDates.add( date.getTime().getTime());
        mDates.add(new Date().getTime());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setHasOptionsMenu(true);
        if(savedInstanceState == null){
            mModels = (HashMap<String, List<HashMap<String, String>>>) getArguments().getSerializable(ARGS_MODELS);
        } else {
            mModels = (HashMap<String, List<HashMap<String, String>>>) savedInstanceState.getSerializable(ARGS_MODELS);
        }

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //mToolbar.setTitle("Filter");

        mToolbar.setNavigationIcon(R.drawable.ic_clear);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // handle back button naviagtion
                dismiss();
            }
        });

        mFilterButton = (ImageButton) view.findViewById(R.id.button_filter);
        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBackResult();
            }
        });


        // set up spinners
        setUpSpinners();

       // mToolbar.inflateMenu(R.menu.filter_fragment_menu);
        // getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.button_from)
    public void from(View view){
        FragmentManager manager = getFragmentManager();
        DatePickerFragment dialog = DatePickerFragment.newInstance(new Date());
        dialog.setTargetFragment(FilterDialogFragment.this,REQUEST_FROM_DATE);
        dialog.show(manager, DIALOG_DATE);
    }

    @OnClick(R.id.button_to)
    public void to(View view){
        FragmentManager manager = getFragmentManager();
        DatePickerFragment dialog = DatePickerFragment.newInstance(new Date());
        dialog.setTargetFragment(FilterDialogFragment.this, REQUEST_TO_DATE);
        dialog.show(manager, DIALOG_DATE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode  == REQUEST_FROM_DATE){
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mDates.remove(0);

            mDates.add(0, getStartOfDay(date).getTime());
            //mPresenter.fetchReports(mAppId, mDates, mProductId, mCashierId);
            mFromButton.setText("From: "  + getFormattedDate(date));
        }

        if(requestCode == REQUEST_TO_DATE){
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mDates.remove(1);
            mDates.add(1, getEndOfDay(date).getTime());
            mToButton.setText("To: " + getFormattedDate(date));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getFormattedDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dateString = Integer.toString(day) + "-" + Integer.toString(month) + "-" + Integer.toString(year);
        return dateString;
    }


    @OnClick(R.id.button_filter)
    public void filter(View view){
        sendBackResult();
    }



    public void sendBackResult(){

        if(getTargetFragment() == null){
            return;
        }

        // TODO: 11/10/2016 create filter payload ;

        FilterDialogListener filterDialogListener = (FilterDialogListener) getTargetFragment();
        if(mDates.get(0).equals(mDates.get(1))){
            Toast.makeText(getActivity(), "Same dates", Toast.LENGTH_SHORT).show();
        }
        filterDialogListener.onFinishedFiltering(mDates, mFilterItems);
        dismiss();

    }

    private Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 0, 0, 0);
        return calendar.getTime();
    }

    private Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 23, 59, 59);
        return calendar.getTime();
    }

    // set up spinners
    public void setUpSpinners(){
        if(mModels.isEmpty()){
            // do nothing
        } else {
            mSpinnerLinearLayout.removeAllViews();
            for(HashMap.Entry<String, List<HashMap<String, String>>> entry: mModels.entrySet()){
               // Toast.makeText(getActivity(), entry.getKey(), Toast.LENGTH_SHORT).show();
                Spinner spinner = new Spinner(getActivity());
                ViewGroup.LayoutParams layoutParams =
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                spinner.setLayoutParams(layoutParams);

                ArrayList<FilterItem> adapterItems = new ArrayList<>();
                // create adapter and add items to spinner
                List<HashMap<String, String>> itemsList = entry.getValue();
                for(HashMap<String, String> listItem: itemsList){
                    FilterItem filterItem = new FilterItem();
                    filterItem.setId(listItem.get("id"));
                    filterItem.setName(listItem.get("name"));
                    adapterItems.add(filterItem);
                }

                FilterItemsAdapter adapter =
                        new FilterItemsAdapter(getActivity(),
                                android.R.layout.simple_spinner_item, adapterItems);

                spinner.setAdapter(adapter);
                mSpinnerLinearLayout.addView(spinner);
            }
        }
    }

}

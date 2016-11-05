package com.mobitill.mobitill_2.components.addedit;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.components.SchemaTypes;
import com.mobitill.mobitill_2.components.ShowAllUtils;
import com.mobitill.mobitill_2.components.showall.ShowAllFragment;
import com.mobitill.mobitill_2.data.models.clients.ClientsDataSource;
import com.mobitill.mobitill_2.net.ConnectivityReceiver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditFragment extends Fragment implements AddEditContract.View,
        ConnectivityReceiver.ConnectivityReceiverListener {

    private AddEditContract.Presenter mPresenter;

    private static final String TAG = AddEditFragment.class.getSimpleName();
    private static final String ARGS_SHOW_ALL_UTILS = "args_show_all_utils";

    private ShowAllUtils mShowAllUtils;
    private Unbinder mUnbinder;

    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.no_products) TextView mEmptyTextView;
    @BindView(R.id.error) TextView mErrorTextView;
    @BindView(R.id.no_network) TextView mNetworkTextView;
    @BindView(R.id.linear_layout) LinearLayout mLinearLayout;

    FloatingActionButton mAddDoneFAB;

    public AddEditFragment() {
        // Required empty public constructor
    }

    public static AddEditFragment newInstance(ShowAllUtils showAllUtils) {

        Bundle args = new Bundle();
        args.putSerializable(ARGS_SHOW_ALL_UTILS, showAllUtils);
        AddEditFragment fragment = new AddEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            mShowAllUtils = (ShowAllUtils) getArguments().getSerializable(ARGS_SHOW_ALL_UTILS);
        } else {
            mShowAllUtils = (ShowAllUtils) savedInstanceState.getSerializable(ARGS_SHOW_ALL_UTILS);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARGS_SHOW_ALL_UTILS, mShowAllUtils);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAddDoneFAB = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_done);
        mAddDoneFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Add", Toast.LENGTH_SHORT).show();
                HashMap<String, String> data = new HashMap<>();
                for(int index = 0; index<mLinearLayout.getChildCount(); index++){
                    EditText nextChild = (EditText) mLinearLayout.getChildAt(index);
                    data.put((String)nextChild.getTag(), nextChild.getText().toString());
//                    Log.i(TAG, "onClick: " + "Index: " + Integer.toString(index) +
//                        " Id: " + Integer.toString(nextChild.getId()) + " Value: " + nextChild.getText().toString() + " Tag: " +
//                        nextChild.getTag());
                }

                mPresenter.add(data);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_edit, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobitillApplication.getInstance().setConnectivityListener(this);
        mPresenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }


    @Override
    public void setPresenter(AddEditContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(isConnected){
            showNetworkError(false);
            showDataError(false);
            mPresenter.start();
        } else {
            showNetworkError(true);
        }
    }

    @Override
    public void showLoading(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showEmpty(boolean show) {
        mEmptyTextView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public String toHex(String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
    }

    @Override
    public void showUI(HashMap<String, String[]> schema) {
        mLinearLayout.removeAllViews();
        int index = 0;
        for(HashMap.Entry<String, String[]> entry : schema.entrySet()){
            Log.i(TAG, "generateUI: " + entry.getKey() + " : " + Arrays.toString(entry.getValue()));
            EditText editText;
            switch (entry.getValue()[0]){
                case SchemaTypes.TEXT:
                    editText = new EditText(getActivity());
                    editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    editText.setHint(entry.getKey());
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                    //editText.setId(Integer.parseInt(entry.getKey()));
                    mLinearLayout.addView(editText);
                    break;
                case SchemaTypes.NUMBER:
                    editText = new EditText(getActivity());
                    editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    editText.setHint(entry.getKey());
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    //editText.setId(Integer.parseInt(entry.getKey()));
                    mLinearLayout.addView(editText);
                    break;
                case SchemaTypes.DATE:
                    editText = new EditText(getActivity());
                    editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    editText.setHint(entry.getKey());
                    editText.setInputType(InputType.TYPE_CLASS_DATETIME);
                    //editText.setId(Integer.parseInt(entry.getKey()));
                    mLinearLayout.addView(editText);
                    break;
                case SchemaTypes.TEXTAREA:
                    editText = new EditText(getActivity());
                    editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    editText.setHint(entry.getKey());
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                    //editText.setId(Integer.parseInt(entry.getKey()));
                    editText.setSingleLine(false);
                    editText.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                    mLinearLayout.addView(editText);
                    break;
                default:
                    editText = new EditText(getActivity());
                    editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    editText.setHint(entry.getKey());
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                    //editText.setId(Integer.parseInt(entry.getKey()));
                    mLinearLayout.addView(editText);
            }
            if(editText!=null){
                editText.setTag(entry.getKey());
                editText.setId(index);
            }
            Log.i(TAG, "showUI: " + Integer.toString(index));
            index++;

        }
    }


    @Override
    public void showNetworkError(boolean show) {
        mNetworkTextView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showDataError(boolean show) {
        mErrorTextView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}

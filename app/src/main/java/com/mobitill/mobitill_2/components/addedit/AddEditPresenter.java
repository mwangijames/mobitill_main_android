package com.mobitill.mobitill_2.components.addedit;

import android.util.Log;

import com.mobitill.mobitill_2.components.ShowAllUtils;
import com.mobitill.mobitill_2.data.models.generic.Actions;
import com.mobitill.mobitill_2.data.models.generic.GenericRepository;
import com.mobitill.mobitill_2.data.models.generic.Payload;
import com.mobitill.mobitill_2.utils.SettingsHelper;

import java.util.Arrays;
import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by james on 11/4/2016.
 */

public class AddEditPresenter implements AddEditContract.Presenter {

    private static final String TAG = AddEditPresenter.class.getSimpleName();

    private final AddEditContract.View mView;
    private final ShowAllUtils mShowAllUtils;
    private final GenericRepository mGenericRepository;
    private final Payload mPayload;
    private final SettingsHelper mSettingsHelper;
    private final Actions mActions;

    @Inject
    public AddEditPresenter(
            AddEditContract.View view,
            ShowAllUtils showAllUtils,
            GenericRepository genericRepository,
            Payload payload,
            SettingsHelper settingsHelper,
            Actions actions
    ){
        mView = view;
        mShowAllUtils = showAllUtils;
        mGenericRepository = genericRepository;
        mPayload = payload;
        mSettingsHelper = settingsHelper;
        mActions = actions;
    }


    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     * For more information, see Java Concurrency in Practice.
     */
    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }


    @Override
    public void start() {
        generateUI();
    }


    @Override
    public void add() {

    }

    @Override
    public void generateUI() {
        if(mShowAllUtils!=null){
            if(mShowAllUtils.isEmpty()){
                Log.i(TAG, "generateUI: mShowAllUtils missing some fields");
            } else {
                HashMap<String, String[]> schema =
                        mSettingsHelper.getSchema(mShowAllUtils.getSettings(), mShowAllUtils.getModel());
                for(HashMap.Entry<String, String[]> entry : schema.entrySet()){
                    Log.i(TAG, "generateUI: " + entry.getKey() + " : " + Arrays.toString(entry.getValue()));
                }
            }
        } else {
            Log.i(TAG, "generateUI: mShowAllUtils is null");
        }
    }
}

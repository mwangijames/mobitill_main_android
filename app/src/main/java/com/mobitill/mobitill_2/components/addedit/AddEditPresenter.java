package com.mobitill.mobitill_2.components.addedit;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mobitill.mobitill_2.components.ShowAllUtils;
import com.mobitill.mobitill_2.data.models.generic.Actions;
import com.mobitill.mobitill_2.data.models.generic.GenericDataSource;
import com.mobitill.mobitill_2.data.models.generic.GenericRepository;
import com.mobitill.mobitill_2.data.models.generic.Payload;
import com.mobitill.mobitill_2.sync.MobitillSyncAdapter;
import com.mobitill.mobitill_2.utils.SettingsHelper;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

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
    private final Context mContext;
    @Nullable private final HashMap<String, String> mItem;

    @Inject
    public AddEditPresenter(
            AddEditContract.View view,
            ShowAllUtils showAllUtils,
            GenericRepository genericRepository,
            Payload payload,
            SettingsHelper settingsHelper,
            Actions actions,
            @Nullable HashMap<String, String> item,
            Context context
    ){
        mView = view;
        mShowAllUtils = showAllUtils;
        mGenericRepository = genericRepository;
        mPayload = payload;
        mSettingsHelper = settingsHelper;
        mActions = actions;
        mItem = item;
        mContext = context;
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
        if(mItem == null){
            generateUI();
        } else {
            generateAndPopulateUI(mItem);
            Log.i(TAG, "start: " + mItem);
        }
    }

    @Override
    public void add(HashMap<String, String> data) {
        if(mSettingsHelper != null){
            if(mShowAllUtils != null && !mShowAllUtils.isEmpty()){
                try {
                   String payload = mSettingsHelper.getInsertPayLoad(data, mShowAllUtils.getAppId());
                    if(payload != null && mPayload != null){
                        mPayload.setPayload(payload);
                        mPayload.setModel(mShowAllUtils.getModel());
                        mPayload.setAction(mActions.INSERT);
                        mPayload.setDemo(false);
                        mPayload.setAppid(mShowAllUtils.getAppId());
                        if(mPayload.isEmpty()){
                            Log.i(TAG, "add: " + "Some Payload fields are empty or null");
                        } else {
                            if(mGenericRepository != null){
                                mView.showLoading(true);
                                mGenericRepository.postData(mPayload, new GenericDataSource.LoadDataCallBack() {
                                    @Override
                                    public void onDataLoaded(String data) {
                                        mView.showSuccess(true);
                                        mView.showLoading(false);
                                        MobitillSyncAdapter.syncImmediately(mContext);
                                        openShowAll();
                                    }

                                    @Override
                                    public void onDataNotLoaded() {
                                        mView.showFail(true);
                                        mView.showLoading(false);
                                    }
                                });
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void addStock(final HashMap<String, String> createData){
        if(mShowAllUtils!=null && !mShowAllUtils.isEmpty()){
            // get the product id
            if(mPayload!=null){
                String payload =mSettingsHelper.getFetchPayload(mActions.FETCH, mShowAllUtils.getAppId());
                if(payload!=null && !payload.isEmpty()){
                    mPayload.setModel("products");
                    mPayload.setDemo(false);
                    mPayload.setAction(mActions.FETCH);
                    mPayload.setPayload(payload);
                    mPayload.setAppid(mShowAllUtils.getAppId());
                    if(!mPayload.isEmpty()){
                        // get products from remote server and check, this should be optimized to work with local data instead
                        mView.showLoading(true);
                        mGenericRepository.postData(mPayload, new GenericDataSource.LoadDataCallBack() {
                            @Override
                            public void onDataLoaded(String data) {
                                String productId = getProductId(data, createData);
                                if(productId == null){
                                    mView.showInvalidIdentifier();
                                } else {
                                    addStockItem(productId, createData);
                                }
                            }
                            @Override
                            public void onDataNotLoaded() {
                                Log.i(TAG, "onDataNotLoaded: addStock: getProducts");
                                mView.showFail(true);
                                mView.showLoading(false);
                            }
                        });
                    }
                }
            }
        }
    }

    private void addStockItem(String productId, HashMap<String, String> createData) {
        String payload = mSettingsHelper.getInsertInventoryPayload(mShowAllUtils.getAppId(), productId, createData);
        Log.i(TAG, "addStockItem: " + payload);
        mPayload.setModel("inventory");
        mPayload.setAction(mActions.CREATE);
        mPayload.setPayload(payload);
        mPayload.setDemo(mSettingsHelper.isDemo(mShowAllUtils.getSettings()));
        mPayload.setAction(mShowAllUtils.getAppId());
        mPayload.setAppid(mShowAllUtils.getAppId());
        if(!mPayload.isEmpty()){
            mView.showLoading(true);
            mGenericRepository.postData(mPayload, new GenericDataSource.LoadDataCallBack() {
                @Override
                public void onDataLoaded(String data) {
                    mView.showSuccess(true);
                    mView.showLoading(false);
                    openShowAll();
                }

                @Override
                public void onDataNotLoaded() {
                    mView.showFail(true);
                    mView.showLoading(false);
                }
            });
        }
    }

    private String getProductId(String data, HashMap<String, String> createData) {
        // obtain the identifier
        String productId = null;
        if(createData.containsKey("identifier")){
            String identifier = createData.get("identifier");

            // get the products
            List<HashMap<String, String>> products =  mSettingsHelper.getList(data);
            for (HashMap<String, String> product: products) {
                if(product.containsKey("identifier")){
                    String productIdentifier = product.get("identifier");
                    if(productIdentifier.equalsIgnoreCase(identifier)){
                        productId = product.get("id");
                    }
                }
            }
        }

        return productId;
    }


    @Override
    public void edit(HashMap<String, String> data) {
        if(mSettingsHelper != null){
            if(mShowAllUtils != null && !mShowAllUtils.isEmpty()){
                try {
                    String payload = mSettingsHelper.getUpdatePayLoad(data, mShowAllUtils.getAppId(), mItem.get("id"));
                    Log.i(TAG, "edit: " + payload);
                    if(payload != null && mPayload != null){
                        mPayload.setPayload(payload);
                        mPayload.setModel(mShowAllUtils.getModel());
                        mPayload.setAction(mActions.UPDATE);
                        mPayload.setDemo(false);
                        mPayload.setAppid(mShowAllUtils.getAppId());
                        if(mPayload.isEmpty()){
                            Log.i(TAG, "edit: " + "Some Payload fields are empty or null");
                        } else {
                            if(mGenericRepository != null){
                                mView.showLoading(true);
                                mGenericRepository.postData(mPayload, new GenericDataSource.LoadDataCallBack() {
                                    @Override
                                    public void onDataLoaded(String data) {
                                        mView.showSuccess(true);
                                        mView.showLoading(false);
                                        MobitillSyncAdapter.syncImmediately(mContext);
                                        openShowAll();
                                    }

                                    @Override
                                    public void onDataNotLoaded() {
                                        mView.showFail(true);
                                        mView.showLoading(false);
                                    }
                                });
                                Log.i(TAG, "edit: " + payload);
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void generateUI() {
        if(mShowAllUtils!=null){
            if(mShowAllUtils.isEmpty()){
                Log.i(TAG, "generateUI: mShowAllUtils missing some fields");
            } else {
                HashMap<String, String[]> schema = new HashMap<>();
                if(!mShowAllUtils.getModel().equalsIgnoreCase("inventory")){
                    schema = mSettingsHelper.getSchema(mShowAllUtils.getSettings(), mShowAllUtils.getModel());
                } else {
                    schema = mSettingsHelper.getInventorySchema(mShowAllUtils.getSettings(), mShowAllUtils.getModel());
                }
                
                mView.showUI(schema);
            }
        } else {
            Log.i(TAG, "generateUI: mShowAllUtils is null");
        }
    }

    @Override
    public void generateAndPopulateUI(HashMap<String, String> item) {
        if(mShowAllUtils!=null){
            if(mShowAllUtils.isEmpty()){
                Log.i(TAG, "generateUI: mShowAllUtils missing some fields");
            } else {
                HashMap<String, String[]> schema =
                        mSettingsHelper.getSchema(mShowAllUtils.getSettings(), mShowAllUtils.getModel());
                mView.showAndPopulateUI(schema, item);

            }
        } else {
            Log.i(TAG, "generateUI: mShowAllUtils is null");
        }
    }

    @Override
    public void openShowAll() {
        if(mShowAllUtils != null && !mShowAllUtils.isEmpty()){
            mView.showAll(mShowAllUtils);
        }
    }

}

package com.mobitill.mobitill_2.cashiersaddedit;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobitill.mobitill_2.cashiersdetail.AppId;
import com.mobitill.mobitill_2.cashiersdetail.CashierGson;
import com.mobitill.mobitill_2.data.models.cashiers.CashiersDataSource;
import com.mobitill.mobitill_2.data.models.cashiers.CashiersRepository;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateParams;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateQuery;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateResponse;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierCreateResponseData;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierEditParams;
import com.mobitill.mobitill_2.data.models.cashiers.models.create.CashierEditQuery;

import javax.inject.Inject;

/**
 * Created by james on 9/27/2016.
 */

public class AddEditCashierPresenter implements AddEditCashierContract.Presenter {

    private static final String TAG = AddEditCashierPresenter.class.getSimpleName();
    private final AddEditCashierContract.View mView;
    private final CashiersRepository mCashiersRepository;
    private CashierCreateQuery mCashierCreateQuery;
    private CashierCreateParams mCashierCreateParams;
    private CashierEditParams mCashierEditParams;
    private CashierEditQuery mCashierEditQuery;
    AppId mAppId;
    @Nullable CashierGson mCashierGson;

    @Inject
    AddEditCashierPresenter(AddEditCashierContract.View view,
                            CashiersRepository cashiersRepository,
                            AppId appId,
                            @Nullable CashierGson cashierGson,
                            CashierCreateQuery cashierCreateQuery,
                            CashierCreateParams cashierCreateParams,
                            CashierEditParams cashierEditParams,
                            CashierEditQuery cashierEditQuery){
        mView = view;
        mAppId = appId;
        mCashiersRepository = cashiersRepository;
        mCashierCreateQuery = cashierCreateQuery;
        mCashierCreateParams = cashierCreateParams;
        mCashierGson = cashierGson;
        mCashierEditQuery = cashierEditQuery;
        mCashierEditParams = cashierEditParams;
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
    public void saveCashier(String appId, String name, String username, String password) {
        mCashierCreateParams.setAppid(appId);
        mCashierCreateParams.setName(name);
        mCashierCreateParams.setUsername(username);
        mCashierCreateParams.setPassword(password);

        if(appId == null){
            mView.showNoApplicationId();
        }

        if(mCashierCreateParams.isEmpty()){
            mView.showNoFields();
        } else {
            mCashierCreateQuery.setParams(mCashierCreateParams);
            mCashiersRepository.createCashier(mCashierCreateQuery, new CashiersDataSource.CreateCashiersCallBack() {
                @Override
                public void onCashiersCreated(CashierCreateResponse cashierCreateResponse) {
                    mView.showCashierCreated(cashierCreateResponse.getData());
                    mView.showCashiersList();
                }

                @Override
                public void onCashierCreateFail() {
                    mView.showCashierCreateFailed();
                }
            });
        }
    }

    @Override
    public void populateCashier() {
        if(mCashierGson.getCashierGson()!=null){
            Gson gson = new Gson();
            Cashier cashier = gson.fromJson(mCashierGson.getCashierGson(), Cashier.class);
            if(cashier != null) {
                mView.populateCashier(cashier);
            }
        }
    }

    @Override
    public void editCashier(String appId, String name, String username, String password) {
        if(getCashierFromJson() != null){
            mCashierEditParams.setAppid(appId);
            mCashierEditParams.setName(name);
            mCashierEditParams.setId(getCashierFromJson().getId());
            mCashierEditParams.setPassword(password);
            mCashierEditParams.setUsername(username);

            final Cashier cashier = getCashierFromJson();
            cashier.setUsername(username);
            cashier.setPassword(password);
            cashier.setName(name);

            if(mCashierEditParams.isEmpty()){
                mView.showNoFields();
            } else {
                if(mCashierEditQuery!=null){
                    mCashierEditQuery.setParams(mCashierEditParams);
                    mCashiersRepository.editCashier(mCashierEditQuery, new CashiersDataSource.EditCashierCallBack() {
                        @Override
                        public void onCashierEdited(CashierCreateResponse cashierCreateResponse) {
                            mView.showCashierEdited(cashierCreateResponse);
                            if(getCashierGson(cashier) != null) {
                                mView.showCashierDetail(getCashierGson(cashier));
                            } else {
                                mView.showCashiersList();
                            }
                        }

                        @Override
                        public void onCashierNotEdited() {
                            mView.showCashierEditFailed();
                        }
                    });
                }
            }
        }
    }

    @Override
    public void openCashierDetail(CashierGson cashierGson) {

    }

    Cashier getCashierFromJson(){

       if(mCashierGson.getCashierGson()!=null){
           Gson gson = new Gson();
           Cashier cashier = gson.fromJson(mCashierGson.getCashierGson(), Cashier.class);
           if(cashier != null){
               return cashier;
           }
       }

       return null;
    }

    CashierGson getCashierGson(Cashier cashier){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        //mView.showCashierDetailUi(gson.toJson(requestedCashier));
        CashierGson cashierGson = new CashierGson();
        if(cashier != null){
            cashierGson.setCashierGson(gson.toJson(cashier));
        }
        return cashierGson;
    }

    @Override
    public void start() {
        populateCashier();
    }

}

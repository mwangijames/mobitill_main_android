package com.mobitill.mobitill_2.clientsaddedit;

import android.support.annotation.Nullable;
import android.util.Log;

import com.mobitill.mobitill_2.cashiersaddedit.AddEditCashierPresenter;
import com.mobitill.mobitill_2.data.models.clients.ClientsDataSource;
import com.mobitill.mobitill_2.data.models.clients.ClientsRepository;
import com.mobitill.mobitill_2.data.models.clients.models.Client;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientCreateParams;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientCreateQuery;
import com.mobitill.mobitill_2.data.models.clients.models.create.ClientCreateResponse;

import javax.inject.Inject;

/**
 * Created by james on 10/7/2016.
 */

public class ClientAddEditPresenter implements ClientAddEditContract.Presenter {

    private static final String TAG = ClientAddEditPresenter.class.getSimpleName();
    private final ClientAddEditContract.View mView;
    private final ClientsRepository mClientsRepository;
    private ClientCreateQuery mClientCreateQuery;
    private ClientCreateParams mClientCreateParams;
    @Nullable String mAppId;
    
    @Inject
    ClientAddEditPresenter(ClientAddEditContract.View view,
                           ClientsRepository clientsRepository,
                           @Nullable String appId,
                           ClientCreateQuery clientCreateQuery,
                           ClientCreateParams clientCreateParams){
        mView = view;
        mClientsRepository = clientsRepository;
        mAppId = appId;
        mClientCreateQuery = clientCreateQuery;
        mClientCreateParams = clientCreateParams;
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
    public void saveClient(String appId, String email, String name, String phone) {
        if(appId == null){
            Log.i(TAG, "saveClient: appId is null");
            return;
        }

        if(mClientCreateParams != null){

            mClientCreateParams.setAppid(appId);
            mClientCreateParams.setEmail(email);
            mClientCreateParams.setName(name);
            mClientCreateParams.setPhone(phone);

            if(mClientCreateParams.isEmpty()){
                mView.showNoFields();
            } else {
                if(mClientCreateQuery!=null){
                    mClientCreateQuery.setParams(mClientCreateParams);
                    mClientsRepository.createClient(mClientCreateQuery, new ClientsDataSource.CreateClientCallBack() {
                        @Override
                        public void onClientCreated(ClientCreateResponse clientCreateResponse) {
                            mView.showClientsList();
                        }

                        @Override
                        public void onClientNotCreated() {
                            mView.showClientCreatedFailed();
                        }
                    });
                }
            }



        }
    }

    @Override
    public void start() {

    }
}

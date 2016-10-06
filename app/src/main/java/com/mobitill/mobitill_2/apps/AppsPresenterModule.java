package com.mobitill.mobitill_2.apps;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.mobitill.mobitill_2.sync.ScheduleAppSync;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DI on 8/10/2016.
 */
@Module
public class AppsPresenterModule {
    private final AppsContract.View mView;

    public AppsPresenterModule(AppsContract.View view) {
        mView = view;
    }

    @Provides
    AppsContract.View providesAppsContractView(){
        return mView;
    }

    @Provides
    ScheduleAppSync provideScheduleAppSync(FirebaseJobDispatcher firebaseJobDispatcher){
        return new ScheduleAppSync(firebaseJobDispatcher);
    }

}

package com.mobitill.mobitill_2.sync;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.apps.AppsFragment;
import com.mobitill.mobitill_2.apps.AppsPresenterModule;
import com.mobitill.mobitill_2.apps.DaggerAppsComponent;

import javax.inject.Inject;

/**
 * Created by james on 8/29/2016.
 */
public class AppsJobsService extends JobService{

    @Inject
    AppsSync mAppsSync;

    AppsFragment appsFragment;



    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        mAppsSync.sync();
        return false; // true if we are not yet done
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true; // true if we'd like to be rescheduled
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Create the presenter
        DaggerAppsComponent.builder()
                .appsRepositoryComponent(((MobitillApplication) getApplication()).getAppsRepositoryComponent())
                .appsPresenterModule(new AppsPresenterModule(appsFragment))
                .build()
                .inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

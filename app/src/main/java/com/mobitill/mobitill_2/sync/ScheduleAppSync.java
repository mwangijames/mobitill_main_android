package com.mobitill.mobitill_2.sync;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by james on 8/29/2016.
 */
public class ScheduleAppSync implements Runnable {

    private final FirebaseJobDispatcher mFirebaseJobDispatcher;
    private final Job jobToSchedule;

    public ScheduleAppSync(FirebaseJobDispatcher dispatcher){
        mFirebaseJobDispatcher = dispatcher;
        this.jobToSchedule = dispatcher
                .newJobBuilder()
                .setTag("apps_sync_job")
                .setService(AppsJobsService.class)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setTrigger(Trigger.NOW)
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setRecurring(false)
                .build();
    }

    @Override
    public void run() {
        mFirebaseJobDispatcher.mustSchedule(this.jobToSchedule);
    }
}

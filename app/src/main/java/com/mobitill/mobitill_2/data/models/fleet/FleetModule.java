package com.mobitill.mobitill_2.data.models.fleet;

import android.support.design.widget.TabLayout;

import com.mobitill.mobitill_2.data.models.fleet.models.Fleet;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetFetch;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetItem;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetParams;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetQuery;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 9/19/2016.
 */
@Module
public class FleetModule {
    @Provides
    Fleet provideFleet(){
        return new Fleet();
    }

    @Provides
    FleetFetch provideFleetFetch(){
        return new FleetFetch();
    }

    @Provides
    FleetItem provideFleetItem(){
        return new FleetItem();
    }

    @Provides
    FleetParams provideFleetParams(){
        return new FleetParams();
    }

    @Provides
    FleetQuery provideFleetQuery(){
        return new FleetQuery();
    }

}

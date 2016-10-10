package com.mobitill.mobitill_2.data.models.fleet;

import android.support.design.widget.TabLayout;

import com.mobitill.mobitill_2.data.models.fleet.models.Fleet;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetFetch;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetItem;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetParams;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetQuery;
import com.mobitill.mobitill_2.data.models.fleet.models.create.FleetCreateParams;
import com.mobitill.mobitill_2.data.models.fleet.models.create.FleetCreateQuery;
import com.mobitill.mobitill_2.data.models.fleet.models.create.FleetCreateResponse;
import com.mobitill.mobitill_2.data.models.fleet.models.create.FleetCreateResponseData;

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

    @Provides
    FleetCreateParams provideFleetCreateParams(){
        return new FleetCreateParams();
    }

    @Provides
    FleetCreateQuery provideFleetCreateQuery(){
        return new FleetCreateQuery();
    }

    @Provides
    FleetCreateResponse provideFleetCreateResponse(){
        return new FleetCreateResponse();
    }

    @Provides
    FleetCreateResponseData provideFleetCreateResponseData(){
        return new FleetCreateResponseData();
    }

}

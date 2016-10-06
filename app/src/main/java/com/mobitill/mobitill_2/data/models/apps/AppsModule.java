package com.mobitill.mobitill_2.data.models.apps;

import com.mobitill.mobitill_2.data.models.apps.models.App;
import com.mobitill.mobitill_2.data.models.apps.models.Apps;
import com.mobitill.mobitill_2.data.models.apps.models.Body;
import com.mobitill.mobitill_2.data.models.apps.models.Datum;
import com.mobitill.mobitill_2.data.models.apps.models.Params;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DI on 8/18/2016.
 */
@Module
public class AppsModule {

    @Provides
    App provideApp(){
        return new App();
    }

    @Provides
    Apps providesApps(){
        return new Apps();
    }

    @Provides
    Datum providesDatum(){
        return new Datum();
    }

    @Provides
    Params providesParams(){
        return new Params();
    }

    @Provides
    Body providesBody(){
        return new Body();
    }
}

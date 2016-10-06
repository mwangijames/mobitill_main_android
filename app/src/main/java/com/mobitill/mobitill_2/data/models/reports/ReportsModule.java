package com.mobitill.mobitill_2.data.models.reports;

import com.mobitill.mobitill_2.data.models.reports.models.Fetch;
import com.mobitill.mobitill_2.data.models.reports.models.Params;
import com.mobitill.mobitill_2.data.models.reports.models.Query;
import com.mobitill.mobitill_2.data.models.reports.models.ReportItem;
import com.mobitill.mobitill_2.data.models.reports.models.Reports;
import com.mobitill.mobitill_2.data.models.reports.models.ReportsLocal;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 9/2/2016.
 */
@Module
public class ReportsModule {

    @Provides
    ReportItem provideReportItem(){
        return new ReportItem();
    }

    @Provides
    Reports provideReports(){
        return new Reports();
    }

    @Provides
    ReportsLocal provideReportsLocal(){
        return new ReportsLocal();
    }

    @Provides
    Query provideQuery(){
        return new Query();
    }

    @Provides
    Fetch provideFetch(){
        return new Fetch();
    }

    @Provides
    Params provideParams(){
        return new Params();
    }

}

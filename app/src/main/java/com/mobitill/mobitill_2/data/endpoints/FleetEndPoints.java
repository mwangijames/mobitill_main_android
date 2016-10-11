package com.mobitill.mobitill_2.data.endpoints;

import com.mobitill.mobitill_2.data.models.fleet.models.Fleet;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetQuery;
import com.mobitill.mobitill_2.data.models.fleet.models.create.FleetCreateQuery;
import com.mobitill.mobitill_2.data.models.fleet.models.create.FleetCreateResponse;
import com.mobitill.mobitill_2.data.models.fleet.models.delete.FleetDeleteQuery;
import com.mobitill.mobitill_2.data.models.fleet.models.delete.FleetDeleteResponse;
import com.mobitill.mobitill_2.data.models.products.models.Products;
import com.mobitill.mobitill_2.data.models.products.models.ProductsQuery;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by james on 9/19/2016.
 */
public interface FleetEndPoints {
    @POST("fleet/fetch")
    Call<Fleet> fetchFleet(@Body FleetQuery fleetQuery);

    @POST("fleet/insert")
    Call<FleetCreateResponse> createFleetItem(@Body FleetCreateQuery fleetCreateQuery);

    @POST("fleet/delete")
    Call<FleetDeleteResponse> deleteFleetItem(@Body FleetDeleteQuery fleetDeleteQuery);
}

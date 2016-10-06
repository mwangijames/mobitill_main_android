package com.mobitill.mobitill_2.data.endpoints;

import com.mobitill.mobitill_2.data.models.cashiers.models.Cashiers;
import com.mobitill.mobitill_2.data.models.cashiers.models.CashiersQuery;
import com.mobitill.mobitill_2.data.models.clients.models.Clients;
import com.mobitill.mobitill_2.data.models.clients.models.ClientsQuery;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by james on 9/19/2016.
 */
public interface ClientsEndPoints {
    @POST("clients/fetch")
    Call<Clients> fetchClients(@Body ClientsQuery clientsQuery);
}

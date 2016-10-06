package com.mobitill.mobitill_2.data.endpoints;

import com.mobitill.mobitill_2.data.models.products.models.Products;
import com.mobitill.mobitill_2.data.models.products.models.ProductsQuery;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by james on 9/13/2016.
 */
public interface ProductsEndPoints {
    @POST("products/fetch")
    Call<Products> fetchProducts(@Body ProductsQuery productsQuery);
}

package com.mobitill.mobitill_2.data.models.products;

import com.mobitill.mobitill_2.data.models.products.models.Product;
import com.mobitill.mobitill_2.data.models.products.models.Products;
import com.mobitill.mobitill_2.data.models.products.models.ProductsFetch;
import com.mobitill.mobitill_2.data.models.products.models.ProductsParams;
import com.mobitill.mobitill_2.data.models.products.models.ProductsQuery;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 9/13/2016.
 */
@Module
public class ProductsModule {

    @Provides
    Product provideProduct(){
        return new Product();
    }

    @Provides
    Products provideProducts(){
        return new Products();
    }

    @Provides
    ProductsFetch provideProductsFetch(){
        return new ProductsFetch();
    }

    @Provides
    ProductsParams provideProductsParams(){
        return new ProductsParams();
    }

    @Provides
    ProductsQuery provideProductsQuery(){
        return new ProductsQuery();
    }

}

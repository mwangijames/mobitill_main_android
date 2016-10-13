package com.mobitill.mobitill_2.data.models.products;

import com.mobitill.mobitill_2.data.models.products.models.Product;
import com.mobitill.mobitill_2.data.models.products.models.Products;
import com.mobitill.mobitill_2.data.models.products.models.ProductsFetch;
import com.mobitill.mobitill_2.data.models.products.models.ProductsParams;
import com.mobitill.mobitill_2.data.models.products.models.ProductsQuery;
import com.mobitill.mobitill_2.data.models.products.models.create.ProductCreateParams;
import com.mobitill.mobitill_2.data.models.products.models.create.ProductCreateQuery;
import com.mobitill.mobitill_2.data.models.products.models.create.ProductCreateResponse;
import com.mobitill.mobitill_2.data.models.products.models.create.ProductCreateResponseData;
import com.mobitill.mobitill_2.data.models.products.models.delete.ProductDeleteParams;
import com.mobitill.mobitill_2.data.models.products.models.delete.ProductDeleteQuery;
import com.mobitill.mobitill_2.data.models.products.models.delete.ProductDeleteResponse;
import com.mobitill.mobitill_2.data.models.products.models.delete.ProductDeleteResponseData;

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

    @Provides
    ProductCreateParams provideProductCreateParams(){
        return new ProductCreateParams();
    }

    @Provides
    ProductCreateQuery provideProductCreateQuery(){
        return new ProductCreateQuery();
    }

    @Provides
    ProductCreateResponse provideProductCreateResponse(){
        return new ProductCreateResponse();
    }

    @Provides
    ProductCreateResponseData provideProductCreateResponseData(){
        return new ProductCreateResponseData();
    }

    @Provides
    ProductDeleteParams provideProductDeleteParams(){
        return new ProductDeleteParams();
    }

    @Provides
    ProductDeleteQuery provideProductDeleteQuery(){
        return new ProductDeleteQuery();
    }

    @Provides
    ProductDeleteResponse provideProductDeleteResponse(){
        return new ProductDeleteResponse();
    }

    @Provides
    ProductDeleteResponseData provideProductDeleteResponseData(){
        return new ProductDeleteResponseData();
    }
}

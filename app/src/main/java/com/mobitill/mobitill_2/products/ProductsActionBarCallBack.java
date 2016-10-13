package com.mobitill.mobitill_2.products;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.fleet.FleetActionBarCallBack;
import com.mobitill.mobitill_2.fleet.FleetFragment;

/**
 * Created by james on 10/12/2016.
 */

public class ProductsActionBarCallBack implements ActionMode.Callback {

    public static final String TAG = ProductsActionBarCallBack.class.getSimpleName();

    private Context mContext;
    private ProductsAdapter mProductsAdapter;
    private Fragment mProductsFragment;

    public ProductsActionBarCallBack(
            Context context,
            ProductsAdapter productsAdapter,
            Fragment productsFragment){
        mContext = context;
        mProductsAdapter = productsAdapter;
        mProductsFragment = productsFragment;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.contextual_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        menu.findItem(R.id.delete_item).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {

        switch (item.getItemId()){
            case R.id.delete_item:
                if(mProductsFragment != null){
                    new AlertDialog.Builder(mContext)
                            .setTitle("Delete Product")
                            .setMessage("Are you sure you want to delete product(s)?")
                            .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((ProductsFragment) mProductsFragment).deleteProduct();
                                    mode.finish();
                                }
                            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mode.finish();
                        }
                    }).show();
                }
                return true;
            default:
                break;
        }

        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mProductsAdapter.removeSelection();
        if(mProductsFragment != null){
            ((ProductsFragment) mProductsFragment).setNullToActionMode();
        }
    }
}

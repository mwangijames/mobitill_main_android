package com.mobitill.mobitill_2.cashiers;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.data.models.cashiers.models.Cashier;

import java.util.List;

/**
 * Created by james on 10/4/2016.
 *
 */

public class CashiersActionBarCallBack implements ActionMode.Callback {

    public static final String TAG  = CashiersActionBarCallBack.class.getSimpleName();

    private Context mContext;
    private CashiersAdapter mCashiersAdapter;
    private List<Cashier> mCashiers;
    private Fragment mCashiersFragment;

    public CashiersActionBarCallBack(Context context, CashiersAdapter cashiersAdapter,
                                     List<Cashier> cashiers, Fragment cashiersFragment){
        mContext = context;
        mCashiersAdapter = cashiersAdapter;
        mCashiers = cashiers;
        mCashiersFragment = cashiersFragment;
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
                if(mCashiersFragment != null){

                    new AlertDialog.Builder(mContext)
                            .setTitle("Delete Cashier")
                            .setMessage("Are you sure you want to delete cashier(s)?")
                            .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((CashiersFragment) mCashiersFragment).deleteCashiers();
                                    mode.finish();
                                }
                            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
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
        mCashiersAdapter.removeSelection();
        if(mCashiersFragment != null){
            ((CashiersFragment) mCashiersFragment).setNullToActionMode();
        }
    }
}

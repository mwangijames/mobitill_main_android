package com.mobitill.mobitill_2.fleet;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.data.models.fleet.models.Fleet;
import com.mobitill.mobitill_2.data.models.fleet.models.FleetItem;

import java.util.List;

/**
 * Created by james on 10/11/2016.
 */

public class FleetActionBarCallBack implements ActionMode.Callback {

    public static final String TAG = FleetActionBarCallBack.class.getSimpleName();

    private Context mContext;
    private FleetAdapter mFleetAdapter;
    private Fragment mFleetFragment;

    public FleetActionBarCallBack(Context context, FleetAdapter fleetAdapter,
                                  Fragment fleetFragment){
        mContext = context;
        mFleetAdapter = fleetAdapter;
        mFleetFragment = fleetFragment;
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
                if(mFleetFragment != null){
                    new AlertDialog.Builder(mContext)
                            .setTitle("Delete Fleet")
                            .setMessage("Are you sure you want to delete fleet?")
                            .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((FleetFragment) mFleetFragment).deleteFleetItem();
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
        mFleetAdapter.removeSelection();
        if(mFleetFragment != null){
            ((FleetFragment) mFleetFragment).setNullToActionMode();
        }
    }
}

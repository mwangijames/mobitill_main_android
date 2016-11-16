package com.mobitill.mobitill_2.clients;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;

import com.mobitill.mobitill_2.R;

/**
 * Created by james on 10/7/2016.
 */

public class ClientsActionBarCallBack implements ActionMode.Callback {

    public static final String TAG  = ClientsActionBarCallBack.class.getSimpleName();

    private Context mContext;
    private ClientsAdapter mClientsAdapter;
    private Fragment mClientsFragment;

    public ClientsActionBarCallBack(Context context, ClientsAdapter clientsAdapter,
                                    Fragment clientsFragment){
        mContext = context;
        mClientsAdapter = clientsAdapter;
        mClientsFragment = clientsFragment;
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
                if(mClientsFragment != null){
                    new AlertDialog.Builder(mContext)
                            .setTitle("Delete Client ?")
                            .setMessage("Are you sure you want to delete the client(s)?")
                            .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((ClientsFragment) mClientsFragment).deleteClients();
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
        mClientsAdapter.removeSelection();
        if(mClientsFragment != null){
            ((ClientsFragment) mClientsFragment).setNullToActionMode();
        }
    }
}

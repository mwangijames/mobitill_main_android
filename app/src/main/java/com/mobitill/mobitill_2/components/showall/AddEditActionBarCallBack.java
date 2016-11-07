package com.mobitill.mobitill_2.components.showall;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mobitill.mobitill_2.R;

/**
 * Created by james on 10/7/2016.
 */

public class AddEditActionBarCallBack implements ActionMode.Callback {

    public static final String TAG  = AddEditActionBarCallBack.class.getSimpleName();

    private Context mContext;
    private ShowAllAdapter mShowAllAdapter;
    private Fragment mShowAllFragment;

    public AddEditActionBarCallBack(Context context, ShowAllAdapter showAllAdapter,
                                    Fragment showAllFragment){
        mContext = context;
        mShowAllAdapter = showAllAdapter;
        mShowAllFragment = showAllFragment;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.contextual_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        menu.findItem(R.id.delete_item).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.findItem(R.id.update_item).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);


        return true;
    }

    @Override
    public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_item:
                if(mShowAllFragment != null){
                    new AlertDialog.Builder(mContext)
                            .setTitle("Delete ?")
                            .setMessage("Are you sure you want to delete the selected item(s)?")
                            .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((ShowAllFragment) mShowAllFragment).delete();
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
            case R.id.update_item:
                if(mShowAllFragment != null){
                    ((ShowAllFragment) mShowAllFragment).openEdit();
                }
            default:
                break;
        }

        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mShowAllAdapter.removeSelection();
        if(mShowAllFragment != null){
            ((ShowAllFragment) mShowAllFragment).setNullToActionMode();
        }
    }


}
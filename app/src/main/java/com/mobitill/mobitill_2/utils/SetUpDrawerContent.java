package com.mobitill.mobitill_2.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;

import com.mobitill.mobitill_2.Constants;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.apps.AppsActivity;
import com.mobitill.mobitill_2.fleet.FleetActivity;
import com.mobitill.mobitill_2.products.ProductsActivity;

/**
 * Created by james on 9/19/2016.
 */
public class SetUpDrawerContent {

    private static final String TAG = SetUpDrawerContent.class.getSimpleName();

    private Context mContext;
    private SharedPreferences mSharedPreferences;
    static  Constants mConstants = new Constants();

    public SetUpDrawerContent(Context context){
        mContext = context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void setupDrawerContent(NavigationView navigationView, final Context context, final DrawerLayout drawerLayout){
        Log.i(TAG, "setupDrawerContent: " + mSharedPreferences.getString(mConstants.APPID, null));
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.home_navigation_menu_item:
                                context.startActivity(AppsActivity.newIntent(context));
                                break;
                            case R.id.products_navigation_menu_item:
                                context.startActivity(ProductsActivity.newIntent(context, mSharedPreferences.getString(mConstants.APPID, null)));
                                break;
                            case R.id.fleet_navigation_menu_item:
                                context.startActivity(FleetActivity.newIntent(context, mSharedPreferences.getString(mConstants.APPID, null)));
                                break;
                            case R.id.cashiers_navigation_menu_item:
                               // context.startActivity(CashiersActivity.newIntent(context, mSharedPreferences.getString(mConstants.APPID, null)));
                                break;
                            case R.id.clients_navigation_menu_item:
                               // context.startActivity(ClientsActivity.newIntent(context, mSharedPreferences.getString(mConstants.APPID, null)));
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }




}

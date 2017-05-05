package com.mobitill.mobitill_2.components.transactions;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mobitill.mobitill_2.MobitillApplication;
import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionsActivity extends AppCompatActivity {

    @Inject TransactionsPresenter transactionsPresenter;

    @BindView(R.id.tb_transactions)
    Toolbar tbTransactions;

    public static Intent newIntent(Context context){
        Intent myIntent = new Intent(context,TransactionsActivity.class);
        return myIntent;
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        ButterKnife.bind(this);

        setSupportActionBar(tbTransactions);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        TransactionsFragment transactionsFragment = (TransactionsFragment)
                getSupportFragmentManager().findFragmentById(R.id.frag_transactions);

        if(transactionsFragment == null){
            transactionsFragment = TransactionsFragment.newInstance();

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    transactionsFragment,
                    R.id.frag_transactions
            );
        }


        DaggerTransactionsComponent.builder()
                .transactionsPresenterModule(new TransactionsPresenterModule(transactionsFragment))
                .baseComponent(((MobitillApplication) getApplication()).getBaseComponent())
                .build()
                .inject(this);


    }


}

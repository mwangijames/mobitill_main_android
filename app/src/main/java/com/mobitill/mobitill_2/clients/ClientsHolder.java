package com.mobitill.mobitill_2.clients;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.data.models.clients.models.Client;

import butterknife.BindView;
import butterknife.ButterKnife;

class ClientsHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.clients)
    TextView mClientsTextView;
    Client mClient;

    public ClientsHolder(View itemView) {
        super(itemView);
        mClient = new Client();
        itemView.setClickable(true);
        itemView.setFocusable(true);
        ButterKnife.bind(this, itemView);
    }

    public void bindClientName(Client client){
        mClientsTextView.setText(client.getName());
    }
}
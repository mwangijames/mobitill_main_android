package com.mobitill.mobitill_2.clients;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.mobitill_2.R;
import com.mobitill.mobitill_2.data.models.clients.models.Client;

import java.util.List;

class ClientsAdapter extends RecyclerView.Adapter<ClientsHolder>{

    private List<Client> mClients;
    private Context mContext;
    private SparseBooleanArray mSelectedItemsIds;

    public ClientsAdapter(List<Client> clients, Context context){
        mClients = clients;
        mContext = context;
        mSelectedItemsIds = new SparseBooleanArray();
    }

    @Override
    public ClientsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_clients, parent, false);
        return new ClientsHolder(view);
    }

    @Override
    public void onBindViewHolder(ClientsHolder holder, int position) {
        Client client = mClients.get(position);
        holder.bindClientName(client);

        // set the selected items to checked
        holder.itemView.setSelected(mSelectedItemsIds.get(position) ? true : false);
    }

    @Override
    public int getItemCount() {
        return mClients.size();
    }

    public void setClients(List<Client> clients){
        mClients = clients;
    }

      /*
    *Methods required for do selections, remove selections etc
    * */
      //Toggle selection methods
      public void toggleSelection(int position){
          selectView(position, !mSelectedItemsIds.get(position));
      }

    // Remove selected selections
    public void removeSelection(){
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    // Put or delete selected position into SparseBooleanArray
    public void selectView(int position, boolean value){
        if(value){
            mSelectedItemsIds.put(position, value);
        } else {
            mSelectedItemsIds.delete(position);
        }
        notifyDataSetChanged();
    }

    // Get total selected count
    public int getSelectedCount(){
        return mSelectedItemsIds.size();
    }

    // Return all selected ids
    public SparseBooleanArray getSelectedIds(){
        return mSelectedItemsIds;
    }

}
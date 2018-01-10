package com.esatic.grenciss.androidiptk.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.esatic.grenciss.androidiptk.R;
import com.esatic.grenciss.androidiptk.classes.Vlsm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grenciss on 1/10/18.
 */

public class NetCardAdapter extends RecyclerView.Adapter<NetCardAdapter.NetCardViewHolder> {

    List<Vlsm> vlsmList = new ArrayList<>();

    public NetCardAdapter(Context context, List<Vlsm> vlsms)
    {
        vlsmList = vlsms;
    }

    @Override
    public NetCardAdapter.NetCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.net_card,parent,false);
        return new NetCardViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return vlsmList.size();
    }

    @Override
    public void onBindViewHolder(NetCardAdapter.NetCardViewHolder holder, int position) {
        Vlsm vlsm = vlsmList.get(position);
        holder.name.setText(vlsm.name);
        holder.neededSize.setText(vlsm.neededSize);
        holder.allocatedSize.setText(vlsm.allocatedSize);
        holder.address.setText(vlsm.address);
        holder.mask.setText(vlsm.mask);
        holder.range.setText(vlsm.range);
        holder.broadcast.setText(vlsm.broadcast);
    }

    public class NetCardViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView neededSize;
        public TextView allocatedSize;
        public TextView address;
        public TextView mask;
        public TextView range;
        public TextView broadcast;
        public NetCardViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView)itemView.findViewById(R.id.txtTitle);
            this.address = (TextView)itemView.findViewById(R.id.txtAddr);
            this.neededSize = (TextView)itemView.findViewById(R.id.txtNbHosts);
            this.allocatedSize = (TextView)itemView.findViewById(R.id.txtAddrHosts);
            this.mask = (TextView)itemView.findViewById(R.id.txtMask);
            this.range = (TextView)itemView.findViewById(R.id.txtAddrRange);
            this.broadcast = (TextView)itemView.findViewById(R.id.txtAddr6);
        }
    }
}

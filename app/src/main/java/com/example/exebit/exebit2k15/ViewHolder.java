package com.example.exebit.exebit2k15;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.exebit.exebit2k15.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    public TextView tvtitle;
    public TextView tvsch;
    public TextView tvdesc;
    public TextView tvcateg;
    public View back;


    public ViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        tvtitle = (TextView) itemView.findViewById(R.id.texttit);
        tvsch = (TextView) itemView.findViewById(R.id.textsch);
        tvdesc = (TextView) itemView.findViewById(R.id.textdesc);
        tvcateg = (TextView) itemView.findViewById(R.id.textcateg);
        back = (View) itemView.findViewById(R.id.back);
    }}
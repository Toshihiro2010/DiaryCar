package com.stecon.patipan_on.diarycar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by patipan_on on 11/3/2017.
 */

public class MyAdapterTwo extends BaseAdapter {

    Context context;
    String strCarTitle[];
    String strCarDate[];
    String strCarKm[];

    public MyAdapterTwo(Context context, String[] strCarTitle, String[] strCarDate, String[] strCarKm) {
        this.context = context;
        this.strCarTitle = strCarTitle;
        this.strCarDate = strCarDate;
        this.strCarKm = strCarKm;
    }

    @Override
    public int getCount() {
        return strCarTitle.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = inflater.inflate(R.layout.custom_list_two, parent, false);
        }
        TextView textView = (TextView) view.findViewById(R.id.tvCarTitle);
        textView.setText(strCarTitle[position]);

        TextView textView1 = (TextView) view.findViewById(R.id.tvCarDate);
        textView1.setText(strCarDate[position]);

        TextView textView2 = (TextView) view.findViewById(R.id.tvCarKm);
        Double my_km = Double.valueOf(strCarKm[position]);
        textView2.setText(my_km + " Km");

        return view;
    }
}

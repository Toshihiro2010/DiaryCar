package com.stecon.patipan_on.diarycar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by patipan_on on 10/27/2017.
 */

public class MyAdapter extends BaseAdapter {

    Context context;
    String strDate[];
    String strMoney[];

    public MyAdapter(Context context, String strDate[], String strMoney[]) {
        this.context = context;
        this.strDate = strDate;
        this.strMoney = strMoney;
    }

    @Override
    public int getCount() {
        return strDate.length;
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
            view = inflater.inflate(R.layout.custom_list_one, parent, false);
        }
        TextView textView = (TextView) view.findViewById(R.id.tvOilDate);
        textView.setText(strDate[position]);

        TextView textView1 = (TextView) view.findViewById(R.id.tvOilMoney);
        textView1.setText(strMoney[position] + " บาท");

        return view;
    }
}

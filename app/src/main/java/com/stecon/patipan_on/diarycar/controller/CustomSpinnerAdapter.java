package com.stecon.patipan_on.diarycar.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stecon.patipan_on.diarycar.R;
import com.stecon.patipan_on.diarycar.model.FuelTypeModel;

import java.util.ArrayList;

/**
 * Created by patipan_on on 2/12/2018.
 */

public class CustomSpinnerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<FuelTypeModel> item;
    private LayoutInflater layoutInflater;


    public CustomSpinnerAdapter(Context context, ArrayList<FuelTypeModel> item) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.custom_spinner_tv, parent, false);
            viewHolder.tvShowSpinnerItem = (TextView) convertView.findViewById(R.id.tv_show_spinner_item);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String strItem = item.get(position).getType_name_th();
        viewHolder.tvShowSpinnerItem.setText(strItem);
        convertView.setTag(viewHolder);
        return convertView;
    }

    public class ViewHolder{
        TextView tvShowSpinnerItem;
    }
}

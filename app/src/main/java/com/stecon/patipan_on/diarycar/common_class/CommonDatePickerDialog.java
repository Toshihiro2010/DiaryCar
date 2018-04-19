package com.stecon.patipan_on.diarycar.common_class;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import com.stecon.patipan_on.diarycar.TripStartActivity;
import com.stecon.patipan_on.diarycar.model.MyDateTimeModify;

/**
 * Created by patipan_on on 4/19/2018.
 */

public class CommonDatePickerDialog {

    private Context context;
    private MyDateTimeModify myDateTimeModify;

    public CustomDatePickerListener pickerListener = null;

    public CommonDatePickerDialog(Context context, MyDateTimeModify myDateTimeModify) {
        this.context = context;
        this.myDateTimeModify = myDateTimeModify;
        setDialog();
    }

    public void setDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myDateTimeModify.setYear(year);
                myDateTimeModify.setMonth(month + 1);
                myDateTimeModify.setDay(dayOfMonth);

                if (pickerListener != null) {
                    pickerListener.onClickListener();
                }
            }
        }, myDateTimeModify.getYear(), myDateTimeModify.getMonth()-1, myDateTimeModify.getDay());
        datePickerDialog.show();
    }


    public interface CustomDatePickerListener {
        void onClickListener();
    }

    public void setRegisterCustomDatePickerListener(CustomDatePickerListener myRegisterCustomDatePickerListener) {
        this.pickerListener = myRegisterCustomDatePickerListener;
    }
}

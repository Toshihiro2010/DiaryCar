package com.stecon.patipan_on.diarycar.common_class;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import com.stecon.patipan_on.diarycar.model.MyDateTimeModify;

/**
 * Created by patipan_on on 4/19/2018.
 */

public class CommonTimePickerDialog {

    private Context context;
    private MyDateTimeModify myDateTimeModify;
    private OnCustomListenerTimePickerDialog customListenerTimePickerDialog = null;

    public CommonTimePickerDialog(Context context, MyDateTimeModify myDateTimeModify) {
        this.context = context;
        this.myDateTimeModify = myDateTimeModify;
        setDialog();
    }

    private void setDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myDateTimeModify.setHour(hourOfDay);
                myDateTimeModify.setMinute(minute);

                if (customListenerTimePickerDialog != null) {
                    customListenerTimePickerDialog.onClickListener();
                }
            }
        }, myDateTimeModify.getHour(), myDateTimeModify.getMinute(), false);
        timePickerDialog.show();
    }

    public interface OnCustomListenerTimePickerDialog{
        void onClickListener();
    }

    public void setRegisterCustomListenerTimePicker(OnCustomListenerTimePickerDialog listenerTimePicker) {
        this.customListenerTimePickerDialog = listenerTimePicker;
    }
}
